package com.example.groovemusic.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.example.groovemusic.reporistoryImpl.PlaybackRepository
import com.example.groovemusic.utils.PlayerNotificationHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@UnstableApi
class MusicService : MediaSessionService(), KoinComponent {

    val playerRepository : PlaybackRepository by inject()
    private lateinit var excoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession


    override fun onCreate() {
        super.onCreate()
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()


        excoPlayer = ExoPlayer.Builder(this).build().apply {
            setAudioAttributes(audioAttributes,true)
            setHandleAudioBecomingNoisy(true)
        }




        val customPlayer = CustomPlayer(excoPlayer,playerRepository)
        mediaSession = MediaSession.Builder(this, customPlayer)

           .build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)



            val notification = PlayerNotificationHelper.createNotification(this)
            startForeground(1, notification)

        }
    }

    override fun onDestroy() {
        mediaSession.release()
        excoPlayer.release()
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        excoPlayer.stop()
        stopSelf()
        mediaSession.release()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession


}
