package com.example.groovemusic.di


import com.example.groovemusic.apiservice.KtorClient
import com.example.groovemusic.reporistoryImpl.PlaybackRepository
import com.example.groovemusic.reporistoryImpl.SongsImpl
import com.example.groovemusic.repositoryInterface.MusicInterface
import com.example.groovemusic.utils.PlayerManger
import com.example.groovemusic.viewmodel.AlbumViewModel
import com.example.groovemusic.viewmodel.ArtistViewModel
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.PlaylistViewModel
import com.example.groovemusic.viewmodel.SharedViewModel
import com.example.groovemusic.viewmodel.SongViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val singleModules = module {
    single { KtorClient }
    single { PlayerManger(androidContext()) }
    single { PlaybackRepository() }
    single { SharedViewModel() }
}

val repoModules = module {
    factory <MusicInterface>{ SongsImpl(get()) }
}

val viewmodelModules = module {
    viewModel { SongViewModel(get()) }
    viewModel { ArtistViewModel(get()) }
    viewModel { AlbumViewModel(get()) }
    viewModel { PlaylistViewModel(get()) }
    viewModel { MusicPlayViewModel(get(),get(),get() ,get())}

}
