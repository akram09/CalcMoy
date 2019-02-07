package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
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
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.toSChool
import java.lang.Exception
import javax.inject.Inject

class Fragment1 : BaseFragment() {

    companion object { fun getInstance() = Fragment1() }

    @Inject lateinit var appContext : Context
    private lateinit var binding : SaveInfoFragment1Binding
    private val viewModel : Fragment1ViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[Fragment1ViewModel::class.java] }
    private var imageUri : Uri? = null

    private var firstTime = true


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
                is Image.ImageUrl -> Picasso.get().load(image.url).into(object : Target{
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        binding.signeInImage.setImageBitmap(bitmap)
                    }
                })
                is Image.ImageUri -> binding.signeInImage.setImageURI(image.uri)
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

        return binding.root
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