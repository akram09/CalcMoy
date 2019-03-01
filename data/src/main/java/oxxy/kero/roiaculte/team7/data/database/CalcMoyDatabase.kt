package oxxy.kero.roiaculte.team7.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.service.autofill.UserData
import oxxy.kero.roiaculte.team7.data.Util.DateConverter
import oxxy.kero.roiaculte.team7.data.Util.EventTypeConverter
import oxxy.kero.roiaculte.team7.data.database.daos.EventsDao
import oxxy.kero.roiaculte.team7.data.database.daos.MatterDao
import oxxy.kero.roiaculte.team7.data.database.daos.NoteDao
import oxxy.kero.roiaculte.team7.data.database.daos.UserDao
import oxxy.kero.roiaculte.team7.data.database.entities.*

@Database(entities = [UserEntity::class, MatterEntity::class  , EventEntity::class , NoteEntity::class], exportSchema = true , version = 1)
@TypeConverters(SchoolConverterClass::class, EventTypeConverter::class  , DateConverter::class)
abstract class CalcMoyDatabase :RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun matterDao() :MatterDao
    abstract fun notedDao():NoteDao
    abstract fun eventDao():EventsDao
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