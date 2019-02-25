package oxxy.kero.roiaculte.team7.data.repositories

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.data.database.LocalData
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.MainInfoResult
import oxxy.kero.roiaculte.team7.domain.interactors.UserActif
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(val auth:FirebaseAuth , val localData: LocalData) :MainRepository{
   var syncer :FirebaseAuth.AuthStateListener?= null
    override fun addUserSyncer(onDisconnected:()->Unit) {
        val lambda :(FirebaseAuth)->Unit = {onDisconnected()}
        syncer = FirebaseAuth.AuthStateListener(lambda)
        auth.addAuthStateListener(syncer!!)
    }
    override fun removeUserSyncer(onDisconnected: () -> Unit){
        auth.removeAuthStateListener { syncer }
    }

    override suspend fun getUsersList(): Either<Failure.GetUsersFailure, List<User>> {
    return localData.getUserList()
    }

    override fun observeUserActif(): Observable<UserActif> {
       return localData.observeConectedUser().map {
           UserActif(it)
       }
    }

    override fun getMainInfo(): Either<Failure.MainInfoFailure, MainInfoResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}