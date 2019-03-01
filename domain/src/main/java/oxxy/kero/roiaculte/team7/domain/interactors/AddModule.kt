package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class AddModule @Inject constructor(val repo: MainRepository, dispatchers: CouroutineDispatchers): EitherInteractor<Matter ,None , Failure.AddModuleFailure > {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher = dispatchers.main

    override suspend fun invoke(executeParams: Matter): Either<Failure.AddModuleFailure, None> {
        return repo.addModule(executeParams)
    }
}

