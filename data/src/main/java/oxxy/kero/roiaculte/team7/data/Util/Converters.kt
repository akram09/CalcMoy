package oxxy.kero.roiaculte.team7.data.Util

import android.arch.persistence.room.TypeConverter
import android.net.wifi.WifiManager
import oxxy.kero.roiaculte.team7.domain.models.EventType
import java.util.*

class EventTypeConverter{
     @TypeConverter
    fun fromIntToEventType(entier:Int ): EventType{
        return when(entier){
            0->EventType.EXAMEN
            1->EventType.DEVOIR
            2->EventType.AUTRE
            else-> EventType.EXAMEN
        }
    }
    @TypeConverter
    fun fromEventTypeToInt(eventType: EventType):Int{
        return when(eventType){
            EventType.EXAMEN->0
            EventType.AUTRE->2
            EventType.DEVOIR->1
        }
    }
}
class DateConverter{
    @TypeConverter
    fun fromLongToDate(long : Long): Date{
        return Date(long)
    }
    @TypeConverter
    fun fromDateToLong(date :Date): Long{
        return date.time
    }
}