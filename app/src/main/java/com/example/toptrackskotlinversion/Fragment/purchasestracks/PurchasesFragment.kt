package com.example.toptrackskotlinversion.Fragment.purchasestracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.R

class PurchasesFragment : Fragment(), PurchasesIterator.PurchasesTrackView {

    private lateinit var presenter: PurchasesFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_purchases, container, false)

        presenter = PurchasesFragmentPresenter()
        presenter.attachView(this)
        presenter.fectchPurchasesTrack()
        return view
    }

    override fun onFetchSuccess() {
    }

    override fun onFailed(msg: String) {
    }

    override fun onError(msg: String?) {
    }
}