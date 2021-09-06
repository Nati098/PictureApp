package ru.geekbrains.pictureapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.geekbrains.pictureapp.model.data.NeoServerResponseData
import ru.geekbrains.pictureapp.model.data.PodServerResponseData

interface ApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("date") date: String?,
                           @Query("hd") hd: Boolean?,
                           @Query("api_key") apiKey: String): Call<PodServerResponseData>

    @GET("neo/rest/v1/feed")
    fun getNearestEarthObjectsByDate(@Query("start_date") startDate: String?,
                                     @Query("end_date") endDate: String?,
                                     @Query("api_key") apiKey: String): Call<NeoServerResponseData>

    @GET("neo/rest/v1/feed/today")
    fun getNearestEarthObjectsToday(@Query("api_key") apiKey: String): Call<NeoServerResponseData>
}