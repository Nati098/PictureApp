package ru.geekbrains.pictureapp.ui.interfaces

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.ui.navigationdrawer.PodBottomNavigationDrawerFragment
import ru.geekbrains.pictureapp.ui.settings.SettingsFragment
import ru.geekbrains.pictureapp.ui.toast


abstract class BaseView<VB : ViewBinding> : BaseContract.View, Fragment() {

    //protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    private var presenter: BaseContract.Presenter<BaseView<VB>>? = null

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var isViewCreated: Boolean = false


    abstract fun bindLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = bindLayout(inflater, container, savedInstanceState) //bindingInflater.invoke(inflater, container, false)
        isViewCreated = true
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun acceptPresenter(presenter: BaseContract.Presenter<BaseContract.View>) {
        this.presenter = presenter

        if (isViewCreated) {
            presenter.onViewCreated()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_weather -> context?.toast(R.string.temp_space_weather)
            R.id.app_bar_settings -> activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main_activity, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit() }
            android.R.id.home -> activity?.let { PodBottomNavigationDrawerFragment().show(it.supportFragmentManager, "navigation_drawer") }
        }

        return super.onOptionsItemSelected(item)
    }
}