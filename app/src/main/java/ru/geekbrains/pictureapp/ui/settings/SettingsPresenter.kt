package ru.geekbrains.pictureapp.ui.settings

import com.google.android.material.chip.Chip
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.model.SettingsListItem
import ru.geekbrains.pictureapp.model.SystemPreferences
import ru.geekbrains.pictureapp.model.enum.Theme
import ru.geekbrains.pictureapp.ui.interfaces.BasePresenter
import ru.geekbrains.pictureapp.ui.interfaces.SettingsContract

class SettingsPresenter (
    val view: SettingsContract.View,
) : SettingsContract.Presenter, BasePresenter() {
    override fun getSettings(): List<SettingsListItem> =
        arrayListOf<SettingsListItem>().apply {
            add(SettingsListItem(
                R.layout.settings_list_card,
                listOf(
                    SettingsListItem.Option(R.string.setting_theme_light),
                    SettingsListItem.Option(R.string.setting_theme_dark)
                ),
                getActiveOption(SystemPreferences.APP_THEME_RES))
            { group, position ->
                group.findViewById<Chip>(position).let { chip ->
                    when(chip.id) {
                        R.string.setting_theme_light -> SystemPreferences.setPreference(
                            SystemPreferences.APP_THEME_RES, Theme.LIGHT.code)
                        R.string.setting_theme_dark -> SystemPreferences.setPreference(
                            SystemPreferences.APP_THEME_RES, Theme.DARK.code)
                    }
                }
                view.updateUi()
            })
            add(SettingsListItem(
                R.layout.settings_list_card,
                listOf(
                    SettingsListItem.Option(R.string.setting_font_default),
                    SettingsListItem.Option(R.string.setting_font_montserrat),
                    SettingsListItem.Option(R.string.setting_font_aguda)
                ),
                getActiveOption(SystemPreferences.APP_FONT_RES)))
        }

    private fun getActiveOption(key: String): Int? {
        val res = SystemPreferences.getIntPreference(key)
        return if (res == 0) null else res
    }

}