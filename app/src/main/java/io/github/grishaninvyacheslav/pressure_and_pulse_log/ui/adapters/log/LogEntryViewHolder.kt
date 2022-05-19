package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.github.grishaninvyacheslav.pressure_and_pulse_log.R
import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.ItemLogEntryBinding
import java.util.*
import android.text.format.DateFormat;

class LogEntryViewHolder(
    private val binding: ItemLogEntryBinding,
    private var onItemClick: ((view: ILogItemView) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root),
    ILogItemView {
    init {
        itemView.setOnClickListener { onItemClick?.invoke(this) }
    }

    override var pos = -1
        set(value) {
            field = value
            binding.background.setImageResource(
                if (pos % 2 == 0) {
                    R.drawable.bg_gradient_yellow
                } else {
                    R.drawable.bg_gradient_green
                }
            )
        }

    override fun setDate(date: Date, isDayDifferentInPreviousEntry: Boolean) = with(binding) {
        sectionTitle.isVisible = isDayDifferentInPreviousEntry
        sectionTitle.text = DateFormat.format(
            sectionTitle.context.getString(R.string.log_entry_section_title_date_format),
            date
        )
        time.text = DateFormat.format(
            sectionTitle.context.getString(R.string.log_entry_time_format),
            date
        )
    }

    override fun setSystolicPressure(pressure: Int) = with(binding) {
        systolicPressure.text = pressure.toString()
    }

    override fun setDiastolicPressure(pressure: Int) = with(binding) {
        diastolicPressure.text = pressure.toString()
    }

    override fun setPulse(pulseValue: Int) = with(binding) {
        pulse.text = pulseValue.toString()
    }
}