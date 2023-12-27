package hu.raven.puppet.interactor

import hu.raven.puppet.StateStorage
import kotlin.concurrent.write

abstract class StateManipulatorBase<S : Any>(protected val storage: StateStorage<S>) {
    protected abstract fun generateNewState(state: S): S

    fun manipulate() = storage.stateLock.write {
        val oldState = storage.state
        val newState = generateNewState(oldState)
        storage.state = newState
    }
}