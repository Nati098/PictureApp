package ru.geekbrains.pictureapp.ui.interfaces

import ru.geekbrains.pictureapp.model.SettingsListItem

interface SettingsContract {

    interface View {
        fun getSettingsList(): List<SettingsListItem>
        fun updateUi()
    }

    interface Presenter {
//        fun onUpdateUi()
    }

}