package oxxy.kero.roiaculte.team7.data.firebase


import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import oxxy.kero.roiaculte.team7.domain.exception.*
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthentificationFirebase @Inject constructor(private val auth : FirebaseAuth) {

   suspend fun registreUserWithEmail(mail :String , pass :String ):Either<CreatUserFailures, None>{
       return suspendCoroutine{
            continuation->
           auth.createUserWithEmailAndPassword(mail , pass).addOnCompleteListener {
               task->
               if(task.isSuccessful){
                   continuation.resume(Either.Right(None()))

               }else {
                   continuation.resume(Either.Left(when(task.exception){
                       is FirebaseAuthUserCollisionException-> FirebaseCoalisedUser(
                           task.exception
                       )
                       is FirebaseAuthWeakPasswordException -> FirebaseWeakPassword(
                           task.exception
                       )
                       is FirebaseNetworkException-> FirebaseNetworkError(task.exception)

                       else -> FirebaseUknownError(task.exception)
                   }))
               }
           }
        }
    }

   fun signUserOut(){
        auth.signOut()
    }
}