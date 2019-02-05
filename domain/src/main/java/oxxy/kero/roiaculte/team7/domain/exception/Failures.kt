package oxxy.kero.roiaculte.team7.domain.exception

sealed class  CreatUserFailures(e: Exception?):Failure.FirebaseError(e)
class FirebaseWeakPassword(val e:Exception?) : CreatUserFailures(e)
class FirebaseCoalisedUser(val e:Exception?): CreatUserFailures(e)
class FirebaseNetworkError(val e:Exception?): CreatUserFailures(e)
class FirebaseUknownError(val e: Exception?): CreatUserFailures(e)
sealed class  SignInCredentielFailure(e:Exception?):Failure.FirebaseError(e)
class SignInNetworkError(e:Exception?):SignInCredentielFailure(e)
class SignInUknownError(e:Exception?):SignInCredentielFailure(e)
class SignInInvalidCredentiel(e:Exception?):SignInCredentielFailure(e)
