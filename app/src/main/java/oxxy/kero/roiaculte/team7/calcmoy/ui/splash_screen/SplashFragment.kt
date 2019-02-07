package oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SplashScreenBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject

class SplashFragment : BaseFragment() {

    private lateinit var binding : SplashScreenBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory)[SplashViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.splash_screen,container,false)

        viewModel.observe(this){
            it?.state.let {
                when(it) {
                    is Success<*> -> handleSuccess(it())
                    is Fail<*,*> -> handleFaillure(it.error)
                }
            }
        }
        viewModel.excuteUserState()

        return binding.root
    }

    private fun handleFaillure(error: Failure) {
        //TODO show somthing
        showMessage("errror in userState")
    }

    private fun handleSuccess(state: UserState?) {
        state.let {
            when(state){
                UserState.USER_NOT_REGISTRED -> {
                    startActivity(RegistrationActivity.getIntent(activity!!))
                    activity?.finish()
                }
                UserState.USER_REGISTRED_NOT_SAVED ->{
                    showMessage("go to save info")
                    startActivity(SaveInfoActivity.getIntent(context!!))
                    activity?.finish()
                }
                UserState.USER_REGISTRED_SAVED -> {
                    showMessage("go to main ")
                    //TODO go to main
                }
                else -> return
            }
        }
    }
}