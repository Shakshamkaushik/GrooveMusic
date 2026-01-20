package com.example.groovemusic.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.session.MediaSession

object PlayerNotificationHelper {

    private const val CHANNEL_ID = "music_channel"
    private const val CHANNEL_NAME = "Music Playback"

    fun createNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Music playback controls"
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }



        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Groove Music")
            .setContentText("Playing music")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("")
            .setOngoing(true)
            .build()
    }
}
