package com.example.toptrackskotlinversion.Fragment.freetracks

import com.example.toptracks.base.BasePresenter

class FreeFragmentPresenter : BasePresenter<FreeIterator.FreeTrackView>(null),
    FreeIterator.FreeTrackPresenter {
    override fun fetchFreeTracks() {
        getMVPView().onFetchSuccess()
    }
}