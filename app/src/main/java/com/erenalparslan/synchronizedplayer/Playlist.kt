package com.erenalparslan.synchronizedplayer

import android.net.Uri
import com.google.android.exoplayer2.MediaItem

object Playlist {
    var mediaItems = mutableListOf(
        MediaItem.fromUri(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")),
        MediaItem.fromUri(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4" ))
    )
}