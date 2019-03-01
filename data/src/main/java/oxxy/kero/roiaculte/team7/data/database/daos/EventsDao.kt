package oxxy.kero.roiaculte.team7.data.database.daos

import android.arch.persistence.room.*
import oxxy.kero.roiaculte.team7.data.database.entities.EventEntity

@Dao
interface EventsDao {
    @Query("SELECT * FROM Events Where userId = :id")
    fun getEventByUser(id :String ):List<EventEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event :EventEntity)
    @Update
    fun updateEvent(event: EventEntity)

}