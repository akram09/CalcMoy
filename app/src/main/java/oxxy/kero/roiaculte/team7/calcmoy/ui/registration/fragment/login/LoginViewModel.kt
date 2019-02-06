package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val logInUseCase: LoginUseCase,private val signInCredentiel: SignInCredentiel)
    : BaseViewModel<LoginState>(LoginState("","",null)) {


    fun login(email : String ,password :String){
        setState {
            LoginState(this.email,this.password,Loading())
        }
        scope.launchInteractor(logInUseCase, LoginParam(email,password)){it.either(::handleFailure,::handleLoginPasst)}
    }

    fun loginInWithCredentil(token : String , type : Int){
        setState {
            LoginState(this.email,this.password,Loading())
        }
        scope.launchInteractor(signInCredentiel, SignInParam(token,type)){ it.either(::handleFailure,::handleLoginPasst)}
    }

    private fun handleLoginPasst(none: None) {
        //TODO excute userState use case
    }

    private fun handleFailure(loginFailure: Failure) {
        setState {
            LoginState(this.email,this.password,Fail(loginFailure))
        }
    }
}