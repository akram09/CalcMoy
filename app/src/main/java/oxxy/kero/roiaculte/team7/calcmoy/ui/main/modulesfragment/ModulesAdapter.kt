package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.domain.models.Matter

class ModulesAdapter(val context : Context, val list :List<Matter> , val onClick:(Matter)->Unit):RecyclerView.Adapter<ModulesAdapter.ModulesViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ModulesViewHolder =
        ModulesViewHolder(LayoutInflater.from(context).inflate(R.layout.main_fragment_module_card, p0 , false ))

    override fun getItemCount()= list.size

    override fun onBindViewHolder(p0: ModulesViewHolder, p1: Int) {
    p0.update(list[p1])
        p0.itemView.setOnClickListener {
            onClick(list[p1])
        }
    }

    inner class ModulesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val modulesName : TextView = itemView.findViewById(R.id.textView3)
        private val moyenne :TextView = itemView.findViewById(R.id.textView5)
        private val coefficient :TextView = itemView.findViewById(R.id.textView4)
        fun update(matter : Matter){
            modulesName.text = matter.name
            modulesName.setBackgroundColor(Color.parseColor(matter.color))
           moyenne.text ="معدلك: 20/"+matter.moyenne.toString()
            coefficient.text = "معامل المادة: "+matter.coifficient.toString()
        }

    }
}