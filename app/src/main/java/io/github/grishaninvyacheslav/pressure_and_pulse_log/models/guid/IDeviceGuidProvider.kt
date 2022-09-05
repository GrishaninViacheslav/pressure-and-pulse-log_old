package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid

interface IDeviceGuidProvider {
    suspend fun getDeviceGUID(): String
}