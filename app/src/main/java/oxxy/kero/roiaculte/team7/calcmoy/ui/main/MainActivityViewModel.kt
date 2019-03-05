package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.eventsfragment.EventsFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment.MainFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment.ModulesFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment.MoyenneFragment
import oxxy.kero.roiaculte.team7.calcmoy.utils.*
import oxxy.kero.roiaculte.team7.data.database.LocalData
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.GetUsersList
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.main.MainGetSemestre
import oxxy.kero.roiaculte.team7.domain.interactors.main.MainGetSemestreResult
import javax.inject.Inject

enum class WhichFragment {
    MAIN_FRAGMENT , MODULES_FRAGMENT , EVENTS_FRAGMENT , MOYENNE_FRAGMENT;
}
fun fromIntToWhichFragment(id:Int):WhichFragment{
    return when(id){
        0-> WhichFragment.MAIN_FRAGMENT
        1-> WhichFragment.MODULES_FRAGMENT
        2-> WhichFragment.EVENTS_FRAGMENT
        3-> WhichFragment.MOYENNE_FRAGMENT
        else-> WhichFragment.MAIN_FRAGMENT
    }
}
data class MainActivityState(val navigationFragment:Event<Pair<Int  , BaseFragment>> =
                                 Event(R.string.main to  MainFragment.getInstance()),
                             val showAddMenu: Event<Boolean> = Event(true )
):State

class MainActivityViewModel @Inject constructor(
    val getSemestre: MainGetSemestre
//    val getUsersList: GetUsersList
) : BaseViewModel<MainActivityState>
    (MainActivityState()), MainActivityCallback{
     var semestres :MutableLiveData<Async<MainGetSemestreResult>>
             =MutableLiveData()

     private var pastInt  = 0
    override fun onNavigationBottomClicked(id: Int) {
       setState {
           val event =fromIntToWhichFragment(id)
           Log.e("errr", event.toString())

           val pastEvent= fromIntToWhichFragment(pastInt)

           pastInt = id
           Log.e("errr", pastEvent.toString())

           if(pastEvent!=event){
               val fragment = when(event){
                   WhichFragment.MAIN_FRAGMENT-> R.string.main to MainFragment.getInstance()
                   WhichFragment.MODULES_FRAGMENT->R.string.module to ModulesFragment.getInstance()
                   WhichFragment.EVENTS_FRAGMENT->R.string.evnts to EventsFragment.getInstance()
                   WhichFragment.MOYENNE_FRAGMENT->R.string.moyene to MoyenneFragment.getInstance()
               }

               if(pastEvent==WhichFragment.MAIN_FRAGMENT ){
                   copy( showAddMenu =  Event(false )  , navigationFragment = Event(fragment))
               }else if( (pastEvent!=WhichFragment.MAIN_FRAGMENT)  &&(event==WhichFragment.MAIN_FRAGMENT)){
                       copy(showAddMenu = Event(true ) , navigationFragment = Event(fragment))
                   }else {
                   copy(  navigationFragment = Event(fragment))
               }

           }else{
              this
           }
       }
    }

    override fun init() {
        setState { copy(navigationFragment = Event(navigationFragment.peekContent().first
                    to  navigationFragment.peekContent().second)) }
    }

    override fun getShowAddButton() :Boolean {
      return  state.value?.showAddMenu?.peekContent()!!
    }
    fun update(){
        scope.launchInteractor(getSemestre , None()){
            it.either({
                semestres.value = Fail(it)
                Log.e("errr", "errrr")
            }, {
                semestres.value =Success(it)
                Log.e("errr", "erooroor")
            })
        }
    }
}
