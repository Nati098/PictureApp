package ru.geekbrains.pictureapp.ui.settings

import ru.geekbrains.pictureapp.ui.interfaces.BasePresenter
import ru.geekbrains.pictureapp.ui.interfaces.SettingsContract

class SettingsPresenter (
    val view: SettingsContract.View,
) : SettingsContract.Presenter, BasePresenter()