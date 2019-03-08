package oxxy.kero.roiaculte.team7.domain.interactors.main

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.SubjectInteractor
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

/**
 * this usecase will have as a job generate observable of a list of semestre and we  use here
 * behaviorsubject just because we need when wo reobserve we got the last emitted data by the observable
 * we will put this usecase in the mainviewmodel and observe it from anywhere
 */
class GetObservableSemestres @Inject constructor(schedulers: AppRxSchedulers ,
                                                 val repository: MainRepository) :SubjectInteractor<List<Semestre> , None>(schedulers) {
    override fun buildObservable(p: None): Observable<List<Semestre>> {
        return repository.getSemestres()
    }
}