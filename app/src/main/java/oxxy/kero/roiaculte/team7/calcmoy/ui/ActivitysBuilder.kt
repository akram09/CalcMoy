package oxxy.kero.roiaculte.team7.calcmoy.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity

/**
 * all Activitys will be injeted here by  @ContributesAndroidInjector with here modules
 */
@Module
abstract class ActivitysBuilder {
//    @ContributesAndroidInjector(modules = [FeatureModule::class
////        , FragmentProvider::class])
////    abstract fun provideActivity(): FeatureActivity

    @ContributesAndroidInjector
    abstract fun provideRegistration() : RegistrationActivity

}