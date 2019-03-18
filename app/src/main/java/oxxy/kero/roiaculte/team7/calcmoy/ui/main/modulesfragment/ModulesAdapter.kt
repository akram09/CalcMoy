package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.cachapa.expandablelayout.ExpandableLayout
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentModuleCardBinding
import oxxy.kero.roiaculte.team7.domain.models.Matter

class ModulesAdapter(val context : Context, val list :List<Matter> , val onClick:(Matter)->Unit):RecyclerView.Adapter<ModulesAdapter.ModulesViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ModulesViewHolder =
        ModulesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.main_fragment_module_card , p0 , false ))

    override fun getItemCount()= list.size

    override fun onBindViewHolder(p0: ModulesViewHolder, p1: Int) {
    p0.update(list[p1])
    }

    inner class ModulesViewHolder(  itemView: View): RecyclerView.ViewHolder(itemView){
        val nameTextView = itemView.findViewById<TextView>(R.id.textView3)
        val moyenneTextView = itemView.findViewById<TextView>(R.id.textView5)
        val coefTextView = itemView.findViewById<TextView>(R.id.textView4)
        fun update(matter : Matter){
             nameTextView.apply {
                 text = matter.name
                 setBackgroundColor(Color.parseColor(matter.color))
             }
            moyenneTextView.text = context.resources.getString(R.string.moyenne_is)+ matter.moyenne.toString()
            coefTextView.text = context.resources.getString(R.string.matter_coefiicient) +matter.coifficient.toString()

        }

    }
}