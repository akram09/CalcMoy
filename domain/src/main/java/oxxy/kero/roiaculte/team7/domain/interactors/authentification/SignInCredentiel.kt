package oxxy.kero.roiaculte.team7.domain.interactors.authentification

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class SignInCredentiel @Inject constructor(dispatchers:CouroutineDispatchers, private val repo:AuthentificationRepository) :
    EitherInteractor<SignInParam, None, Failure.SignInCredentielFailure> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: SignInParam): Either<Failure.SignInCredentielFailure, None>
      =  repo.signInUserCredentiel(executeParams.token,executeParams.type)

}
data class SignInParam(val token :String , val type:Int)