package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.UserActif
import oxxy.kero.roiaculte.team7.domain.models.User

interface MainRepository {
 fun addUserSyncer(onDisconnected:()->Unit)
 fun removeUserSyncer(onDisconnected: () -> Unit)
 suspend fun getUsersList():Either< Failure.GetUsersFailure, List<User>>
 fun observeUserActif():Observable<UserActif>
// fun getMainInfo():Either<Failure.MainInfoFailure , MainInfoResult>
}