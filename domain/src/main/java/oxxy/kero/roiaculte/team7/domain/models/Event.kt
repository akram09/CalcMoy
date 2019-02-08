package oxxy.kero.roiaculte.team7.domain.models

import java.util.*


// Matter --> Module (to avoid confusing with dagger Module and Models)
data class Event (val id :String,val type : EventType,val time : Date,val place : String ,val  matterId : String,val userId : String)


