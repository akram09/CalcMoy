package oxxy.kero.roiaculte.team7.domain.interactors.saveinfo

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class ProvideUserInfo  @Inject constructor(val repo :AuthentificationRepository, dispatchers: CouroutineDispatchers) :
    EitherInteractor<None, UserInfo, Failure.NoUserInfo> {
    override val dispatcher =dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.NoUserInfo, UserInfo> {
        return repo.provideUserInfo()
    }
}
data class UserInfo(val username :String, val prename:String , val ImageUrl:String)