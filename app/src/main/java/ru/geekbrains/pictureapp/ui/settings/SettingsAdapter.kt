package ru.geekbrains.pictureapp.ui.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.model.SettingsListItem

class SettingsAdapter(
    private val settingsList: List<SettingsListItem>
): RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.settings_list_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(settingsList[position])

    override fun getItemCount(): Int = settingsList.size


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val optionsGroup: ChipGroup = view.findViewById(R.id.options_group)

        private val context: Context = view.context
        private val options: List<Chip>? = null

        fun bind(item: SettingsListItem) {
            item.titleId?.let {
                title.text = context.getString(it)
                item.options.forEach { opt -> optionsGroup.addView(createChip(context, opt)) }
            }

            // TODO: (optionsGroup.childCount == 0)
        }

        private fun createChip(context: Context, option: SettingsListItem.Option): Chip =
            Chip(context, null, R.style.Widget_MaterialComponents_Chip_Choice).apply {
                id = option.titleId
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                setText(option.titleId)
                isChecked = option.isChecked
            }

    }
}