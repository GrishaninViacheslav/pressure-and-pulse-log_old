package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource

interface IResourceProvider {
    fun getString(id: Int): String
}