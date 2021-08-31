package ru.geekbrains.pictureapp.ui.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.pictureapp.BuildConfig
import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.model.data.PodServerResponseData
import ru.geekbrains.pictureapp.model.retrofit.PodApiImpl
import ru.geekbrains.pictureapp.ui.interfaces.BasePresenter
import ru.geekbrains.pictureapp.ui.interfaces.PodContract
import java.text.SimpleDateFormat
import java.util.*

class PodPresenter(
    val view: PodContract.View,
    private var date: Date? = null,
    private var isHd: Boolean? = null
) : PodContract.Presenter, BasePresenter() {

    private val liveData: MutableLiveData<PodData> = MutableLiveData()
    private val apiImpl: PodApiImpl = PodApiImpl()
    private val getDataCallback: Callback<PodServerResponseData> = object :
        Callback<PodServerResponseData> {
        override fun onResponse(call: Call<PodServerResponseData>, response: Response<PodServerResponseData>) {
            if (response.isSuccessful && response.body() != null) {
                liveData.value = PodData.Success(response.body()!!)
            }
            else {
                val msg = if (response.message().isNullOrEmpty()) "Unknown error" else response.message()
                liveData.value = PodData.Error(Throwable(msg))
            }
        }

        override fun onFailure(call: Call<PodServerResponseData>, t: Throwable) {
            liveData.value = PodData.Error(t)
        }
    }


    override fun onViewCreated() {
        getData(date, isHd).observe(view, { view.showData(it) })
    }

    override fun requestData() {
        getData(date, isHd).observe(view, { view.showData(it) })
    }

    override fun requestData(date: Date?, isHd: Boolean?) {
        this.date = date
        this.isHd = isHd
        getData(date, isHd).observe(view, { view.showData(it) })
    }

    private fun getData(date: Date?, isHd: Boolean?): LiveData<PodData> {
        liveData.value = PodData.Loading(null)

        val formattedDate = date?.let { DATE_FORMATTER.format(it) }
        if (BuildConfig.NASA_API_KEY.isBlank()) {
            liveData.value = PodData.Error(Throwable("API key is empty"))
        }
        else {
            apiImpl.getInstance()
                .getPictureOfTheDay(formattedDate, isHd, BuildConfig.NASA_API_KEY)
                .enqueue(getDataCallback)
        }

        return liveData
    }

    companion object {
        private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    }

}