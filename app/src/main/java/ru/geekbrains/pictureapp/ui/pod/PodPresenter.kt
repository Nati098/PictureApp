package ru.geekbrains.pictureapp.ui.pod

import ru.geekbrains.pictureapp.BuildConfig
import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.model.retrofit.WebApiService
import ru.geekbrains.pictureapp.ui.interfaces.BasePresenter
import ru.geekbrains.pictureapp.ui.interfaces.OnReceivingResponseListener
import ru.geekbrains.pictureapp.ui.interfaces.PodContract
import java.text.SimpleDateFormat
import java.util.*

class PodPresenter(
    val view: PodContract.View,
) : PodContract.Presenter, BasePresenter(), OnReceivingResponseListener {

    private var date: String? = null
    private var isHd: Boolean? = null

    constructor(view: PodContract.View, date: Date?, isHd: Boolean?) : this(view) {
        this.date = date?.let { DATE_FORMATTER.format(it) }
        this.isHd = isHd
    }

    override fun onViewCreated() {
        getData(date, isHd)
    }

    override fun requestData() {
        getData(date, isHd)
    }

    override fun requestData(date: Date?, isHd: Boolean?) {
        this.date = date?.let { DATE_FORMATTER.format(it) }
        this.isHd = isHd
        getData(this.date, this.isHd)
    }

    private fun getData(date: String?, isHd: Boolean?) {
        onLoading(PodData.Loading(null))

        if (BuildConfig.NASA_API_KEY.isBlank()) {
            onFailure(PodData.Error(Throwable("API key is empty")))
        } else {
            WebApiService.getPictureOfTheDayRequest(this, date, isHd)
        }
    }

    override fun onSuccess(data: PodData.Success) {
        view.showData(data.serverResponseData)
    }

    override fun onLoading(data: PodData.Loading) {
        view.showLoading()
    }

    override fun onFailure(data: PodData.Error) {
        view.showError(data.error.message)
    }

    companion object {
        private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    }

}