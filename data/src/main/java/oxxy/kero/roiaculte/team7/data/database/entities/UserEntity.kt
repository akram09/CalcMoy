package oxxy.kero.roiaculte.team7.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import oxxy.kero.roiaculte.team7.domain.models.School
@Entity(tableName = "User")
data class UserEntity(@PrimaryKey @ColumnInfo(name = "Id")val id:String
                      , @ColumnInfo(name= "Name") val name :String,
                      @ColumnInfo(name = "Prename") val prename :String,
                      @ColumnInfo(name= "School")  val school : School,
                      @ColumnInfo(name= "Year")val year :String,
                      @ColumnInfo(name="Semestre")val semestre :Int,
                      @ColumnInfo(name="ImageUrl")val imageUrl :String,
                      val isConnected:Boolean,
                      @ColumnInfo(name="GenMoy")val moyenneGenerale :Double)
data class ProfileUser(
    @ColumnInfo(name= "Name") val name:String ,@ColumnInfo(name = "Prename") val prename :String
    ,@ColumnInfo(name="ImageUrl") val imageUrl:String ,@ColumnInfo(name="Semestre") val semestre: Int ,
    @ColumnInfo(name="GenMoy") val moyenneGenerale: Double )
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
