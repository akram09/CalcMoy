package oxxy.kero.roiaculte.team7.data.database

import android.arch.persistence.room.RoomDatabase
import android.database.sqlite.SQLiteException
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocalData @Inject constructor(val database: CalcMoyDatabase){
    suspend  fun saveUserInfo(user: User, list: List<Semestre>) :Either<Failure.SaveUserFailure.DataBaseFailure, None> =
        suspendCoroutine {
      //TODO Dont forget to add the ids
            try {
         database.userDao().addUser(UserEntity(user.id, user.name , user.prename , user.school ,
             user.year , user.semstre , user.imageUrl , true , user.moyenneGenerale))
            var list1 :List<Matter> = emptyList()
            for (semestre in list ){
                 list1 +=semestre.matters
            }
            database.matterDao().insertMatters(list1.map {
                MatterEntity(name =  it.name ,coifficient =  it.coifficient ,color =  it.color
                    ,semestre =  it.semestre ,moyenne = it.moyenne ,userId =  it.userId)
            })
     }catch (e:SQLiteException){
          it.resume(Either.Left(Failure.SaveUserFailure.DataBaseFailure(e)))
     }finally {
         it.resume(Either.Right(None()))
     }
    }

    fun getMatters():List<MatterEntity>{
        return database.matterDao().getModulesByUserId()
    }
}