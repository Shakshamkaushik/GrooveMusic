package com.example.groovemusic.sealedclass

sealed class PlaybackCommand {
    object Next : PlaybackCommand()
    object Previous : PlaybackCommand()
}