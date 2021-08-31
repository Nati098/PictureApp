package ru.geekbrains.pictureapp.ui.interfaces

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ru.geekbrains.pictureapp.model.PodData
import java.util.*

interface PodContract {

    interface View: LifecycleOwner {
        fun showData(data: PodData)
    }


    interface Presenter {
        fun requestData()
        fun requestData(date: Date?, isHd: Boolean?)
    }

}