package oxxy.kero.roiaculte.team7.calcmoy.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivityViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment.MainViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login.LoginViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin.SigneInViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen.SplashViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1ViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.Fragment2ViewModel


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

    @Binds
    @IntoMap
    @ViewModelKey(Fragment1ViewModel::class)
    internal abstract fun provideSaveInfo1(viewModel : Fragment1ViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Fragment2ViewModel::class)
    internal abstract fun provideSaveInfo2(viewModel : Fragment2ViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun provideMainViewModel(viewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun provideMainActivityViewModel(viewModel: MainActivityViewModel) : ViewModel

//    Fragment2ViewModel
}