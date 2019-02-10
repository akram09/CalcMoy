package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
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
    private val callback : ItemTouchHelper.SimpleCallback =object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean = false

        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
            val matter = adapter.remove(p0.adapterPosition)
            Snackbar.make(p0.itemView,R.string.delete_item,Snackbar.LENGTH_LONG).setAction(R.string.cancel){
                adapter.listOfMatters.add(matter)
            }.show()
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

            RecyclerViewSwipeDecorator.Builder(context,c,binding.moduleRecyclerview,viewHolder,dX,dY,actionState,isCurrentlyActive)
                .addSwipeLeftBackgroundColor(R.color.red_delete)
                .addSwipeLeftActionIcon(R.drawable.delete)
                .addSwipeLeftLabel(getString(R.string.delete))
                .setSwipeLeftLabelColor(Color.WHITE)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
    private val itemTouchHelper = ItemTouchHelper(callback)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_2,container,false)

        binding.moduleRecyclerview.adapter = adapter
        binding.moduleRecyclerview.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        binding.moduleRecyclerview.layoutManager = LinearLayoutManager(context)
        itemTouchHelper.attachToRecyclerView(binding.moduleRecyclerview)

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
            viewModel.saveDate(name,prename,year,stage ,faculty,image)
        }

        return binding.root
    }

    private fun handleFaillure(error: Failure) {
        onError(R.string.no_matters)
        Log.v("fucking_error","is failling now ....")
        viewModel.finichOnError()
    }

    private fun handleSuccess(curent : Int) {
            val list = ArrayList<String>()
            for (i in 0 until listSemestre.size){
                list.add(getString(R.string.semestre)+(i+1))
            }
            val semestreAdapter : ArrayAdapter<String> = ArrayAdapter (context!!,R.layout.save_info_fragment_2_spinner,list)
            binding.spinner.adapter = semestreAdapter
            binding.spinner.addOnLayoutChangeListener{ _, _, _, _, _, _, _, _, _ ->
                adapter.listOfMatters.clear()
                val position = binding.spinner.selectedItemPosition
                adapter.replaceAll(listSemestre[position].matters)
                Log.v("fucking_error","OnLayoutChangeListener element in adapter  ${adapter.listOfMatters.size()}")
            }
            binding.spinner.setSelection(curent)
    }



}

