package com.example.groovemusic.utils

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.groovemusic.service.MusicService
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

class PlayerManger(val context: Context) {
    private var controller: MediaController? = null
    private var positionJob: Job? = null
    private var isReady = false
    private val initMutex = Mutex()
    var onDurationReady: ((String) -> Unit)? = null


    val isPlayingFlow = MutableStateFlow(false)


    val positionFlow = MutableStateFlow(0L)


    private var currentMediaId : String? = null
    val durationFlow = MutableStateFlow(0L)

    suspend fun init() {
       initMutex.withLock {// ensure only one init
           if (controller != null) return
           val token = SessionToken(context, ComponentName(context, MusicService::class.java))
           controller = suspendCancellableCoroutine { cont ->
               val future = MediaController.Builder(context, token).buildAsync()
               future.addListener(
                   { cont.resume(future.get()) },
                   ContextCompat.getMainExecutor(context)
               )
               cont.invokeOnCancellation { future.cancel(true) }
           }

           controller?.addListener(object : Player.Listener {
               override fun onIsPlayingChanged(isPlaying: Boolean) {
                   isPlayingFlow.value = isPlaying
                   if (isPlaying) startPositionUpdates()
                   else stopPositionUpdates()
               }

               override fun onPlaybackStateChanged(state: Int) {
                   if (state == Player.STATE_READY) {
                       durationFlow.value = controller?.duration ?: 0L
                   }
               }

               override fun onEvents(player: Player, events: Player.Events) {
                   positionFlow.value = player.currentPosition
               }

           })
       }
        isReady = true
    }
    suspend fun playSong(track: String,name: String, url: String,artist: String) {
        if (!isReady) init()


        if (currentMediaId == track){
            controller?.play()
            return
        }

        currentMediaId = track

        val metadata = MediaMetadata.Builder()
            .setTitle(name)
            .setArtworkUri(Uri.parse(url))
            .setArtist(artist)
            .build()


        val media = MediaItem.Builder()
            .setUri(track)
            .setMediaId(track)
            .setMediaMetadata(metadata)
            .build()

        controller?.setMediaItem(media)
        controller?.prepare()
        controller?.play()
    }

    fun play() = controller?.play()

    fun pause() = controller?.pause()

    fun togglePlayPause() {
        if (controller?.isPlaying!!) {
            pause()
        } else {
            play()
        }
    }
    fun pauseClick(){
        controller?.pause()
    }

    fun nextSong() {
        controller?.seekToNext()
    }

    fun prevSong() {
        controller?.seekToPrevious()
    }

    fun seekTo(position: Int){
        controller?.seekTo(position * 1000L)
    }

    private fun format(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format("%02d:%02d", min, sec)
    }

    
    private fun startPositionUpdates() {
        positionJob?.cancel()
        positionJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive && controller?.isPlaying == true) {
                positionFlow.value = controller?.currentPosition ?: 0L
                delay(1000) // 1 second tick
            }
        }
    }

    
    private fun stopPositionUpdates() {
        positionJob?.cancel()
    }

}