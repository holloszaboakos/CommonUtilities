package hu.raven.puppet.interactor

import hu.raven.puppet.StateStorage
import kotlin.concurrent.read

abstract class StateDeriverBase<S : Any, P, R>(protected val storage: StateStorage<S>) {
    protected abstract fun extractParams(state: S): P
    protected abstract fun calculateResult(state: P): R
    fun getValue(): R = storage.stateLock.read {
        val params = extractParams(storage.state)
        calculateResult(params)
    }
}