package com.example.toptrackskotlinversion.Fragment.setting

import com.example.toptracks.base.BasePresenter

class SettingFragmentPresenter : BasePresenter<SettingIterator.SettingView>(null),
    SettingIterator.SettingPresenter {
    override fun fetchSetting() {
        getMVPView().onFetchSuccess()
    }
}