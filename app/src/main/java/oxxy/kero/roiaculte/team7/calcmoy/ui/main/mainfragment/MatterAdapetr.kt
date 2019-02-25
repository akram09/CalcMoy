package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMainMoyCardBinding
import oxxy.kero.roiaculte.team7.domain.models.Matter

class MatterAdapetr : RecyclerView.Adapter<MatterAdapetr.MatterHolder>() {

    private val callback =object : SortedList.Callback<Matter>(){
        override fun areItemsTheSame(p0: Matter?, p1: Matter?): Boolean = p0 === p1


        override fun onMoved(p0: Int, p1: Int) {
            this@MatterAdapetr.notifyItemMoved(p0,p1)
        }

        override fun onChanged(p0: Int, p1: Int) {
            this@MatterAdapetr.notifyItemRangeChanged(p0,p1)
        }

        override fun onInserted(p0: Int, p1: Int) {
            this@MatterAdapetr.notifyItemRangeInserted(p0,p1)
        }

        override fun onRemoved(p0: Int, p1: Int) {
            this@MatterAdapetr.notifyItemRangeRemoved(p0,p1)
        }

        override fun compare(p0: Matter?, p1: Matter?): Int  = (p1?.coifficient ?: 0)-(p0?.coifficient ?: 0)

        override fun areContentsTheSame(p0: Matter?, p1: Matter?): Boolean = p0 == p1
    }

    private val matters : SortedList<Matter> = SortedList(Matter::class.java,callback)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatterHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding :MainFragmentMainMoyCardBinding  = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment_main_moy_card,p0,false)
        return MatterHolder(binding)
    }

    fun replaceAll(matter : List<Matter>){
        matters.beginBatchedUpdates()
        if(matters.size() >0) {
            for (i in (matters.size() - 1) downTo 0) {
                val mat = matters[i]
                if (!matter.contains(mat)) matters.remove(mat)
            }
        }

        matters.addAll(matter)
        matters.endBatchedUpdates()
    }

    override fun getItemCount(): Int  = matters.size()

    override fun onBindViewHolder(p0: MatterHolder, position: Int) {
        p0.upDateView(matters[position])
    }

    inner class MatterHolder(val viewHolderBinding : MainFragmentMainMoyCardBinding)  : RecyclerView.ViewHolder(viewHolderBinding.root){


        fun upDateView(matter : Matter){
            //TODO updateView later
            viewHolderBinding.name.setText(matter.name)
            viewHolderBinding.moy.setText(matter.moyenne.toString()+"/20")
            val color = ColorDrawable(Color.parseColor(matter.color))
            viewHolderBinding.colorImage.setImageDrawable(color)
        }
    }
}