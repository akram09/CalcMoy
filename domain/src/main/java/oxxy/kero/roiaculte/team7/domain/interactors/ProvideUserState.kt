package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.UserState
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class ProvideUserState @Inject constructor(val repo:AuthentificationRepository, dispatchers: CouroutineDispatchers) :EitherInteractor<None , UserState, Failure.ProvideUserStateFailure> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher= dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.ProvideUserStateFailure, UserState> {
          return repo.provideUserState()
     }
}