package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.subjects.BehaviorSubject
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.profile.ProfileUser
import oxxy.kero.roiaculte.team7.domain.interactors.profile.ProfileUserResult
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import javax.inject.Inject

class ProfileViewModel  @Inject constructor(usecase:ProfileUser):BaseViewModel<MoyenneState>(MoyenneState())
    , ProfileCallback{
    var whichSemestre = 0
    var list :List<Semestre> = emptyList()
    val semestres:MutableLiveData<List<Matter>> by lazy {
        val livedata = MutableLiveData<List<Matter>>()
        livedata
    }
    init {
        setState {
            copy(data = Loading())
        }
        scope.launchInteractor(usecase , None()){
            it.either(this::handleFailure , this::handleSuccess)
        }
    }
    private fun handleFailure(failure:Failure.DataBaseError ){
       setState {
           copy(data =Fail(failure))
       }
    }
   private  fun handleSuccess(result :ProfileUserResult){
      setState {
          copy(data = Success(None()) ,imageUrl = result.ImageUrl , name = result.name , prename = result.prename ,
              moyenne = result.moyenne , semestres = result.moyenneDeChaqueSEmestre)
      }
    }

    override fun setSemestres(list: List<Semestre>) {
        this.list= list
       semestres.value = list[whichSemestre].matters
    }

    override fun setSemestre(whichSemestre: Int) {
        Log.e("errr", whichSemestre.toString())
      this.whichSemestre = whichSemestre
        semestres.postValue(list[whichSemestre].matters)
//        semestres.value = list[whichSemestre].matters
    }
}