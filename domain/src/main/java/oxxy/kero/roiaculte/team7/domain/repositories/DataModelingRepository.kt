package oxxy.kero.roiaculte.team7.domain.repositories

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaultParam
import oxxy.kero.roiaculte.team7.domain.models.Semestre

interface DataModelingRepository {
    fun getDefaultModules(executeParams: GetModulesDefaultParam): Either<Failure.GetModulesDEfaultFailure, List<Semestre>>
}