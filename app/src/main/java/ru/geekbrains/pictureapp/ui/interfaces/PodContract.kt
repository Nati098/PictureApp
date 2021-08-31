package ru.geekbrains.pictureapp.ui.interfaces

import ru.geekbrains.pictureapp.model.data.PodServerResponseData
import java.util.*

interface PodContract {

    interface View: BaseContract.View {
        fun showData(data: PodServerResponseData)
    }


    interface Presenter {
        fun requestData()
        fun requestData(date: Date?, isHd: Boolean?)
    }

}