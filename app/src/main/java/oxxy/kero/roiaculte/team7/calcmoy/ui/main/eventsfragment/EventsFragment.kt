package oxxy.kero.roiaculte.team7.calcmoy.ui.main.eventsfragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentEventBinding

class EventsFragment:BaseFragment() {
    companion object {
        fun getInstance()= EventsFragment()
    }
    private lateinit var  binding:MainFragmentEventBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment_event, container, false )
        return binding.root
    }
}