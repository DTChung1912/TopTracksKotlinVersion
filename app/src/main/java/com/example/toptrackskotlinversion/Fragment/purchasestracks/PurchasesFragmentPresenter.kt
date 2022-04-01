package com.example.toptrackskotlinversion.Fragment.purchasestracks

import com.example.toptracks.base.BasePresenter

class PurchasesFragmentPresenter : BasePresenter<PurchasesIterator.PurchasesTrackView>(null),
    PurchasesIterator.PurchasesTrackPresenter {
    override fun fectchPurchasesTrack() {
        getMVPView().onFetchSuccess()
    }
}