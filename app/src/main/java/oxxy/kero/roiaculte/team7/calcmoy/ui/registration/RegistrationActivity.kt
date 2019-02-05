package oxxy.kero.roiaculte.team7.calcmoy.ui.registration

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.Login
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.inTransaction
import oxxy.kero.roiaculte.team7.calcmoy.databinding.RegistrationActivityBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.SigneIn
import javax.inject.Inject

class RegistrationActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var signeIn : SigneIn? = null
    private var logIn : Login? = null

    private lateinit var binding : RegistrationActivityBinding
    lateinit var viewModel : RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.registration_activity)
        viewModel = ViewModelProviders.of(this,viewModelFactory)[RegistrationViewModel::class.java]
        viewModel.observe(this){
            if(it?.fragment == REGISTRATION_SIGNEIN){
                loadSignInFragment()
            }else{
                loadLoginFragment()
            }
        }
    }

    private fun loadLoginFragment() {
        if(logIn == null) logIn = Login.getInstance()

        val  tmp  = supportFragmentManager.findFragmentById(R.id.main_activity_framelayout)
        if(tmp == null) supportFragmentManager.inTransaction { add(R.id.main_activity_framelayout, logIn!!) }
        else if(tmp != logIn) supportFragmentManager.inTransaction {replace(R.id.main_activity_framelayout,logIn!!).addToBackStack("login_fragment") }
    }

    private fun loadSignInFragment() {
            if(signeIn == null) signeIn = SigneIn.getInstance()
           val  tmp  = supportFragmentManager.findFragmentById(R.id.main_activity_framelayout)
        if(tmp == null) supportFragmentManager.inTransaction { add(R.id.main_activity_framelayout, signeIn!!) }
        else if(tmp != signeIn) supportFragmentManager.inTransaction { replace(R.id.main_activity_framelayout, signeIn!!) }
    }

}