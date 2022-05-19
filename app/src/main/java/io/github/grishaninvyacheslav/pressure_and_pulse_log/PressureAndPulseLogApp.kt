package io.github.grishaninvyacheslav.pressure_and_pulse_log

import android.app.Application
import io.github.grishaninvyacheslav.pressure_and_pulse_log.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PressureAndPulseLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PressureAndPulseLogApp)
            modules(appModule)
        }
    }
}