package oxxy.kero.roiaculte.team7.data.database.daos

import android.arch.persistence.room.*
import android.service.autofill.UserData
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import oxxy.kero.roiaculte.team7.data.database.entities.ProfileUser
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user :UserEntity)

    @Query("SELECT *FROM User WHERE isConnected =1")
    fun getConnectedUser():List<UserEntity>
    @Query("SELECT *FROM USER WHERE Id = :id")
    fun getUserById(id:String):UserEntity?

    @Query("Update User SET isConnected = :connected WHERE Id = :id")
    fun update(id :String , connected:Boolean)
    @Query("SELECT * FROM User ")
    fun getAllUsers():List<UserEntity>

    @Query("SELECT Id From User WHERE isConnected = 1 ")
    fun getActifUser():Flowable<String >


    @Query("SELECT Id FROM User Where isConnected = 1")
    fun getIDConnectedUser():String


    @Query("SELECT GenMoy ,Prename , Name , ImageUrl , Semestre From User WHERE isConnected = 1  ")
    fun getProfileInfo():ProfileUser
    @Query("UPDATE User SET GenMoy  = :moyene WHERE isConnected = 1")
    fun updateMoyenne( moyene:Double )
}