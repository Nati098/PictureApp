package ru.geekbrains.pictureapp.ui.navigationdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.geekbrains.pictureapp.R
import ru.geekbrains.pictureapp.databinding.BottomNavigationLayoutBinding
import ru.geekbrains.pictureapp.ui.toast

class PodBottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigation.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_navigation_fav -> context?.toast(R.string.menu_item_favourite)
                R.id.bottom_navigation_send -> context?.toast(R.string.menu_item_send)
            }
            true
        }
    }
}