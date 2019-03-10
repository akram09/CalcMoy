package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import at.grabner.circleprogress.CircleProgressView
import com.squareup.picasso.Picasso
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMoyBinding
import oxxy.kero.roiaculte.team7.calcmoy.ui.main.MainActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.setValeur
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.setValue
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class MoyenneFragment :BaseFragment(){
    companion object {
        fun getInstance()=MoyenneFragment()
    }
lateinit var  binding :MainFragmentMoyBinding
    val viewModel :ProfileViewModel by lazy {
      ViewModelProviders.of(this, viewModelFactory)[ProfileViewModel::class.java]
    }
    val callback :ProfileCallback
    get() = viewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment_moy, container, false)
        viewModel.observe(this ){
            Log.e("errr", "entere first time")
            it!!
            when (it.data) {
                 is Fail<*,*> ->showError(it.data as Fail<None, Failure.DataBaseError>)
                is Success<*> -> showSucces(it.imageUrl , it.moyenne , it.name , it.prename )
                is Loading -> showLoading()
            }
            if(it.semestres!=null){
                Log.e("errr", "entered in success")
                Log.e("errr", it.semestres.toString())
                showMatters(it.semestres)
            }
        }
        (activity as MainActivity).callback.observeSemestre(::handleSuccess , ::handleFailure)


        return binding.root
    }

     fun showMatters(metadata: Pair<List<Double>,List<Semestre>>) {
        Log.e("errr", "hello bitch ")
        binding.mainProfileSemestreRecyclerview.apply {
//            setHasFixedSize(true)
            layoutManager =LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
            adapter = MoyennesAdapter(context , metadata.first , metadata.second)

        }

    }

    private fun handleFailure(throwable: Throwable) {

    }

    private fun handleSuccess(list: List<Semestre>) {
   callback.setSemestres(list)
    }

    private fun showLoading(){

        binding.cardView2.visibility = View.INVISIBLE
        binding.cicularProfile.visibility= View.VISIBLE
    }
    private fun showSucces(image :String , moyenne:Double , name:String , prename:String ){

        binding.cardView2.visibility = View.VISIBLE
        binding.cicularProfile.visibility= View.INVISIBLE
        if(image.isEmpty()) binding.imageView.setImageResource(R.drawable.signe_in_holder)
        else {
            Picasso.Builder(context!!).build()
                .load(image)
                .error(R.drawable.signe_in_holder)
                .placeholder(R.drawable.signe_in_holder)
                .into(binding.imageView)
        }
        (  binding.mainProfileMoygenProgressview to binding.mainProfileMoygenTextview ).setValeur(moyenne)
        binding.mainProfileNameTextview.text = "$name  , \n$prename"
        binding.mainProfileMarkTextview.text = when {
            moyenne<5.0 -> "دون المتوسط"
            moyenne<10.0 -> "متوسط"
            moyenne<14.0 -> "جيد"
            moyenne<18.0 -> "جيد جدا"
            else -> "ممتاز"
        }

        }
    private fun showError( fail:Fail<None,Failure.DataBaseError>){

    }
}
interface ProfileCallback{
   fun setSemestres(list :List<Semestre>)

}
