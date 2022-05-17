package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import java.lang.Exception

sealed class LogState {
    data class Success(val log: List<LogEntry>): LogState()
    object Loading: LogState()
    data class Error(val error: Exception): LogState()
}