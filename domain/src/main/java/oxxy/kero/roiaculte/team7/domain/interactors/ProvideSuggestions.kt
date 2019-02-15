package oxxy.kero.roiaculte.team7.domain.interactors

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.SearchRepository
import javax.inject.Inject

class ProvideSuggestions @Inject constructor(dispatchers: CouroutineDispatchers, val repo :SearchRepository)   : EitherInteractor<String,List<Suggestions>,Failure.ProvideSuggestionFaillure>{

    override val dispatcher = dispatchers.io
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.ProvideSuggestionFaillure, List<Suggestions>> {
        return repo.getSuggestions(executeParams)
    }
}


data class Suggestions(val id : Long , val nameFR : String , val nameAR : String)