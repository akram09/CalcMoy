package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Uninitialized
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Semestre

data class MoyenneState(
    val data :Async<None> = Uninitialized,
    val imageUrl: String = "",
    val name :String = "",
    val prename :String= "",
    val moyenne :Double= 0.0,
    val semestres :Pair<List<Double>,List<Semestre>>?= null
): State