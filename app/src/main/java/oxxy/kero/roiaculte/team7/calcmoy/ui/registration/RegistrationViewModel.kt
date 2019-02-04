package oxxy.kero.roiaculte.team7.calcmoy.ui.registration

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.RegistrationModel
import oxxy.kero.roiaculte.team7.domain.interactors.SignInUseCase
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(private val signInUseCase: SignInUseCase)
    : BaseViewModel<RegistrationState>(RegistrationState(REGISTRATION_SIGNEIN,
    SigninInfo("","","",null),
    LoginInfo("","",null)
) ){

    fun signIn(email :String, password : String){
        setState {
            RegistrationState(this.fragment,
                SigninInfo(this.signinInfo.email,this.signinInfo.password,this.signinInfo.repeatPassword, Loading( )),
                LoginInfo(this.loginInfo.email,this.loginInfo.password,this.loginInfo.login)
            )
        }

        scope.launchInteractor(signInUseCase, RegistrationModel(email,password)){
            it.either(::handleSignInFaillure,::handleSignInSuccess)
        }
    }

    private fun handleSignInSuccess(none: None) {
        setState {
            RegistrationState(this.fragment,
                SigninInfo(this.signinInfo.email,this.signinInfo.password,this.signinInfo.repeatPassword,Success( none)),
                LoginInfo(this.loginInfo.email,this.loginInfo.password,this.loginInfo.login)
            )
        }
    }

    private fun handleSignInFaillure(failure: Failure) {
        setState {
            RegistrationState(this.fragment,
                SigninInfo(this.signinInfo.email,this.signinInfo.password,this.signinInfo.repeatPassword,Fail(
                    Throwable(failure.toString())
                )),
                LoginInfo(this.loginInfo.email,this.loginInfo.password,this.loginInfo.login)
            )
        }
    }

}