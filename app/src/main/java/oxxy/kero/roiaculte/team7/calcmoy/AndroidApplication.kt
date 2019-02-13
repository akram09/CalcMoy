package oxxy.kero.roiaculte.team7.calcmoy

import android.content.Context
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import oxxy.kero.roiaculte.team7.calcmoy.di.Component.DaggerAppComponent

class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }
}