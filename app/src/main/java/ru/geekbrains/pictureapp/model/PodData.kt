package ru.geekbrains.pictureapp.model

sealed class PodData<out ResponseData: Any> {
    data class Success<out ResponseData: Any>(val serverResponseData: ResponseData) : PodData<ResponseData>()
    data class Error(val error: Throwable) : PodData<Nothing>()
    data class Loading(val progress: Int?) : PodData<Nothing>()
}
