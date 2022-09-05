package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard


interface IClipboardRepository {
    suspend fun setTextClip(
        label: String,
        text: String
    )
}