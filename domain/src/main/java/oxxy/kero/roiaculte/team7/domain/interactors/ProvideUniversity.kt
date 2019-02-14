package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.SearchRepository
import javax.inject.Inject

class ProvideUniversity @Inject constructor( val repository: SearchRepository ,dispatchers: CouroutineDispatchers)
    :EitherInteractor<Int , List<Semestre> , Failure.ProvideUniversityFailure>{

    override val dispatcher= dispatchers.io
    override val ResultDispatcher =dispatchers.main
    override suspend fun invoke(executeParams: Int): Either<Failure.ProvideUniversityFailure, List<Semestre>> {
        return repository.getMattersById(executeParams)
    }
}