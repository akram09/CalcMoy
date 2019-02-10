package oxxy.kero.roiaculte.team7.data.repositories

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.data.Util.getModuleList
import oxxy.kero.roiaculte.team7.data.Util.getMoulesListe
import oxxy.kero.roiaculte.team7.data.database.LocalData
import oxxy.kero.roiaculte.team7.data.disk.LocalStorage
import oxxy.kero.roiaculte.team7.data.firebase.RemoteDatabase
import oxxy.kero.roiaculte.team7.data.firebase.StorageHandler
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.GetModulesDefaultParam
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.SaveUserParam
import oxxy.kero.roiaculte.team7.domain.models.FacultyType
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.repositories.DataModelingRepository
import javax.inject.Inject

class DataModelingRepositoryImpl @Inject constructor(val auth:FirebaseAuth, val storage:StorageHandler
                                                     , val remote : RemoteDatabase,
                                                     val local:LocalData
)
    : DataModelingRepository {
   override suspend fun getDefaultModules(executeParams: GetModulesDefaultParam): Either<Failure.GetModulesDEfaultFailure, List<Semestre>> {
       Log.v("fucking_error", "ejejje")
        if(executeParams.facultyType==null){
            Log.v("fucking_error", "ejejje")

            return Either.Right(LocalStorage.modulesWithoutFaculte.getMoulesListe(executeParams.school,
                executeParams.year, auth.currentUser!!.uid))
        }else{
            val facultyType = (executeParams.facultyType  as FacultyType)
          return Either.Right(LocalStorage.lyceeArray[executeParams.year]
              .getModuleList(facultyType, auth.currentUser!!.uid))
        }
    }

    override fun updateFile(p: String): Observable<Double> {
     return storage.saveFile(Uri.parse(p), auth.currentUser?.uid!!)
    }

//    override suspend fun getUrl(): Either<Failure.SaveImageFailure, String> {
//      return storage.getUrl(auth.currentUser?.uid!!)
//    }

    override suspend fun saveUser(executeParams: SaveUserParam): Either<Failure.SaveUserFailure, None> {
        val url:String = if(executeParams.hasSumbitImage){
            val either= storage.getUrl(auth.currentUser?.uid!!)
            if(either.isRight){
                (either as Either.Right<String>).b
            }else{
                return either as Either.Left<Failure.SaveUserFailure>
            }
        }else{
            executeParams.user.imageUrl
        }
        executeParams.user.imageUrl= url
          remote.saveUserInfo(executeParams.user , executeParams.list)
        local.saveUserInfo(executeParams.user, executeParams.list)
           return Either.Left(Failure.SaveUserFailure.UknownFailure(null))
    }
}