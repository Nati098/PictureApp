package ru.geekbrains.pictureapp.ui.settings

import android.content.Context
import android.util.Log
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


    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val optionsGroup: ChipGroup = view.findViewById(R.id.options_group)

        fun bind(item: SettingsListItem) {
            Log.d("MyDebug", "${javaClass.simpleName} item.titleId=${item.titleId}")
            item.titleId?.let {
                Log.d("MyDebug", "${javaClass.simpleName} item=${view.context.getString(it)}")
                title.text = view.context.getString(it)

                item.optionWithResource.forEach { opt -> optionsGroup.addView(createChip(view.context, opt)) }
                optionsGroup.setOnCheckedChangeListener(item.onCheckedChangeListener)
            }

            Log.d("MyDebug", "${javaClass.simpleName} optionsGroup.childCount=${optionsGroup.childCount}")
            // TODO: (optionsGroup.childCount == 0)
        }

        private fun createChip(context: Context, optionWithResource: SettingsListItem.OptionWithResource): Chip =
            Chip(context, null, R.style.Widget_MaterialComponents_Chip_Choice).apply {
                id = optionWithResource.titleId
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                setText(optionWithResource.titleId)
                isChecked = optionWithResource.isChecked
            }

    }
}