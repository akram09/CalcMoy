package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMainBinding
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.invisible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.visible
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Semestre

private const val LOG_TAG="MAIN_FRAGMENT"
class MainFragment: BaseFragment() {
    companion object { fun getInstance()= MainFragment() }

    private val viewModel : MainViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[MainViewModel::class.java] }

    private lateinit var binding :MainFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater  ,R.layout.main_fragment_main, container, false)

        viewModel.observe(this){
            handleMatterState(it?.matterState)
            handleEventsState(it?.evensAsync)
            handleSemestres(it?.semestres)
            handleEvents(it?.events)
        }

        return binding.root
    }

    private fun handleEvents(events: List<Event>?) {

    }

    private fun handleSemestres(semestres: List<Semestre>?) {

    }

    private fun handleEventsState(evensAsync: Async<None>?) {
        when(evensAsync){
            is Loading ->{
                binding.loadingEvents.visible()
                binding.mainEventrecycler.invisible()
            }

            is Success ->{
                binding.loadingEvents.invisible()
                binding.mainEventrecycler.visible()
            }

            is Fail<*,*> -> {
                //TODO show there is no matters
            }
        }
    }

    private fun handleMatterState(matterState: Async<None>?) {
        when(matterState){
            is Loading ->{
                binding.loadingMatter.visible()
                binding.mainNoterecycler.invisible()
            }

            is Success ->{
                binding.loadingMatter.invisible()
                binding.mainNoterecycler.visible()
            }

            is Fail<*,*> -> {
                //TODO show there is no matters
            }
        }
    }
}