package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.ILogRepository

class EntryViewModel(private val repository: ILogRepository) : ViewModel() {
    private val mutableSaveState: MutableLiveData<EntrySaveState> = MutableLiveData()

    val saveState: LiveData<EntrySaveState> = mutableSaveState

    fun addEntry(entry: LogEntry) {
        mutableSaveState.value = EntrySaveState.Saving
        repository.addLogEntry(
            entry,
            { mutableSaveState.value = EntrySaveState.Saved },
            { mutableSaveState.value = EntrySaveState.SaveError(it) }
        )
    }
}