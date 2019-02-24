package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMainBinding

private const val LOG_TAG="MAIN_FRAGMENT"
class MainFragment: BaseFragment() {
companion object {
    fun getInstance()= MainFragment()
}
    private lateinit var binding :MainFragmentMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater  ,R.layout.main_fragment_main, container, false)
        Log.e(LOG_TAG, "entered")
        return binding.root
    }
}