package oxxy.kero.roiaculte.team7.data.Util

import android.database.sqlite.SQLiteException
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun<P>  RoomNoneCrudToEither(crud: (P)->Unit  , param:P):Either<Failure.DataBaseError , None> = suspendCoroutine{
    try {
        crud(param)
    }catch (e:SQLiteException){
        it.resume(Either.Left(Failure.DataBaseError(e)))
    }finally {
        it.resume(Either.Right(None()))
    }
}