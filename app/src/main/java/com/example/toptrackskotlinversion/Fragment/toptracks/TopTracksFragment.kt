package com.example.toptrackskotlinversion.Fragment.toptracks

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.toptrackskotlinversion.Adapter.MusicAdapter
import com.example.toptrackskotlinversion.Adapter.OnItemClick
import com.example.toptrackskotlinversion.Fragment.register.BottomSheetRegisterFragment
import com.example.toptrackskotlinversion.Fragment.register.OnRegister
import com.example.toptrackskotlinversion.Model.Constants.KEY_CURRENT_USER
import com.example.toptrackskotlinversion.Model.Music
import com.example.toptrackskotlinversion.R
import com.example.toptrackskotlinversion.Scroll.RecyclerViewScrollListener

class TopTracksFragment : Fragment(), TopTracksIterator.TopTrackView {
    private var musicList: ArrayList<Music> = ArrayList<Music>()
    private lateinit var musicAdapter: MusicAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var presenter: TopTracksFragmentPresenter
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var limit = 5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_tracks, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(linearLayoutManager)

        presenter = TopTracksFragmentPresenter()
        presenter.attachView(this)

        musicAdapter = MusicAdapter(requireContext(), musicList, object : OnItemClick {
            override fun onClicked() {
                BottomSheetRegisterFragment(object : OnRegister {
                    override fun onRegistered(userName: String) {
                        val editor: SharedPreferences.Editor =
                            requireActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE)
                                .edit()
                        editor.putString(KEY_CURRENT_USER, userName)
                        editor.commit()
                    }

                }).show(requireActivity().supportFragmentManager, null)
            }

        })

        recyclerView.adapter = musicAdapter
        presenter.fetchTopTracks(limit)

        recyclerView.addOnScrollListener(object : RecyclerViewScrollListener(linearLayoutManager) {
            override fun loadmoreItem() {
                isLoading = true
                if (isLoading) {
                    musicAdapter.isLoadmore(isLoading)
                    musicAdapter.notifyDataSetChanged()
                    presenter.addProgessBar();
                }
                presenter.fetchTopTracks(limit)
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun isLastpage(): Boolean {
                return isLastPage
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            presenter.RefreshTopTracks()
            presenter.fetchTopTracks(limit)
            swipeRefreshLayout.setRefreshing(false)
        }

        return view
    }

    override fun onFetchSuccsess(musicList: ArrayList<Music>) {
        isLoading = false
        isLastPage = false
        this.musicList.clear()
        this.musicList.addAll(musicList)
        this.musicAdapter.notifyDataSetChanged()
    }

    override fun onProgessbar() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (musicList.size >= 50) {
                    isLastPage = true
                    Toast.makeText(context, "out of data", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    isLoading = false
                    musicAdapter.isLoadmore(isLoading)
                    musicAdapter.notifyDataSetChanged()
                    limit += 5
                }
            }

        }, 2000)
    }

    override fun onSwipeRefresh() {
        musicList.clear()
        musicAdapter.notifyDataSetChanged()
        limit = 5
    }

    override fun onFailed() {
    }

    override fun onError(msg: String?) {
    }
}