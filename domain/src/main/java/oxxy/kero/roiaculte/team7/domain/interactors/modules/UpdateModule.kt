package oxxy.kero.roiaculte.team7.domain.interactors.modules

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class UpdateModule @Inject constructor(dispatchers: CouroutineDispatchers , val repo :MainRepository)
    :
    EitherInteractor<Matter, None, Failure.DataBaseError> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: Matter): Either<Failure.DataBaseError, None> {
       return repo.updateMatter(executeParams)
    }
}