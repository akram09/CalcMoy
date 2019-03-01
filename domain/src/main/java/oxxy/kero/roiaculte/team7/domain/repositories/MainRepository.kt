package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.Events
import oxxy.kero.roiaculte.team7.domain.interactors.MainGetSemestreResult
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.UserActif
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User

interface MainRepository {
 fun addUserSyncer(onDisconnected:()->Unit)
 fun removeUserSyncer(onDisconnected: () -> Unit)
 suspend fun getUsersList():Either< Failure.GetUsersFailure, List<User>>
 fun observeUserActif():Observable<UserActif>
suspend fun getMainInfoSemestre():Either<Failure.MainInfoFailure , MainGetSemestreResult>
 suspend fun getMainInfoEents():Either<Failure.MainInfoFailure , Events>
 suspend fun addModule(matter :Matter):Either<Failure.AddModuleFailure, None>
 suspend fun updateMatter(matter: Matter):Either<Failure.UpdateMatterFailure ,None>
}