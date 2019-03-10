package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView
import net.cachapa.expandablelayout.ExpandableLayout
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.setValeur
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class MoyennesAdapter(val context : Context, val moyenneList :List<Double> , val matterList:List<Semestre>)
    :RecyclerView.Adapter<MoyennesAdapter.MoyenneViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoyenneViewHolder {
      return MoyenneViewHolder(LayoutInflater.from(context).inflate(R.layout.main_moy_card_semestre,p0 , false ))
    }

    override fun getItemCount(): Int {
   return matterList.size
    }

    override fun onBindViewHolder(p0: MoyenneViewHolder, p1: Int) {
    p0.updateCard(moyenneList[p1], context.resources.getStringArray(R.array.number_profile)[p1] , matterList[p1].matters)
    }

    inner  class MoyenneViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private val textView :TextView = itemView.findViewById(R.id.textView18)
        private val circularandMoyenne :Pair<CircleProgressView , TextView>
                = itemView.findViewById<CircleProgressView>(R.
            id.main_profile_semestre_recyclerview_progressview) to itemView
            .findViewById(R.id.main_profile_semestre_textview)
        private val recyclerView:RecyclerView = itemView.findViewById(R.id.main_profile_semestre_recyclerview_matters)
       private val expandable :ExpandableLayout = itemView.findViewById(R.id.expandable_layout)
        fun updateCard(moyenn :Double, text:String , matters :List<Matter>){
            textView.text =  "${textView.text} $text"
            circularandMoyenne.setValeur(moyenn)
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context , LinearLayout.VERTICAL , false )
                adapter = ModulesAdapter(context , matters)
            }
            itemView.setOnClickListener {
                if(expandable.isExpanded)expandable.collapse()
                else expandable.expand(true)
            }
        }
    }
}