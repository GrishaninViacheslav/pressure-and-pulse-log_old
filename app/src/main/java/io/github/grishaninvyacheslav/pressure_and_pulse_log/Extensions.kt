package io.github.grishaninvyacheslav.pressure_and_pulse_log

import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import java.util.*

fun Long.toDate() = Date(this * 1000)

enum class LogEntityField {
    TIMESTAMP,
    SYSTOLIC,
    DIASTOLIC,
    PULSE
}

fun Map<String, Any>.toLogEntity() = LogEntry(
    timestamp = this[LogEntityField.TIMESTAMP.name] as Long,
    systolic = (this[LogEntityField.SYSTOLIC.name] as Long).toInt(),
    diastolic = (this[LogEntityField.DIASTOLIC.name] as Long).toInt(),
    pulse = (this[LogEntityField.PULSE.name] as Long).toInt()
)

fun LogEntry.toMap() = hashMapOf(
    LogEntityField.TIMESTAMP.name to timestamp,
    LogEntityField.SYSTOLIC.name to systolic,
    LogEntityField.DIASTOLIC.name to diastolic,
    LogEntityField.PULSE.name to pulse
)

