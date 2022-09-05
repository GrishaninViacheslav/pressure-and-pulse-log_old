package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.ILogRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.utils.CancelableJobs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(private val repository: ILogRepository) : ViewModel() {
    private val mutableSaveState: MutableLiveData<EntrySaveState> = MutableLiveData()

    val saveState: LiveData<EntrySaveState> = mutableSaveState

    private val cancelableJobs = CancelableJobs()

    private val saveExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableSaveState.postValue(EntrySaveState.SaveError(throwable))
    }

    fun addEntry(entry: LogEntry) {
        mutableSaveState.value = EntrySaveState.Saving
        CoroutineScope(Dispatchers.IO + saveExceptionHandler).launch {
            repository.addLogEntry(entry)
            mutableSaveState.postValue(EntrySaveState.Saved)
        }.also { cancelableJobs.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        cancelableJobs.cancel()
    }
}