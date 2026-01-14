package com.example.radio2

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import java.util.prefs.Preferences.userRoot

import javafx.application.Platform

actual fun createLastStationStore(): LastStationStore {
    val prefs = userRoot().node("radio")

    return object : LastStationStore {
        override suspend fun saveLastStation(id: String) {
            prefs.put("last_station", id)
        }

        override suspend fun loadLastStation(): String? {
            return prefs.get("last_station", null)
        }
    }
}

actual fun createRadioPlayer(): RadioPlayer {
    return DesktopRadioPlayer()
}

actual fun createWhiteNoisePlayer(): WhiteNoisePlayer {
    return DesktopWhiteNoisePlayer()
}

fun main() {
    val factory = MediaPlayerFactory()

    Platform.startup {}

    application {
        val model = createViewModel(stations)
        Window(
            onCloseRequest = ::exitApplication,
            title = "radio2",
        ) {
            App(model)
        }
    }
}