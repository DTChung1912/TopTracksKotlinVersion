package com.example.toptrackskotlinversion.View

import com.example.toptrackskotlinversion.base.MVPView

interface MainIterator {
    interface MainView : MVPView {
        fun onSuccess()
        fun onFailed(msg: String)
    }

    interface MainPresenter {
        fun fetchMain()
    }
}