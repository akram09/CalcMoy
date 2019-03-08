package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.interactors.main.Events
import oxxy.kero.roiaculte.team7.domain.interactors.main.MainGetSemestreResult
import oxxy.kero.roiaculte.team7.domain.interactors.profile.ProfileUserResult
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User

interface MainRepository {
 fun addUserSyncer(onDisconnected:()->Unit)
 fun removeUserSyncer(onDisconnected: () -> Unit)
 suspend fun getUsersList():Either< Failure.DataBaseError, List<User>>
 fun observeUserActif():Observable<UserActif>
suspend fun getMainInfoSemestre():Either<Failure.MainInfoFailure , MainGetSemestreResult>
 suspend fun getMainInfoEents():Either<Failure.MainInfoFailure , Events>
 suspend fun addModule(matter :Matter):Either<Failure.DataBaseError, None>
 suspend fun updateMatter(matter: Matter):Either<Failure.DataBaseError ,None>
 suspend fun updateEvent(event : Event) :Either<Failure.DataBaseError ,None>
 suspend fun addEvent(event :Event) :Either<Failure.DataBaseError ,None>
  suspend  fun getProfileInfo():Either<Failure.DataBaseError , ProfileUserResult>
  suspend fun deleteMatter(matter :Matter):Either<Failure.DataBaseError , None>
 fun getSemestres():Observable<List<Semestre>>
}