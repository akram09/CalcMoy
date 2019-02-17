package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.Snackbar
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.databinding.DialogueUpdateModuleBinding
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SaveInfoFragment2CardBinding
import oxxy.kero.roiaculte.team7.calcmoy.di.viewmodel.ViewModelFactory
import oxxy.kero.roiaculte.team7.domain.models.Matter

class Fragment2Adapter(val id : String,val viewModel: Fragment2ViewModel) : RecyclerView.Adapter<Fragment2Adapter.SemestresHolder>() {


    private val callback : SortedList.Callback<Matter> =object : SortedList.Callback<Matter>(){

        override fun areItemsTheSame(p0: Matter?, p1: Matter?): Boolean = p0 == p1

        override fun onMoved(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemMoved(p0,p1) }

        override fun onChanged(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeChanged(p0,p1) }

        override fun onInserted(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeInserted(p0,p1) }

        override fun onRemoved(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeRemoved(p0,p1) }

        override fun compare(p0: Matter?, p1: Matter?): Int  {
            return if(p0 == null ) {
                if (p1 == null) 0
                else -Integer.compare(0,p1.coifficient)
            }else if(p1 == null) -Integer.compare(p0.coifficient,0)
            else -Integer.compare(p0.coifficient,p1.coifficient)
        }

        override fun areContentsTheSame(p0: Matter?, p1: Matter?): Boolean =  p0?.name == p1?.name
    }
    val listOfMatters : SortedList<Matter> = SortedList(Matter::class.java,callback)


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SemestresHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding : SaveInfoFragment2CardBinding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_2_card,parent,false)
        Log.v("recycler_fucking_error","inflating item on recycler view ")
        return SemestresHolder(binding)
    }

    override fun getItemCount(): Int{
        Log.v("recycler_fucking_error","getItem on RecyclerAdapter ${listOfMatters.size()}")
        return listOfMatters.size()
    }

    override fun onBindViewHolder(holder: SemestresHolder, position: Int) {
        holder.upDateView(listOfMatters[position])
    }

    fun replaceAll(matters: List<Matter>) {
        listOfMatters.beginBatchedUpdates()
        val size = listOfMatters.size()
        if(size != 0) {
            for (i in (size-1) downTo 0 ){
                val matter =listOfMatters[i]
                if(!matters.contains(matter)) listOfMatters.remove(matter)
            }
        }
        listOfMatters.addAll(matters)
        listOfMatters.endBatchedUpdates()
    }

    inner class SemestresHolder(val binding: SaveInfoFragment2CardBinding) : RecyclerView.ViewHolder(binding.root) {

        private var dialogueBinding : DialogueUpdateModuleBinding? = null

        fun upDateView(matter : Matter){
            binding.coif.text = "${binding.root.context?.getString(R.string.coif) ?: ""} : ${matter.coifficient}"
            binding.name.text = matter.name
            val colorDrawable = ColorDrawable(Color.parseColor(matter.color))
            binding.couler.setImageDrawable(colorDrawable)
            binding.root.setOnClickListener{
                var alertDialog : AlertDialog? =null
                val builder = AlertDialog.Builder(binding.root.context)
                dialogueBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.root.context),
                    R.layout.dialogue_update_module,
                    null,
                    false)
                builder.setView(dialogueBinding!!.root)
                dialogueBinding!!.updatemoduleSave.setOnClickListener{
                    val name = dialogueBinding!!.updatemoduleName.text.toString()
                    val coif = dialogueBinding!!.updatemoduleCoei.text.toString()

                    if(TextUtils.isEmpty(name)) {
                        Snackbar.make(binding.root,R.string.name_empty,Snackbar.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if(TextUtils.isEmpty(coif)) {
                        Snackbar.make(binding.root,R.string.coief,Snackbar.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val color = (dialogueBinding!!.updatemoduleColor.drawable as? ColorDrawable)?.color ?: binding.root.resources.getColor(R.color.default_matter_color)
                    val colorHex = "#${Integer.toHexString(color)}"
                    val matter = Matter(0,name,coif.toInt(),colorHex,viewModel.curent,0.0,id)
                    updateMatter(matter,adapterPosition)
                    alertDialog?.dismiss()
                }
                alertDialog = builder.create()
                alertDialog.show()
            }
        }
    }

    private fun updateMatter(matter: Matter, adapterPosition: Int) {
        viewModel.updateMatter(matter,adapterPosition)
    }
}