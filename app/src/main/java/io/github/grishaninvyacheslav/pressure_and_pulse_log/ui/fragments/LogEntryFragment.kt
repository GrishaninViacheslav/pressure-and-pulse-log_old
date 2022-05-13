package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments

import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.FragmentLogEntryBinding

class LogEntryFragment :
    BaseFragment<FragmentLogEntryBinding>(FragmentLogEntryBinding::inflate) {
    companion object {
        fun newInstance() = LogEntryFragment()
    }
}