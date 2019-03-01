package oxxy.kero.roiaculte.team7.data.Util

import android.util.Log
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

fun List<MatterEntity>.calculateAverage():Double{
    var list = this.deviseToSemestre()
    val listMoyenne = list.map {
        var somme = 0.0
        var sommeCoef =0
        it.matters.forEach {
            somme += it.moyenne*it.coifficient
            sommeCoef +=it.coifficient
            Log.e("errr", somme.toString())
            Log.e("errr", sommeCoef.toString())
        }
        somme/sommeCoef
    }
    var i=0
    var somme =0.0
    for (double in listMoyenne){
        Log.e("errr", double.toString())
        i++
        somme += double
    }
    return somme/i
}
fun List<MatterEntity>.deviseToSemestre():List<Semestre>{
    var i =0
    var list = emptyList<Semestre>()
    do {
        var currentList = this.filter { it.semestre==i }
        i++
        if(!currentList.isEmpty()){
            list +=Semestre(i , currentList.map {
             Matter(it.MatterId ,it.name , it.coifficient , it.color , it.semestre ,
                 it.moyenne,it.userId)
            }.toMutableList())
        }
    }while (!currentList.isEmpty())
    return list
}