package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class MainInfo
    @Inject constructor( dispatchers: CouroutineDispatchers ,val repo :MainRepository)
    : EitherInteractor<None ,MainInfoResult , Failure.MainInfoFailure > {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.MainInfoFailure, MainInfoResult> {
     return repo
 }
}
data class MainInfoResult(val SemestreNumber:Int , val semstres :List<Semestre> , val events: List<Event> )