package io.github.grishaninvyacheslav.pressure_and_pulse_log.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogEntry(
    /**
     * Epoch Unix Timestamp in seconds
     **/
    val timestamp: Long,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int
): Parcelable