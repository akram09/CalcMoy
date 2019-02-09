package oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin


import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SigneinBinding
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.invisible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.visible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.isEmailValid
import com.google.android.gms.auth.api.Auth
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.facebook.login.LoginManager
import com.google.android.gms.common.api.GoogleApiClient
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.GOOGLE_SIGNEIN
import javax.inject.Inject
import oxxy.kero.roiaculte.team7.data.repositories.AuthentificationRepositoryImpl
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.RegistrationActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
import oxxy.kero.roiaculte.team7.domain.models.UserState


class SigneIn : BaseFragment(){

    @Inject lateinit var googleApiClient : GoogleApiClient
    @Inject lateinit var loginManager : LoginManager
    @Inject lateinit var callbackManager : CallbackManager
    private lateinit var binding :  SigneinBinding
    private val viewModel :SigneInViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[SigneInViewModel::class.java]}

    companion object{ fun getInstance() = SigneIn() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.signein,container,false)
        viewModel.observe(this ){
            binding.signeinEmail.setText(it?.email ?: "")
            binding.signeinPassword.setText(it?.password ?: "")
            binding.signeinRepeatpassword.setText(it?.repeatPassword ?: "")

            fun onFail(error  : Failure) {

                handleFailure(error)

                binding.signeinBtn.alpha = 1f
                binding.signeinBtn.isClickable =true
                binding.inputs.visible()
                binding.progressBar.invisible()
                it?.state = null
            }

            it?.state?.let { async ->
                when(async){
                    is Loading<*> -> {
                        binding.inputs.invisible()
                        binding.progressBar.visible()
                        binding.signeinBtn.alpha = 0.7f
                        binding.signeinBtn.isClickable =false
                    }
                    is Success -> handleSuccess(async())
                    is Fail<*,*> -> onFail(async.error)

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

        binding.signeinGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, GOOGLE_SIGNEIN)
        }

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                viewModel.signInWithCredentil(result?.accessToken?.token!!,AuthentificationRepositoryImpl.FACEBOOK_CONST)
            }

            override fun onCancel() {onError(R.string.operation_canceld)}

            override fun onError(error: FacebookException?) {onError(R.string.inknown_error)}
        })
        binding.signeinFb.setOnClickListener {
            loginManager.logInWithReadPermissions(this, listOf("public_profile", "email"))

        }

        binding.signeinLogin.setOnClickListener{
            activity?.let {
                (it as RegistrationActivity).loadLoginFragment()
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
                viewModel.signInWithCredentil(result.signInAccount!!.idToken!!,AuthentificationRepositoryImpl.GOOGLE_CONST)
            } else {
                onError(R.string.inknown_error)
            }
        }
    }

    private fun handleFailure(error: Failure) {
        when (error){
            is Failure.CreatUserFailures -> when(error) {
                is Failure.CreatUserFailures.FirebaseWeakPassword -> onError(R.string.weak_password)
                is Failure.CreatUserFailures.FirebaseCoalisedUser -> {
                    onError("user alredy exist !!")
                    activity?.let { (it as? RegistrationActivity)?.loadLoginFragment() }
                }
                is Failure.CreatUserFailures.FirebaseNetworkError -> onError(R.string.cnx_failed)
                is Failure.CreatUserFailures.FirebaseUknownError -> onError(R.string.inknown_error)
            }
            is Failure.SignInCredentielFailure -> when(error){
                is Failure.SignInCredentielFailure.SignInNetworkError -> onError(R.string.cnx_failed)
                is Failure.SignInCredentielFailure.SignInInvalidCredentiel -> onError(R.string.invalid_credentiel)
                is Failure.SignInCredentielFailure.SignInUknownError -> onError(R.string.inknown_error)
            }
            is Failure.ProvideUserStateFailure -> onError(getString(R.string.user_state_error))

        }
    }

    private fun handleSuccess(type : UserState) {
        showMessage("registration success"+type)
        when(type){
            UserState.USER_REGISTRED_NOT_SAVED -> {
                startActivity(SaveInfoActivity.getIntent(context!!))
                activity?.finish()
            }
            UserState.USER_REGISTRED_SAVED -> showMessage("go to main ") //TODO go to main
        }
    }
}
