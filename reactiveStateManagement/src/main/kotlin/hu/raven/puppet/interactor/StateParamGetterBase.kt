package hu.raven.puppet.interactor

import hu.raven.puppet.StateStorage
import kotlin.concurrent.read

abstract class StateParamGetterBase<S : Any, V>(protected val storage: StateStorage<S>) {
    protected abstract fun map(state: S): V
    fun getValue(): V = storage.stateLock.read {
        map(storage.state)
    }
}