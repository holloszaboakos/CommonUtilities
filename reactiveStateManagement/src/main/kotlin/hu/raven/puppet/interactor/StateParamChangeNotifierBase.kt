package hu.raven.puppet.interactor

abstract class StateParamChangeNotifierBase<S : Any, T> {
    private var lastHash: Int? = null
    private val subscribersLock = object {}
    private val subscribers: MutableList<(T) -> Unit> = mutableListOf()

    protected abstract fun map(state: S): T

    fun dispatch(state: S): Unit = synchronized(subscribersLock) {
        val dispatchedValue = map(state)

        if (dispatchedValue.hashCode() == lastHash)
            return

        subscribers.map { it(dispatchedValue) }
    }

    operator fun plusAssign(updateHandler: (T) -> Unit): Unit = synchronized(subscribersLock) {
        subscribers.add(updateHandler)
    }

    operator fun minusAssign(updateHandler: (T) -> Unit): Unit = synchronized(subscribersLock) {
        subscribers.remove(updateHandler)
    }
}