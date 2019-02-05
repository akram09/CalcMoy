package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SigneinBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.invisible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.visible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.isEmailValid

class SigneIn : BaseFragment(){

    private lateinit var binding :  SigneinBinding
    private lateinit var viewModel :RegistrationViewModel
    companion object{ fun getInstance() = SigneIn() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = (context as RegistrationActivity).viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.signein,container,false)
        viewModel.observe(this ){
            binding.signeinEmail.setText(it?.signinInfo?.email ?: "")
            binding.signeinPassword.setText(it?.signinInfo?.password ?: "")
            binding.signeinRepeatpassword.setText(it?.signinInfo?.repeatPassword ?: "")

            fun onFail(){
                binding.signeinBtn.alpha = 1f
                binding.signeinBtn.isClickable =true
                binding.inputs.visible()
                binding.progressBar.invisible()
                it?.signinInfo?.signIn = null
            }

            it?.signinInfo?.signIn.let { async ->
                when(async){
                    is Loading<*> -> {
                        binding.inputs.invisible()
                        binding.progressBar.visible()
                        binding.signeinBtn.alpha = 0.7f
                        binding.signeinBtn.isClickable =false
                    }
                    is Success<*> -> openSaveInfo()
                    is Fail<*> -> {
                        onError(R.string.registration_faill)
                        onFail()
                    }
                }
            }
        }


        binding.signeinBtn.setOnClickListener {
            val email :String = binding.signeinEmail.text.toString()
            val password :String = binding.signeinPassword.text.toString()
            val repeatPassword :String = binding.signeinRepeatpassword.text.toString()

            var cancel = false

            if (!email.isEmailValid()){
                onError(R.string.email_not_valid)
                cancel = true
            }
            if (password.length < 8 && !cancel ){
                onError(R.string.password_short)
                cancel =true
            }

            if(password != repeatPassword && !cancel){
                onError(R.string.password_not_compatible)
                cancel =true
            }

            if(!isNetworkConnected()&& !cancel ) {
                onError(R.string.cnx_failed)
                cancel = true
            }

            if(!cancel) {
                viewModel.signIn(email,password)
            }
        }

        return binding.root
    }

    private fun openSaveInfo() {
        showMessage("registration success")
        //TODO open SaveInfo Activity
    }
}