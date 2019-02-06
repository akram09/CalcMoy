package oxxy.kero.roiaculte.team7.domain.repositories


import oxxy.kero.roiaculte.team7.domain.exception.Failure

import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.LoginParam
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.RegistrationModel
import oxxy.kero.roiaculte.team7.domain.models.UserState

interface  AuthentificationRepository{
    suspend  fun registreUser(user: RegistrationModel):Either<Failure.CreatUserFailures, None>


    suspend fun signUserOut()
    suspend fun signInUserCredentiel(credentiel:String , type:Int):Either<Failure.SignInCredentielFailure, None>
    suspend fun loginUser(param: LoginParam ):Either<Failure.LoginFailure, None>
    suspend fun provideUserState():Either<Failure.ProvideUserStateFailure, UserState>

    suspend fun getUserState():Either<Failure.ProvideUserStateFailure, UserState>

}