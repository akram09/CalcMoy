package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.SaveInfoFragment1Binding
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.toInt
import com.squareup.picasso.Target
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.SaveInfoActivity
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.toSChool
import java.lang.Exception
import javax.inject.Inject

class Fragment1 : BaseFragment() {

    companion object {
        fun getInstance() = Fragment1()
        const val IMAGE_URI =0
        const val IMAGE_URL =1
        const val IMAGE_DEFAULT =1

        const val NAME = "name"
        const val PRENAME = "prename"
        const val STAGE = "stage"
        const val YEAR = "year"
        const val FACULTY = "faculty"
        const val TYPE_IMAGE ="type_of_image"
        const val IMAGE = "image"
    }

    @Inject lateinit var appContext : Context
    private lateinit var binding : SaveInfoFragment1Binding
    private val viewModel : Fragment1ViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[Fragment1ViewModel::class.java] }
    private var imageUri : Uri? = null

    private var firstTime = true
    private var  which1 = 0
    private var which2 = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment_1,container,false)

        viewModel.setInfo()

        viewModel.observe(this){
            binding.signeInName.setText(it?.name ?: "")
            binding.signeInPrenom.setText(it?.prename ?: "")
            binding.signeInStage.setSelection(it?.school?.toInt() ?: 0)
            binding.signeInYear.setSelection(it?.year ?: 0)

            val image = it?.image
            when(image){
                is Image.ImageUrl -> if(image.url != "" )Picasso.get().load(image.url).into(object : Target{
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        binding.signeInImage.setImageBitmap(bitmap)
                    }
                })
                is Image.ImageUri -> {
                    imageUri = image.uri
                    binding.signeInImage.setImageURI(imageUri)
                }
            }
        }

        binding.signeInStage.addOnLayoutChangeListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int ->
            if(! firstTime) binding.signeInYear.setSelection(0)
            else viewModel.withState { binding.signeInYear.setSelection(it.year) }

            setYears(binding.signeInStage.selectedItemPosition,binding.signeInYear.selectedItemPosition)
            firstTime = false
        }

        binding.signeInImage.setOnClickListener{
            CropImage.activity()
                .setCropShape ( CropImageView.CropShape.OVAL )
                .setAspectRatio(1,1 )
                .start(context!!,this)
        }

        binding.signeInNextBtn.setOnClickListener(){

            val name = binding.signeInName.text.toString()
            val prenam = binding.signeInPrenom.text.toString()

            if(TextUtils.isEmpty(name)){
                onError(R.string.name_empty)
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(prenam) ){
                onError(R.string.prenam_empty)
                return@setOnClickListener
            }

                val stage = binding.signeInStage.selectedItemPosition
                val year  = binding.signeInYear.selectedItemPosition

                val bundlle  = Bundle()
                bundlle.putString(NAME,name)
                bundlle.putString(PRENAME,prenam)
                bundlle.putInt(YEAR,year)
                bundlle.putInt(STAGE,stage)

                //saving image in bundlle
                if(imageUri != null) {
                    bundlle.putInt(TYPE_IMAGE, IMAGE_URI)
                    bundlle.putString(IMAGE,imageUri!!.path)
                }else{
                    viewModel.withState {
                        val img= it.image
                        if(it.image != null && img is Image.ImageUrl){
                            bundlle.putInt(TYPE_IMAGE, IMAGE_URL)
                            bundlle.putString(IMAGE,img.url)
                        }else{
                            bundlle.putInt(TYPE_IMAGE, IMAGE_DEFAULT)
                        }
                    }
                }

                if(stage == 0 || stage == 1 || stage == 3) (activity as? SaveInfoActivity)?.loadFragment2(bundlle)
                else if(stage == 2 &&  year == 0) showDialogueLicy1(bundlle)
                else showDialogueLicy2_3(bundlle)


        }

        return binding.root
    }

    private fun showDialogueLicy2_3(bundlle: Bundle) {
        which1 =0
        val list = arrayOf(getString(R.string.scien),getString(R.string.lettre))
        val builder = AlertDialog.Builder ( context!! )
        builder.setTitle ( getString(R.string.choose_faculty) )
        builder.setSingleChoiceItems ( list , 0 ){ _, i: Int -> which1 = i }
        builder.setPositiveButton (  R.string.confirme ){ _, _ ->
            which2 = 0
            val builder2 = AlertDialog.Builder ( context!! )
            builder2.setTitle ( R.string.choose_faculty )
            var list2 : Array<String>
            if(list[which1] == getString(R.string.scien) ) {
                list2 = arrayOf( getString(R.string.math) , getString(R.string.scien_taj) , getString(R.string.economi) , getString(R.string.tm_electrique) , getString(R.string.tm_mecanique) , getString(R.string.tm_civil) , getString(R.string.tm_tar) )
            }else {
                list2 = arrayOf(getString(R.string.language) , getString(R.string.philo) )
            }

            builder2.setSingleChoiceItems ( list2 , 0 ){_,i:Int -> which2 =i }

            builder2.setPositiveButton ( R.string.confirme ){_,_ ->
                bundlle.putString(FACULTY,list2[which2])
                (activity as? SaveInfoActivity)?.loadFragment2(bundlle)
            }

            builder2.show ()
        }

        builder.show ()
    }

    private fun showDialogueLicy1(bundlle: Bundle) {
        which1 =0
        val list = arrayOf(getString(R.string.scien),getString(R.string.lettre))
        val builder = AlertDialog.Builder ( context!! )
        builder.setTitle ( getString(R.string.choose_faculty) )
        builder.setSingleChoiceItems ( list , 0 ){ _, i: Int -> which1 = i }
        builder.setPositiveButton ( getString(R.string.confirme) ){ _, _ ->
            bundlle.putString(FACULTY,list[which1])
            (activity as? SaveInfoActivity)?.loadFragment2(bundlle)
        }
        builder.show ()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                val result :CropImage.ActivityResult = CropImage.getActivityResult(data)
                imageUri = result.uri
                binding.signeInImage.setImageURI(imageUri)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //save info in view model in case configuration changed
        viewModel.setState {
            Fragment1State(
                binding.signeInName.text.toString(),
                binding.signeInPrenom.text.toString(),
                binding.signeInStage.selectedItemPosition.toSChool(),
                binding.signeInYear.selectedItemPosition,
                if (imageUri == null) this.image else Image.ImageUri(imageUri!!)
            )
        }
    }


    private fun setYears(school :Int ,year : Int) {

        when(school){
            0 -> {
                val primair = arrayOf(getString(R.string.pr_1), getString(R.string.pr_2), getString(R.string.pr_3), getString(R.string.pr_4),
                    getString(R.string.pr_5))
                val adapter : ArrayAdapter<String> = ArrayAdapter (appContext,android.R.layout.simple_dropdown_item_1line,primair)
                binding.signeInYear.isEnabled = true
                binding.signeInYear.adapter = adapter
                binding.signeInYear.setSelection(year)
            }

            1 -> {
                val cem = arrayOf(getString(R.string.cem_1), getString(R.string.cem_2), getString(R.string.cem_3), getString(R.string.cem_4))
                val adapter : ArrayAdapter<String> = ArrayAdapter (appContext,android.R.layout.simple_dropdown_item_1line,cem)
                binding.signeInYear.isEnabled = true
                binding.signeInYear.adapter = adapter
                binding.signeInYear.setSelection(year)
            }

            2-> {
                val licy = arrayOf(getString(R.string.licy_1), getString(R.string.licy_2), getString(R.string.licy_3))
                val adapter : ArrayAdapter<String> = ArrayAdapter (appContext,android.R.layout.simple_dropdown_item_1line,licy)
                binding.signeInYear.isEnabled = true
                binding.signeInYear.adapter = adapter
                binding.signeInYear.setSelection(year)
            }

            3-> {
                binding.signeInYear.isEnabled = false
                binding.signeInYear.adapter = null
            }
        }

    }
}