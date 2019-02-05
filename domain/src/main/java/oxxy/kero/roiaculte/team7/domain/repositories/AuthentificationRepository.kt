package oxxy.kero.roiaculte.team7.domain.repositories

import oxxy.kero.roiaculte.team7.domain.exception.CreatUserFailures
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.exception.SignInCredentielFailure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.RegistrationModel

interface  AuthentificationRepository{
    suspend  fun registreUser(user: RegistrationModel):Either<CreatUserFailures, None>


    suspend fun signUserOut()
    suspend fun signInUserCredentiel(credentiel:String , type:Int):Either<SignInCredentielFailure, None>

}