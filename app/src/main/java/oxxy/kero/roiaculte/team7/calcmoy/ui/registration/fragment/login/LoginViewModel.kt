package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.interactors.authentification.LoginParam
import oxxy.kero.roiaculte.team7.domain.interactors.authentification.LoginUseCase
import oxxy.kero.roiaculte.team7.domain.interactors.authentification.SignInCredentiel
import oxxy.kero.roiaculte.team7.domain.interactors.authentification.SignInParam
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val logInUseCase: LoginUseCase,
                                         private val signInCredentiel: SignInCredentiel,
                                         private val userState : ProvideUserState)
    : BaseViewModel<LoginState>(LoginState("","",null)) {


    fun login(email : String ,password :String){
        setState {
            LoginState(this.email,this.password,Loading())
        }
        scope.launchInteractor(logInUseCase,
            LoginParam(email, password)
        ){it.either(::handleFailure,::handleLoginPasst)}
    }

    fun loginInWithCredentil(token : String , type : Int){
        setState {
            LoginState(this.email,this.password,Loading())
        }
        scope.launchInteractor(signInCredentiel,
            SignInParam(token, type)
        ){ it.either(::handleFailure,::handleLoginPasst)}
    }

    private fun handleLoginPasst(none: None) {
        scope.launchInteractor(userState,None()){it.either(::handleFailure,::handleUserState)}
    }

    private fun handleUserState(userState: UserState) {
        setState {
            LoginState(this.email,this.password, Success(userState))
        }
    }

    private fun handleFailure(loginFailure: Failure) {
        setState {
            LoginState(this.email,this.password,Fail(loginFailure))
        }
    }
}