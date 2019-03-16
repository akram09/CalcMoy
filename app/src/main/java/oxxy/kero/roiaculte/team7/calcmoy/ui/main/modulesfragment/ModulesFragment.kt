package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentModuleBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class ModulesFragment:BaseFragment() {
    companion object {
        fun getInstance()= ModulesFragment()
    }
private lateinit var binding :MainFragmentModuleBinding
    private val viewModel :ModulesViewModel by lazy {
        ViewModelProviders.of(this , this.viewModelFactory)[ModulesViewModel::class.java]
    }
    private val  callback:ModulesCallback by lazy {
        viewModel
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding= DataBindingUtil.inflate(inflater, R.layout.main_fragment_module, container , false )
        (mActivity as MainActivity).callback.observeSemestre(callback::doOnSuccess , callback::doOnFailure)
         viewModel.observe(this){
             it!!
             if(it.isLoading){

             }else{
                 showSemestres(it.size  , it.whichSemestre)
                 if(it.modules.isNotEmpty()){
                     showMatters(it.modules)
                 }
             }
         }
        return  binding.root
    }

    private fun showMatters(modules: List<Matter>) {
          Log.e("errr", "called show matters")
    }

    private fun showSemestres(size: Int, whichSemestre: Int) {
         binding.mainModulesSemestresRecycler.apply {
             setHasFixedSize(true)
             layoutManager = LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL , false)
             adapter = SemestreAdapter(context , size ,whichSemestre , viewModel::onSemestreClicked)
         }
    }


}
interface  ModulesCallback{
fun doOnSuccess(list:List<Semestre>)
    fun doOnFailure(t:Throwable)
    fun onSemestreClicked(which:Int)
}