package ru.geekbrains.pictureapp.ui.interfaces

import ru.geekbrains.pictureapp.model.PodData

interface OnReceivingResponseListener<ResponseData: Any> {
    fun onSuccess(data: PodData.Success<ResponseData>)
    fun onLoading(data: PodData.Loading)
    fun onFailure(data: PodData.Error)
}