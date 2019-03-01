package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class ProfileUser   @Inject constructor(dispatchers: CouroutineDispatchers ,
                                        val repo:MainRepository):EitherInteractor<None , ProfileUserResult , Failure.DataBaseError> {
    override val dispatcher = dispatchers.io
    override val ResultDispatcher = dispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.DataBaseError, ProfileUserResult> {
          return repo.getProfileInfo()
    }
}
data class ProfileUserResult(val name :String , val prename :String , val ImageUrl:String
                             , val moyenne :Double , val numberSemestre:Int , val moyenneDeChaqueSEmestre:List<Double>
                              )