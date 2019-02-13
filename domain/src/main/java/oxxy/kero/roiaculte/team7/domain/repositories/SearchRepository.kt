package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.Suggestions
import oxxy.kero.roiaculte.team7.domain.models.Semestre

interface SearchRepository {
    suspend fun search(executeParams: String): Either<Failure.SearchFailure, None>
     fun observe(): Observable<List<String>>
    fun complete()
     fun getMattersById(executeParams: Int): Either<Failure.ProvideUniversityFailure, List<Semestre>>
    suspend fun getSuggestions(executeParams: String): Either<Failure.ProvideSuggestionFaillure, List<Suggestions>>


}