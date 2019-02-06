package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import javax.inject.Inject

class SigneInViewModel @Inject constructor(private val signInUseCase: SignInUseCase, private val signInCredentiel: SignInCredentiel)
    : BaseViewModel<SignInState> (SignInState("","","",null)){

    fun signIn(email :String, password : String){
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Loading())
        }
        scope.launchInteractor(signInUseCase, RegistrationModel(email,password)){it.either(::handleSignInFaillure,::handleSignInSuccess)}
    }

    fun signInWithCredentil(token : String , type : Int){
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Loading())
        }
        scope.launchInteractor(signInCredentiel, SignInParam(token,type)){ it.either(::handleSignInFaillure,::handleCredentielPasst)}
    }

    private fun handleCredentielPasst(none: None) {
        //TODO excute getUserState
    }

    private fun handleSignInSuccess(none: None) {
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Success(null))
        }
    }

    private fun handleSignInFaillure(failure: Failure) {
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Fail(failure))
        }
    }
}