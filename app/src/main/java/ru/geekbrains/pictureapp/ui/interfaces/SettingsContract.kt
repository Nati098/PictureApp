package ru.geekbrains.pictureapp.ui.interfaces

import ru.geekbrains.pictureapp.model.SettingsListItem

interface SettingsContract {

    interface View {
        fun updateUi()
    }

    interface Presenter {
        fun getSettings(): List<SettingsListItem>
    }

}