package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.provider

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContentProviderModule {

    @ContributesAndroidInjector
    abstract  fun provideSuggestions() : SuggetionsProvider
}