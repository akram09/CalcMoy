package oxxy.kero.roiaculte.team7.domain.repositories

import io.reactivex.Observable
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.interactors.None

interface SearchRepository {
    suspend fun search(executeParams: String): Either<Failure.SearchFailure, None>
     fun observe(): Observable<List<String>>
    fun complete()

}