package oxxy.kero.roiaculte.team7.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import oxxy.kero.roiaculte.team7.domain.models.EventType
import java.util.*
@Entity(tableName = "Events")
data class EventEntity(@PrimaryKey(autoGenerate = true) val id :Long =0, val type: EventType
                       , val time : Date, val place : String, val  matterId : String, val userId : String)