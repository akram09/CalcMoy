package oxxy.kero.roiaculte.team7.calcmoy.ui.main.moyennefragment

import oxxy.kero.roiaculte.team7.calcmoy.base.BaseViewModel
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.launchInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.profile.ProfileUser
import oxxy.kero.roiaculte.team7.domain.interactors.profile.ProfileUserResult
import javax.inject.Inject

class ProfileViewModel  @Inject constructor(usecase:ProfileUser):BaseViewModel<MoyenneState>(MoyenneState())
    , ProfileCallback{
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
}