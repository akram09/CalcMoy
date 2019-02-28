package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class MainViewModel @Inject
constructor(val mainInfo : MainGetSemestre) : BaseViewModel<MainState>
    (MainState(Loading(),Loading(), emptyList(), emptyList())) ,
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
        Log.e("errr", mainInfoResult.semestres.size.toString())
//        val matters = ArrayList<Matter>()
//        matters.add(Matter(111,"matter1",1,"#808080",0,10.0,"blabla"))
//        matters.add(Matter(222,"matter2",9,"#808080",0,10.0,"blabla"))
//        matters.add(Matter(333,"matter3",4,"#808080",0,10.0,"blabla"))
//        matters.add(Matter(333,"matter4",5,"#808080",0,10.0,"blabla"))
//        val semestres = ArrayList<Semestre>()
//        semestres.add(Semestre(0,matters))
        setState { copy(semestres = mainInfoResult.semestres,matterState = Success(None())) }
    }

    private fun handleMainInfoFaillure(mainInfoFailure: Failure.MainInfoFailure) {
        Log.e("errr", "hhdh")
        setState { copy(matterState = Fail(mainInfoFailure)) }
    }


    override fun isItFirstTime() : Boolean  = firsrTime

    override fun getCurentSemestre(): Int  = semster

    override fun setCurentSemetre(curent : Int) {semster = curent }
}