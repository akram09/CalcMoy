package oxxy.kero.roiaculte.team7.calcmoy.utils.extension

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView
import oxxy.kero.roiaculte.team7.calcmoy.R


fun View.cancelTransition() {
    transitionName = null
}


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Pair<CircleProgressView, TextView>.setValeur(double :Double , context:Context){
    val entier = double *10
    if(entier.toInt() % 10==0){
       val valeur = double.toInt()
        this.first.setValue(valeur.toFloat())
        this.first.setBarColor( context.resources.getColor(when {
            valeur<5.0 -> {
                R.color.red_delete
            }
            valeur<10.0 -> R.color.blue
            valeur<14.0 -> R.color.green
            valeur<18.0 -> R.color.black_green
            else -> R.color.yellow
        }))
        this.second.text = valeur.toString()
       this.second.setTextColor(context.resources.getColor( when {
           double<5.0 -> {
               R.color.red_delete
           }
           double<10.0 -> R.color.blue
           double<14.0 -> R.color.green
           double<18.0 ->R.color.black_green
           else ->R.color.yellow
       }))
    }else{
     val valeur = entier.toInt() /10.0
        this.first.setValue(valeur.toFloat())
        this.second.text = valeur.toString()
        this.first.setBarColor(context.resources.getColor( when {
            valeur<5.0 -> {
                R.color.red_delete
            }
            valeur<10.0 -> R.color.blue
            valeur<14.0 -> R.color.green
            valeur<18.0 -> R.color.black_green
            else -> R.color.yellow
        }))
        this.second.setTextColor(context.resources.getColor( when {
            double<5.0 -> {
                R.color.red_delete
            }
            double<10.0 -> R.color.blue
            double<14.0 -> R.color.green
            double<18.0 ->R.color.black_green
            else ->R.color.yellow
        }))

    }
}
