package oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideSplashFragment() : SplashFragment
}