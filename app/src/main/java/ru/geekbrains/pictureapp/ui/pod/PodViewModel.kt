package ru.geekbrains.pictureapp.ui.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.pictureapp.BuildConfig
import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.model.data.PodServerResponseData
import ru.geekbrains.pictureapp.model.retrofit.PodApiImpl
import java.text.SimpleDateFormat
import java.util.*

class PodViewModel(
    private val liveData: MutableLiveData<PodData> = MutableLiveData(),
    private val apiImpl: PodApiImpl = PodApiImpl()
) : ViewModel() {

    private val getDataCallback: Callback<PodServerResponseData> = object : Callback<PodServerResponseData> {
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

    fun getPOD(date: Date?, isHd: Boolean?): LiveData<PodData> {
        getPODFromServer(date?.let { DATE_FORMATTER.format(it) }, isHd)
        return liveData
    }

    private fun getPODFromServer(date: String?, isHd: Boolean?) {
        liveData.value = PodData.Loading(null)

        if (BuildConfig.NASA_API_KEY.isBlank()) {
            liveData.value = PodData.Error(Throwable("API key is empty"))
        }
        else {
            apiImpl.getRetrofitImpl()
                .getPictureOfTheDay(date, isHd, BuildConfig.NASA_API_KEY)
                .enqueue(getDataCallback)
        }
    }

    companion object {
        private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    }

}