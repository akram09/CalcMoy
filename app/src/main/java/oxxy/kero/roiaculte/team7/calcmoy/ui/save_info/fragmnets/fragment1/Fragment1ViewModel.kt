package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1

import android.net.Uri
import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.ProvideUserInfo
import oxxy.kero.roiaculte.team7.domain.interactors.UserInfo
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.models.School
import javax.inject.Inject

class Fragment1ViewModel @Inject constructor(private val userInfo : ProvideUserInfo)
    : BaseViewModel<Fragment1State>(Fragment1State("","",School.PRIMAIRE,0,null)){

    private var firstTime = true

    fun setInfo() {
        if(firstTime) {
            Log.v("fucking_errror","excuting getInfo")
            scope.launchInteractor(userInfo, None()){it.either(::handleFaillure,::handleSuccecc)}
        }
        firstTime = false
    }

    private fun handleSuccecc(userInfo: UserInfo) {
        setState {
            Log.v("fucking_error","handling success image --> : ${userInfo.ImageUrl}")

            Fragment1State(userInfo.username,userInfo.prename,this.school,this.year,Image.ImageUrl(userInfo.ImageUrl))
        }
    }

    private fun handleFaillure(noUserInfo: Failure.NoUserInfo) {

    }
}