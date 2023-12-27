package hu.raven.puppet

import hu.raven.puppet.interactor.StateParamChangeNotifierBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read


class StateStorage<S : Any>(initialState: S) {
    val stateLock: ReentrantReadWriteLock = ReentrantReadWriteLock()
    var state: S = initialState
        set(value) {
            field = value
            dispatch(value)
        }
    val dispatchers: MutableList<StateParamChangeNotifierBase<S, *>> = mutableListOf()
    private fun dispatch(stateFrozen: S) = stateLock.read {
        runBlocking {
            dispatchers
                .map {
                    launch(Dispatchers.IO) {
                        it.dispatch(stateFrozen)
                    }
                }
                .forEach { it.join() }
        }
    }
}