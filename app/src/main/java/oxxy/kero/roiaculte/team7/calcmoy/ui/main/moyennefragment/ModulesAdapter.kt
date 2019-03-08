package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class ModulesAdapter (val context : Context,val list: List<Matter>):RecyclerView.Adapter<ModulesAdapter.MatterViewHolder>()  {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MatterViewHolder {
        return MatterViewHolder(LayoutInflater.from(context).inflate(R.layout.main_fragment_moy_cardmatter, p0 , false)
        )
    }

    override fun getItemCount()= list.size


    override fun onBindViewHolder(p0: MatterViewHolder, p1: Int) {
       p0.update(list[p1].moyenne , list[p1].name)
    }

    class MatterViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val  moyenne : TextView = itemView.findViewById(R.id.main_profile_semestre_matter_moy)
        private val moduleName :TextView = itemView.findViewById(R.id.main_profile_semestre_matters)
        fun update (double: Double , nom:String){
            moyenne.text =double.toString()
            moduleName.text = nom
        }
    }
}