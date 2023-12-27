package hu.raven.puppet.interactor

import hu.raven.puppet.StateStorage
import kotlin.concurrent.write

abstract class StateReactorBase<S : Any, P, R>(protected val storage: StateStorage<S>) {
    private var hashCode: Int = 0
    abstract fun extractParams(state: S): P
    abstract fun calculate(params: P): R
    abstract fun generateNewState(state: S, result: R): S
    fun onStateUpdate() {
        storage.stateLock.write {
            val params = extractParams(storage.state)
            if (params.hashCode() == hashCode)
                return
            val result = calculate(params)
            val newState = generateNewState(storage.state, result)
            storage.state = newState
        }
    }
}