package oxxy.kero.roiaculte.team7.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Matters" ,
    foreignKeys = arrayOf(ForeignKey(entity = UserEntity::class ,parentColumns = arrayOf("Id")
    , childColumns = arrayOf("userId"),onDelete = ForeignKey.CASCADE  )))
data class MatterEntity (@ColumnInfo(name = "Id")
                         @PrimaryKey(autoGenerate = true) val MatterId: Long=0,
                         @ColumnInfo(name="Name")val  name :String,
                         @ColumnInfo(name= "Ceof")val  coifficient :  Int
                         , @ColumnInfo(name= "Color")val  color : String,
                         @ColumnInfo(name="Semestre")val  semestre : Int,
                         @ColumnInfo(name="Moyenne")
                         val  moyenne :Double=0.0,
                         @ColumnInfo(name="userId")
                         val  userId : String)