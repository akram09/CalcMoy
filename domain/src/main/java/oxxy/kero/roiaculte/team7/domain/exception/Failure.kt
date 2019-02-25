package oxxy.kero.roiaculte.team7.domain.exception


import kotlin.Exception


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
    class GetModulesDEfaultFailure(val e:Exception?):Failure()
    class NoUserInfo():Failure()
    sealed class SearchFailure:Failure(){
        class NetworkFailure(val e:Exception?):SearchFailure()
        class UknowFailure(val e:Exception?):SearchFailure()
    }
    sealed class SaveImageFailure:Failure(){
        class NetworkFailure(val e:Exception?):SaveImageFailure()
        class UknownFailure(val e:Exception?):SaveImageFailure()
    }
    sealed class SaveUserFailure:Failure(){
        class NetworkFailure(val e:Exception?):SaveUserFailure()
        class DataBaseFailure(val e:Exception?):SaveUserFailure()
        class UknownFailure(val e:Exception?):SaveUserFailure()

    }

    sealed class ProvideSuggestionFaillure :Failure(){
        class NetworkFailure(val e:Exception?):ProvideSuggestionFaillure()
        class UknownFailure(val e:Exception?):ProvideSuggestionFaillure()
    }
    sealed class ProvideUniversityFailure : Failure() {
        class NetworkFailure(val e:Exception?):ProvideUniversityFailure()
        class UknownFailure(val e:Exception):ProvideUniversityFailure()
        class UNiversiteDontExiste(val e:Exception?):ProvideUniversityFailure()
    }
    sealed class GetUserInfoFromRemote():Failure(){
        class NetworkFailure(val e: Exception?):GetUserInfoFromRemote()
        class UknownFAilure(val  e:Exception? ) : GetUserInfoFromRemote()
    }
    class GetUsersFailure(val e:Exception?):Failure()
    class MainInfoFailure(val e:Exception?): Failure()


//    class NetworkConnection: Failure()
//    class ServerError: Failure()
//    abstract class FirebaseError(val t:Exception? ):Failure()
//    class DataBaseError(val  t:Throwable):Failure()
//    /** * Extend this class for feature specific failures.*/
//    abstract class FeatureFailure: Failure()

}

