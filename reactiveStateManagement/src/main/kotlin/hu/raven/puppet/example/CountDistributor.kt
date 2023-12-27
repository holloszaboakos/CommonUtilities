package hu.raven.puppet.example

import hu.raven.puppet.interactor.StateParamChangeNotifierBase

class CountDistributor : StateParamChangeNotifierBase<MyState, Int>() {
    override fun map(state: MyState): Int = state.count
}