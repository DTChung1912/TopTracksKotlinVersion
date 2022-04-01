package com.example.toptrackskotlinversion.Fragment.purchasestracks

import com.example.toptrackskotlinversion.base.MVPView

interface PurchasesIterator {
    interface PurchasesTrackView : MVPView {
        fun onFetchSuccess()
        fun onFailed(msg: String)
    }

    interface PurchasesTrackPresenter {
        fun fectchPurchasesTrack()
    }

}