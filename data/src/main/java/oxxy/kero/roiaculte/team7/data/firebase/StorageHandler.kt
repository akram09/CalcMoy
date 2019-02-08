package oxxy.kero.roiaculte.team7.data.firebase

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class StorageHandler @Inject constructor(val storage:FirebaseStorage ) {


    fun saveFile(uri : Uri, id:String):Observable<Double> {
        return Observable.create {
            observer->
           var  reference = storage.reference.child("images").child("$id.jpg")
            reference.putFile(uri).addOnProgressListener {
                if(it.error!=null) {
                    if (!observer.isDisposed) {
                        observer.onNext((it.bytesTransferred / it.totalByteCount) * 100.0)
                    }
                }else{
                    if(!observer.isDisposed){
                        observer.onError(it.error?.cause!!)
                    }
                }
            }.addOnCompleteListener{
                if(it.isSuccessful){
                    observer.onComplete()
                }else{
                    observer.onError(it.exception?.cause!!)
                }
            }
        }
    }

    suspend fun getUrl(id:String): Either<Failure.SaveImageFailure, String> {
        return suspendCoroutine {
            continuation->
            storage.reference.child("images").child("$id.jpg").downloadUrl
                .addOnCompleteListener {
                if(it.isSuccessful){
                    continuation.resume(Either.Right(it.result!!.toString()))
                }else{
                    continuation.resume(Either.Left(
                        if(it.exception is FirebaseNetworkException) Failure.SaveImageFailure.NetworkFailure(it.exception)
                    else Failure.SaveImageFailure.UknownFailure(it.exception)
                    ))
                }
            }

        }
    }
}