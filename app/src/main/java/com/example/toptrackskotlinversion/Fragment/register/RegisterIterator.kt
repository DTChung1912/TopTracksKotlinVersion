package com.example.toptrackskotlinversion.Fragment.register

import com.example.toptrackskotlinversion.base.MVPView

interface RegisterIterator {
    interface RegisterView : MVPView {
        fun onFetchSuccess()
        fun onFailed(msg: String)
    }

    interface RegisterPresenter {
        fun fetchRegister()
    }
}