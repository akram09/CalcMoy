package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentModuleBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity

class ModulesFragment:BaseFragment() {
    companion object {
        fun getInstance()= ModulesFragment()
    }
private lateinit var binding :MainFragmentModuleBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding= DataBindingUtil.inflate(inflater, R.layout.main_fragment_module, container , false )

        return  binding.root
    }


}