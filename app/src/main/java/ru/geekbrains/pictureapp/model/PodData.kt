package ru.geekbrains.pictureapp.model

import ru.geekbrains.pictureapp.model.data.PodServerResponseData

sealed class PodData {
    data class Success(val serverResponseData: PodServerResponseData) : PodData()
    data class Error(val error: Throwable) : PodData()
    data class Loading(val progress: Int?) : PodData()
}
