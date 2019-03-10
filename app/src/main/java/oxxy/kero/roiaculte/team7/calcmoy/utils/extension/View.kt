package oxxy.kero.roiaculte.team7.calcmoy.utils.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView


fun View.cancelTransition() {
    transitionName = null
}


fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Pair<CircleProgressView, TextView>.setValeur(double :Double){
    val entier = double *10
    if(entier.toInt() % 10==0){
       val valeur = double.toInt()
        this.first.setValue(valeur.toFloat())
        this.second.text = valeur.toString()
    }else{
     val valeur = entier.toInt() /10.0
        this.first.setValue(valeur.toFloat())
        this.second.text = valeur.toString()

    }
}
