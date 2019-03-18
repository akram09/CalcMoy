package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
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
             val updateContent = it.updateModuleEvent?.getContentIfNotHandled()
             val addContent = it.addModuleEvent?.getContentIfNotHandled()
             if(updateContent!=null){
                     showUpdateDialog(updateContent)
             }else
             if(addContent!=null){
                 showAddDialog()
             }else {
                 if (it.isLoading) {
                     showLoading(true)
                 } else {
                     showLoading(false)
                     showSemestres(it.size, it.whichSemestre)
                     if (it.modules.isNotEmpty()) {
                         showMatters(it.modules)
                     }
                 }
             }
         }
        return  binding.root
    }

    private fun showAddDialog() {
       AlertDialog.Builder(context!!).setView(R.layout.dialogue_add_module).create().show()
    }

    private fun showUpdateDialog(content: Matter) {
       val dialog =   AlertDialog.Builder(context!!).setView(R.layout.dialogue_update_module)
            .create()
       dialog.apply {
           findViewById<TextView>(R.id.updatemodule_title)?.text = content.name
         val nameEditTtext =  findViewById<EditText>(R.id.updatemodule_name)
             nameEditTtext?.setText(content.name)
         val coefEditText =   findViewById<EditText>(R.id.updatemodule_coei)
              coefEditText?.setText(content.coifficient.toString())
          val circleImageView= findViewById<CircleImageView>(R.id.updatemodule_color)
               circleImageView?.apply {
               circleBackgroundColor = Color.parseColor(content.color)
               setOnClickListener {

               }
           }

               findViewById<TextView>(R.id.updatemodule_save)?.setOnClickListener {

                   if(TextUtils.isEmpty(nameEditTtext!!.text.toString())){ onError(R.string.matter_empty) ; return@setOnClickListener }
                   if(TextUtils.isEmpty(coefEditText!!.text.toString())){ onError(R.string.coif_empty) ; return@setOnClickListener}
                   val color =circleImageView!!.circleBackgroundColor
                   val colorHex = "#${Integer.toHexString(color)}"
                   callback.updateMatter(content.copy(name = nameEditTtext!!.text.toString() , coifficient =
                   if(coefEditText!!.text.toString().toInt() is Int) coefEditText!!.text.toString().toInt()else content.coifficient ,
                       color = colorHex))
                   dialog.dismiss()
               }
               findViewById<TextView>(R.id.updatemodule_delete)?.setOnClickListener {  }
               findViewById<TextView>(R.id.updatemodule_disable)?.setOnClickListener {  }
               show()
           }
       }


    private fun showLoading(b: Boolean) {
      binding.mainModulesProgress.visibility=if(b) View.VISIBLE else View.INVISIBLE
    }

    private fun showMatters(modules: List<Matter>) {
        binding.moduleRecycler.apply {
            setHasFixedSize(true)
            //todo fix the expandable problem with the gridlayout manager
            layoutManager =GridLayoutManager(activity , 2 )
            adapter = ModulesAdapter(context  , modules, callback::moduleClicked)
        }
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
    fun moduleClicked(matter :Matter)
    fun fabClicked()
    fun updateMatter(copy: Matter)
}