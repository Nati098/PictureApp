package ru.geekbrains.pictureapp.model

import ru.geekbrains.pictureapp.model.data.PODServerResponseData

sealed class PODData {
    data class Success(val serverResponseData: PODServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
