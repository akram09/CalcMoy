package oxxy.kero.roiaculte.team7.data.firebase


import com.google.firebase.auth.FirebaseAuth
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthentificationFirebase @Inject constructor(val auth : FirebaseAuth) {

   suspend fun registreUserWithEmail(mail :String , pass :String ):Either<Failure, None>{
       return suspendCoroutine{
            continuation->
           auth.createUserWithEmailAndPassword(mail , pass).addOnCompleteListener {
               task->
               if(task.isSuccessful){
                   continuation.resume(Either.Right(None()))

               }else {
                   continuation.resume(Either.Left(Failure.FirebaseError(task.exception)))
               }

           }

        }
    }
}