package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideFragment() : SigneIn
}