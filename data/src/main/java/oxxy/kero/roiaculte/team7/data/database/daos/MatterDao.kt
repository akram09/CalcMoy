package oxxy.kero.roiaculte.team7.data.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.domain.models.Matter
@Dao
interface MatterDao{
    @Insert
    fun insertMatters(materList:List<MatterEntity>)
    @Query("SELECT * FROM Matters ")
    fun getModulesByUserId():List<MatterEntity>
    @Query("SELECT * FROM Matters  WHERE userId = :id")
    fun getMattersConnected( id:String): List<MatterEntity>
}