package oxxy.kero.roiaculte.team7.data.database.daos

import android.arch.persistence.room.*
import io.reactivex.Flowable
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.domain.models.Matter
@Dao
interface MatterDao{
    @Insert
    fun insertMatters(materList:List<MatterEntity>)
    @Query("SELECT * FROM Matters  WHERE userId = :id")
    fun getMattersConnected( id:String): List<MatterEntity>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMatter(matter :MatterEntity)
    @Delete
    fun deleteMater(matter: MatterEntity)
    @Query("SELECT * FROM Matters  WHERE userId = :id")
    fun observeConnected( id:String): Flowable<List<MatterEntity>>
}