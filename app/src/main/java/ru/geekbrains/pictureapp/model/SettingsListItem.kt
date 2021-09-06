package ru.geekbrains.pictureapp.model

import com.google.android.material.chip.ChipGroup

class SettingsListItem (
    val titleId: Int?,
    val optionWithResource: List<OptionWithResource> = emptyList(),  // здесь дб лист с объектами-опциями. Сейчас у опции только id заголовка
    private val activeOption: Int? = null,
    val onCheckedChangeListener: ChipGroup.OnCheckedChangeListener? = null
) {

    init {
        optionWithResource
            .filter { it.resId == activeOption }
            .forEach { it.isChecked = true}
    }

    class OptionWithResource(
        val titleId: Int,
        val resId: Int? = null,
        var isChecked: Boolean = false
    )

}