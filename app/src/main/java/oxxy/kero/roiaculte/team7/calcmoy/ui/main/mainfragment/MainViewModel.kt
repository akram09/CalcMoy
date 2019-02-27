package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import javax.inject.Inject

class MainViewModel @Inject constructor(val mainInfo : MainGetSemestre) : BaseViewModel<MainState>(MainState(Loading(),Loading(), emptyList(), emptyList())) ,
    MainFragment.CallbackFromViewModel {
    private var semster : Int = 0
    var firsrTime : Boolean = true

    override fun getMainInfo() {
        firsrTime =false
        scope.launchInteractor(mainInfo, None()){
            it.either(::handleMainInfoFaillure,::handleSemestreSuccess)
        }
        //TODO launch getEventUseCase
    }

    private fun handleSemestreSuccess(mainInfoResult: MainGetSemestreResult) {
        setState { copy(semestres = mainInfoResult.semestres,matterState = Success(None())) }
    }

    private fun handleMainInfoFaillure(mainInfoFailure: Failure.MainInfoFailure) {
        setState { copy(matterState = Fail(mainInfoFailure)) }
    }


    override fun isItFirstTime() : Boolean  = firsrTime

    override fun getCurentSemestre(): Int  = semster

    override fun setCurentSemetre(curent : Int) {semster = curent }
}