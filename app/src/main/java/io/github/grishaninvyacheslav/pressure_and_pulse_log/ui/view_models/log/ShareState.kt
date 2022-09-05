package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

sealed class ShareState {
    object Success: ShareState()
    data class Error(val error: Throwable): ShareState()
}