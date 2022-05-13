package io.github.grishaninvyacheslav.pressure_and_pulse_log.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.IScreens
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.Screens
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.view_models.log.LogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LogViewModel() }

    single { provideCicerone() }
    single { provideRouter(get()) }
    single { provideNavigatorHolder(get()) }
    single { provideScreens() }
}

fun provideCicerone(): Cicerone<Router> = Cicerone.create()
fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()
fun provideScreens(): IScreens = Screens()