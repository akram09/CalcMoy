package oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.GetUserState
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val getUserState: GetUserState)  : BaseViewModel<SplashState> (SplashState(Loading())){

    fun excuteUserState(){
        scope.launchInteractor(getUserState, None()){ it.either(::handlerFaillure,::handleSuccess)}
    }

    private fun handleSuccess(userState: UserState) { setState { SplashState(Success(userState)) } }

    private fun handlerFaillure(provideUserStateFailure: Failure.ProvideUserStateFailure) { setState { SplashState(Fail(provideUserStateFailure)) } }

}

class SplashState (var state : Async<UserState>): State