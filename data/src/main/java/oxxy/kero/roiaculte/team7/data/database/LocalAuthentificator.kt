package oxxy.kero.roiaculte.team7.data.database

import android.util.Log
import io.reactivex.Completable
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.rx2.await

import oxxy.kero.roiaculte.team7.data.database.daos.UserDao
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocalAuthentificator @Inject constructor(val database: CalcMoyDatabase) {
    val dao:UserDao = database.userDao()

    suspend fun addUserDao(user : UserEntity){
        Completable.fromAction{
            dao.addUser(UserEntity(user.id, user.name, user.prename, user.school, user.year, user.semestre, user.imageUrl,
                true , user.moyenneGenerale))
        }
        .await()
    }
    suspend  fun updateUser(id:String , isConnected:Boolean ){
        Completable.fromAction {
            dao.update(id, isConnected)
        }
        .await()
    }
     fun getConnectedUser():List<UserEntity>{
        return dao.getConnectedUser()
    }
     suspend fun getUserById(id:String):UserEntity?{
        return suspendCoroutine { val  user = dao.getUserById(id)
        it.resume(user)}
    }
     fun provideUserState():Either<Failure.ProvideUserStateFailure, UserState>{
       return if(getConnectedUser().isEmpty()){
           Log.e("errr", "entered")
          Either.Right(UserState.USER_REGISTRED_NOT_SAVED)
       }else Either.Right(UserState.USER_REGISTRED_SAVED)
    }
    suspend fun signOut(id:String? ){
        if(id!=null)
       Completable.fromAction{ dao.update(id , false)

       }.await()
    }
    suspend fun addMatters(list: List<MatterEntity>){
        Completable.fromAction{
            database.matterDao().insertMatters(list)
        }.await()
    }


}