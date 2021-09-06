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
                R.string.setting_theme_title,
                listOf(
                    SettingsListItem.OptionWithResource(R.string.setting_theme_space, Theme.SPACE.code),
                    SettingsListItem.OptionWithResource(R.string.setting_theme_mars, Theme.MARS.code)
                ),
                getActiveOption(SystemPreferences.APP_THEME_RES) ?: Theme.SPACE.code)
            { group, position ->
                group.findViewById<Chip>(position).let { chip ->
                    when(chip.id) {
                        R.string.setting_theme_mars -> SystemPreferences.setPreference(
                            SystemPreferences.APP_THEME_RES, Theme.MARS.code)
                        else -> SystemPreferences.setPreference(
                            SystemPreferences.APP_THEME_RES, Theme.SPACE.code)
                    }
                }
                view.updateUi()
            })
            add(SettingsListItem(
                R.string.setting_font_title,
                listOf(
                    SettingsListItem.OptionWithResource(R.string.setting_font_aguda, R.font.aguda_regular)
                ),
                getActiveOption(SystemPreferences.APP_FONT_RES) ?: R.string.setting_font_aguda))
        }

    private fun getActiveOption(key: String): Int? {
        val res = SystemPreferences.getIntPreference(key)
        return if (res == 0) null else res
    }

}