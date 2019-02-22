package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Event
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
data class MainActivityState(val navigationEvent: Event<WhichFragment>):State

class MainActivityViewModel @Inject constructor(


) : BaseViewModel<MainActivityState>
    (MainActivityState(Event(WhichFragment.MAIN_FRAGMENT))), MainActivityCallback{
    override fun onNavigationBottomClicked(id: Int) {
       setState {
           val event =fromIntToWhichFragment(id)
           if(navigationEvent.peekContent()!=event){
              copy( navigationEvent=Event(event))
           }else{
              this
           }
       }
    }

    override fun init() {
        setState { copy(navigationEvent = Event(navigationEvent.peekContent())) }
    }
}
