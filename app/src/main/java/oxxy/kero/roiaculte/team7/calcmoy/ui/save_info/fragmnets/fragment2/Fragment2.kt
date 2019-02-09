package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SaveInfoFragment2Binding
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.FACULTY
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.IMAGE
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.IMAGE_URI
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.IMAGE_URL
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.NAME
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.PRENAME
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.STAGE
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.TYPE_IMAGE
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1.Companion.YEAR
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.mapToFaculty
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.toSChool
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import java.io.File

class Fragment2 : BaseFragment(){
    companion object { fun getInstance() = Fragment2() }

    private  lateinit var binding : SaveInfoFragment2Binding
    private val viewModel: Fragment2ViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[Fragment2ViewModel::class.java] }
    private val adapter = Fragment2Adapter()
    private val listSemestre : ArrayList<Semestre> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_2,container,false)

        binding.moduleRecyclerview.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.moduleRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.moduleRecyclerview.setHasFixedSize(true)

        viewModel.observe(this){
            val semestres = it?.semestres
            when(semestres){
                is Loading<*> -> {
                    //TODO show progress  bare and hide recyclerView
                    Log.v("fucking_error","is loading now ....")
                }
                is Success<*> -> {
                    listSemestre.clear()
                    listSemestre.addAll(semestres() ?: ArrayList() )
                    handleSuccess(it.curentSemestre)
                }
                is Fail<*,*> -> handleFaillure(semestres.error)
            }
        }

        if(viewModel.firstTime){
            val name = arguments!!.getString(NAME)!!
            val prename = arguments!!.getString(PRENAME)!!
            val stage = arguments!!.getInt(STAGE).toSChool()
            var year = arguments!!.getInt(YEAR)
            val faculty =arguments?.getString(FACULTY)?.mapToFaculty(context!!)
            val imageType = arguments!!.getInt(TYPE_IMAGE)
            var image  : Image? = null

            if(imageType == IMAGE_URL ) image = Image.ImageUrl(arguments?.getString(IMAGE)!!)
            else if (imageType == IMAGE_URI) image = Image.ImageUri(Uri.fromFile(File( arguments?.getString(IMAGE))))

            viewModel.firstTime = false
            viewModel.saveDate(name,prename,year+1,stage ,faculty,image)
        }

        return binding.root
    }

    private fun handleFaillure(error: Failure) {
        onError(R.string.no_matters)
        Log.v("fucking_error","is failling now ....")
        viewModel.finichOnError()
    }

    private fun handleSuccess(curent : Int) {
        Log.v("fucking_error","is success now ....")

            binding.moduleRecyclerview.adapter = adapter
            Log.v("fucking_error","is success with out null now ....")
            val list = ArrayList<String>()
            for (i in 0 until listSemestre.size){
                list.add(getString(R.string.semestre)+(i+1))
            }
            val semestreAdapter : ArrayAdapter<String> = ArrayAdapter (context!!,android.R.layout.simple_dropdown_item_1line,list)
            binding.spinner.adapter = semestreAdapter
            binding.spinner.addOnLayoutChangeListener{ _, _, _, _, _, _, _, _, _ ->
                adapter.listOfMatters.clear()
                val position = binding.spinner.selectedItemPosition
                adapter.listOfMatters.addAll(listSemestre[position].matters)
                adapter.notifyDataSetChanged()
                Log.v("fucking_error","OnLayoutChangeListener adding ${listSemestre[position].matters.size}")
            }
            binding.spinner.setSelection(curent)


    }



}

