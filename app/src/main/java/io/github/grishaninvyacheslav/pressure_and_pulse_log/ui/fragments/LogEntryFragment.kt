package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.pressure_and_pulse_log.R
import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.FragmentLogEntryBinding
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry.EntrySaveState
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry.EntryViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class LogEntryFragment :
    BaseFragment<FragmentLogEntryBinding>(FragmentLogEntryBinding::inflate) {
    companion object {
        fun newInstance() = LogEntryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewsValues()
        initViewListeners()
        initViewModelObservers()
    }

    @SuppressLint("SimpleDateFormat")
    private fun initViewsValues() = with(binding) {
        with(Date(currTimestampMs)) {
            yearInputEditText.setText(
                SimpleDateFormat(getString(R.string.entry_year_format)).format(this)
            )
            dayInputEditText.setText(
                SimpleDateFormat(getString(R.string.date_format)).format(this)
            )
            timeInputEditText.setText(
                SimpleDateFormat(getString(R.string.time_format)).format(this)
            )
        }
    }

    private fun initViewListeners() = with(binding) {
        yearInputEditText.keyListener = null
        dayInputEditText.keyListener = null
        timeInputEditText.keyListener = null
        confirmButton.setOnClickListener {
            viewModel.addEntry(
                LogEntry(
                    currTimestampMs / 1000,
                    systolicPressureInputEditText.text.toString().toInt(),
                    diastolicPressureInputEditText.text.toString().toInt(),
                    pulseInputEditText.text.toString().toInt()
                )
            )
        }
    }

    private fun initViewModelObservers() =
        viewModel.saveState.observe(viewLifecycleOwner) { renderSaveState(it) }

    private fun renderSaveState(state: EntrySaveState) =
        when (state) {
            EntrySaveState.Saving -> with(binding) {
                confirmButton.isVisible = false
                progressBar.isVisible = true
            }
            is EntrySaveState.SaveError -> with(binding) {
                confirmButton.isVisible = true
                progressBar.isVisible = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_occurred, state.error),
                    Toast.LENGTH_LONG
                ).show()
            }
            is EntrySaveState.Saved -> {
                binding.progressBar.isVisible = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.saved_successfully),
                    Toast.LENGTH_LONG
                ).show()
                router.exit()
            }
        }

    private val viewModel: EntryViewModel by viewModel()

    private val router: Router by inject()

    private val currTimestampMs = System.currentTimeMillis()
}