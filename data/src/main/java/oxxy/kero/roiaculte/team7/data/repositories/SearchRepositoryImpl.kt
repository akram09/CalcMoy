package oxxy.kero.roiaculte.team7.data.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.repositories.SearchRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SearchRepositoryImpl @Inject constructor(val database: FirebaseDatabase):SearchRepository {
    val subject = BehaviorSubject.create<List<String>>()
    override suspend fun search(executeParams: String): Either<Failure.SearchFailure, None> {
       return  suspendCoroutine {
           Log.e("errr", "we are inside a couroutine")
           database.reference.child("modules").child("universite")
               .addListenerForSingleValueEvent(
               object :ValueEventListener{
                   override fun onCancelled(p0: DatabaseError) {
                       Log.e("errr", "there is an error")
                       if((p0.code == DatabaseError.NETWORK_ERROR) or (p0.code ==DatabaseError.DISCONNECTED)){
                               it.resume(Either.Left(Failure.SearchFailure.NetworkFailure(p0.toException())))
                           }else it.resume(Either.Left(Failure.SearchFailure.UknowFailure(p0.toException())))
                   }
                   override fun onDataChange(p0: DataSnapshot) {
                       Log.e("errr", "there ischange ")
                       subject.onNext(p0.children.map {
                           it.child("universiteName").value as String
                       }.map { it.trim() }.filter {
                           it.contains(executeParams)
                       })
                       it.resume(Either.Right(None()))
                   }
               }
           )
       }
    }

    override fun complete() {
        subject.onComplete()
    }

    override fun observe(): Observable<List<String>> {
        return subject.toFlowable(BackpressureStrategy.DROP).toObservable()
    }
}