package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2

import android.util.Log
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.ui.registration.LOGIN
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Image
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaultParam
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaults
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class Fragment2ViewModel @Inject constructor(private val getDefaultMatters : GetModulesDefaults)
    : BaseViewModel<Fragment2State>(Fragment2State(Loading(),null,0)){

    private lateinit var name: String
    private lateinit var prenam: String
    private var year: Int =0
    private lateinit var school : School
    private var facultyType: FacultyType? = null

    var firstTime  =true

    fun saveDate(name : String , prenam : String, year : Int , school : School, facultyType: FacultyType?, image : Image?){

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
        if(image != null) setState { Fragment2State(this.semestres,image,this.curentSemestre) }

        if(year <3 && year != 0) { //(year =0) university
            scope.launchInteractor(getDefaultMatters, GetModulesDefaultParam(year, school, facultyType)) {
                it.either(::handleDefaultFaillure, ::handleDafaultSuccess)
            }
        }else  setState { Fragment2State(Success(listOf(Semestre(1, emptyList()))),this.image,0) }
    }

    private fun handleDafaultSuccess(list: List<Semestre>) {
        setState { Fragment2State(Success(list),this.image,this.curentSemestre) }
    }

    private fun handleDefaultFaillure(getModulesDEfaultFailure: Failure.GetModulesDEfaultFailure) {
        setState { Fragment2State(Fail(getModulesDEfaultFailure),this.image,this.curentSemestre) }
    }

    fun finichOnError() {
        //TODO change it for search later
        setState {
            Log.v("fucking_error","is finiching failling now ....")
            Fragment2State(Success(listOf(Semestre(1, emptyList()))),this.image,0)
        }
    }
}