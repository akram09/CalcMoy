package oxxy.kero.roiaculte.team7.data.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import oxxy.kero.roiaculte.team7.data.database.entities.EventEntity

@Dao
interface EventsDao {
    @Query("SELECT * FROM Events Where userId = :id")
    fun getEventByUser(id :String ):List<EventEntity>


}