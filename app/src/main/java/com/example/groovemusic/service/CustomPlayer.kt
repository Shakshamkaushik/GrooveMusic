package com.example.groovemusic.service


import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.groovemusic.reporistoryImpl.PlaybackRepository
import com.example.groovemusic.sealedclass.PlaybackCommand

@UnstableApi
class CustomPlayer(player: ExoPlayer, val playerRepository: PlaybackRepository) :
    ForwardingPlayer(player) {
    override fun getAvailableCommands(): Player.Commands {
        return Player.Commands.Builder()
            .addAll(super.getAvailableCommands())   // keep default commands
            .add(COMMAND_PLAY_PAUSE)
            .add(COMMAND_SEEK_TO_NEXT)
            .add(COMMAND_SEEK_TO_PREVIOUS)
            .build()
    }


    override fun play() {

        super.play()
    }

    override fun pause() {

        super.pause()
    }

    override fun seekTo(positionMs: Long) {

        super.seekTo(positionMs)
    }

    override fun seekToNext() {

        playerRepository.commands.tryEmit(PlaybackCommand.Next)
        super.seekToNext()
    }

    override fun seekToPrevious() {

        playerRepository.commands.tryEmit(PlaybackCommand.Previous)
        super.seekToPrevious()
    }
}
