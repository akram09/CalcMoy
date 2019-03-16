package oxxy.kero.roiaculte.team7.calcmoy.ui.main.modulesfragment

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.domain.models.Matter

class SemestreAdapter(val context:Context ,val size :Int, val whichOne:Int  , val onclick:(Int)->Unit ) :RecyclerView.Adapter<SemestreAdapter.SemestreViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SemestreViewHolder {
     return SemestreViewHolder(LayoutInflater.from(context).inflate(R.layout.main_fragment_main_sem_card, p0 , false))
    }

    override fun getItemCount()= size

    override fun onBindViewHolder(p0: SemestreViewHolder, p1: Int) {
       p0.update(context.resources.getStringArray(R.array.number_profile)[p1] , p1==whichOne)
        p0.itemView.setOnClickListener {
            onclick(p1)
        }
    }

    inner class  SemestreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private  val cardView :CardView  = itemView.findViewById(R.id.main_modules_semestre_card)
        private val textView :TextView = itemView.findViewById(R.id.textView9)
       fun update(string :String  , boolean: Boolean){
          if(boolean){
              cardView.setCardBackgroundColor(context.resources.getColor(R.color.green))
              cardView.cardElevation = 0f
          }
           textView.text = textView.text.toString() + string
       }

    }
}