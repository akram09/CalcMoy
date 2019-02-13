package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class GetInfoFromRemote  @Inject constructor(dispatchers: CouroutineDispatchers
                                             , val repo :AuthentificationRepository):EitherInteractor<None , None, Failure.GetUserInfoFromRemote> {
    override val dispatcher=dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.GetUserInfoFromRemote, None> {
        return repo.getUserInfoFromRemote()
    }
}