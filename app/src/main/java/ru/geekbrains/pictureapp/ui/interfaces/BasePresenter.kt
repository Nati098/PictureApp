package ru.geekbrains.pictureapp.ui.interfaces


abstract class BasePresenter : BaseContract.Presenter<BaseContract.View> {
    private var _view: BaseContract.View? = null

    override fun onViewCreated() {}

    override fun attachView(view: BaseContract.View) {
        if (_view == null) {
            _view = view
            view.acceptPresenter(this)
        }
    }

    override fun detachView() {
        _view = null
    }

}