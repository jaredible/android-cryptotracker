package net.jaredible.crypto.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import net.jaredible.crypto.R
import net.jaredible.crypto.ui.base.BaseService

class MusicService : BaseService(), MediaPlayer.OnCompletionListener {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_background)
            mediaPlayer?.isLooping = true
        } else {
            mediaPlayer?.pause()
            mediaPlayer?.seekTo(0)
        }

        mediaPlayer?.start()

        return START_STICKY
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

}