package ru.geekbrains.pictureapp.ui.pod

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.geekbrains.pictureapp.*
import ru.geekbrains.pictureapp.databinding.MainFragmentBinding
import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.ui.navigationdrawer.PodBottomNavigationDrawerFragment
import ru.geekbrains.pictureapp.ui.toast
import java.util.*

class PodFragment : Fragment() {
    private val MEDIA_TYPE_IMAGE = "image"

    private val viewModel: PodViewModel by lazy { ViewModelProvider(this).get(PodViewModel::class.java) }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        viewModel.getPOD(null, null).observe(this@PodFragment, Observer<PodData> {renderData(it)})
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

    private fun bindView() {
        binding.searchTextInput.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_URL${binding.searchTextInputEdit.text.toString()}")
            })
        }

        binding.chipsLayout.dataGroup.setOnCheckedChangeListener { group, position ->
            group.findViewById<Chip>(position)?.let { chip ->
                val isHd = binding.chipsLayout.hdChoiceChip.isChecked
                when(chip.id) {
                    R.id.data_2_days_before_chip -> {
                        viewModel.getPOD(getDaysAgo(2), isHd).observe(this@PodFragment, { renderData(it) })
                    }
                    R.id.data_yesterday_chip -> {
                        viewModel.getPOD(getDaysAgo(1), isHd).observe(this@PodFragment, { renderData(it) })
                    }
                    R.id.data_today_chip -> viewModel.getPOD(null, isHd).observe(this@PodFragment, { renderData(it) })
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

        binding.fab.setOnClickListener { v -> onCLickListenerFab(v, R.string.temp_added_to_fav) }

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 0) {
                moveFabToEnd(context, binding.bottomAppBar, binding.fab)
            }
            else if (scrollY < SCROLL_Y_THRESHOLD) {
                moveFabToCenter(context, binding.bottomAppBar, binding.fab)
            }
        }
    }

    private fun renderData(data: PodData) =
        when(data) {
            is PodData.Success -> {
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

            is PodData.Loading -> renderLoading()
            is PodData.Error -> renderError(data.error.message)
        }

    private fun renderImage(url: String) {
        binding.image.isVisible = true
        binding.chipsLayout.chipsLayout.isVisible = true
        binding.loadingProgressBar.isVisible = false

        binding.image.load(url) {
            lifecycle(this@PodFragment)
            error(R.drawable.ic_load_error)
            placeholder(R.drawable.ic_no_photo)
        }
    }

    private fun renderImageInfo(title: String?, description: String?) {
        binding.bottomSheetContainer.bottomSheetContainer.isVisible = !title.isNullOrBlank()
        binding.bottomSheetContainer.bottomSheetHeader.text = title
        binding.bottomSheetContainer.bottomSheetDescription.text = description
    }

    private fun renderLoading() {
        binding.image.isVisible = false
        binding.chipsLayout.chipsLayout.isVisible = false
        binding.loadingProgressBar.isVisible = true
    }

    private fun renderError(message: String? = null) {
        binding.image.isVisible = true
        binding.chipsLayout.chipsLayout.isVisible = true
        binding.loadingProgressBar.isVisible = false
        binding.image.setImageResource(R.drawable.ic_load_error)

        context?.toast(message ?: "Unknown error")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val WIKI_URL = "https://en.wikipedia.org/wiki/"
        private const val SCROLL_Y_THRESHOLD = 22

        fun newInstance() = PodFragment()
    }
}