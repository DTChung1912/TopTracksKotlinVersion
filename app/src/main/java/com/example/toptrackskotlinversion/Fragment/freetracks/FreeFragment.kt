package com.example.toptrackskotlinversion.Fragment.freetracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.R

class FreeFragment : Fragment(), FreeIterator.FreeTrackView {
    private lateinit var presenter: FreeFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_free, container, false)
        presenter = FreeFragmentPresenter()
        presenter.attachView(this)
        presenter.fetchFreeTracks()
        return view
    }

    override fun onFetchSuccess() {
    }

    override fun onFailed(msg: String) {
    }

    override fun onError(msg: String?) {
    }
}