package com.example.potyczkazhistoria

import android.content.Context
import android.media.MediaPlayer

object MusicPlayer {
    private var player: MediaPlayer? = null
    private var isPrepared = false
    fun start(context: Context) {
        if (player == null) {
            player = MediaPlayer().apply {
                isLooping = true
                setVolume(0.8f, 0.8f)
                setOnPreparedListener {
                    isPrepared = true
                    start()
                }
                setOnCompletionListener {
                    if (isPrepared) start()
                }
                val afd = context.resources.openRawResourceFd(R.raw.muzyka)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepareAsync()
            }
        } else {
            if (isPrepared && !(player?.isPlaying ?: false)) {
                player?.start()
            }
        }
    }
    fun pause() {
        if (isPrepared && (player?.isPlaying == true)) {
            player?.pause()
        }
    }
    fun stop() {
        try {
            player?.stop()
        } catch (_: Exception) {}
        try {
            player?.release()
        } catch (_: Exception) {}
        player = null
        isPrepared = false
    }
    fun setVolume(v: Float) {
        player?.setVolume(v, v)
    }
}
