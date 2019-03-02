package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMoyBinding
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.setValeur

class MoyenneFragment :BaseFragment(){
    companion object {
        fun getInstance()=MoyenneFragment()
    }
lateinit var  binding :MainFragmentMoyBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment_moy, container, false)
        return binding.root
    }

}
