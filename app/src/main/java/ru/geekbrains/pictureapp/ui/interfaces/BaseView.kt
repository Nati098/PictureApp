package ru.geekbrains.pictureapp.ui.interfaces

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.ui.navigationdrawer.PodBottomNavigationDrawerFragment
import ru.geekbrains.pictureapp.ui.toast


abstract class BaseView<VB : ViewBinding> : BaseContract.View, Fragment() {

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    private var presenter: BaseContract.Presenter<BaseView<VB>>? = null

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var isViewCreated: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_weather -> context?.toast(R.string.temp_space_weather)
            R.id.app_bar_settings -> context?.toast(R.string.temp_settings) //activity?.supportFragmentManager?.addFragment<ChipsFragment>(activity!!.applicationContext, Bundle(), R.id.container_main_activity)
            android.R.id.home -> activity?.let { PodBottomNavigationDrawerFragment().show(it.supportFragmentManager, "navigation_drawer") }
        }

        return super.onOptionsItemSelected(item)
    }
}