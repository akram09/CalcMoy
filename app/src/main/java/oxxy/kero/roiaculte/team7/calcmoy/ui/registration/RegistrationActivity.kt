package oxxy.kero.roiaculte.team7.calcmoy.ui.registration

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.login.Login
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.inTransaction
import oxxy.kero.roiaculte.team7.calcmoy.databinding.RegistrationActivityBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.fragment.signin.SigneIn


const val GOOGLE_SIGNEIN = 0

const val LOGIN = 0
const val SIGNIN = 1
const val FRAGMENT_KEY ="registration_curent_fragment_key"


class RegistrationActivity : BaseActivity() {

    companion object {
        fun getIntent(context : Context) = Intent(context,RegistrationActivity::class.java)
    }
    private lateinit var binding : RegistrationActivityBinding
    private var curentFragment :Int = SIGNIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.registration_activity)
        savedInstanceState?.let { curentFragment = it.getInt(FRAGMENT_KEY) }

        setUpFragment()
    }

    private fun setUpFragment() {
        if(curentFragment == LOGIN) loadLoginFragment()
        else loadSignInFragment()
    }

    fun loadLoginFragment() {

        val  tmp  = supportFragmentManager.findFragmentById(R.id.main_activity_framelayout)
        if(tmp == null){
            val logIn = Login.getInstance()
            supportFragmentManager.inTransaction { add(R.id.main_activity_framelayout, logIn) }
        }
        else if(tmp !is Login) {
            val logIn = Login.getInstance()
            supportFragmentManager.inTransaction { replace(R.id.main_activity_framelayout, logIn) }
        }
        curentFragment = LOGIN
    }

    fun loadSignInFragment() {

        val  tmp  = supportFragmentManager.findFragmentById(R.id.main_activity_framelayout)
        if(tmp == null){
            val signeIn = SigneIn.getInstance()
            supportFragmentManager.inTransaction { add(R.id.main_activity_framelayout, signeIn) }
        }
        else if(tmp !is SigneIn ) {
            val signeIn = SigneIn.getInstance()
            supportFragmentManager.inTransaction { replace(R.id.main_activity_framelayout, signeIn) }
        }
        curentFragment = SIGNIN
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(FRAGMENT_KEY,curentFragment)
    }
}