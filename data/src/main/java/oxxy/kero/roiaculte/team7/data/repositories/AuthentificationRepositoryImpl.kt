package oxxy.kero.roiaculte.team7.data.repositories


import oxxy.kero.roiaculte.team7.data.firebase.AuthentificationFirebase
import oxxy.kero.roiaculte.team7.domain.exception.CreatUserFailures
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.RegistrationModel
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository

import javax.inject.Inject

class AuthentificationRepositoryImpl @Inject constructor(private val authentificator: AuthentificationFirebase)
    :AuthentificationRepository{
    override suspend fun registreUser(user: RegistrationModel): Either<CreatUserFailures, None> {
        return authentificator.registreUserWithEmail(user.mail , user.pass)
    }

    override suspend fun signUserOut() {
         authentificator.signUserOut()
    }
}