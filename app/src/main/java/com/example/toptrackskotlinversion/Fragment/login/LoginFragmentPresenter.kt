package com.example.toptrackskotlinversion.Fragment.login

import com.example.toptracks.base.BasePresenter

class LoginFragmentPresenter : BasePresenter<LoginIterator.LoginView>(null),
    LoginIterator.LoginPresenter {
    override fun fetchLogin() {
        getMVPView().onFetchSuccess()
    }
}