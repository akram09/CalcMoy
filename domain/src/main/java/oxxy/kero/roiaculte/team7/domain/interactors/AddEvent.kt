package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class AddEvent @Inject constructor(dispatchers: CouroutineDispatchers , val repo :MainRepository)
    : EitherInteractor<Event, None , Failure.DataBaseError> {
    override val dispatcher = dispatchers.io
    override val ResultDispatcher =dispatchers.main
    override suspend fun invoke(executeParams: Event): Either<Failure.DataBaseError, None> {
         return repo.addEvent(executeParams)
    }
}