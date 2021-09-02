package ru.geekbrains.pictureapp.model

import android.content.Context
import android.content.SharedPreferences
import ru.geekbrains.pictureapp.ui.App


object SystemPreferences {
    private val SYSTEM_PREFERENCES = "system_preferences"
    val APP_THEME_RES = "app_theme"
    val APP_FONT_RES = "app_font"

    private fun getSP(): SharedPreferences {
        return App.getContext().getSharedPreferences(SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getIntPreference(key: String): Int {
        return getSP().getInt(key, -1)
    }

    fun setPreference(key: String?, value: Int?) {
        value?.let { getSP().edit().putInt(key, it).apply() }
    }
}