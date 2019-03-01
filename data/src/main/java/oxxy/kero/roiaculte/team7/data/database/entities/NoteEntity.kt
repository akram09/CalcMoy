package oxxy.kero.roiaculte.team7.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import oxxy.kero.roiaculte.team7.domain.models.EventType
@Entity(tableName = "Notes")
data class NoteEntity (@PrimaryKey(autoGenerate = true)val id :Long =0, val note : Double, val matterId : Int
                       , val eventType: EventType, val precentage : Double)