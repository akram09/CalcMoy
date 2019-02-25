package oxxy.kero.roiaculte.team7.data.firebase


import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import oxxy.kero.roiaculte.team7.data.Util.getName
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.data.database.entities.SchoolConverterClass
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity
import oxxy.kero.roiaculte.team7.domain.exception.*
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.UserInfo
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.models.UserState
import java.lang.ref.PhantomReference
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthentificationFirebase @Inject constructor(private val auth : FirebaseAuth, private val database :FirebaseDatabase) {

    suspend fun registreUserWithEmail(mail: String, pass: String): Either<Failure.CreatUserFailures, None> {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Either.Right(None()))

                } else {
                    continuation.resume(
                        Either.Left(
                            when (task.exception!!) {
                                is FirebaseAuthUserCollisionException -> Failure.CreatUserFailures.FirebaseCoalisedUser(
                                    task.exception
                                )
                                is FirebaseAuthWeakPasswordException -> Failure.CreatUserFailures.FirebaseWeakPassword(
                                    task.exception
                                )
                                is FirebaseNetworkException -> Failure.CreatUserFailures.FirebaseNetworkError(task.exception)

                                else -> Failure.CreatUserFailures.FirebaseUknownError(task.exception)
                            }
                        )
                    )
                }
            }
        }
    }

    suspend fun signInUserWithCredentiel(credentiel: AuthCredential): Either<Failure.SignInCredentielFailure, None> {
        return suspendCoroutine { continuation ->
            auth.signInWithCredential(credentiel).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Either.Right(None()))
                } else {
                    continuation.resume(
                        Either.Left(
                            when (task.exception) {
                                is FirebaseAuthInvalidCredentialsException -> Failure.SignInCredentielFailure.SignInInvalidCredentiel(
                                    task.exception
                                )
                                is FirebaseNetworkException -> Failure.SignInCredentielFailure.SignInNetworkError(task.exception)

                                else -> Failure.SignInCredentielFailure.SignInUknownError(task.exception)
                            }
                        )
                    )

                }

            }
        }
    }

    suspend fun logUserIn(mail: String, pass: String): Either<Failure.LoginFailure, None> {
        return suspendCoroutine {
            auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.resume(Either.Right(None()))
                } else {
                    val exception = task.exception
                    it.resume(
                        when (exception) {
                            is FirebaseNetworkException -> Either.Left(Failure.LoginFailure.LoginNetworkError(exception))
                            is FirebaseAuthInvalidCredentialsException -> Either.Left(
                                Failure.LoginFailure.LoginPasswordInvalid(
                                    exception
                                )
                            )
                            else -> {
                                if (exception is FirebaseAuthException) {
                                    if ((exception.errorCode == "ERROR_USER_TOKEN_EXPIRED") or (exception.errorCode == "ERROR_WRONG_PASSWORD")) {
                                        Either.Left(Failure.LoginFailure.LoginPasswordInvalid(exception))
                                    } else if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
                                        Either.Left(Failure.LoginFailure.LoginUsrNotFound(exception))
                                    } else {
                                        Either.Left(Failure.LoginFailure.LoginUknownError(exception))
                                    }
                                } else {
                                    Either.Left(Failure.LoginFailure.LoginUknownError(exception))
                                }
                            }
                        }
                    )

                }

            }
        }
    }

    suspend fun checkUserRemote(id: String): UserEntity? {
        return suspendCoroutine { continuation ->
            val ref = database.reference
            ref.child("users").child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("errr", p0.message)
                    Log.e("errr", p0.code.toString())
                }

                override fun onDataChange(p0: DataSnapshot) {
                    continuation.resume(
                        if (p0.exists()) {
                            UserEntity(
                                id, p0.child("name").value as String,
                                p0.child("prename").value as String, SchoolConverterClass().fromIntToSchool(
                                    (p0.child("school").value as Long).toInt()
                                ), p0.child("year").value as String, (p0.child("semestre").value as Long).toInt(),
                                p0.child("imageUrl").value as String, true,
                                (p0.child("moyenneGenerale").value as? Double)
                                    ?: (p0.child("moyenneGenerale").value as Long).toDouble()
                            )
                        } else null
                    )
                }
            })
        }
    }

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    fun isThereUser(): Boolean {
        return auth.currentUser != null
    }

    fun signUserOut(): String? {
        val id = auth.currentUser?.uid
        auth.signOut()
        return id
    }

    suspend fun provideUser(): Either<Failure.NoUserInfo, UserInfo> {
        return suspendCoroutine {
            var displayName = auth.currentUser?.displayName
            var imageUrl = auth.currentUser?.photoUrl
            if ((displayName == null) or (imageUrl == null) or ((displayName == "") or (imageUrl == Uri.EMPTY))) {
                it.resume(Either.Left(Failure.NoUserInfo()))
            } else {
                if (displayName == null) displayName = ""
                if (imageUrl == null) imageUrl = Uri.EMPTY
                it.resume(
                    Either.Right(
                        UserInfo(
                            displayName.getName().first,
                            displayName.getName().second, imageUrl.toString()
                        )
                    )
                )
            }

        }
    }

    suspend fun getMatter(): Either<Failure.GetUserInfoFromRemote, List<MatterEntity>> =
        suspendCoroutine {
            database.reference.child("users").child(auth.currentUser?.uid!!).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        if((p0.code == DatabaseError.NETWORK_ERROR) or (p0.code ==DatabaseError.DISCONNECTED)){
                            it.resume(Either.Left(Failure.GetUserInfoFromRemote.NetworkFailure(p0.toException())))
                        }else it.resume(Either.Left(Failure.GetUserInfoFromRemote.UknownFAilure(p0.toException())))
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        var listMatter = emptyList<MatterEntity>()
                    for (data in p0.children){
                        if(data.hasChildren()){
                            listMatter += data.children.map {
                                MatterEntity(0 , it.child("name").value as String  ,
                                    (it.child("coifficient").value as Long).toInt()
                                    , it.child("color").value as String ,
                                    (it.child("semestre").value as Long).toInt()
                                    , (it.child("moyenne").value as? Double)
                                        ?: (it.child("moyenne").value as Long).toDouble(),
                                    it.child("userId").value as String )
                            }
                        }
                    }
                        it.resume(Either.Right(listMatter))

            }

        })
}
}