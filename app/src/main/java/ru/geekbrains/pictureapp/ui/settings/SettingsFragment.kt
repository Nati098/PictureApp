package ru.geekbrains.pictureapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.databinding.SettingsFragmentBinding
import ru.geekbrains.pictureapp.model.SettingsListItem
import ru.geekbrains.pictureapp.model.SettingsListItem.Option
import ru.geekbrains.pictureapp.model.SystemPreferences
import ru.geekbrains.pictureapp.model.SystemPreferences.APP_FONT_RES
import ru.geekbrains.pictureapp.model.SystemPreferences.APP_THEME_RES
import ru.geekbrains.pictureapp.model.enum.Theme
import ru.geekbrains.pictureapp.ui.interfaces.BaseView
import ru.geekbrains.pictureapp.ui.interfaces.SettingsContract

class SettingsFragment: SettingsContract.View, BaseView<SettingsFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SettingsFragmentBinding =
        {layoutInflater: LayoutInflater, _, _ -> SettingsFragmentBinding.inflate(layoutInflater) }

    private lateinit var settingsAdapter: SettingsAdapter
    private val settingsList: ArrayList<SettingsListItem> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acceptPresenter(SettingsPresenter(this))
        bindView()
    }

    override fun bindView() {
        settingsAdapter = SettingsAdapter(getSettingsList())
        binding.recycler.adapter = settingsAdapter

//        binding.loadingProgressBar.isVisible = false
//        binding.recycler.isVisible = true
    }

    override fun getSettingsList(): List<SettingsListItem> {
        if (settingsList.isEmpty()) {
            return settingsList.apply {
                add(SettingsListItem(
                    R.layout.settings_list_card,
                    listOf(Option(R.string.setting_theme_light), Option(R.string.setting_theme_dark)),
                    getActiveOption(APP_THEME_RES))
                { group, position ->
                    group.findViewById<Chip>(position).let { chip ->
                        when(chip.id) {
                            R.string.setting_theme_light -> SystemPreferences.setPreference(APP_THEME_RES, Theme.LIGHT.code)
                            R.string.setting_theme_dark -> SystemPreferences.setPreference(APP_THEME_RES, Theme.DARK.code)
                        }
                    }
                    activity?.recreate()
                })
                add(SettingsListItem(
                    R.layout.settings_list_card,
                    listOf(Option(R.string.setting_font_default), Option(R.string.setting_font_montserrat), Option(R.string.setting_font_aguda)),
                    getActiveOption(APP_FONT_RES)))
            }
        }

        return settingsList
    }

    private fun getActiveOption(key: String): Int? {
        val res = SystemPreferences.getIntPreference(key)
        return if (res < 0) null else res
    }

    override fun updateUi() {
        // TODO("Not yet implemented")
    }

    override fun showLoading() {
//        binding.loadingProgressBar.isVisible = true
//        binding.recycler.isVisible = false
    }

    override fun showError(message: String?) {}

    companion object {
        fun newInstance() = SettingsFragment()
    }
}