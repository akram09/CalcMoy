package oxxy.kero.roiaculte.team7.calcmoy.di.Module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import oxxy.kero.roiaculte.team7.calcmoy.utils.Schedulers.AppCoroutineDispatchers
import oxxy.kero.roiaculte.team7.calcmoy.utils.Schedulers.AppRxSchedulersImpl
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import javax.inject.Singleton
import oxxy.kero.roiaculte.team7.calcmoy.AndroidApplication


@Module
class AppModule {

    @Provides
    @Singleton
      fun provideContext(app:AndroidApplication):Context{
        return app.applicationContext
    }
    @Provides @Singleton
    fun provideSchedulers(): AppRxSchedulers {
        return AppRxSchedulersImpl(
            Schedulers.io()
            , AndroidSchedulers.mainThread(), Schedulers.computation())
    }
    @Provides @Singleton
    fun provideDispatchers(): CouroutineDispatchers {
        return AppCoroutineDispatchers(Dispatchers.IO, Dispatchers.Unconfined, Dispatchers.Main)
    }
}