package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject

class SigneInViewModel @Inject constructor(private val signInUseCase: SignInUseCase,
                                           private val signInCredentiel: SignInCredentiel,
                                           private val userState : ProvideUserState )
    : BaseViewModel<SignInState> (SignInState("","","",null)){

    fun signIn(email :String, password : String){
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Loading())
        }
        scope.launchInteractor(signInUseCase, RegistrationModel(email,password)){it.either(::handleSignInFaillure,::handleSignInWithEmailSuccess)}
    }

    fun signInWithCredentil(token : String , type : Int){
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Loading())
        }
        scope.launchInteractor(signInCredentiel, SignInParam(token,type)){ it.either(::handleSignInFaillure,::handleCredentielPasst)}
    }

    private fun handleCredentielPasst(none: None) {
        scope.launchInteractor(userState,None()){it.either(::handleSignInFaillure,::handleUserState)}
    }

    private fun handleUserState(userState: UserState) {
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Success(userState))
        }
    }

    private fun handleSignInWithEmailSuccess(none: None) {
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Success(UserState.USER_REGISTRED_NOT_SAVED))
        }
    }

    private fun handleSignInFaillure(failure: Failure) {
        setState {
            SignInState(this.email,this.password,this.repeatPassword,Fail(failure))
        }
    }
}