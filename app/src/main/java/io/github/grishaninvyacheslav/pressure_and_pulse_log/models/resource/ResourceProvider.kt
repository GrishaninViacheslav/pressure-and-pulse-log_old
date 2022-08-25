package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource

import android.content.Context

class ResourceProvider(private val context: Context): IResourceProvider {
    override fun getString(id: Int) = context.resources.getString(id)
}