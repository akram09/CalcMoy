package oxxy.kero.roiaculte.team7.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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
import oxxy.kero.roiaculte.team7.domain.interactors.saveinfo.Suggestions
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.SearchRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SearchRepositoryImpl @Inject constructor(val database: FirebaseDatabase, val auth :FirebaseAuth):SearchRepository {
    override suspend fun getMattersById(executeParams: Int): Either<Failure.ProvideUniversityFailure, List<Semestre>>
    = suspendCoroutine {
        database.reference.child("modules").child("universite").child(executeParams.toString()).addListenerForSingleValueEvent(
            object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("errr", "there is an error")
                    if((p0.code == DatabaseError.NETWORK_ERROR) or (p0.code ==DatabaseError.DISCONNECTED)){
                       it.resume(Either.Left(Failure.ProvideUniversityFailure.NetworkFailure(p0.toException())))
                   }else  it.resume( Either.Left(Failure.ProvideUniversityFailure.UknownFailure(p0.toException())))
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()) {
                        var listSemestre = emptyList<Semestre>()
                        var l = 1
                        for (data in p0.children) {
                            if (data.hasChildren()) {
                                Log.e("errr", data.key)
                                listSemestre += Semestre(
                                    l,
                                    data.children.map {
                                        Log.e("errr", it.key)
                                        Matter(
                                            0, (it.child("name").value as String),
                                            (it.child("coef").value as Long).toInt(),
                                            it.child("color").value as String,
                                            l,
                                            0.0, auth.currentUser?.uid!!
                                        )
                                    }.toMutableList()
                                )
                            }
                        }
                        it.resume(Either.Right(listSemestre))
                    }else it.resume(Either.Left(Failure.ProvideUniversityFailure.UNiversiteDontExiste(Exception())))
                }
            }
        )
    }

    override suspend fun getSuggestions(executeParams: String): Either<Failure.ProvideSuggestionFaillure, List<Suggestions>>
    = suspendCoroutine{
        database.reference.child("modules").child("universite").addListenerForSingleValueEvent(
            object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("errr", "there is an error")
                    if((p0.code == DatabaseError.NETWORK_ERROR) or (p0.code ==DatabaseError.DISCONNECTED)){
                        it.resume(Either.Left(Failure.ProvideSuggestionFaillure.NetworkFailure(p0.toException())))
                    }else it.resume(Either.Left(Failure.ProvideSuggestionFaillure.UknownFailure(p0.toException())))
                }
                override fun onDataChange(p0: DataSnapshot) {
                    var list = emptyList<Suggestions>()
                   for(data in p0.children){
                       val name = (data.child("universiteName").value as String) to (data.child("universiteNameFr").value as String)
                       if(name.first.contains(executeParams.trim()
                           )
                           or name.second.contains(executeParams.trim())){
                               list += Suggestions(
                                   data.key?.toLongOrNull()!!,
                                   name.second,
                                   name.first
                               )
                           }
                   }
                    it.resume(Either.Right(list))
                }
            }
        )
    }
}