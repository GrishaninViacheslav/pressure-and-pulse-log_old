package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import java.lang.Exception

sealed class ShareState {
    object Success: ShareState()
    data class Error(val error: Exception): ShareState()
}