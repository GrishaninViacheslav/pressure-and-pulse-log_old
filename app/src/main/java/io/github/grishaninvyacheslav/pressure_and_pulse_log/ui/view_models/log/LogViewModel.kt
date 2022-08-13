package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.ILogRepository

class LogViewModel(private val repository: ILogRepository) : ViewModel() {
    private val mutableLogState: MutableLiveData<LogState> = MutableLiveData()

    val logState: LiveData<LogState>
        get() {
            if (mutableLogState.value == LogState.Loading) {
                return mutableLogState
            }
            return mutableLogState.apply {
                value = LogState.Loading
                repository.getLog(
                    { value = LogState.Success(it) },
                    { value = LogState.Error(it) }
                )
            }
        }
}