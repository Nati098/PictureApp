package ru.geekbrains.pictureapp.ui.neo

import ru.geekbrains.pictureapp.model.PodData
import ru.geekbrains.pictureapp.model.data.NeoServerResponseData
import ru.geekbrains.pictureapp.ui.interfaces.BasePresenter
import ru.geekbrains.pictureapp.ui.interfaces.NeoContract
import ru.geekbrains.pictureapp.ui.interfaces.OnReceivingResponseListener
import java.text.SimpleDateFormat

class NeoPresenter(
    val view: NeoContract.View
): NeoContract.Presenter, BasePresenter(), OnReceivingResponseListener<NeoServerResponseData> {

    override fun onSuccess(data: PodData.Success<NeoServerResponseData>) {
        // TODO("Not yet implemented")
    }

    override fun onLoading(data: PodData.Loading) {
        view.showLoading()
    }

    override fun onFailure(data: PodData.Error) {
        view.showError(data.error.message)
    }


    companion object {
        private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    }
}