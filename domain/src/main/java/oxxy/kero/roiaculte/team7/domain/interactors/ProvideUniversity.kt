package oxxy.kero.roiaculte.team7.domain.interactors

import kotlinx.coroutines.CoroutineDispatcher
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.Either
import oxxy.kero.roiaculte.team7.domain.models.Semestre

class ProvideUniversity : EitherInteractor<Int,List<Semestre>,Failure.ProvideUniversityFailure> {

    override val dispatcher: CoroutineDispatcher
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val ResultDispatcher: CoroutineDispatcher
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override suspend fun invoke(executeParams: Int): Either<Failure.ProvideUniversityFailure, List<Semestre>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}