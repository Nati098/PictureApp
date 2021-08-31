package ru.geekbrains.pictureapp.ui.pod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import ru.geekbrains.pictureapp.*
import ru.geekbrains.pictureapp.databinding.MainFragmentBinding
import ru.geekbrains.pictureapp.model.data.PodServerResponseData
import ru.geekbrains.pictureapp.ui.interfaces.BaseView
import ru.geekbrains.pictureapp.ui.interfaces.PodContract
import ru.geekbrains.pictureapp.ui.toast
import java.util.*

class PodFragment : PodContract.View, BaseView<MainFragmentBinding>() {

    private lateinit var presenter: PodContract.Presenter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding =
        {layoutInflater: LayoutInflater, _, _ -> MainFragmentBinding.inflate(layoutInflater) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
        createPresenter()
    }

    private fun createPresenter() {
        presenter = PodPresenter(this)
        acceptPresenter(presenter as PodPresenter)
    }

    override fun bindView() {
        binding.searchTextInput.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("$WIKI_URL${binding.searchTextInputEdit.text.toString()}")
            })
        }

        binding.chipsLayout.dataTodayChip.isChecked = true
        binding.chipsLayout.dataGroup.setOnCheckedChangeListener { group, position ->
            group.findViewById<Chip>(position)?.let { chip ->
                val isHd = binding.chipsLayout.hdChoiceChip.isChecked
                when(chip.id) {
                    R.id.data_2_days_before_chip -> presenter.requestData(getDaysAgo(2), isHd)
                    R.id.data_yesterday_chip -> presenter.requestData(getDaysAgo(1), isHd)
                    R.id.data_today_chip -> presenter.requestData(null, isHd)
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
            } else if (scrollY < SCROLL_Y_THRESHOLD) {
                moveFabToCenter(context, binding.bottomAppBar, binding.fab)
            }
        }
    }

    override fun showData(response: PodServerResponseData)  {
        if (response.mediaType != MEDIA_TYPE_IMAGE) {
            showError(getString(R.string.error_not_picture))
        }
        else {
            val url = response.url

            if (url.isNullOrEmpty()) {
                showError(getString(R.string.error_empty_url))
            }
            else {
                showImage(url)
                showImageInfo(response.title, response.explanation)
            }
        }
    }

    private fun showImage(url: String) {
        binding.image.isVisible = true
        binding.chipsLayout.chipsLayout.isVisible = true
        binding.loadingProgressBar.isVisible = false

        binding.image.load(url) {
            lifecycle(this@PodFragment)
            error(R.drawable.ic_load_error)
            placeholder(R.drawable.ic_no_photo)
        }
    }

    private fun showImageInfo(title: String?, description: String?) {
        binding.bottomSheetContainer.bottomSheetContainer.isVisible = !title.isNullOrBlank()
        binding.bottomSheetContainer.bottomSheetHeader.text = title
        binding.bottomSheetContainer.bottomSheetDescription.text = description
    }

    override fun showLoading() {
        binding.image.isVisible = false
        binding.chipsLayout.chipsLayout.isVisible = false
        binding.loadingProgressBar.isVisible = true
    }

    override fun showError(message: String?) {
        binding.image.isVisible = true
        binding.chipsLayout.chipsLayout.isVisible = true
        binding.loadingProgressBar.isVisible = false
        binding.image.setImageResource(R.drawable.ic_load_error)

        context?.toast(message ?: getString(R.string.error_unknown))
    }

    companion object {
        private const val WIKI_URL = "https://en.wikipedia.org/wiki/"
        private const val SCROLL_Y_THRESHOLD = 22
        private const val MEDIA_TYPE_IMAGE = "image"


        fun newInstance() = PodFragment()
    }
}