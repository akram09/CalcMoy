package oxxy.kero.roiaculte.team7.data.database

import android.arch.persistence.room.RoomDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.data.Util.RoomNoneCrudToEither
import oxxy.kero.roiaculte.team7.data.database.entities.EventEntity
import oxxy.kero.roiaculte.team7.data.database.entities.MatterEntity
import oxxy.kero.roiaculte.team7.data.database.entities.ProfileUser
import oxxy.kero.roiaculte.team7.data.database.entities.UserEntity
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.*
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre
import oxxy.kero.roiaculte.team7.domain.models.User
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocalData @Inject constructor(val database: CalcMoyDatabase){
    suspend  fun saveUserInfo(user: User, list: List<Semestre>) :Either<Failure.SaveUserFailure.DataBaseFailure, None> =
        suspendCoroutine {
      //TODO Dont forget to add the ids
            try {
         database.userDao().addUser(UserEntity(user.id, user.name , user.prename , user.school ,
             user.year , user.semstre , user.imageUrl , true , user.moyenneGenerale))
            var list1 :List<Matter> = emptyList()
            for (semestre in list ){
                 list1 +=semestre.matters
            }
            database.matterDao().insertMatters(list1.map {
                MatterEntity(name =  it.name ,coifficient =  it.coifficient ,color =  it.color
                    ,semestre =  it.semestre ,moyenne = it.moyenne ,userId =  it.userId)
            })
     }catch (e:SQLiteException){
          it.resume(Either.Left(Failure.SaveUserFailure.DataBaseFailure(e)))
     }finally {
         it.resume(Either.Right(None()))
     }
    }

    suspend fun getUserList(): Either<Failure.DataBaseError , List<User>>
            = suspendCoroutine{
        var list:Either<Failure.DataBaseError, List<User>> = Either.Right(emptyList())
        try {
            list = Either.Right(database.userDao().getAllUsers().map {
            User(it.id
              , it.name , it.prename , it.school , it.year , it.semestre , it.imageUrl , it.moyenneGenerale )
        })


        }catch (e:SQLiteException){
            it.resume(Either.Left(Failure.DataBaseError(e)))
        }finally {
            it.resume(list)
        }
    }
    fun observeConectedUser():Observable<String>{
        return database.userDao().getActifUser().toObservable()
    }
    fun getMatters():List<MatterEntity>{
        return database.matterDao().getModulesByUserId()
    }
    suspend fun getEvents():Either<Failure.MainInfoFailure , Events> = suspendCoroutine{
          var events :List<EventEntity> = emptyList()
        try{
            val id = database.userDao().getIDConnectedUser()
            events = database.eventDao().getEventByUser(id)
        }catch (e:SQLiteException ){
            it.resume(Either.Left(Failure.MainInfoFailure(e)))
        }finally {
            it.resume(Either.Right(events.map { Event(it.id , it.type, it.time , it.place , it.matterId , it.userId) }))
        }

    }

    suspend fun getProfileInfo():Either<Failure.DataBaseError , ProfileUserResult> = suspendCoroutine{
  var user =ProfileUser("", "", "", 0, 0.0)
         var listMoy  = emptyList<Double>()
        try {
             user= database.userDao().getProfileInfo()
            val id = database.userDao().getIDConnectedUser()
             var listMatter = database.matterDao().getMattersConnected(id)
            for (i in 1..user.semestre){
            var somee =0.0 ; var sommeCoef =0
                listMatter.filter { it.semestre ==i-1 }.forEach {
                somee +=it.moyenne
                    sommeCoef  += it.coifficient
            }
                listMoy += somee /sommeCoef
            }
        }catch (e:SQLiteException){
            it.resume(Either.Left(Failure.DataBaseError(e)))
        }finally {
           it.resume(Either.Right(ProfileUserResult(user.name , user.prename , user.imageUrl , user.moyenneGenerale
           , user.semestre , listMoy)))
        }
    }
    suspend fun addEvent(event :Event)= RoomNoneCrudToEither(crud = database.eventDao()::addEvent
     , param =  event.let {
            EventEntity(type = it.type ,time= it.time , place = it.place , matterId = it.matterId ,userId = it.userId)
        }
    )
    suspend fun updateEvent(event :Event) = RoomNoneCrudToEither(database.eventDao()::updateEvent ,
        event.let {
            EventEntity(it.id , it.type ,it.time, it.place , it.matterId , it.userId)
        })

    suspend fun addModule(module :Matter):Either<Failure.DataBaseError , None>
            = RoomNoneCrudToEither(param = listOf(module.let {
        MatterEntity(name=it.name , coifficient = it.coifficient , userId = it.userId , color = it.color , semestre =
        it.semestre , moyenne = it.moyenne)
    }),crud = database.matterDao()::insertMatters )

    suspend fun updateModule(module :Matter):Either<Failure.DataBaseError , None>
            = RoomNoneCrudToEither(param = module.let {
        MatterEntity(MatterId = it.id , name = it.name , coifficient = it.coifficient
            ,userId = it.userId , color = it.color , moyenne = it.moyenne, semestre = it.semestre)
    }
        ,  crud =  database.matterDao()::updateMatter)

    suspend fun getMatterConnected():Either<Failure.MainInfoFailure , MainGetSemestreResult> = suspendCoroutine {
        var list = emptyList<Semestre>()
        var curentmatter = emptyList<Matter>()
        try {
            val id = database.userDao().getIDConnectedUser()
          val MatterList = database.matterDao().getMattersConnected(id).sortedBy {
              it.semestre
          }
           var int =0
            do{
                curentmatter = MatterList.filter {
                    it.semestre==int
                }.map {
                    Matter(it.MatterId , it.name , it.coifficient , it.color , it.semestre , it.moyenne, it.userId)
                }
                if(!curentmatter.isEmpty()){
                list +=Semestre( int , curentmatter.toMutableList())
                }
                int++
                }while (!curentmatter.isEmpty())
        }catch (e:SQLiteException){
            it.resume(Either.Left(Failure.MainInfoFailure(e)))
        }finally {
            it.resume(Either.Right(MainGetSemestreResult(list.size , list ) ))
        }
    }
}