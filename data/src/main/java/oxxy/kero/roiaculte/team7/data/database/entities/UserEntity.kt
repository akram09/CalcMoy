package oxxy.kero.roiaculte.team7.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import oxxy.kero.roiaculte.team7.domain.models.School
@Entity(tableName = "User")
data class UserEntity(@PrimaryKey @ColumnInfo(name = "UserId")val id:String
                      , @ColumnInfo(name= "UserName") val name :String,
                      @ColumnInfo(name = "UserPrename") val prename :String,
                      @ColumnInfo(name= "UserSchool")  val school : School,
                      @ColumnInfo(name= "UserYear")val year :String,
                      val semestre :Int,
                      val imageUrl :String,
                      val isConnected:Boolean,
                      val moyenneGenerale :Double)
class SchoolConverterClass{
    @TypeConverter
    fun fromIntToSchool(entier:Int):School{
        return when(entier){
            0-> School.PRIMAIRE
            1-> School.CEM
            2->School.LYCEE
            3->School.UNIVERSITE
            else->{
                School.PRIMAIRE
            }
        }
    }
    @TypeConverter
    fun fromSchoolToInt(school: School):Int {
        return when(school){
            School.PRIMAIRE->0
            School.CEM->1
            School.LYCEE->2
            School.UNIVERSITE->3

        }
    }
}
