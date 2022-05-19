package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log

import java.util.*

interface ILogItemView {
    var pos: Int
    fun setDate(date: Date, isDayDifferentInPreviousEntry: Boolean)
    fun setSystolicPressure(pressure: Int)
    fun setDiastolicPressure(pressure: Int)
    fun setPulse(pulseValue: Int)
}