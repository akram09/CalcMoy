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

    /**
     * this will store the current list of semestre  that we observe from the mainactivity
     * here we have to liste because we have 2 sources  and we donnt know who will arrive the first
     * so we  store them untill both of them are ready and we modify the state
     * @author akramkero
     */
    var list :List<Semestre> = emptyList()
    var listMoyenne :List<Double> = emptyList()

    init {
        /**
         * this will some loading action while we send the request to get profileInfo
          */
        setState {
            copy(data = Loading())
        }
        scope.launchInteractor(usecase , None()){
            it.either(this::handleFailure , this::handleSuccess)
        }
    }

    /**
     * handle getProfile Data failure
     */
    private fun handleFailure(failure:Failure.DataBaseError ){
       setState {
           copy(data =Fail(failure))
       }
    }

    /**
     * handle getProfile Data Success
     * and configure the state so that the view can change now
     */
   private  fun handleSuccess(result :ProfileUserResult){

        if(list.isEmpty()){
            Log.e("errr", "moyenne came first ")
            listMoyenne = result.moyenneDeChaqueSEmestre
            setState {
                copy(data = Success(None()) ,imageUrl = result.ImageUrl , name = result.name , prename = result.prename ,
                    moyenne = result.moyenne )
            }
        }else{
            listMoyenne = result.moyenneDeChaqueSEmestre
            setState {
                copy(data = Success(None()) ,imageUrl = result.ImageUrl , name = result.name , prename = result.prename ,
                    moyenne = result.moyenne  , semestres = listMoyenne to list, isLoading = false
                )
            }
        }

    }

    /**
     * this function is the implementation of the function  defined in the callback in the fragment
     * its is here to set the list of smestres
     * we check firstly if the moyennelist is empty if it is empty we cant modify the state until both listes are ready
     * if not we mofify the state
     */
    override fun setSemestres(list: List<Semestre>) {
       if(listMoyenne.isEmpty()){
           this.list = list
       }else{
           this.list = list
           setState {
               copy(semestres = listMoyenne to list , isLoading = false)
           }
       }
    }

}