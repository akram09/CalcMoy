package oxxy.kero.roiaculte.team7.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.service.autofill.UserData
import oxxy.kero.roiaculte.team7.data.database.daos.MatterDao
import oxxy.kero.roiaculte.team7.data.database.daos.UserDao
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.data.database.entities.SchoolConverterClass
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity

@Database(entities = [UserEntity::class, MatterEntity::class ], exportSchema = true , version = 1)
@TypeConverters(SchoolConverterClass::class)
abstract class CalcMoyDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun matterDao() :MatterDao
    companion object {
        private var MyInstance :CalcMoyDatabase? = null
        fun create(context :Context):CalcMoyDatabase?{
            if(MyInstance==null){
                synchronized(CalcMoyDatabase::class ){
                    MyInstance = Room.databaseBuilder(context, CalcMoyDatabase::class.java,"CalcMoyDatabase.db")
                        .allowMainThreadQueries().build()
                }
            }
            return MyInstance
        }

    }



}