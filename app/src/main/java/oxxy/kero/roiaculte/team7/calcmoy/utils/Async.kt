package oxxy.kero.roiaculte.team7.calcmoy.utils

import oxxy.kero.roiaculte.team7.domain.exception.Failure
import java.util.Arrays


sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean) {

    open operator fun invoke(): T? = null

    companion object {

        fun <T> Success<*>.setMetadata(metadata: T) {
            this.metadata = metadata
        }

        @Suppress("UNCHECKED_CAST")
        fun <T> Success<*>.getMetadata(): T? = this.metadata as T?
    }
}

object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true), Incomplete

class Loading<out T> : Async<T>(complete = false, shouldLoad = false), Incomplete {
    override fun equals(other: Any?) = other is Loading<*>

    override fun hashCode() = "Loading".hashCode()
}

data class Success<out T>(private val value: T) : Async<T>(complete = true, shouldLoad = false) {

    override operator fun invoke(): T = value


    internal var metadata: Any? = null
}

data class Fail<out T,F : Failure>(val error: F) : Async<T>(complete = true, shouldLoad = true)

interface Incomplete
