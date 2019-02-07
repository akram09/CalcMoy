package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.Fragment2

@Module
abstract class SaveInfoFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideFragment1() : Fragment1

    @ContributesAndroidInjector
    abstract fun provideFragment2() : Fragment2

}