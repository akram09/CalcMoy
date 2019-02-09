package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SaveInfoFragment2CardBinding
import oxxy.kero.roiaculte.team7.domain.models.Matter

class Fragment2Adapter : RecyclerView.Adapter<Fragment2Adapter.SemestresHolder>() {

    private val callback : SortedList.Callback<Matter> =object : SortedList.Callback<Matter>(){

        override fun areItemsTheSame(p0: Matter?, p1: Matter?): Boolean = p0 == p1

        override fun onMoved(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemMoved(p0,p1) }

        override fun onChanged(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeChanged(p0,p1) }

        override fun onInserted(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeInserted(p0,p1) }

        override fun onRemoved(p0: Int, p1: Int) { this@Fragment2Adapter.notifyItemRangeRemoved(p0,p1) }

        override fun compare(p0: Matter?, p1: Matter?): Int  {
            return if(p0 == null ) {
                if (p1 == null) 0
                else p1.name.compareTo("")
            }else p0.name.compareTo(p1?.name ?: "")
        }

        override fun areContentsTheSame(p0: Matter?, p1: Matter?): Boolean =  p0?.name == p1?.name
    }
    val listOfMatters : SortedList<Matter> = SortedList(Matter::class.java,callback)


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SemestresHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding : SaveInfoFragment2CardBinding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_2_card,parent,false)
        return SemestresHolder(binding)
    }

    override fun getItemCount(): Int= listOfMatters.size()

    override fun onBindViewHolder(holder: SemestresHolder, position: Int) { holder.upDateView(listOfMatters[position]) }

    fun replaceAll(matters: List<Matter>) {
        listOfMatters.beginBatchedUpdates()
        listOfMatters.clear()
        listOfMatters.addAll(matters)
        listOfMatters.endBatchedUpdates()
    }

    class SemestresHolder(val binding: SaveInfoFragment2CardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun upDateView(matter : Matter){
            binding.coif.setText(matter.coifficient.toString())
            binding.name.setText(matter.name)
            val color  = Color.parseColor(matter.color)
            Log.v("fucking_error","color is --->${matter.color} , $color")
            binding.couler.setCircleBackgroundColor(color)
            binding.couler.setOnClickListener{
                //TODO choose color
            }
        }
    }
}