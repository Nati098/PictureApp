package ru.geekbrains.pictureapp.ui.interfaces

import ru.geekbrains.pictureapp.model.PodData

interface OnReceivingResponseListener {
    fun onSuccess(data: PodData.Success)
    fun onLoading(data: PodData.Loading)
    fun onFailure(data: PodData.Error)
}