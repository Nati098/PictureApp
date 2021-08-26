package ru.geekbrains.pictureapp.ui.pod

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.geekbrains.pictureapp.*
import ru.geekbrains.pictureapp.databinding.MainFragmentBinding
import ru.geekbrains.pictureapp.model.PODData
import ru.geekbrains.pictureapp.ui.navigationdrawer.PODBottomNavigationDrawerFragment
import ru.geekbrains.pictureapp.ui.toast
import java.util.*

class PODFragment : Fragment() {
    private val MEDIA_TYPE_IMAGE = "image"

    private val viewModel: PODViewModel by lazy { ViewModelProvider(this).get(PODViewModel::class.java) }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        bindView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getPOD(null, null).observe(this@PODFragment, Observer<PODData> {renderData(it)})
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_weather -> toast("Space Weather will be soon")
            R.id.app_bar_settings -> toast("Settings will be soon") //activity?.supportFragmentManager?.addFragment<ChipsFragment>(activity!!.applicationContext, Bundle(), R.id.container_main_activity)
            android.R.id.home -> activity?.let { PODBottomNavigationDrawerFragment().show(it.supportFragmentManager, "navigation_drawer") }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun bindView() {
        binding.textInputSearch.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.textInputEditSearch.text.toString()}")
            })
        }

        binding.chipsLayout.dataGroup.setOnCheckedChangeListener { group, position ->
            group.findViewById<Chip>(position)?.let { chip ->
                val isHd = binding.chipsLayout.hdChoiceChip.isChecked
                when(chip.id) {
                    R.id.data_2_days_before_chip -> {
                        viewModel.getPOD(getDaysAgo(2), isHd).observe(this@PODFragment, Observer<PODData> { renderData(it) })
                    }
                    R.id.data_yesterday_chip -> {
                        viewModel.getPOD(getDaysAgo(1), isHd).observe(this@PODFragment, Observer<PODData> { renderData(it) })
                    }
                    R.id.data_today_chip -> viewModel.getPOD(null, isHd).observe(this@PODFragment, Observer<PODData> { renderData(it) })
                }
            }
        }

        setBottomSheetBehaviour(binding.bottomSheetContainer.bottomSheetContainer)
        setBottomBar(binding.bottomAppBar)
    }

    private fun getDaysAgo(daysAgo: Int): Date = Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)*daysAgo)

    private fun setBottomSheetBehaviour(bottomSheet: ConstraintLayout) {
        BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setBottomBar(bottomAppBar: BottomAppBar) {
        (activity as MainActivity).setSupportActionBar(bottomAppBar)
        setHasOptionsMenu(true)

//        binding.bottomAppBarFab.setOnClickListener {
//            if (isMainFragment) {
//                binding.bottomAppBar.navigationIcon = null
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                binding.bottomAppBarFab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_hamburger_menu_bottom_bar) })
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_fab)
//            }
//            else {
//                binding.bottomAppBar.navigationIcon = context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_hamburger_menu_bottom_bar) }
//                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                binding.bottomAppBarFab.setImageDrawable(context?.let { c -> ContextCompat.getDrawable(c, R.drawable.ic_plus_fab) })
//                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_main)
//            }
//
//            isMainFragment = !isMainFragment
//        }
        binding.bottomAppBarFab.setOnClickListener { v -> onCLickListenerFab(v, "Added to favourites") }

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 0) {
                moveFabToEnd(context, binding.bottomAppBar, binding.bottomAppBarFab)
            }
            else if (scrollY < 22) {
                moveFabToCenter(context, binding.bottomAppBar, binding.bottomAppBarFab)
            }
        }
    }

    private fun renderData(data: PODData) =
        when(data) {
            is PODData.Success -> {
                val response = data.serverResponseData

                if (response.mediaType != MEDIA_TYPE_IMAGE) {
                    renderError("Today it's not a picture! See you next day!")
                }
                else {
                    val url = response.url

                    if (url.isNullOrEmpty()) {
                        renderError("URL is empty")
                    }
                    else {
                        renderImage(url)
                        renderImageInfo(response.title, response.explanation)
                    }
                }
            }

            is PODData.Loading -> renderLoading()
            is PODData.Error -> renderError(data.error.message)
        }

    private fun renderImage(url: String) {
        binding.image.visibility = View.VISIBLE
        binding.chipsLayout.chipsLayout.visibility = View.VISIBLE
        binding.progressLoading.visibility = View.GONE

        binding.image.load(url) {
            lifecycle(this@PODFragment)
            error(R.drawable.ic_load_error)
            placeholder(R.drawable.ic_no_photo)
        }
    }

    private fun renderImageInfo(title: String?, description: String?) {
        binding.bottomSheetContainer.bottomSheetContainer.visibility = if(title.isNullOrBlank()) View.GONE else View.VISIBLE
        binding.bottomSheetContainer.bottomSheetHeader.text = title
        binding.bottomSheetContainer.bottomSheetDescription.text = description
    }

    private fun renderLoading() {
        binding.image.visibility = View.GONE
        binding.chipsLayout.chipsLayout.visibility = View.GONE
        binding.progressLoading.visibility = View.VISIBLE
    }

    private fun renderError(message: String? = null) {
        binding.image.visibility = View.VISIBLE
        binding.chipsLayout.chipsLayout.visibility = View.VISIBLE
        binding.progressLoading.visibility = View.GONE
        binding.image.setImageResource(R.drawable.ic_load_error)

        toast(message ?: "Unknown error")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var isMainFragment = true

        fun newInstance() = PODFragment()
    }
}