package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.fragments

import android.R.attr.label
import android.content.ClipData
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.pressure_and_pulse_log.R
import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.FragmentLogBinding
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toDate
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log.ILogDataModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log.ILogItemView
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log.LogListAdapter
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.IScreens
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log.LogState
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log.LogViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import android.content.ClipboardManager


class LogFragment :
    BaseFragment<FragmentLogBinding>(FragmentLogBinding::inflate) {

    companion object {
        fun newInstance() = LogFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        viewModel.logState.observe(viewLifecycleOwner) { renderLogState(it) }
    }

    private fun initListeners() = with(binding) {
        shareViaLink.setOnClickListener {
            val clipboard: ClipboardManager? =
                getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("LogLink", "https://grishaninvyacheslav.github.io/pressure_and_pulse_demo.html?guid=d75204d9-745d-4f1f-84bf-0c1d79af3ae6")
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Ссылка скопирована", Toast.LENGTH_SHORT).show()
        }
        addEntry.setOnClickListener {
            router.navigateTo(screens.addLogEntry())
        }
    }

    private fun renderLogState(state: LogState) =
        when (state) {
            LogState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is LogState.Error -> {
                binding.progressBar.isVisible = false
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_occurred, state.error),
                    Toast.LENGTH_LONG
                ).show()
            }
            is LogState.Success -> {
                binding.progressBar.isVisible = false
                initList(state.log)
            }
        }

    private fun initList(log: List<LogEntry>) = with(binding) {
        logList.layoutManager = LinearLayoutManager(requireContext())
        adapter = LogListAdapter(
            logDataModel.apply { logEntries = log },
            onItemClick = { },
        )
        logList.adapter = adapter
    }

    private var adapter: LogListAdapter? = null

    private val logDataModel = object : ILogDataModel {
        var logEntries = listOf<LogEntry>()
        override fun getCount() = logEntries.size
        override fun bindView(view: ILogItemView) = with(logEntries[view.pos]) {
            with(view) {
                setDate(
                    date = timestamp.toDate(),
                    isDayDifferentInPreviousEntry = if (view.pos == 0) {
                        true
                    } else {
                        !isSameDay(timestamp.toDate(), logEntries[view.pos - 1].timestamp.toDate())
                    }
                )
                setSystolicPressure(systolic)
                setDiastolicPressure(diastolic)
                setPulse(pulse)
            }
        }

        private fun isSameDay(a: Date, b: Date): Boolean {
            val calendarA = Calendar.getInstance().apply {
                time = a
            }
            val calendarB = Calendar.getInstance().apply {
                time = b
            }
            return calendarA[Calendar.DAY_OF_YEAR] == calendarB[Calendar.DAY_OF_YEAR] &&
                    calendarA[Calendar.YEAR] == calendarB[Calendar.YEAR]
        }
    }

    private val viewModel: LogViewModel by viewModel()

    private val router: Router by inject()
    private val screens: IScreens by inject()
}