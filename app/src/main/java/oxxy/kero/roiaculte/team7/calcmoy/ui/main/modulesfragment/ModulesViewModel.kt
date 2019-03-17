package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Event
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class ModulesViewModel @Inject constructor() :BaseViewModel<ModulesState>(ModulesState()) , ModulesCallback {
   private var semestres = emptyList<Semestre>()

    init {
        setState {
            copy(isLoading = true  )
        }
    }

    override fun doOnSuccess(list: List<Semestre>) {
         withState {
             setState {
                 copy(modules = list[it.whichSemestre].matters , isLoading = false , size = list.size)
             }
         }
        semestres = list
    }

    override fun doOnFailure(t: Throwable) {
        /**
         * dont forget failures
         */
    }

    override fun onSemestreClicked(which: Int) {
        setState {
            copy(whichSemestre = which , modules = semestres[this.whichSemestre].matters , isLoading = false )
        }
    }

    override fun moduleClicked(matter: Matter) {
        setState {
            copy(updateModuleEvent = Event(matter))
        }
    }

    override fun fabClicked() {
        setState {
            copy(addModuleEvent = Event(None()))
        }
    }

    override fun updateMatter(copy: Matter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}