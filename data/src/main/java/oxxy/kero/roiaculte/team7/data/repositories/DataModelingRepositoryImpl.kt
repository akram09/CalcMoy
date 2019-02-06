package oxxy.kero.roiaculte.team7.data.repositories

import com.google.firebase.auth.FirebaseAuth
import oxxy.kero.roiaculte.team7.data.Util.getModuleList
import oxxy.kero.roiaculte.team7.data.Util.getMoulesListe
import oxxy.kero.roiaculte.team7.data.disk.LocalStorage
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaultParam
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.DataModelingRepository
import javax.inject.Inject

class DataModelingRepositoryImpl @Inject constructor(val auth:FirebaseAuth)
    : DataModelingRepository {
   override suspend fun getDefaultModules(executeParams: GetModulesDefaultParam): Either<Failure.GetModulesDEfaultFailure, List<Semestre>> {
        if(executeParams.facultyType==null){
            return Either.Right(LocalStorage.modulesWithoutFaculte.getMoulesListe(executeParams.school,
                executeParams.year, auth.currentUser!!.uid))
        }else{
            val facultyType = (executeParams.facultyType  as FacultyType)
            if(executeParams.year==2){
                return Either.Right(
                    LocalStorage.modulesSecondYear.getModuleList(facultyType , auth.currentUser!!.uid)
                )
            }else{
                return Either.Right(
                    LocalStorage.moduleThirdYear.getModuleList(facultyType , auth.currentUser!!.uid)
                )
            }
        }
    }
}