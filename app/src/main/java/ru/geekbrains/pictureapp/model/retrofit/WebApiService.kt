package ru.geekbrains.pictureapp.model.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.pictureapp.BuildConfig
import ru.geekbrains.pictureapp.model.ApiService
import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.model.data.PodServerResponseData
import ru.geekbrains.pictureapp.ui.interfaces.OnReceivingResponseListener

object WebApiService {

    fun getPictureOfTheDayRequest(listener: OnReceivingResponseListener, date: String?, isHd: Boolean?) =
        getApiService().getPictureOfTheDay(date, isHd, BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<PodServerResponseData> {
                override fun onResponse(call: Call<PodServerResponseData>, response: Response<PodServerResponseData>) {
                    if (response.isSuccessful && response.body() != null) {
                        listener.onSuccess(PodData.Success(response.body()!!))
                    }
                    else {
                        val msg = if (response.message().isNullOrEmpty()) "Unknown error" else response.message()
                        listener.onFailure(PodData.Error(Throwable(msg)))
                    }
                }

                override fun onFailure(call: Call<PodServerResponseData>, t: Throwable) {
                    listener.onFailure(PodData.Error(t))
                }
            })

    private fun getApiService(): ApiService = RetrofitClient.getInstance().create(ApiService::class.java)

}