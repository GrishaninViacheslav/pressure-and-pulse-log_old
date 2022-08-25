package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat

class ClipboardRepository(private val context: Context) : IClipboardRepository {
    private val clipboard by lazy {
        ContextCompat.getSystemService(
            context,
            ClipboardManager::class.java
        )
    }

    override fun setTextClip(
        label: String,
        text: String,
        onSuccessListener: () -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        try {
            ClipData.newPlainText(label, text).let {
                clipboard?.setPrimaryClip(it) ?: throw ClipboardWriteError()
            }
        } catch (exception: Exception) {
            onFailureListener(exception)
        }
        onSuccessListener()
    }
}