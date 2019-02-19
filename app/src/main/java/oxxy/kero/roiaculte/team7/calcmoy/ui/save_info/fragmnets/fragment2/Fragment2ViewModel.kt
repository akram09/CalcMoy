package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.oneElement
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class Fragment2ViewModel @Inject constructor(private val getDefaultMatters : GetModulesDefaults,
                                             private val getUniversityMatters: ProvideUniversity,
                                             private val saveUser : SaveUser)
    : BaseViewModel<Fragment2State>(Fragment2State(Loading(),ArrayList(),null)),
    Fragment2.CalbackFromViewModel{

    private val loadImageState : LiveData<Async<None>> by lazy {
        val liveData = MutableLiveData<Async<None>>()
        liveData.value = Loading()
        liveData
    }

    private lateinit var name: String
    private lateinit var prenam: String
    private var year: Int =0
    private lateinit var school : School
    private var facultyType: FacultyType? = null

    var curent : Int = 0

    var firstTime  =true
    var showSearch = false

    override fun saveDate(name : String , prenam : String, year : Int , school : School, facultyType: FacultyType?, image : Image?){

        this.name = name
        this.prenam = prenam
        this.year = year
        this.school = school
        this.facultyType = facultyType

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

    override fun saveImageToRemote() {
        withState {
            saveUser.observe((it.image!! as Image.ImageUrl).url,::onLoadImageFaill,::onLoadIMageSuccess,::onLoadImageComplete)
        }
    }

    private fun onLoadImageComplete() {

    }

    private fun onLoadIMageSuccess(d: @ParameterName(name = "t") Double) {

    }

    private fun onLoadImageFaill(throwable: @ParameterName(name = "e") Throwable) {

    }

    override fun saveSemestresToRemote() {

    }

    override fun cancelLoadImage() {

    }

    fun updateMatter(matter: Matter, adapterPosition: Int) {
        setState {
            val list = this.semestres.toMutableList()
            list[matter.semestre].matters.removeAt(adapterPosition)
            list[matter.semestre].matters.add(adapterPosition,matter)
            copy(semestres = list)
        }
    }
    private fun setLoadImageState(changer :Async<None>.()->Async<None> ){

    }
}