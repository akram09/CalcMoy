package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import io.reactivex.disposables.Disposable
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.oneElement
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import javax.inject.Inject
import android.graphics.Bitmap
import oxxy.kero.roiaculte.team7.domain.models.*
import java.io.ByteArrayOutputStream

class Fragment2ViewModel @Inject constructor(private val getDefaultMatters : GetModulesDefaults,
                                             private val getUniversityMatters: ProvideUniversity,
                                             private val saveUser : SaveUser)
    : BaseViewModel<Fragment2State>(Fragment2State(Loading(),ArrayList(),null,Loading())),
    Fragment2.CalbackFromViewModel{

    private val loadImageState : MutableLiveData<Async<Double>> by lazy {
        val liveData = MutableLiveData<Async<Double>>()
        liveData.value = Loading()
        liveData
    }

    private var disposable : Disposable? = null
    private var loadImageObserver : Observer<Async<Double>>? = null

    private lateinit var name: String
    private lateinit var prenam: String
    private var year: Int =0
    private lateinit var school : School
    private var facultyType: FacultyType? = null
    private  lateinit var userId : String

    var curent : Int = 0

    var firstTime  =true
    var showSearch = false

    override fun saveDate(name : String , prenam : String,id: String, year : Int , school : School, facultyType: FacultyType?, image : Image?){

        this.name = name
        this.prenam = prenam
        this.year = year
        this.school = school
        this.facultyType = facultyType
        this.userId =id

        Log.v("fucking_error","name --> $name")
        Log.v("fucking_error","prenam --> $prenam")
        Log.v("fucking_error","year --> $year")
        Log.v("fucking_error","school --> $school")
        Log.v("fucking_error","faculté type --> $facultyType")
        if(image != null) setState { copy(image = image) }

        if(school != School.UNIVERSITE) {
            scope.launchInteractor(getDefaultMatters, GetModulesDefaultParam(year, school, facultyType)) {
                it.either(::handleDefaultFaillure, ::handleDafaultSuccess)
            }
        }else {
            showSearch = true
            setState { copy(mattersState =Success(None()),semestres = listOf(Semestre(1, ArrayList()))) }
        }
    }

    private fun handleDafaultSuccess(list: List<Semestre>) {
        setState { copy(mattersState =Success(None()),semestres = list) }
    }

    private fun handleDefaultFaillure(getModulesDEfaultFailure: Failure.GetModulesDEfaultFailure) {
        setState { copy(mattersState = Success(None()),semestres =ArrayList<Semestre>().oneElement(Semestre(0, ArrayList() )) ) }
    }

    override fun setCurentSemstre(curent: Int) { this.curent = curent }

    override fun addEmptySemestre(): Int {
        var size = 0
        withState {
            val  list = it.semestres.toMutableList()
            size  = list.size
            list.add(Semestre(size,ArrayList()))
            setState { copy(semestres = list) }
        }
        return size
    }

    override fun addMatter(matter: Matter) {
        withState {
            if(matter.semestre>-1 && matter.semestre<it.semestres.size){
                val semestre = it.semestres
                semestre[matter.semestre].matters.add(matter)
                setState { copy(semestres =semestres ) }
            }
        }
    }

    override fun removeMatter(matter: Matter, curent: Int) {
        withState {
            if(curent>-1 && curent<it.semestres.size){
                val semestre = it.semestres
                if (semestre[curent].matters.contains(matter)) semestre[curent].matters.remove(matter)
                setState { copy(semestres =semestres ) }
            }
        }
    }

    override fun removeSemestre(position: Int) {
        withState {
            if(position>-1 && position<it.semestres.size){
                val list = it.semestres.toMutableList()
                var curent = this.curent
                list.removeAt(position)
                if(curent>=position ) curent-=1
                setState { copy(semestres = list)  }
                this.curent = curent
            }
        }
    }

    override fun loadUniversityMatters(id: Int) {
        scope.launchInteractor(getUniversityMatters,id){
            it.either(::handleGetUniversutyMatterFailure,::handleGetUniversutyMatterSuccess)
        }
    }

    private fun handleGetUniversutyMatterSuccess(list: List<Semestre>) {
        curent = 0
        setState { copy(semestres = list) }
    }

    private fun handleGetUniversutyMatterFailure(provideUniversityFailure: Failure.ProvideUniversityFailure) {
        
    }

    override fun saveImageToRemote(contentResolver: ContentResolver,name :String) {
        withState {
            val uri = (it.image!! as Image.ImageUri).uri
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val baos =ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            Log.v("fucking_loading","path --> ${uri.path}")

            disposable =saveUser.observe(data,::onLoadImageFaill,::onLoadIMageSuccess,::onLoadImageComplete)
        }
        Log.v("fucking_loading","saving image to remote")
    }

    private fun onLoadImageComplete() {
        Log.v("fucking_loading","OnComplete")
        loadImageState.value = Success(100.0)
        saveSemestresToRemote(true)
    }

    private fun onLoadIMageSuccess(d:  Double) {
        Log.v("fucking_loading","OnSuccess")
        loadImageState.value = Success(d)
    }

    private fun onLoadImageFaill(throwable: Throwable) {
        Log.v("fucking_loading","Faill")
        Log.v("fucking_loading",throwable.message)
        throwable.printStackTrace()
        loadImageState.value = Fail(Failure.SaveImageFailure.UknownFailure(null))
    }

    override fun saveSemestresToRemote(hasSubmitImg :Boolean) {
        withState {
            var imgUrl  =""
            if(it.image  != null && it.image is Image.ImageUrl ) imgUrl  = it.image.url
            val user= User(userId,name,prenam,school,year.toString(),it.semestres.size,imgUrl,0.0)
            scope.launchInteractor(saveUser, SaveUserParam(hasSubmitImg,user,it.semestres)){ it.either(::handleSaveInfoFaillure,::handleSaveInfoSuccess) }
        }
    }

    private fun handleSaveInfoSuccess(none: None) {
        Log.v("fucking_save_info","onSuccess we should go to main")
        setState{ copy(saveInfoState =  Success(None())) }
    }

    private fun handleSaveInfoFaillure(saveUserFailure: Failure.SaveUserFailure) {
        Log.v("fucking_save_info","onSaveInfoError !!!")
        setState { copy(saveInfoState = Fail(saveUserFailure)) }
    }

    override fun cancelLoadImage() {
        Log.v("fucking_save_info","loadImage cancceld")
        disposable?.dispose()
        loadImageState.removeObserver(loadImageObserver!!)
    }

    fun observeLoadingImage(owner: LifecycleOwner,observer: Observer<Async<Double>>){
        loadImageObserver = observer
        loadImageState.observe(owner,observer)
    }

    fun updateMatter(matter: Matter, adapterPosition: Int) {
        setState {
            val list = this.semestres.toMutableList()
            list[matter.semestre].matters.removeAt(adapterPosition)
            list[matter.semestre].matters.add(adapterPosition,matter)
            copy(semestres = list)
        }
    }
}