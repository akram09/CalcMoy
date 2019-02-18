package oxxy.kero.roiaculte.team7.domain.interactors

import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class AddUserToSync @Inject constructor(val repo:MainRepository){
     fun syncUser(onDisconnected: ()->Unit ){
        repo.addUserSyncer(onDisconnected)
    }
    fun removeSyncer(onDisconnected: () -> Unit){
        repo.removeUserSyncer(onDisconnected)
    }
}