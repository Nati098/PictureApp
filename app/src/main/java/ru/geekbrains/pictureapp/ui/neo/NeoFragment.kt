package ru.geekbrains.pictureapp.ui.neo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.geekbrains.pictureapp.databinding.NeoFragmentBinding
import ru.geekbrains.pictureapp.ui.interfaces.BaseView
import ru.geekbrains.pictureapp.ui.interfaces.NeoContract


class NeoFragment: NeoContract.View, BaseView<NeoFragmentBinding>() {


    override fun bindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): NeoFragmentBinding {
        // TODO("Not yet implemented")
    }

    override fun bindView() {
        // TODO("Not yet implemented")
    }

    override fun showLoading() {
        // TODO("Not yet implemented")
    }

    override fun showError(message: String?) {
        // TODO("Not yet implemented")
    }


    companion object {
        fun newInstance() = NeoFragment()
    }

}