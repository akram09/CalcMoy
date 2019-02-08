package oxxy.kero.roiaculte.team7.data.database

import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import javax.inject.Inject

class LocalData @Inject constructor(database: CalcMoyDatabase){
    fun saveUserInfo(user: User, list: List<Semestre>) {
     //todo
    }


}