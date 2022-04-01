package com.example.toptrackskotlinversion.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.toptrackskotlinversion.Model.Music
import com.example.toptrackskotlinversion.R

class MusicAdapter(
    private val context: Context,
    private var musicList: ArrayList<Music>,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private var isLoadmore = false
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicImage: ImageView? = itemView.findViewById(R.id.musicImage)
        val songName: TextView? = itemView.findViewById(R.id.songName)
        val singerName: TextView? = itemView.findViewById(R.id.singerName)
        val songRank: TextView? = itemView.findViewById(R.id.songRank)
        val listener: TextView? = itemView.findViewById(R.id.listener)
        val loadmore: ProgressBar? = itemView.findViewById(R.id.loadmore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {

        var view = if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater.from(context).inflate(R.layout.all_item, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.load_view, parent, false)
        }
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            val music = musicList.get(position)
            holder.songName?.text = music.songName
            holder.singerName?.text = music.singerName
            holder.songRank?.text = "Rank: " + music.songRank
            holder.listener?.text = music.listener
            holder.musicImage?.let {
                Glide.with(context).load(music.musicImage).transform(CircleCrop())
                    .into(it)
            }
            holder.musicImage?.setOnClickListener {
                onItemClick.onClicked()
            }
        } else {
            holder.loadmore?.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if (musicList == null) {
            0
        } else if (isLoadmore) {
            musicList.size + 1
        } else {
            musicList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadmore && position == musicList!!.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun isLoadmore(isLoadmore: Boolean) {
        this.isLoadmore = isLoadmore
    }
}