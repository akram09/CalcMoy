package oxxy.kero.roiaculte.team7.data.firebase

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.roundToLong
@Singleton
class StorageHandler @Inject constructor(val storage:FirebaseStorage ) {


    fun saveFile(bytes :ByteArray, id:String):Observable<Double> {
        return Observable.create {
            observer->
           val  reference = storage.reference.child("images").child("$id.jpg")
            reference.putBytes(bytes).addOnProgressListener {
                if(it.error==null) {
                    if (!observer.isDisposed) {
                        observer.onNext(((it.bytesTransferred.toDouble() /
                                it.totalByteCount.toDouble()) * 100.0).roundToLong().toDouble())
                    }
                }else{
                    if(!observer.isDisposed){
                        observer.onError(it.error?.cause!!)
                    }
                }
            }.addOnCompleteListener{
                if(!observer.isDisposed) {
                    if (it.isSuccessful) observer.onComplete()
                    else observer.onError(it.exception?.cause ?: Throwable())
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