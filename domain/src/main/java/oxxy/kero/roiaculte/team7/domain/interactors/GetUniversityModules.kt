package oxxy.kero.roiaculte.team7.domain.interactors

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.SearchRepository
import javax.inject.Inject

class GetUniversityModules @Inject constructor(schedulers: AppRxSchedulers,dispatchers:CouroutineDispatchers ,val repo :SearchRepository )
    : ObservableInteractor<List<String>, None>(schedulers) ,EitherInteractor<String , None , Failure.SearchFailure> {
    override val dispatcher = dispatchers.io
    override val ResultDispatcher= dispatchers.main
    override suspend fun invoke(executeParams: String): Either<Failure.SearchFailure, None> {
       return repo.search(executeParams)
    }

    override fun buildObservable(p: None): Observable<List<String>> {
        return repo.observe()
    }
    fun completeListeninng(){
        repo.complete()
    }

}