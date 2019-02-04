package oxxy.kero.roiaculte.team7.domain.interactors

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.functional.AppRxSchedulers
import oxxy.kero.roiaculte.team7.domain.functional.Either

interface EitherInteractor<in P, out R> {
    val dispatcher: CoroutineDispatcher
    val ResultDispatcher :CoroutineDispatcher
    suspend operator fun invoke(executeParams: P):Either<Failure, R>
}

interface Interactor<in P>{
    val  dispatcher: CoroutineDispatcher
    val ResultDispatcher :CoroutineDispatcher
    suspend operator fun invoke(executeParams: P)
}

abstract class ObservableInteractor<Type , in Params>(private val schedulers:AppRxSchedulers){

    protected abstract fun buildObservable(p:Params):Observable< Type>

    fun observe(p:Params, observer: (e:Either<Failure, Type>)->Unit): Disposable {
       return buildObservable(p).subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe({
                observer(Either.Right(it))
            }, {
                observer(Either.Left(Failure.DataBaseError(it)))
            })
    }
}

fun <P, R> CoroutineScope.launchInteractor(interactor: EitherInteractor<P,R >, param: P , OnResult:(Either<Failure, R>)->Unit): Job {
    val  job = async(interactor.dispatcher) { interactor(param) }
    return launch(interactor.ResultDispatcher) { OnResult(job.await()) }
}
class None


fun <P> CoroutineScope.launchInteractor(interactor:Interactor<P>, param: P, onResult:()->Unit):Job{
    val job = async(context = interactor.dispatcher){interactor(param)}
    return launch (interactor.ResultDispatcher){ job.await()
        onResult()}

}
fun  <R>CoroutineScope.launchInteractor(interactor: EitherInteractor<Unit, R>, OnResult: (Either<Failure, R>) -> Unit) =
    launchInteractor(interactor, Unit, OnResult = OnResult)
