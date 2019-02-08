package oxxy.kero.roiaculte.team7.data.firebase

import com.google.firebase.database.FirebaseDatabase
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import javax.inject.Inject

class RemoteDatabase @Inject constructor(database: FirebaseDatabase){
    fun saveUserInfo(user: User, list: List<Semestre>) {
     //todo
    }


}