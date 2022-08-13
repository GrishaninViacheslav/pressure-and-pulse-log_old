package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log

import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry

interface ILogRepository {
    fun getLog(
        onSuccessListener: (List<LogEntry>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )

    fun addLogEntry(
        entry: LogEntry,
        onSuccessListener: () -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )
}