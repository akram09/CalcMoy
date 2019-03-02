package oxxy.kero.roiaculte.team7.data.Util

import oxxy.kero.roiaculte.team7.data.database.entities.EventEntity
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Matter

fun Matter.toMatterEntityWithId():MatterEntity{
    return MatterEntity(this.id , this.name , this.coifficient , this.color , this.semestre , this.moyenne , this.userId)
}
fun Matter.toMatterEntity():MatterEntity = MatterEntity(name = this.name, coifficient = this.coifficient , color = this.color,
    semestre = this.semestre , moyenne =  this.moyenne ,userId = this.userId)
fun MatterEntity.toMatter():Matter= Matter(this.MatterId , this.name, this.coifficient , this.color , this.semestre , this.moyenne , this.userId)
fun EventEntity.toEvent()=
    Event(this.id , this.type, this.time , this.place , this.matterId , this.userId)
fun Event.toEventEntity()=  EventEntity(type = this.type ,time= this.time , place = this.place , matterId = this.matterId ,userId = this.userId)
fun Event.toEventEntityWithId()= EventEntity(this.id , this.type ,this.time, this.place , this.matterId , this.userId)