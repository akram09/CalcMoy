package oxxy.kero.roiaculte.team7.calcmoy.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainFragmentsModule
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationModule
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationScoop
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.RegistrationFragmentBuilder
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.SaveInfoFragmentBuilder
import oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen.SplashActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen.SplashFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen.SplashFragmentBuilder

/**
 * all Activitys will be injeted here by  @ContributesAndroidInjector with here modules
 */
@Module
abstract class ActivitysBuilder {

    @ContributesAndroidInjector(modules = [SplashFragmentBuilder::class])
    abstract fun provideSplash() : SplashActivity

    @ContributesAndroidInjector(modules = [RegistrationFragmentBuilder::class,RegistrationModule::class])
    @RegistrationScoop
    abstract fun provideRegistration() : RegistrationActivity

    @ContributesAndroidInjector(modules = [SaveInfoFragmentBuilder::class])
    abstract fun provideSaveInfo() : SaveInfoActivity
    @ContributesAndroidInjector(modules = [MainFragmentsModule::class])
   abstract fun provideMainActivity():MainActivity

}