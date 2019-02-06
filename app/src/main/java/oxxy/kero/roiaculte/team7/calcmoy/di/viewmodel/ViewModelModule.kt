package oxxy.kero.roiaculte.team7.calcmoy.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login.LoginViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin.SigneInViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen.SplashViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun provideSplashViewModel(viewModel : SplashViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SigneInViewModel::class)
    internal abstract fun provideSignInViewModel(viewModel : SigneInViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun provideLoginViewModel(viewModel : LoginViewModel) : ViewModel
}