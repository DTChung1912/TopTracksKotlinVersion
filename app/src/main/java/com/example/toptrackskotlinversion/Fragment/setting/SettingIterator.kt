package com.example.toptrackskotlinversion.Fragment.setting

import com.example.toptrackskotlinversion.base.MVPView

interface SettingIterator {
    interface SettingView : MVPView {
        fun onFetchSuccess()
        fun onFailed(msg: String)
    }

    interface SettingPresenter {
        fun fetchSetting()
    }
}