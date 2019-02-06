package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.models.UserState

class SignInState(var email :String, var password : String, var repeatPassword : String, var  state : Async<UserState?>?) : State
