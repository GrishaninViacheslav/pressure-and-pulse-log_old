package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry

sealed class EntrySaveState {
    object Saving: EntrySaveState()
    object Saved: EntrySaveState()
    data class SaveError(val error: Exception): EntrySaveState()
}