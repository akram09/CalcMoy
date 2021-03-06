package oxxy.kero.roiaculte.team7.data.Util

import android.util.Log
import oxxy.kero.roiaculte.team7.data.disk.DataMatter
import oxxy.kero.roiaculte.team7.data.disk.year
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre

fun Map<School, Array<year>>.getMoulesListe(school :School  , year :Int, id:String ): List<Semestre>{
   var list = emptyList<Semestre>()
    for ( i in 1..3 ){
        Log.v("fucking_error", "errjrr")
        Log.v("fucking_error", year.toString())
        list +=  Semestre(i , this[school]!![year].map {
            Matter(0, it.name, it.coifficient, it.color, i-1, 0.0, id)
        }.toMutableList())
    }
    return list
}
fun Map<FacultyType , Array<DataMatter>>.getModuleList(faculteType:FacultyType, id:String):List<Semestre>{
    val list = emptyList<Semestre>().toMutableList()
    for (i in 1..3){
        list += Semestre(i , this[faculteType]!!.map {
            matter->
            Matter(0, matter.name  ,matter.coifficient, matter.color, i-1 , 0.0, id)
        }.toMutableList())
    }
    return list
}
fun String.getName():Pair<String , String>{
    if(this.isEmpty()) return Pair("", "")
    val index = this.indexOf(" ", ignoreCase = true)
    if(index<0) return Pair(this , "")
    return Pair(this.substring(0, index), this.substring(index , this.length))
}
