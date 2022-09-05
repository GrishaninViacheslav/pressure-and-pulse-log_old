package io.github.grishaninvyacheslav.pressure_and_pulse_log

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.github.grishaninvyacheslav.pressure_and_pulse_log.databinding.ActivityMainBinding
import io.github.grishaninvyacheslav.pressure_and_pulse_log.ui.screens.IScreens
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val router: Router by inject()
    private val screens: IScreens by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            router.replaceScreen(screens.log())
        }
    }

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator = AppNavigator(this, R.id.container)

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}