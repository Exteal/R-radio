package com.example.radio2

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.localStorage


@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    ComposeViewport {
        val model = createViewModel(stations)
        App(model)
    }
}

actual fun createRadioPlayer() : RadioPlayer {
    return WebRadioPlayer()
}

actual fun createLastStationStore(): LastStationStore {
   return object : LastStationStore {
       override suspend fun saveLastStation(id: String) {
           localStorage.setItem("lastStationId", id)
       }

       override suspend fun loadLastStation(): String? {
           return localStorage.getItem("lastStationId")
       }

   }
}

actual fun createWhiteNoisePlayer(): WhiteNoisePlayer {
    return WebWhiteNoisePlayer()
}