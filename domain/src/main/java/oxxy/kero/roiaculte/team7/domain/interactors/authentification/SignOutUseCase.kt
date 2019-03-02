package oxxy.kero.roiaculte.team7.domain.interactors.authentification

import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.interactors.Interactor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(val repo: AuthentificationRepository , dispatchers:CouroutineDispatchers) :
    Interactor<None> {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher= dispatchers.main


    override suspend fun invoke(executeParams: None) {
      repo.signUserOut()
    }
}