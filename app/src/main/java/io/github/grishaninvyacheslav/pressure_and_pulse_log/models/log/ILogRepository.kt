package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log

import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry

interface ILogRepository {
    suspend fun getLog(): List<LogEntry>
    suspend fun addLogEntry(entry: LogEntry)
}