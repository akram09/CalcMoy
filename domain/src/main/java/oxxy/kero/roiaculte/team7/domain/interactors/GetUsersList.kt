package oxxy.kero.roiaculte.team7.domain.interactors

import io.reactivex.Observable
import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class GetUsersList
    @Inject constructor(schedulers: AppRxSchedulers , dispatchers: CouroutineDispatchers
    , val repo :MainRepository):EitherInteractor<None , List<User>, Failure.GetUsersFailure>
    , ObservableInteractor<UserActif, None>(schedulers)
{
    override val dispatcher = dispatchers.io
    override val ResultDispatcher = dispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.GetUsersFailure, List<User>> {
       return repo.getUsersList()
    }

    override fun buildObservable(p: None): Observable<UserActif> {
      return repo.observeUserActif()
    }
}
data class UserActif(val id:String )