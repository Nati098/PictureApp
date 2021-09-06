package ru.geekbrains.pictureapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.geekbrains.pictureapp.databinding.MainActivityBinding
import ru.geekbrains.pictureapp.model.SystemPreferences
import ru.geekbrains.pictureapp.model.SystemPreferences.APP_THEME_RES
import ru.geekbrains.pictureapp.model.enum.Theme
import ru.geekbrains.pictureapp.ui.pod.PodFragment
import ru.geekbrains.pictureapp.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private var _binding: MainActivityBinding? = null
    private val binding: MainActivityBinding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        _binding = MainActivityBinding.inflate(layoutInflater)
        bindView()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main_activity, PodFragment.newInstance())
                    .commitNow()
        }
    }

    private fun bindView() {
        setBottomNavigation(binding.bottomNavigationView)
        binding.bottomNavigationView.selectedItemId = R.id.nav_bar_pod
    }

    protected fun setBottomNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_bar_nearest -> {}
                R.id.nav_bar_settings -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main_activity, SettingsFragment.newInstance())
                    .commitAllowingStateLoss()
                R.id.nav_bar_search -> {}
                else -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main_activity, PodFragment.newInstance())
                    .commitAllowingStateLoss()
            }
            true
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun getTheme(): Resources.Theme {
        val theme: Resources.Theme = super.getTheme()

        when(SystemPreferences.getIntPreference(APP_THEME_RES)) {
            0 -> {
                SystemPreferences.setPreference(APP_THEME_RES, Theme.SPACE.code)
                theme.applyStyle(Theme.SPACE.code, true)
            }
            else -> theme.applyStyle(SystemPreferences.getIntPreference(APP_THEME_RES), true)
        }
        return theme
    }
}