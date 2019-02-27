package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

typealias  Events =List<Event >
class MainGetEvents @Inject constructor(dispatchers: CouroutineDispatchers , val repo: MainRepository)
    : EitherInteractor<None ,Events , Failure.MainInfoFailure> {
    override val dispatcher = dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.MainInfoFailure, Events> {
        return repo.getMainInfoEents()
    }
}
