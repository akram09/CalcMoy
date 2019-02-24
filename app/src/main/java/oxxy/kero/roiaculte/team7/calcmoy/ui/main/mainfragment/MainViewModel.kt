package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel<MainState>(MainState(Loading(),Loading(), emptyList(), emptyList())) {
    private var semster : Int = 0
    var fisrTime : Boolean = true

}