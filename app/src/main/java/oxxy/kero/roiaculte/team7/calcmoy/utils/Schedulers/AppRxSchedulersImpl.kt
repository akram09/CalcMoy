package oxxy.kero.roiaculte.team7.calcmoy.utils.Schedulers

import io.reactivex.Scheduler
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers

data class AppRxSchedulersImpl(override val io :Scheduler, override val main:Scheduler, override val computation:Scheduler ):
    AppRxSchedulers