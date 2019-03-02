package oxxy.kero.roiaculte.team7.domain.interactors.saveinfo

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.ObservableCompleteInteractor
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.repositories.DataModelingRepository
import javax.inject.Inject

class SaveUser  @Inject constructor(schedulers: AppRxSchedulers, dispatchers: CouroutineDispatchers
                                    ,private val repo:DataModelingRepository)
    : ObservableCompleteInteractor<Double, ByteArray>(schedulers)
    ,
    EitherInteractor<SaveUserParam, None, Failure.SaveUserFailure> {

    override val dispatcher= dispatchers.io
    override val ResultDispatcher=dispatchers.main
    override suspend fun invoke(executeParams: SaveUserParam): Either<Failure.SaveUserFailure, None> {
      return repo.saveUser(executeParams)
    }

    override fun buildObservable(p: ByteArray): Observable<Double> {
     return repo.updateFile(p)
    }
}
data class SaveUserParam(val hasSumbitImage:Boolean ,val user:User, val list:List<Semestre>)