package com.example.toptrackskotlinversion.Fragment.toptracks

import com.example.toptrackskotlinversion.Model.Music
import com.example.toptrackskotlinversion.base.MVPView

interface TopTracksIterator {
    interface TopTrackView : MVPView {
        fun onFetchSuccsess(musicList: ArrayList<Music>)
        fun onProgessbar()
        fun onSwipeRefresh()
        fun onFailed()
    }

    interface TopTrackPresenter {
        fun fetchTopTracks(limit: Int)
        fun addProgessBar()
        fun RefreshTopTracks()
    }
}