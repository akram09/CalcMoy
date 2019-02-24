package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import oxxy.kero.roiaculte.team7.calcmoy.base.State
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Semestre

data class MainState (val matterState : Async<None>,
                      val evensAsync: Async<None>,
                      val semestres : List<Semestre>,
                      val events : List<Event>):State