package com.example.toptrackskotlinversion.Fragment.freetracks

import com.example.toptrackskotlinversion.base.MVPView

interface FreeIterator {
    interface FreeTrackView : MVPView {
        fun onFetchSuccess()
        fun onFailed(msg: String)
    }

    interface FreeTrackPresenter {
        fun fetchFreeTracks()
    }
}