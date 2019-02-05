package oxxy.kero.roiaculte.team7.data.firebase


import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import oxxy.kero.roiaculte.team7.domain.exception.*
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthentificationFirebase @Inject constructor(private val auth : FirebaseAuth) {

   suspend fun registreUserWithEmail(mail :String , pass :String ):Either<Failure.CreatUserFailures, None>{
       return suspendCoroutine{
            continuation->
           auth.createUserWithEmailAndPassword(mail , pass).addOnCompleteListener {
               task->
               if(task.isSuccessful){
                   continuation.resume(Either.Right(None()))

               }else {
                   continuation.resume(Either.Left(when(task.exception){
                       is FirebaseAuthUserCollisionException-> Failure.CreatUserFailures.FirebaseCoalisedUser(
                           task.exception
                       )
                       is FirebaseAuthWeakPasswordException -> Failure.CreatUserFailures.FirebaseWeakPassword(
                           task.exception
                       )
                       is FirebaseNetworkException-> Failure.CreatUserFailures.FirebaseNetworkError(task.exception)

                       else -> Failure.CreatUserFailures.FirebaseUknownError(task.exception)
                   }))
               }
           }
        }
    }
    suspend fun signInUserWithCredentiel(credentiel :AuthCredential):Either<Failure.SignInCredentielFailure, None>{
        return suspendCoroutine {
            continuation->
            auth.signInWithCredential(credentiel).addOnCompleteListener{
                task->
                if(task.isSuccessful){
                     continuation.resume(Either.Right(None()))
                }else{
                    continuation.resume(Either.Left(
                        when(task.exception){
                          is  FirebaseAuthInvalidCredentialsException-> Failure.SignInCredentielFailure.SignInInvalidCredentiel(
                              task.exception
                          )
                            is FirebaseNetworkException-> Failure.SignInCredentielFailure.SignInNetworkError(task.exception)

                            else -> Failure.SignInCredentielFailure.SignInUknownError(task.exception)
                        }
                    ))

                }

            }
        }
    }

    suspend fun logUserIn(mail:String  , pass:String):Either<Failure.LoginFailure, None>{
        return suspendCoroutine {
            auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    it.resume(Either.Right(None()))
                }else{
                  val exception = task.exception
                    it.resume(
                        when(exception){
                            is FirebaseNetworkException-> Either.Left(Failure.LoginFailure.LoginNetworkError(exception))
                            is FirebaseAuthInvalidCredentialsException-> Either.Left(Failure.LoginFailure.LoginPasswordInvalid(exception))
                            else->{
                                 if(exception is FirebaseAuthException){
                                   if((exception.errorCode == "ERROR_USER_TOKEN_EXPIRED")or (exception.errorCode == "ERROR_WRONG_PASSWORD"))
                                      {
                                       Either.Left(Failure.LoginFailure.LoginPasswordInvalid(exception))
                                   }else if(exception.errorCode=="ERROR_USER_NOT_FOUND"){
                                       Either.Left(Failure.LoginFailure.LoginUsrNotFound(exception))
                                   }else{
                                       Either.Left(Failure.LoginFailure.LoginUknownError(exception))
                                   }
                                 }else{
                                     Either.Left(Failure.LoginFailure.LoginUknownError(exception))
                                 }
                            }
                        }
                    )

                }

            }
        }
    }
   fun signUserOut(){
        auth.signOut()
    }
}