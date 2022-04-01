package com.example.toptrackskotlinversion.Scroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewScrollListener(private val linearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = linearLayoutManager.childCount
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        if (isLoading() || isLastpage()) {
            return
        }
        if (firstVisibleItemPosition >= 0
            && visibleItemCount + firstVisibleItemPosition >= totalItemCount
        ) {
            loadmoreItem()
        }
    }

    abstract fun loadmoreItem()
    abstract fun isLoading(): Boolean
    abstract fun isLastpage(): Boolean

}