package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry

class LogViewModel : ViewModel() {
    private val mutableLogState: MutableLiveData<LogState> = MutableLiveData()

    val logState: LiveData<LogState>
        get() {
            return mutableLogState.apply {
                value = LogState.Success(
                    listOf(
                        LogEntry(1652721565, 111, 222, 333),
                        LogEntry(1652721565, 111, 222, 333)
                    )
                )
            }
        }
}