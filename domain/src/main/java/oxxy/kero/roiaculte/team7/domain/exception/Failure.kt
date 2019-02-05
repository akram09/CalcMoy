package oxxy.kero.roiaculte.team7.domain.exception

import java.lang.Exception


sealed class Failure {
    class NetworkConnection: Failure()
    class ServerError: Failure()
    abstract class FirebaseError(val t:Exception? ):Failure()
    class DataBaseError(val  t:Throwable):Failure()
    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()

}
