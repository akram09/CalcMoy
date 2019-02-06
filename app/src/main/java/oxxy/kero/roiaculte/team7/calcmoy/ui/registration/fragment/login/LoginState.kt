package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.models.UserState

data class LoginState (var email :String ,var password :String ,var state : Async<UserState>?) : State