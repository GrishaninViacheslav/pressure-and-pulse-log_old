package io.github.grishaninvyacheslav.pressure_and_pulse_log.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard.ClipboardRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard.IClipboardRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid.DeviceGuidProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid.IDeviceGuidProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.ILogRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log.LogRepository
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource.IResourceProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.resource.ResourceProvider
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.IScreens
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.Screens
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log.LogViewModel
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.logEntry.EntryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single{ Firebase.firestore }
    single<ILogRepository> { LogRepository(get(), get()) }
    single<IDeviceGuidProvider> { DeviceGuidProvider(get()) }
    single<IClipboardRepository> { ClipboardRepository(get(), get()) }
    single<IResourceProvider> { ResourceProvider(get()) }

    single { provideCicerone() }
    single { provideRouter(get()) }
    single { provideNavigatorHolder(get()) }
    single { provideScreens() }

    viewModel { LogViewModel(get(), get(), get(), get()) }
    viewModel { EntryViewModel(get()) }
}

fun provideCicerone(): Cicerone<Router> = Cicerone.create()
fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()
fun provideScreens(): IScreens = Screens()