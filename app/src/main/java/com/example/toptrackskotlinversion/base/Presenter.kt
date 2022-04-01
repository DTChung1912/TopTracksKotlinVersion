package com.example.toptracks.base

import com.example.toptrackskotlinversion.base.MVPView

interface Presenter<V : MVPView?> {
    fun attachView(view: V)
    fun detachView()
    fun unSubscribe()
}