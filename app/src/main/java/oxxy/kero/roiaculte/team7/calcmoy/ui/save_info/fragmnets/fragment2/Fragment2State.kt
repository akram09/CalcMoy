package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Semestre

data class Fragment2State (val mattersState : Async<None> ,val semestres : List<Semestre>, val image :Image?,val saveInfoState : Async<None>) : State