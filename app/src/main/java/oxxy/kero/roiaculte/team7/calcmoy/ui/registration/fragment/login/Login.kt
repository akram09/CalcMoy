package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.LoginBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.GOOGLE_SIGNEIN
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.invisible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.isEmailValid
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.visible
import oxxy.kero.roiaculte.team7.data.repositories.AuthentificationRepositoryImpl
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.models.UserState
import javax.inject.Inject

class Login : BaseFragment() {

    companion object { fun getInstance() = Login() }

    @Inject lateinit var googleApiClient : GoogleApiClient
    @Inject lateinit var loginManager : LoginManager
    @Inject lateinit var callbackManager : CallbackManager

    private lateinit var binding : LoginBinding
    private val viewModel : LoginViewModel by lazy { ViewModelProviders.of(this ,viewModelFactory)[LoginViewModel::class.java] }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.login,container,false)

        viewModel.observe(this){
            binding.loginEmail.setText( it?.email ?: "" )
            binding.loginPassword.setText( it?.password ?: "")

            fun onFail(error: Failure) {

                handleFailure(error)
                binding.loginBtn.alpha = 1f
                binding.loginBtn.isClickable =true
                binding.loginSignein.isClickable = true
                binding.loginGoogle.isClickable = true
                binding.loginFb.isClickable = true
                binding.inputs.visible()
                binding.progressBar.invisible()
                it?.state = null
            }

            it?.state?.let { async ->
                when(async){
                    is Loading<*> -> {
                        binding.inputs.invisible()
                        binding.progressBar.visible()
                        binding.loginBtn.alpha = 0.7f
                        binding.loginBtn.isClickable =false
                        binding.loginSignein.isClickable = false
                        binding.loginGoogle.isClickable = false
                        binding.loginFb.isClickable = false
                    }
                    is Success -> onSuccess(async())
                    is Fail<*, *> -> onFail(async.error)

                }
            }
        }

        binding.loginBtn.setOnClickListener {
            val email :String = binding.loginEmail.text.toString()
            val password :String = binding.loginPassword.text.toString()

            var cancel = false
            if (!email.isEmailValid()){
                onError(R.string.email_not_valid)
                cancel = true
            }
            if (password.length < 8 && !cancel ){
                onError(R.string.password_short)
                cancel =true
            }
            if(!isNetworkConnected()&& !cancel ) {
                onError(R.string.cnx_failed)
                cancel = true
            }
            if(!cancel) {
                viewModel.login(email,password)
            }
        }

        binding.loginGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, GOOGLE_SIGNEIN)
        }

        loginManager.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                viewModel.loginInWithCredentil(result?.accessToken?.token!!,AuthentificationRepositoryImpl.FACEBOOK_CONST)
            }

            override fun onCancel() {onError(R.string.operation_canceld)}

            override fun onError(error: FacebookException?) {onError(R.string.inknown_error)}
        })
        binding.loginFb.setOnClickListener {
            loginManager.logInWithReadPermissions(this, listOf("public_profile", "email"))
        }

        binding.loginSignein.setOnClickListener(){
            activity?.let {
                (it as RegistrationActivity).loadSignInFragment()
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
        if (requestCode == GOOGLE_SIGNEIN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                viewModel.loginInWithCredentil(result.signInAccount!!.idToken!!, AuthentificationRepositoryImpl.GOOGLE_CONST)
            } else { onError(R.string.inknown_error) }
        }
    }

    private fun handleFailure(error: Failure) {
        when (error){
            is Failure.LoginFailure -> when(error) {
                is Failure.LoginFailure.LoginNetworkError -> onError(R.string.cnx_failed)
                is Failure.LoginFailure.LoginUknownError -> onError(R.string.inknown_error)
                is Failure.LoginFailure.LoginPasswordInvalid -> onError(R.string.password_not_correct)
                is Failure.LoginFailure.LoginUsrNotFound -> onError(R.string.email_not_correct)
            }
            is Failure.SignInCredentielFailure -> when(error){
                is Failure.SignInCredentielFailure.SignInNetworkError -> onError(R.string.cnx_failed)
                is Failure.SignInCredentielFailure.SignInInvalidCredentiel -> onError(R.string.invalid_credentiel)
                is Failure.SignInCredentielFailure.SignInUknownError -> onError(R.string.inknown_error)
            }
        }
    }


    private fun onSuccess(userState: UserState) {
        when(userState){
            UserState.USER_REGISTRED_NOT_SAVED ->{
                startActivity(SaveInfoActivity.getIntent(context!!))
                activity?.finish()
            }
            UserState.USER_REGISTRED_SAVED -> {
                startActivity(MainActivity.getIntent(context!!))
                activity?.finish()
            }
        }
    }
}

