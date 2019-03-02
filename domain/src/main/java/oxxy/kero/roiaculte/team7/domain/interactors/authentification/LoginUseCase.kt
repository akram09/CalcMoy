package oxxy.kero.roiaculte.team7.domain.interactors.authentification

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repo :AuthentificationRepository, dispatchers: CouroutineDispatchers) :
    EitherInteractor<LoginParam, None, Failure.LoginFailure> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: LoginParam): Either<Failure.LoginFailure, None> {
        return repo.loginUser(executeParams)
    }
}
data class LoginParam(val mail:String , val password:String)