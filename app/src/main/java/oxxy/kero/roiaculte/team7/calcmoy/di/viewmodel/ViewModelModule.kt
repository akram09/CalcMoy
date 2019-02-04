package oxxy.kero.roiaculte.team7.calcmoy.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationViewModel


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    internal abstract fun provideRegistrationViewModel(viewModel : RegistrationViewModel) : ViewModel

}