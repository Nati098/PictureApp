package ru.geekbrains.pictureapp.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.databinding.SettingsFragmentBinding
import ru.geekbrains.pictureapp.ui.interfaces.BaseView
import ru.geekbrains.pictureapp.ui.interfaces.SettingsContract


class SettingsFragment: SettingsContract.View, BaseView<SettingsFragmentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SettingsFragmentBinding =
        {layoutInflater: LayoutInflater, _, _ -> SettingsFragmentBinding.inflate(layoutInflater) }

    private lateinit var presenter: SettingsContract.Presenter
    private lateinit var settingsAdapter: SettingsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SettingsPresenter(this)
        acceptPresenter(presenter as SettingsPresenter)
        bindView()
    }


    override fun bindView() {
        val settingsList = presenter.getSettings()
        Log.d("MyDebug", "${javaClass.simpleName} onViewCreated settings size = ${settingsList.size}")

        if (settingsList.isEmpty()) {
            showError(getString(R.string.error_empty_list))
            return
        }

        binding.recycler.adapter = SettingsAdapter(settingsList)

//        binding.loadingProgressBar.isVisible = false
        binding.recycler.isVisible = true
    }

    override fun updateUi() {
        activity?.recreate()
    }

    override fun showLoading() {
//        binding.loadingProgressBar.isVisible = true
//        binding.recycler.isVisible = false
    }

    override fun showError(message: String?) {
        // TODO
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}