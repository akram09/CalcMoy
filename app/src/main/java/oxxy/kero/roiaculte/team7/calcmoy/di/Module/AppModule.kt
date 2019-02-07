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
import oxxy.kero.roiaculte.team7.domain.interactors.SignInUseCase
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Singleton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import oxxy.kero.roiaculte.team7.calcmoy.AndroidApplication
import oxxy.kero.roiaculte.team7.calcmoy.R


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