package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.BuildConfig
import io.github.grishaninvyacheslav.pressure_and_pulse_log.R
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard.IClipboardRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid.IDeviceGuidProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.ILogRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource.IResourceProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.utils.CancelableJobs
import kotlinx.coroutines.*

class LogViewModel(
    private val logRepository: ILogRepository,
    private val clipboardRepository: IClipboardRepository,
    private val resourceProvider: IResourceProvider,
    private val deviceGuidProvider: IDeviceGuidProvider
) :
    ViewModel() {
    private val mutableLogState: MutableLiveData<LogState> = MutableLiveData()
    private val mutableShareState: MutableLiveData<ShareState> = MutableLiveData()

    private val cancelableJobs = CancelableJobs()

    private val logExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableLogState.postValue(LogState.Error(throwable))
    }

    private val clipboardExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutableShareState.postValue(ShareState.Error(throwable))
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
        CoroutineScope(Dispatchers.IO + clipboardExceptionHandler).launch {
            clipboardRepository.setTextClip(
                resourceProvider.getString(R.string.log_link_clip_label),
                String.format(BuildConfig.USER_LOG_URL_FORMAT_STRING, deviceGuidProvider.getDeviceGUID())
            )
            mutableShareState.postValue(ShareState.Success)
        }.also { cancelableJobs.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        cancelableJobs.cancel()
    }
}