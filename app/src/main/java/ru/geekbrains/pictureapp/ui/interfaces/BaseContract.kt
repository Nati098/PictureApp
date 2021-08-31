package ru.geekbrains.pictureapp.ui.interfaces


interface BaseContract {

    interface View {

        fun bindView()
        fun acceptPresenter(presenter: Presenter<View>)
        fun showLoading()
        fun showError(message: String?)

    }


    interface Presenter<in V: View> {

        fun onViewCreated()
        fun attachView(view: V)
        fun detachView()

    }

}