package ru.geekbrains.pictureapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.pictureapp.model.SystemPreferences
import ru.geekbrains.pictureapp.model.SystemPreferences.APP_THEME_RES
import ru.geekbrains.pictureapp.model.enum.Theme
import ru.geekbrains.pictureapp.ui.pod.PodFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main_activity, PodFragment.newInstance())
                    .commitNow()
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