package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard


interface IClipboardRepository {
    fun setTextClip(
        label: String,
        text: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    )
}