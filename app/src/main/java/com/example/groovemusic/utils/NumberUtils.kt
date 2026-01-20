package com.example.groovemusic.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NumberUtils {

    fun parseTimeToSeconds(time: String): Int {
        val parts = time.split(":")
        val mins = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val secs = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return mins * 60 + secs
    }
    @SuppressLint("DefaultLocale")
    fun formatTime(seconds: Int): String {
        val mins = seconds / 60
        val secs = seconds % 60
        return String.format("%02d:%02d", mins, secs)
    }
    @SuppressLint("DefaultLocale")
    fun formatNumber(number: Long): String {
        return when {
            number >= 1_00_00_000 -> { // Crores
                String.format("%.2f Cr", number / 1_00_00_000.0)
            }

            number >= 1_00_000 -> { // Lakhs
                String.format("%.2f L", number / 1_00_000.0)
            }

            number >= 1_000_000 -> { // Millions
                String.format("%.2f M", number / 1_000_000.0)
            }

            number >= 1_000 -> { // Thousands
                String.format("%.2f K", number / 1_000.0)
            }

            else -> number.toString()
        }
    }
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.let {
                // Check if the network has internet connectivity
                return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        } else {
            // For older versions of Android
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.let {
                return it.isConnected
            }
        }
        return false
    }
}