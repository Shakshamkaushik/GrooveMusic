package com.example.groovemusic.naviagtion

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.groovemusic.model.storage.Favourite
import com.example.groovemusic.screens.AddPlaylistScreen
import com.example.groovemusic.screens.AlbumScreen
import com.example.groovemusic.screens.ArtistDetailsScreen
import com.example.groovemusic.screens.ArtistScreen
import com.example.groovemusic.screens.FavouriteScreen
import com.example.groovemusic.screens.HomeScreen
import com.example.groovemusic.screens.PlayerScreen
import com.example.groovemusic.screens.PlaylistDetailScreen
import com.example.groovemusic.screens.PlaylistScreen
import com.example.groovemusic.screens.SearchScreen
import com.example.groovemusic.viewmodel.MusicPlayViewModel
import com.example.groovemusic.viewmodel.SharedViewModel
import com.example.groovemusic.viewmodel.SongViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
//        composable(Routes.SplashScreen.route) {
//            SplashScreen(navController)
//        }

        composable(Routes.HomeScreen.route) { backStackEntry ->


            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val sharedViewModel: SharedViewModel = koinViewModel(viewModelStoreOwner = rootEntry)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)


            HomeScreen(navController, sharedViewModel, musicPlayViewModel)
        }
        composable(
            route = Routes.PlayingScreen.route,
            arguments = listOf(
                navArgument("song_id") { type = NavType.StringType },
                navArgument("from_which_screen") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("song_id")
            val fromWhichScreen = backStackEntry.arguments?.getString("from_which_screen")


            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val sharedViewModel: SharedViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)

            val songViewModel: SongViewModel = koinViewModel(viewModelStoreOwner = rootEntry)

            PlayerScreen(
                navController,
                songId.toString(),
                fromWhichScreen.toString(),
                sharedViewModel,
                musicPlayViewModel,
                songViewModel
            )
        }

        composable(
            Routes.ArtistScreen.route,
            arguments = listOf(
                navArgument("artist_id") { type = NavType.StringType }
            )) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artist_id")
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)

            ArtistScreen(navController, artistId!!, musicPlayViewModel)
        }
        composable(
            Routes.AlbumScreen.route,
            arguments = listOf(
                navArgument("alubum_id") { type = NavType.StringType }
            )) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("alubum_id")
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)

            AlbumScreen(navController, albumId!!, musicPlayViewModel)
        }
        composable(
            Routes.PlayListDetails.route,
            arguments = listOf(
                navArgument("playlist_id") { type = NavType.StringType }
            )) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getString("playlist_id")
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            PlaylistScreen(navController, playlistId!!, musicPlayViewModel)
        }

        composable(Routes.SearchScreen.route) {
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            val songViewModel: SongViewModel = koinViewModel(viewModelStoreOwner = rootEntry)
            SearchScreen(navController, musicPlayViewModel, songViewModel)
        }
        composable(Routes.FavouriteScreen.route) {
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            FavouriteScreen(navController,musicPlayViewModel)
        }
        composable(
            Routes.AddPlaylistScreen.route,
            arguments = listOf(
                navArgument("song_details_to_add_playlist") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )) { backStackEntry ->
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            val encoded = backStackEntry.arguments?.getString("song_details_to_add_playlist")
            val playListSongDetail =
                encoded?.let { Json.decodeFromString<Favourite>(Uri.decode(it)) }

            AddPlaylistScreen(navController, playListSongDetail, musicPlayViewModel)
        }
        composable(
            Routes.PlayListDetailScreen.route,
            arguments = listOf(
                navArgument("playlist_id") { type = NavType.StringType },
                navArgument("playlist_name") { type = NavType.StringType },
                //navArgument("playlist_songs") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val PlayListId = backStackEntry.arguments?.getString("playlist_id")
            val PlayListName = backStackEntry.arguments?.getString("playlist_name")
//            val encoded = backStackEntry.arguments?.getString("playlist_songs")
//            val playListSongs =
//                encoded.let { Json.decodeFromString<List<Favourite>>(Uri.decode(it)) }
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            PlaylistDetailScreen(navController, PlayListId.toString(),PlayListName.toString(), musicPlayViewModel)
        }

        composable(Routes.ArtistDetailScreen.route,
            arguments = listOf(
                navArgument("artist_id"){ type = NavType.StringType},
                navArgument("fetch_Songs"){ type = NavType.BoolType},
                navArgument("artist_name"){ type = NavType.StringType},
            )) {  backStackEntry ->
            val rootEntry = navController.getBackStackEntry(navController.graph.id)
            val musicPlayViewModel: MusicPlayViewModel =
                koinViewModel(viewModelStoreOwner = rootEntry)
            val artistId = backStackEntry.arguments?.getString("artist_id")
            val artistName = backStackEntry.arguments?.getString("artist_name")
            val fetchSong = backStackEntry.arguments?.getBoolean("fetch_Songs")
            ArtistDetailsScreen(navController,artistId.toString(), fetchSong!!,artistName.toString(),musicPlayViewModel)
        }
    }

}