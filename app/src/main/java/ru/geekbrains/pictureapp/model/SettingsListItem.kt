package ru.geekbrains.pictureapp.model

import com.google.android.material.chip.ChipGroup

class SettingsListItem (
    val titleId: Int?,
    val options: List<Option> = emptyList(),  // здесь дб лист с объектами-опциями. Сейчас у опции только id заголовка
    private val activeOption: Int? = null,
    val onCheckedChangeListener: ChipGroup.OnCheckedChangeListener? = null
) {

    init {
        options
            .filter { it.titleId == activeOption }
            .forEach { it.isChecked = true}
    }

    class Option(
        val titleId: Int,
        var isChecked: Boolean = false
    )

}