package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.SignInCredentielFailure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository

class SignInCredentiel(dispatchers:CouroutineDispatchers, private val repo:AuthentificationRepository) :EitherInteractor<SignInParam, None, SignInCredentielFailure> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: SignInParam): Either<SignInCredentielFailure, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
data class SignInParam(val token :String , val type:Int)