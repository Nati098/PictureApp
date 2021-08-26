package ru.geekbrains.pictureapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.pictureapp.model.data.PODServerResponseData

interface PODApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("date") date: String?,
                           @Query("hd") hd: Boolean?,
                           @Query("api_key") apiKey: String): Call<PODServerResponseData>

}