package hu.raven.puppet.interactor

import hu.raven.puppet.StateStorage
import kotlin.concurrent.write

abstract class StateParamSetterBase<N, S : Any>(private val storage: StateStorage<S>) {

    protected abstract fun generateNewState(state: S, newValue: N): S

    fun setNewValue(newValue: N) = storage.stateLock.write {
        val oldState = storage.state
        val newState = generateNewState(oldState, newValue)
        storage.state = newState
    }
}