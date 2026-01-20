package com.example.groovemusic.apiservice

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val apiClient: HttpClient by lazy {

        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.INFO
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
            defaultRequest {
                url.takeFrom("https://saavn.sumit.co/api/")
                header("Accept", "*/*")
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(3)
                exponentialDelay()
            }
        }

    }
}