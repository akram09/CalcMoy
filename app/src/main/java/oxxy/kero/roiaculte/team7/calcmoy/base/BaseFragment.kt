package oxxy.kero.roiaculte.team7.calcmoy.base

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment :DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var mActivity: BaseActivity? = null

    fun hideKeyboeard() {
        mActivity?.hideKeyboard()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    fun showToast(message: String) {
        mActivity?.showToast(message)
    }

    fun onError(resId: Int) {
        mActivity?.onError(resId)
    }

    fun onError(message: String) {
        mActivity?.onError(message)
    }

    fun showMessage(message: String) {
        mActivity?.showMessage(message)
    }

    fun showMessage(resId: Int) {
        mActivity?.showMessage(resId)
    }

    fun isNetworkConnected(): Boolean {
        return mActivity?.isNetworkConnected() ?: false
    }
}