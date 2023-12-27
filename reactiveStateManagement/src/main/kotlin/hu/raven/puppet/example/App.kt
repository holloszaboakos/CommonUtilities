package hu.raven.puppet.example

import hu.raven.puppet.AccessPoint
import hu.raven.puppet.StateStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

/**
 * Hello world!
 *
 */
fun main(args: Array<String>) {
    val stateStorage = StateStorage(MyState())
    val countDispatcher = CountDistributor()
    val countUpdater = CountUpdater(stateStorage)
    val countManipulator = CountManipulator(stateStorage)

    stateStorage.dispatchers += countDispatcher
    var count by AccessPoint(0, countDispatcher, countUpdater)

    runBlocking {
        (0 until 100)
            .map {
                CoroutineScope(Dispatchers.Default).launch {
                    repeat(1000) {
                        count++
                        if (it % 100 == 0)
                            println(count)
                    }
                }
            }
            .forEach { it.join() }
    }
    println("count should be ${100 * 1000}")
    println("count actualy is $count")
}