package com.example.toptracks.base

import com.example.toptrackskotlinversion.base.MVPView

abstract class BasePresenter<T : MVPView?>(private var mvpView: T?) : Presenter<T> {

    override fun attachView(view: T) {
        mvpView = view
    }

    override fun detachView() {
        mvpView = null
    }

    override fun unSubscribe() {}

    public fun getMVPView(): T {
        return mvpView!!
    }
}