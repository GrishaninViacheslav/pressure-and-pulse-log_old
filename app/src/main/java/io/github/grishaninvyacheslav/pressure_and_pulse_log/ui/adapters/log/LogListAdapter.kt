package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.ItemLogEntryBinding

class LogListAdapter(
    private val dataModel: ILogDataModel,
    private var onItemClick: ((view: ILogItemView) -> Unit)
) : RecyclerView.Adapter<LogEntryViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LogEntryViewHolder(
            ItemLogEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )

    override fun getItemCount() = dataModel.getCount()

    override fun onBindViewHolder(logEntryView: LogEntryViewHolder, position: Int) =
        dataModel.bindView(logEntryView.apply { pos = position })
}