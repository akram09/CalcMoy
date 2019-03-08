package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.setValeur

class MoyenneSemestreAdapter(val list: List<Double> , val context :Context) :RecyclerView.Adapter<MoyenneSemestreAdapter.MoyenneSemestreViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoyenneSemestreViewHolder {
        return MoyenneSemestreViewHolder(LayoutInflater.from(context).inflate(R.layout.main_moy_card_semestre ,
            p0 , false ))
    }

    override fun getItemCount() = list.size
    override fun onBindViewHolder(p0: MoyenneSemestreViewHolder, p1: Int) {
     p0.update(list[p1] , context.resources.getStringArray(R.array.number_profile)[p1])
    }

    inner class MoyenneSemestreViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val whichSmestre :TextView = itemView.findViewById(R.id.textView18)
         val moyenne :Pair<CircleProgressView , TextView> = itemView
             .findViewById<CircleProgressView>(R.id.main_profile_semestre_recyclerview_progressview) to itemView
             .findViewById(R.id.main_profile_semestre_textview)
        fun update(double: Double , text:String){
            whichSmestre.text =whichSmestre.text.toString()+text
            moyenne.setValeur(double)
        }
    }

}