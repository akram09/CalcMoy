package oxxy.kero.roiaculte.team7.calcmoy.ui.splash_screen

import android.databinding.DataBindingUtil
import android.os.Bundle
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SplashScreenBinding

class SplashScreen : BaseActivity() {

//    @Inject private lateinit var  userSatateUseCase : UserStateUseCase
    private lateinit var binding : SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.splash_screen)
    }
}