package com.example.potyczkazhistoria

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {

    private var player: MediaPlayer? = null

    fun start(context: Context) {
        if (player == null) {
            player = MediaPlayer.create(context, R.raw.muzyka)
            player?.isLooping = true
            player?.setVolume(0.8f, 0.8f)
            player?.start()
        } else {
            if (!(player?.isPlaying ?: false)) {
                player?.start()
            }
        }
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    fun setVolume(v: Float) {
        player?.setVolume(v, v)
    }
}
