package com.example.toptrackskotlinversion.Fragment.toptracks

import android.util.Log
import com.example.toptracks.Service.APIClient
import com.example.toptracks.Service.MusicAPI
import com.example.toptracks.base.BasePresenter
import com.example.toptrackskotlinversion.Model.Music
import com.example.toptrackskotlinversion.Model.TopTracksModel
import com.example.toptrackskotlinversion.Model.Toptracks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopTracksFragmentPresenter : BasePresenter<TopTracksIterator.TopTrackView>(null),
    TopTracksIterator.TopTrackPresenter {

    val api_key = "4bae3a6d607a824a4eb8dc9455402d76"

    override fun fetchTopTracks(limit: Int) {
        val musicList = ArrayList<Music>()
        val musicAPI: MusicAPI = APIClient.getAPIClient().create(MusicAPI::class.java)
        val call: Call<TopTracksModel> = musicAPI.getTrack(api_key, limit)
        call.enqueue(object : Callback<TopTracksModel> {
            override fun onResponse(
                call: Call<TopTracksModel>,
                response: Response<TopTracksModel>
            ) {
                if (response == null || response.body() == null) {
                    Log.d("Tag", "null")
                    return
                }

                val topTracksModel: TopTracksModel = response.body()!!
                val topTrack: Toptracks = topTracksModel.toptracks
                for (track in topTrack.track) {
                    val attr = track.attr
                    val artist = track.artist
                    val image = track.image.get(3)
                    val music =
                        Music(track.name, artist.name, attr.rank, track.listeners, image.text)
                    musicList.add(music)
                    getMVPView().onFetchSuccsess(musicList)
                    Log.d("Tag", "OK")
                }
            }

            override fun onFailure(call: Call<TopTracksModel>, t: Throwable) {
                Log.d("Tag", "fail")
            }

        })
    }

    override fun addProgessBar() {
        getMVPView().onProgessbar()
    }

    override fun RefreshTopTracks() {
        getMVPView().onSwipeRefresh()
    }

}



