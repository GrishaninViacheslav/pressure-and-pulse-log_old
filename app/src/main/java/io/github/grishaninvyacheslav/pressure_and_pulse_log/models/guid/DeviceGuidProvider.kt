package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid

import android.content.Context
import io.github.grishaninvyacheslav.pressure_and_pulse_log.BuildConfig
import java.util.*

class DeviceGuidProvider(applicationContext: Context) : IDeviceGuidProvider {
    override suspend fun getDeviceGUID() = guid

    private val guid: String by lazy {
        extractGuid() ?: makeGuid()
    }

    private val sharedPref by lazy {
        applicationContext.getSharedPreferences(BuildConfig.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    private val editor by lazy {
        sharedPref.edit()
    }

    private fun makeGuid(): String {
        val guid = UUID.randomUUID().toString()
        editor.putString(BuildConfig.PREFERENCES_GUID_KEY, guid)
        editor.commit()
        return guid
    }

    private fun extractGuid() = sharedPref.getString(BuildConfig.PREFERENCES_GUID_KEY, null)
}