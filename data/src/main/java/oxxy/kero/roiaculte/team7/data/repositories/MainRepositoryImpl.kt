package oxxy.kero.roiaculte.team7.data.repositories

import com.google.firebase.auth.FirebaseAuth
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(val auth:FirebaseAuth) :MainRepository{
   var syncer :FirebaseAuth.AuthStateListener?= null
    override fun addUserSyncer(onDisconnected:()->Unit) {
        val lambda :(FirebaseAuth)->Unit = {onDisconnected()}
        syncer = FirebaseAuth.AuthStateListener(lambda)
        auth.addAuthStateListener(syncer!!)
    }
    override fun removeUserSyncer(onDisconnected: () -> Unit){
        auth.removeAuthStateListener { syncer }
    }
}