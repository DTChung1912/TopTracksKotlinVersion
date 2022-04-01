package com.example.toptrackskotlinversion.Fragment.register

import com.example.toptracks.base.BasePresenter

class RegisterFragmentPresenter : BasePresenter<RegisterIterator.RegisterView>(null),
    RegisterIterator.RegisterPresenter {
    override fun fetchRegister() {
        getMVPView().onFetchSuccess()
    }
}