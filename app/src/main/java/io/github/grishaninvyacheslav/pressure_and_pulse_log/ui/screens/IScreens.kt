package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens

import com.github.terrakok.cicerone.Screen
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry

interface IScreens {
    fun log(): Screen
    fun addLogEntry(): Screen
}