package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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
    val whichSemestre :MutableLiveData<Int> by lazy {
        val livedata= MutableLiveData<Int>()
        livedata.value = 0
        livedata
    }
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
       semestres.value = list[whichSemestre.value!!].matters
    }

    override fun setSemestre(whichSemestre: Int) {
      this.whichSemestre.value= whichSemestre
    }
}