package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Event
import oxxy.kero.roiaculte.team7.calcmoy.utils.Uninitialized
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter

data class ModulesState(
    val isLoading:Boolean= false,
    val whichSemestre:Int =0, val modules:List<Matter> = emptyList() ,
    val size:Int = 0 ,
    val updateModuleEvent :Event<Matter>? = null,
    val addModuleEvent:Event<None>?=null
):State