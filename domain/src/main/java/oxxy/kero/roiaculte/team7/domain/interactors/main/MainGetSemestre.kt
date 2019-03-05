package oxxy.kero.roiaculte.team7.domain.interactors.main

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.EitherInteractor
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.MainRepository
import javax.inject.Inject

class MainGetSemestre  @Inject constructor(dispatchers: CouroutineDispatchers , val repo: MainRepository)
    :
    EitherInteractor<None, MainGetSemestreResult, Failure.MainInfoFailure> {
    override val dispatcher =dispatchers.io
    override val ResultDispatcher = dispatchers.main
    override suspend fun invoke(executeParams: None): Either<Failure.MainInfoFailure, MainGetSemestreResult> {
          return repo.getMainInfoSemestre()
     }
}
data class MainGetSemestreResult(val numberSemestre :Int , val semestres: List<Semestre>)
