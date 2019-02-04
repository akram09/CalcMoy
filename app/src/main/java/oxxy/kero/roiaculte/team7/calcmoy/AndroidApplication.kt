package oxxy.kero.roiaculte.team7.calcmoy

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import oxxy.kero.roiaculte.team7.calcmoy.di.Component.DaggerAppComponent

class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerAppComponent.builder().create(this)
    }
}