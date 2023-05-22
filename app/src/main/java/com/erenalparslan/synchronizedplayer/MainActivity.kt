package com.erenalparslan.synchronizedplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.google.android.exoplayer2.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var player: ExoPlayer
    private var nextIndex = 0
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    var playlist=Playlist.mediaItems
    private var startTime: Long = 0
    private var startTimeVideo: Long = 0
    inner class PlayerEventListener : Player.Listener {

        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)


        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            println("READY--->" + error)
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            println("isPlaying--->" + isPlaying)
        }

        override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
            super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
            println("SEEK--->" + seekForwardIncrementMs.toInt())
        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)
            //  println("PLAYER--->" + player)
            //println("EVENTS--->" + events)
        }

        override fun onTracksChanged(tracks: Tracks) {
            super.onTracksChanged(tracks)
            //  println("TRACKS--->" + tracks.toString())
            startTimeVideo = SystemClock.elapsedRealtime()
            tracksTime()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player = ExoPlayer.Builder(this).build()
        val playerEventListener = PlayerEventListener()
        player.addListener(playerEventListener)



        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt("current_window")
            playbackPosition = savedInstanceState.getLong("playback_position")
        }
        exoPlay.player=player
        playerSetter(player, playlist)
        startTime = SystemClock.elapsedRealtime()
        updateTime()

    }

    override fun onDestroy() {
        super.onDestroy()
        println("ONE FRAGMENT DESTROY")
        player.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Player state'ini kaydet
        outState.putInt("current_window", player.currentWindowIndex)
        outState.putLong("playback_position", player.currentPosition)
    }


    fun playerSetter(player: ExoPlayer, mediaItem: List<MediaItem>) {
        player.setMediaItems(mediaItem,currentWindow,playbackPosition)
        player.prepare()
        player.play()
    }

    private fun updateTime() {
        val elapsedTime = SystemClock.elapsedRealtime() - startTime
        val seconds = (elapsedTime / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60

        val timeString = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
        time.text = timeString
        time.postDelayed({ updateTime() }, 1000)
    }

    private fun tracksTime() {
        val elapsedTime = SystemClock.elapsedRealtime() - startTimeVideo
        val seconds = (elapsedTime / 1000).toInt()
        val minutes = seconds / 60
        val hours = minutes / 60

        val timeString = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
        timeVideo.text = timeString
        timeVideo.postDelayed({ tracksTime() }, 1000)
    }
}