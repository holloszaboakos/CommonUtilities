package hu.raven.puppet.example

import hu.raven.puppet.interactor.StateManipulatorBase
import hu.raven.puppet.StateStorage

class CountManipulator(storage: StateStorage<MyState>) : StateManipulatorBase<MyState>(storage) {
    override fun generateNewState(state: MyState): MyState {
        return state.copy(count = state.count + 1)
    }
}