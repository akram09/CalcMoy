package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login.Login
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin.SigneIn


@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideFragmentSignIn() : SigneIn

    @ContributesAndroidInjector
    abstract fun provideFragmentLogIn() : Login
}