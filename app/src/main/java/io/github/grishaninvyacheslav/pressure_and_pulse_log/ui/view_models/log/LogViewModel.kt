package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.R
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard.IClipboardRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.ILogRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource.IResourceProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.utils.CancelableJobs
import kotlinx.coroutines.*

class LogViewModel(
    private val logRepository: ILogRepository,
    private val clipboardRepository: IClipboardRepository,
    private val resourceProvider: IResourceProvider
) :
    ViewModel() {
    private val mutableLogState: MutableLiveData<LogState> = MutableLiveData()
    private val mutableShareState: MutableLiveData<ShareState> = MutableLiveData()

    private val cancelableJobs = CancelableJobs()

    private val logExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableLogState.postValue(LogState.Error(throwable))
    }

    val logState: LiveData<LogState>
        get() {
            if (mutableLogState.value == LogState.Loading) {
                return mutableLogState
            }
            return mutableLogState.apply {
                value = LogState.Loading
                CoroutineScope(Dispatchers.IO + logExceptionHandler).launch {
                    postValue(
                        LogState.Success(
                            logRepository.getLog()
                        )
                    )
                }.also { cancelableJobs.add(it) }
            }
        }

    val shareState: LiveData<ShareState> = mutableShareState

    fun shareLogViaLink() {
        clipboardRepository.setTextClip(
            resourceProvider.getString(R.string.log_link_clip_label),
            "https://grishaninvyacheslav.github.io/pressure_and_pulse_demo.html?guid=d75204d9-745d-4f1f-84bf-0c1d79af3ae6",
            { mutableShareState.value = ShareState.Success },
            { mutableShareState.value = ShareState.Error(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        cancelableJobs.cancel()
    }
}