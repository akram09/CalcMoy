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
    val int  = ((double *10).toInt()/10.0)
    this.first.setValue(int.toFloat())
    var valeur :Int
    try{
        valeur =int.toInt()
        this.second.text= valeur.toString()
    }catch (e:TypeCastException){
        this.second.text = int.toString()
    }

}