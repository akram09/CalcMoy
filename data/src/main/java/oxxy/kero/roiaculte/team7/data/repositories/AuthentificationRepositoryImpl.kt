package oxxy.kero.roiaculte.team7.data.repositories


import android.util.Log
import com.google.android.gms.common.api.GoogleApi
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import oxxy.kero.roiaculte.team7.data.database.LocalAuthentificator
import oxxy.kero.roiaculte.team7.data.firebase.AuthentificationFirebase
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.LoginParam
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.interactors.RegistrationModel
import oxxy.kero.roiaculte.team7.domain.interactors.UserInfo
import oxxy.kero.roiaculte.team7.domain.models.UserState
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import java.lang.NullPointerException

import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class AuthentificationRepositoryImpl @Inject constructor(private val authentificator: AuthentificationFirebase
            , private val local:LocalAuthentificator)
    :AuthentificationRepository{
    override suspend fun registreUser(user: RegistrationModel): Either<Failure.CreatUserFailures, None> {
        return authentificator.registreUserWithEmail(user.mail , user.pass)
    }

    override suspend fun signUserOut() {
       val  id =  authentificator.signUserOut()
        local.signOut(id)
    }

    override suspend fun signInUserCredentiel(credentiel: String, type: Int): Either<Failure.SignInCredentielFailure, None> {
       val either = authentificator.signInUserWithCredentiel(
           if(type== GOOGLE_CONST)GoogleAuthProvider.getCredential( credentiel, null)

           else FacebookAuthProvider.getCredential(credentiel) )
        if(either is Either.Left){
            Log.e("errr", "ther is a failure")
            return either
        }else{
            Log.e("errr", "there is success")
            val id  = authentificator.getUserId()
            Log.e("errr", id)
            if(local.getUserById(id!!)==null){
                Log.e("errr", "there is no user in db ")
                val user = authentificator.checkUserRemote(id)
                if( user!=null){
                    local.addUserDao(user)
                    Log.e("errr", "user added successfully")
                }

            }else {
                Log.e("errr", "there is a user")
                local.updateUser(id, true)
            }
            return either
        }

    }

    override suspend fun loginUser(param: LoginParam): Either<Failure.LoginFailure, None> {
        Log.e("errr", "from the repositoy ")
        val either= authentificator.logUserIn(param.mail, param.password)

        if( either is Either.Left){
            //Login Failure
            Log.e("errr", "there is a failure ")
            return either
        }else{
             //Login Success
            Log.e("errr", "there is success")
            val id  = authentificator.getUserId()
            Log.e("errr", id)
            if(local.getUserById(id!!)==null){
                Log.e("errr", "there is no user in db ")
                val user = authentificator.checkUserRemote(id)
                 if( user!=null){
                     local.addUserDao(user)
                     //Todo  add get Modules
                     Log.e("errr", "user added successfully")
                 }

            }else {
                Log.e("errr", "there is a user")
                local.updateUser(id, true)
            }
            return either
        }

    }

    override suspend fun provideUserState(): Either<Failure.ProvideUserStateFailure, UserState> {
        return local.provideUserState()
    }

    override suspend fun getUserState(): Either<Failure.ProvideUserStateFailure, UserState> {
        return if(authentificator.isThereUser()){
             local.provideUserState()
        }else Either.Right(UserState.USER_NOT_REGISTRED)
    }

    override suspend fun provideUserInfo(): Either<Failure.NoUserInfo, UserInfo> {
        return authentificator.provideUser()
    }

    override suspend fun getUserInfoFromRemote(): Either<Failure.GetUserInfoFromRemote, None> {
           val either=  authentificator.getMatter()
            if(either is Either.Left<Failure.GetUserInfoFromRemote>){
                return either
            }else {
                either as Either.Right
               local.addMatters(either.b)
                return Either.Right(None())
            }
    }

    companion object {
        const val GOOGLE_CONST = 0
        const val FACEBOOK_CONST = 1
    }
}