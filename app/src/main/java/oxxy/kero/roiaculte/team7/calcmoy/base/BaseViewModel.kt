package  oxxy.kero.roiaculte.team7.calcmoy.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import oxxy.kero.roiaculte.team7.domain.exception.Failure
import oxxy.kero.roiaculte.team7.domain.interactors.ObservableInteractor

abstract  class BaseViewModel<S: State>(initialState:S): ViewModel() {

    protected val state: MutableLiveData<S> by lazy {
       val liveData:MutableLiveData<S> = MutableLiveData()
        liveData.value=initialState
        liveData
    }

    private val job = Job()
    protected val scope  = CoroutineScope(Dispatchers.Main+job)
    private  val disposable:CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if(job.isActive){
            job.cancel()
        }
        disposable.dispose()
    }

    protected fun setState( statechenger :S.()->S){
        state.value = state.value?.statechenger()
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer : (S?)->Unit ) {
        state.observe(lifecycleOwner, Observer<S>(observer))
    }

    fun withState(chenger :(S)->Unit){
        chenger(state.value!!)
    }

    protected fun  <P, Type> launchObservableInteractor(interactor: ObservableInteractor<Type, P>, p:P, errorHandler :(Throwable)->Unit
                                                        , dataHandler:(Type)->Unit){
       disposable.add(interactor.observe(p, errorHandler, dataHandler))
    }


}

