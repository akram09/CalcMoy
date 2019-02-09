package oxxy.kero.roiaculte.team7.data.firebase

import com.google.firebase.database.FirebaseDatabase
import oxxy.kero.roiaculte.team7.data.database.entities.SchoolConverterClass
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
private const val IMAGE_URL = "imageUrl"
private const val NAME  ="name"
private const val PRENAME= "prename"
private const val YEAR = "year"
private const val MOYENNE_GENERALE= "moyenneGenerale"
private const val SEMESTRE= "semestre"
private const val SCHOOL ="school"
class RemoteDatabase @Inject constructor(val database: FirebaseDatabase){
    suspend fun saveUserInfo(user: User, list: List<Semestre>) :Either<Failure.SaveUserFailure , None>{
        val Stringmap =
            mapOf<String, String>(
                IMAGE_URL to user.imageUrl, NAME to user.name, PRENAME to user.prename
                , YEAR to user.year
            )
        val DoubleMap = mapOf<String, Double>(
            MOYENNE_GENERALE to user.moyenneGenerale
        )
        val IntMap = mapOf<String, Int>(
            SEMESTRE to user.semstre, SCHOOL to SchoolConverterClass().fromSchoolToInt(user.school)
        )
        var modules = mapOf<String, List<Matter>>()
        for (semstre in list){
            modules += "semestre${semstre.numbre}" to semstre.matters
        }
        val either1 = saveMap(Stringmap , user.id)
        val either2 = saveMap(DoubleMap, user.id)
        val either3  = saveMap(IntMap, user.id)
       val either4  = suspendCoroutine<Either<Failure.SaveUserFailure ,None>> {
            continuation->
            database.reference.child("users").child(user.id).updateChildren(
                modules
            ).addOnCompleteListener {
                if(it.isSuccessful){
                    continuation.resume(Either.Right(None()))
                }else{
                    continuation.resume(Either.Left(Failure.SaveUserFailure.UknownFailure(it.exception)))
                }
            }

        }
        val set = listOf(either1 , either2 , either3 , either4).filter { it.isLeft }
        if(set.isEmpty()){
            return Either.Right(None())
        }else{
            database.reference.child("users").child(user.id).removeValue()
            return set[0]
        }
    }
        private suspend fun saveMap(map:Map<String , Any>, id:String):Either<Failure.SaveUserFailure, None>
                = suspendCoroutine {
            continuation->
            database.reference.child("users").child(id).push().updateChildren(map).addOnCompleteListener{
                if(it.isSuccessful){
                    continuation.resume(Either.Right(None()))
                }else{
                    //todo specify the error
                    continuation.resume(Either.Left(Failure.SaveUserFailure.UknownFailure(it.exception)))
                }
            }
        }



}