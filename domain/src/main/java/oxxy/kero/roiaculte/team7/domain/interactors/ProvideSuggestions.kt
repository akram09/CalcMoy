package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either

class ProvideSuggestions  : EitherInteractor<String,List<Suggestions>,Failure.ProvideSuggestionFaillure>{

    override val dispatcher: CoroutineDispatcher
        get() = TODO("not implemented")
    override val ResultDispatcher: CoroutineDispatcher
        get() = TODO("not implemented")

    override suspend fun invoke(executeParams: String): Either<Failure.ProvideSuggestionFaillure, List<Suggestions>> {
        TODO("not implemented")
    }
}


data class Suggestions(val id : Long , val nameFR : String , val nameAR : String)