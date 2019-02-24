package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaultParam
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.SaveUserParam
import oxxy.kero.roiaculte.team7.domain.models.Semestre

interface DataModelingRepository {
    suspend fun getDefaultModules(executeParams: GetModulesDefaultParam): Either<Failure.GetModulesDEfaultFailure, List<Semestre>>
    fun updateFile(p: ByteArray): Observable<Double>
    suspend fun saveUser(executeParams: SaveUserParam): Either<Failure.SaveUserFailure, None>
}