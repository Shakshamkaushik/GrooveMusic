package com.example.groovemusic.reporistoryImpl

import com.example.groovemusic.sealedclass.PlaybackCommand
import kotlinx.coroutines.flow.MutableSharedFlow

class PlaybackRepository {
    val commands = MutableSharedFlow<PlaybackCommand>(extraBufferCapacity = 1)
}