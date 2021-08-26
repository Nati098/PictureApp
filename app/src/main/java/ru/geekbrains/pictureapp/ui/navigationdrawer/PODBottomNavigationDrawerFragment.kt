package ru.geekbrains.pictureapp.ui.navigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.databinding.BottomNavigationLayoutBinding
import ru.geekbrains.pictureapp.ui.toast

class PODBottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomNavigationLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.navigation.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_navigation_fav -> toast("Favourites")
                R.id.bottom_navigation_send -> toast("Send to...")
            }
            true
        }
    }
}