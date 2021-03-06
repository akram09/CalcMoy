package oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.inTransaction

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_containner)
//        startActivity(Intent(this , TestActivity::class.java))
        setFragment()
    }

    private fun setFragment() {
        var tmp = supportFragmentManager.findFragmentById(R.id.splash_container)
        if(tmp == null){
            tmp = SplashFragment()
            supportFragmentManager.inTransaction {
                add(R.id.splash_container,tmp)
            }
        }
    }
}