package hu.raven.puppet.example

import hu.raven.puppet.interactor.StateParamSetterBase
import hu.raven.puppet.StateStorage

class CountUpdater(storage: StateStorage<MyState>) : StateParamSetterBase<Int, MyState>(storage) {
    override fun generateNewState(state: MyState, newValue: Int): MyState = state.copy(count = newValue)

}