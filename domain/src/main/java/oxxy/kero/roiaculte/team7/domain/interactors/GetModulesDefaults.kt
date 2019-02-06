package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.CouroutineDispatchers
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.School
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.DataModelingRepository
import javax.inject.Inject

class GetModulesDefaults  @Inject constructor(val repo:DataModelingRepository, dispatchers: CouroutineDispatchers )
    :EitherInteractor<GetModulesDefaultParam, List<Semestre>, Failure.GetModulesDEfaultFailure> {

    override val dispatcher = dispatchers.io
    override val ResultDispatcher= dispatchers.main
    override suspend fun invoke(executeParams: GetModulesDefaultParam): Either<Failure.GetModulesDEfaultFailure, List<Semestre>> {
          return repo.getDefaultModules(executeParams)
    }
}
data class GetModulesDefaultParam(val year:Int, val school: School, val facultyType: FacultyType?)