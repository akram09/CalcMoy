package oxxy.kero.roiaculte.team7.calcmoy.ui.registration

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.interactors.None

const val  REGISTRATION_SIGNEIN = 0
const val  REGISTRATION_LOGIN = 1

class RegistrationState (val fragment :Int,val signinInfo: SigninInfo,val  loginInfo: LoginInfo ) : State

class SigninInfo (val email: String, val password : String, val repeatPassword:String,var login :  Async<None>?)
class LoginInfo (val email: String, val password : String,var login :  Async<None>?)