package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
        Log.v("recycler_fucking_error","inflating item on recycler view ")
        return SemestresHolder(binding)
    }

    override fun getItemCount(): Int{
        Log.v("recycler_fucking_error","getItem on RecyclerAdapter ${listOfMatters.size()}")
        return listOfMatters.size()
    }

    override fun onBindViewHolder(holder: SemestresHolder, position: Int) {
        Log.v("recycler_fucking_error","upDateView in position : $position")
        holder.upDateView(listOfMatters[position])
    }

    fun replaceAll(matters: List<Matter>) {
        listOfMatters.beginBatchedUpdates()
        for( i in (listOfMatters.size()-1)..0){
            val matter = listOfMatters[i]
            if(!matters.contains(matter)) listOfMatters.remove(matter)
        }
        listOfMatters.addAll(matters)
        listOfMatters.endBatchedUpdates()
    }

    class SemestresHolder(val binding: SaveInfoFragment2CardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun upDateView(matter : Matter){
            Log.v("recycler_fucking_error","start updating ....")
            binding.coif.text = "${binding.root.context?.getString(R.string.coif) ?: ""} : ${matter.coifficient}"
            binding.name.text = matter.name
            val colorDrawable = ColorDrawable(Color.parseColor(matter.color))
            binding.couler.setImageDrawable(colorDrawable)

            binding.couler.setOnClickListener{
                //TODO choose color
            }
            Log.v("recycler_fucking_error","finish updating")
        }
    }
}