package oxxy.kero.roiaculte.team7.data.repositories

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.data.database.LocalData
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor( val auth:FirebaseAuth , val localData: LocalData) :MainRepository{
   var syncer :FirebaseAuth.AuthStateListener?= null
    override fun addUserSyncer(onDisconnected:()->Unit) {
        val lambda :(FirebaseAuth)->Unit = {onDisconnected()}
        syncer = FirebaseAuth.AuthStateListener(lambda)
        auth.addAuthStateListener(syncer!!)
    }
    override fun removeUserSyncer(onDisconnected: () -> Unit){
        auth.removeAuthStateListener { syncer }
    }

    override suspend fun getUsersList(): Either<Failure.DataBaseError, List<User>> {
    return localData.getUserList()
    }

    override fun observeUserActif(): Observable<UserActif> {
       return localData.observeConectedUser().map {
           UserActif(it)
       }
    }

    override suspend fun getMainInfoSemestre(): Either<Failure.MainInfoFailure, MainGetSemestreResult> {
        return localData.getMatterConnected()
    }


    override suspend fun getMainInfoEents(): Either<Failure.MainInfoFailure, Events> {
        return localData.getEvents()
    }

    override suspend fun addModule(matter: Matter): Either<Failure.DataBaseError, None> {
       return localData.addModule(matter)
    }

    override suspend fun updateMatter(matter: Matter): Either<Failure.DataBaseError, None> {
     return localData.updateModule(matter)
    }

    override suspend fun updateEvent(event: Event): Either<Failure.DataBaseError, None> {
      return localData.updateEvent(event)
    }

    override suspend fun addEvent(event: Event): Either<Failure.DataBaseError, None> {
       return localData.addEvent(event)
    }
    override suspend fun getProfileInfo(): Either<Failure.DataBaseError, ProfileUserResult> {
       return localData.getProfileInfo()
    }
}