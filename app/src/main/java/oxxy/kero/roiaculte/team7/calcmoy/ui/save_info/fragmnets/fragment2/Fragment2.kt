package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.app.AlertDialog
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ContentResolver
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.*
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.DialogueAddModuleBinding
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SaveInfoFragment2Binding
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
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
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.*
import oxxy.kero.roiaculte.team7.data.firebase.UserId
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import java.io.File
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.roundToInt

class Fragment2 : BaseFragment() , SaveInfoActivity.Fragment2CallbackkFromActivity{
    companion object { fun getInstance() = Fragment2() }

    private  lateinit var binding : SaveInfoFragment2Binding
    private val viewModel: Fragment2ViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[Fragment2ViewModel::class.java] }
    private val callbackFromViewModel : CalbackFromViewModel by lazy { viewModel }
    private val adapter : Fragment2Adapter by lazy { Fragment2Adapter(userId.id,viewModel) }
    private val callback : ItemTouchHelper.SimpleCallback =object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean = false

        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
            val matter  = adapter.listOfMatters[p0.adapterPosition]
            val pos = binding.spinner.selectedItemPosition
            callbackFromViewModel.removeMatter(matter,pos)
            Log.v("fucking_save_info","semstres --> ${matter.semestre}")
            Snackbar.make(p0.itemView,R.string.delete_item,Snackbar.LENGTH_LONG).setAction(R.string.cancel){
                callbackFromViewModel.addMatter(matter)
            }.show()
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

            RecyclerViewSwipeDecorator.Builder(context,c,binding.moduleRecyclerview,viewHolder,dX,dY,actionState,isCurrentlyActive)
                .addSwipeLeftBackgroundColor(R.color.red_delete)
                .addSwipeRightBackgroundColor(R.color.red_delete)
                .addSwipeLeftActionIcon(R.drawable.delete)
                .addSwipeLeftLabel(getString(R.string.delete))
                .setSwipeLeftLabelColor(Color.WHITE)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
    private val itemTouchHelper = ItemTouchHelper(callback)
    private var dialogueBinding : DialogueAddModuleBinding? =null
    @Inject lateinit var userId : UserId


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_2,container,false)
        binding.moduleRecyclerview.adapter = adapter
        binding.moduleRecyclerview.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        binding.moduleRecyclerview.layoutManager = LinearLayoutManager(context)
        itemTouchHelper.attachToRecyclerView(binding.moduleRecyclerview)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.spinner.addOnLayoutChangeListener{ _, _, _, _, _, _, _, _, _ ->

            val position = binding.spinner.selectedItemPosition
            if (position != -1 ) {
                viewModel.withState {
                    changeAll(it.semestres[position].matters)
                    callbackFromViewModel.setCurentSemstre(position)
                }
            }
        }

        viewModel.observe(this){
            it?.also {
                when (it.mattersState) {
                    is Loading<*> -> { showLoading(true) }
                    is Success<*> -> { showLoading(false) }
                }
                setUpRecyclerView(it.semestres , viewModel.curent)
                setUpImage(it.image)
                if(it.saveInfoState is Success) goToMain()
            }
        }

        if(viewModel.firstTime){ saveDataToViewModel() }

        binding.addMatter.setOnClickListener{ addMater() }
        binding.addSemestre.setOnClickListener{ binding.spinner.setSelection(callbackFromViewModel.addEmptySemestre()) }
        return binding.root
    }

    private fun goToMain() {
        (activity as? SaveInfoActivity)?.goToMain()
    }

    private fun changeAll(matters: MutableList<Matter>) {
        adapter.replaceAll(matters)
    }

    private fun showLoading(b: Boolean) {
        if(b){
            binding.loading.visible()
            binding.moduleRecyclerview.invisible()
            binding.spinner.isClickable = false
        }else {
            binding.loading.invisible()
            binding.moduleRecyclerview.visible()
            binding.spinner.isClickable = true
        }
    }

    private fun saveDataToViewModel() {
        val name = arguments!!.getString(NAME)!!
        val prename = arguments!!.getString(PRENAME)!!
        val stage = arguments!!.getInt(STAGE).toSChool()
        val year = arguments!!.getInt(YEAR)
        val faculty =arguments?.getString(FACULTY)?.mapToFaculty(context!!)
        val imageType = arguments!!.getInt(TYPE_IMAGE)
        var image  : Image? = null

        if(imageType == IMAGE_URL ) image = Image.ImageUrl(arguments?.getString(IMAGE)!!)
        else if (imageType == IMAGE_URI) image = Image.ImageUri(Uri.fromFile(File( arguments?.getString(IMAGE))))

        viewModel.firstTime = false
        callbackFromViewModel.saveDate(name,prename,userId.id,year,stage ,faculty,image)
    }

    private fun addMater() {
        var alertDialog : AlertDialog? =null
        val builder = AlertDialog.Builder(context)
        dialogueBinding  = DataBindingUtil.inflate(layoutInflater,R.layout.dialogue_add_module,null,false)
        builder.setView(dialogueBinding!!.root)
        dialogueBinding!!.addmodulePickColor.setOnClickListener{pickColor()}
        dialogueBinding!!.addmoduleColor.setImageDrawable(ColorDrawable(resources.getColor(R.color.default_matter_color)))
        dialogueBinding!!.addMatterSave.setOnClickListener{
            val name = dialogueBinding!!.addmoduleName.text.toString()
            val coi = dialogueBinding!!.addmoduleCoei.text.toString()
            val color = (dialogueBinding!!.addmoduleColor.drawable as? ColorDrawable)?.color ?: resources.getColor(R.color.default_matter_color)

            if(TextUtils.isEmpty(name)){ onError(R.string.matter_empty) ; return@setOnClickListener }
            if(TextUtils.isEmpty(coi)){ onError(R.string.coif_empty) ; return@setOnClickListener}

            val colorHex = "#${Integer.toHexString(color)}"
            val matter = Matter(0,name,coi.toInt(),colorHex,binding.spinner.selectedItemPosition,0.0,userId.id)
            callbackFromViewModel.addMatter(matter)
        }
        alertDialog  = builder.create()
        alertDialog.show()
    }

    private fun pickColor() {
        ColorPickerDialog.newBuilder()
            .setDialogTitle(R.string.color_pick)
            .setAllowCustom(true)
            .setAllowPresets(true)
            .show(activity)
    }

    override fun colorSelected(color: Int) {
        dialogueBinding?.addmoduleColor?.setImageDrawable(ColorDrawable(color))
    }

    private fun setUpImage(image: Image?) {
        when(image){
            is Image.ImageUri ->{/*TODO load image uri*/}
            is Image.ImageUrl -> {/* Picasso */}
        }
    }

    private fun setUpRecyclerView(semestres: List<Semestre>, curentSemestre: Int) {
        Log.v("fucking_error", "is set up recycler with ${semestres.size}.")
        val list = ArrayList<String>()
        for (i in 0 until semestres.size){
            list.add(getString(R.string.semestre)+(i+1))
        }
        val semestreAdapter : ArrayAdapter<String> = ArrayAdapter (context!!,R.layout.save_info_fragment_2_spinner,list)
        binding.spinner.adapter = semestreAdapter
        binding.spinner.setSelection(curentSemestre)
    }

    override fun getUniversity(data: String) {
        try {
            callbackFromViewModel.loadUniversityMatters(data.toInt())
        }catch (e : Exception){
            onError(R.string.university_not_found)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.save_info_menu, menu)

        menu.setGroupVisible(R.id.search_group,viewModel.showSearch)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId  == android.R.id.home ) close()
        else if(item?.itemId == R.id.next){
            viewModel.withState {
                if(it.image != null && it.image is Image.ImageUri){
                    loadImage()
                }else {
                    callbackFromViewModel.saveSemestresToRemote(false)
                }
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadImage() {
        val builder = AlertDialog.Builder(context)

        val view = LayoutInflater.from(context).inflate(R.layout.load_image_progress,null)
        builder.setView(view)
        builder.setPositiveButton(R.string.cancel){_,_->
            callbackFromViewModel.cancelLoadImage()
        }
        builder.setTitle(R.string.upload_img)

        val progress = view.findViewById<ProgressBar>(R.id.loading_image)
        val percentage = view.findViewById<TextView>(R.id.loadig_percentage)

        viewModel.observeLoadingImage(this, Observer {
            when(it){
                is Success -> {
                    val percen = it()
                    Log.v("fucking_loading","Success --> $percen")
                    percentage.text = percen.toString()
                    progress.progress = percen.roundToInt()
                }
                is Failure -> onError(R.string.faill_image)
            }
        })
        builder.show()
        callbackFromViewModel.saveImageToRemote(activity!!.contentResolver,"${userId.id}.png")

    }

    interface CalbackFromViewModel {
        fun setCurentSemstre(curent: Int)
        fun addEmptySemestre() : Int
        fun addMatter(matter : Matter )
        fun removeMatter(matter : Matter ,curent: Int )
        fun removeSemestre(position:Int)
        fun saveDate(name : String, prenam : String,id: String, year : Int, school : School, facultyType: FacultyType?, image : Image?)
        fun loadUniversityMatters(id : Int)
        fun saveImageToRemote(contentResolver: ContentResolver,name :String)
        fun saveSemestresToRemote(hasSubmitImg :Boolean)
        fun cancelLoadImage()
    }
}

