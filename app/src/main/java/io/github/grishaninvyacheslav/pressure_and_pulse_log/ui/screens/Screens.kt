package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments.LogEntryFragment
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments.LogFragment

class Screens: IScreens {
    override fun log() = FragmentScreen { LogFragment.newInstance() }
    override fun addLogEntry(): Screen = FragmentScreen { LogEntryFragment.newInstance() }
}