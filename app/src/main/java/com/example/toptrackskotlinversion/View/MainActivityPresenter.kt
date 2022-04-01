package com.example.toptrackskotlinversion.View

import com.example.toptracks.base.BasePresenter

class MainActivityPresenter : BasePresenter<MainIterator.MainView>(null),
    MainIterator.MainPresenter {

    override fun fetchMain() {
        getMVPView().onSuccess()
    }
}