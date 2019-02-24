package oxxy.kero.roiaculte.team7.calcmoy.ui.main

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import dagger.Module
import dagger.android.ContributesAndroidInjector
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.eventsfragment.EventsFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment.MainFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment.ModulesFragment
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment.MoyenneFragment

@Module
abstract class MainFragmentsModule {
    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment
    @ContributesAndroidInjector
    abstract fun provideMoyennrFragment():MoyenneFragment
    @ContributesAndroidInjector
    abstract fun provideModulesFragment():ModulesFragment
    @ContributesAndroidInjector
    abstract fun provideEventsFragment():EventsFragment
}