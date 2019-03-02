package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.saveinfo.Suggestions
import oxxy.kero.roiaculte.team7.domain.models.Semestre

interface SearchRepository {
     suspend fun getMattersById(executeParams: Int): Either<Failure.ProvideUniversityFailure, List<Semestre>>
    suspend fun getSuggestions(executeParams: String): Either<Failure.ProvideSuggestionFaillure, List<Suggestions>>


}