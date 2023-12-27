package hu.raven.puppet

import hu.raven.puppet.interactor.StateParamChangeNotifierBase
import hu.raven.puppet.interactor.StateParamSetterBase
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AccessPoint<O, S : Any, T>(
    initialValue: T,
    dispatcher: StateParamChangeNotifierBase<S, T>,
    private val manipulator: StateParamSetterBase<T, S>,
) : ReadWriteProperty<O, T> {
    private var lastValue: T = initialValue

    init {
        dispatcher += ::onNewValue
        manipulator.setNewValue(initialValue)
    }

    private fun onNewValue(newValue: T) {
        lastValue = newValue
    }

    override fun getValue(thisRef: O, property: KProperty<*>): T = lastValue

    override fun setValue(thisRef: O, property: KProperty<*>, value: T) {
        manipulator.setNewValue(value)
    }
}