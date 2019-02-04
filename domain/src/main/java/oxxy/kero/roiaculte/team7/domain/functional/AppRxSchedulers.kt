package oxxy.kero.roiaculte.team7.domain.functional

import io.reactivex.Scheduler

interface AppRxSchedulers {
     val io : Scheduler
     val main :Scheduler
    val computation:Scheduler
}