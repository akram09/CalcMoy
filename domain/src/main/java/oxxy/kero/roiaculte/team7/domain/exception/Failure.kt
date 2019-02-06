package oxxy.kero.roiaculte.team7.domain.exception

import java.lang.Exception


sealed class Failure {
    sealed class  CreatUserFailures:Failure(){
        class FirebaseWeakPassword(val e:Exception?) : Failure.CreatUserFailures()
        class FirebaseCoalisedUser(val e:Exception?): Failure.CreatUserFailures()
        class FirebaseNetworkError(val e:Exception?): Failure.CreatUserFailures()
        class FirebaseUknownError(val e: Exception?): Failure.CreatUserFailures()
    }

    sealed class  SignInCredentielFailure:Failure(){
        class SignInNetworkError(val e:Exception?):SignInCredentielFailure()
        class SignInUknownError(val e:Exception?):SignInCredentielFailure()
        class SignInInvalidCredentiel(val e:Exception?):SignInCredentielFailure()
    }
    sealed class LoginFailure:Failure(){
        class LoginNetworkError(val e:Exception?):LoginFailure()
        class LoginUknownError(val e:Exception?):LoginFailure()
        class LoginPasswordInvalid(val e:Exception?):LoginFailure()
        class LoginUsrNotFound(val e:Exception?):LoginFailure()
    }
    class ProvideUserStateFailure(val e:Exception?):Failure()

//    class NetworkConnection: Failure()
//    class ServerError: Failure()
//    abstract class FirebaseError(val t:Exception? ):Failure()
//    class DataBaseError(val  t:Throwable):Failure()
//    /** * Extend this class for feature specific failures.*/
//    abstract class FeatureFailure: Failure()

}

