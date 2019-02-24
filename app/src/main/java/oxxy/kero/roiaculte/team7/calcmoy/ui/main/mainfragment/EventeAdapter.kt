package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.databinding.DataBindingUtil
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMainEventCardBinding
import oxxy.kero.roiaculte.team7.domain.models.Event

class EventeAdapter : RecyclerView.Adapter<EventeAdapter.EventHolder>() {

    private val callback =object : SortedList.Callback<Event>(){
        override fun areItemsTheSame(p0: Event?, p1: Event?): Boolean = p0 === p1


        override fun onMoved(p0: Int, p1: Int) {
            this@EventeAdapter.notifyItemMoved(p0,p1)
        }

        override fun onChanged(p0: Int, p1: Int) {
            this@EventeAdapter.notifyItemRangeChanged(p0,p1)
        }

        override fun onInserted(p0: Int, p1: Int) {
            this@EventeAdapter.notifyItemRangeInserted(p0,p1)
        }

        override fun onRemoved(p0: Int, p1: Int) {
            this@EventeAdapter.notifyItemRangeRemoved(p0,p1)
        }

        override fun compare(p0: Event?, p1: Event?): Int  = p1?.time?.compareTo(p0?.time) ?: 0

        override fun areContentsTheSame(p0: Event?, p1: Event?): Boolean = p0 == p1
    }

    private val matters : SortedList<Event> = SortedList(Event::class.java,callback)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventHolder {
        val inflater = LayoutInflater.from(p0.context)
        val binding : MainFragmentMainEventCardBinding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment_main_moy_card,p0,false)
        return EventHolder(binding)
    }

    override fun getItemCount(): Int  = matters.size()

    override fun onBindViewHolder(p0: EventHolder, position: Int) {
        p0.upDateView(matters[position])
    }

    inner class EventHolder(val viewHolderBinding : MainFragmentMainEventCardBinding)  : RecyclerView.ViewHolder(viewHolderBinding.root){


        fun upDateView(event : Event){
            //TODO updateView later
            //TODO add matter color in event model
//            val color = ColorDrawable(Color.parseColor(event.))
//            viewHolderBinding.imageColor.setImageDrawable(color)
        }
    }
}