package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class Fragment2State (val semestres : Async<List<Semestre>>,val image : Image?,val curentSemestre : Int) : State