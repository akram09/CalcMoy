package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class ModulesViewModel @Inject constructor() :BaseViewModel<ModulesState>(ModulesState()) , ModulesCallback {
    init {
        setState {
            copy(modules = Loading())
        }
    }

    override fun doOnSuccess(list: List<Semestre>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doOnFailure(t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}