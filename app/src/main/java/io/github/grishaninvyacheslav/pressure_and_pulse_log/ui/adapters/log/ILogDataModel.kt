package io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.adapters.log

interface ILogDataModel {
    fun getCount(): Int
    fun bindView(view: ILogItemView)
}