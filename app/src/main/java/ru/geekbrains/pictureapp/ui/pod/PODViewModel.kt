package ru.geekbrains.pictureapp.ui.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.pictureapp.BuildConfig
import ru.geekbrains.pictureapp.model.PODData
import ru.geekbrains.pictureapp.model.data.PODServerResponseData
import ru.geekbrains.pictureapp.model.retrofit.PODRetrofitImpl
import java.text.SimpleDateFormat
import java.util.*

class PODViewModel(
            private val liveData: MutableLiveData<PODData> = MutableLiveData(),
            private val retrofit: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    private val getDataCallback: Callback<PODServerResponseData> = object : Callback<PODServerResponseData> {
        override fun onResponse(call: Call<PODServerResponseData>, response: Response<PODServerResponseData>) {
            if (response.isSuccessful && response.body() != null) {
                liveData.value = PODData.Success(response.body()!!)
            }
            else {
                val msg = if (response.message().isNullOrEmpty()) "Unknown error" else response.message()
                liveData.value = PODData.Error(Throwable(msg))
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveData.value = PODData.Error(t)
        }
    }

    fun getPOD(date: Date?, isHd: Boolean?): LiveData<PODData> {
        getPODFromServer(date?.let { DATE_FORMATTER.format(it) }, isHd)
        return liveData
    }

    private fun getPODFromServer(date: String?, isHd: Boolean?) {
        liveData.value = PODData.Loading(null)

        if (BuildConfig.NASA_API_KEY.isBlank()) {
            liveData.value = PODData.Error(Throwable("API key is empty"))
        }
        else {
            retrofit.getRetrofitImpl()
                .getPictureOfTheDay(date, isHd, BuildConfig.NASA_API_KEY)
                .enqueue(getDataCallback)
        }
    }

    companion object {
        val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    }

}