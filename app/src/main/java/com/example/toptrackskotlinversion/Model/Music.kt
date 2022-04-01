package com.example.toptrackskotlinversion.Model

class Music {
    var songName: String? = null
    var singerName: String? = null
    var songRank: String? = null
    var listener: String? = null
    var musicImage: String? = null

    constructor(
        songName: String?,
        singerName: String?,
        songRank: String?,
        listener: String?,
        musicImage: String?
    ) {
        this.songName = songName
        this.singerName = singerName
        this.songRank = songRank
        this.listener = listener
        this.musicImage = musicImage
    }

    constructor() {}
}