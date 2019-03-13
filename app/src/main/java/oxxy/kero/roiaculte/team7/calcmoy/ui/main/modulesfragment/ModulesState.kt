package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Uninitialized
import oxxy.kero.roiaculte.team7.domain.models.Matter

data class ModulesState(
    val whichSemestre:Int =0, val modules:Async<List<Matter>> = Uninitialized
):State