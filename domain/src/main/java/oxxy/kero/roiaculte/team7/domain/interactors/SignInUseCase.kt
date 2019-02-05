package oxxy.kero.roiaculte.team7.domain.interactors

import oxxy.kero.roiaculte.team7.domain.exception.CreatUserFailures
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(dispatchers: CouroutineDispatchers,
                                        private val authRepository :AuthentificationRepository)
    : EitherInteractor<RegistrationModel, None, CreatUserFailures>{
    override val ResultDispatcher = dispatchers.main
    override val dispatcher = dispatchers.computaion

    override suspend fun invoke(executeParams: RegistrationModel): Either<CreatUserFailures, None> {
        return authRepository.registreUser(executeParams)
    }
}
data class RegistrationModel(val mail :String , val pass :String )