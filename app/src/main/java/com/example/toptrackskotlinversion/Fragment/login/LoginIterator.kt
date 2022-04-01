package com.example.toptrackskotlinversion.Fragment.login

import com.example.toptrackskotlinversion.base.MVPView

interface LoginIterator {
    interface LoginView : MVPView {
        fun onFetchSuccess()
        fun onFailed(msg: String?)
    }

    interface LoginPresenter {
        fun fetchLogin()
    }
}