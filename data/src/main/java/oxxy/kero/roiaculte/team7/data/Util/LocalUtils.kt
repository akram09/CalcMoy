package oxxy.kero.roiaculte.team7.data.Util

import oxxy.kero.roiaculte.team7.data.disk.DataMatter
import oxxy.kero.roiaculte.team7.data.disk.year
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre

fun Map<School, Array<year>>.getMoulesListe(school :School  , year :Int, id:String ): List<Semestre>{
   var list = emptyList<Semestre>()
    for ( i in 1..3 ){
        list +=  Semestre(i , this[school]!![year-1].map {
            Matter("", it.name, it.coifficient, it.color, i, 0.0, id)
        })
    }
    return list
}
fun Map<FacultyType , Array<DataMatter>>.getModuleList(faculteType:FacultyType, id:String):List<Semestre>{
    var list = emptyList<Semestre>()
    for (i in 1..3){
        list += Semestre(i , this[faculteType]!!.map {
            matter->
            Matter("", matter.name  ,matter.coifficient, matter.color, i , 0.0, id)
        })
    }
    return list
}
fun String.getName():Pair<String , String>{
    if(this.isEmpty()) return Pair("", "")
    val index = this.indexOf(" ", ignoreCase = true)
    if(index<0) return Pair(this , "")
    return Pair(this.substring(0, index), this.substring(index , this.length))
}
