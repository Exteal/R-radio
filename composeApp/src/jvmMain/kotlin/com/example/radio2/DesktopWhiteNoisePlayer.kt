package com.example.radio2

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import java.io.File
import radio2.composeapp.generated.resources.Res
class DesktopWhiteNoisePlayer : WhiteNoisePlayer {

//    private var audioPlayerComponent: AudioPlayerComponent = AudioPlayerComponent()
    private val maxAudio = 100

    val _audioLevel = MutableStateFlow(1f)
    override val audioLevel: StateFlow<Float> = _audioLevel

    var player : MediaPlayer? = null

    init {
//        audioPlayerComponent.mediaPlayer().controls().repeat = true
//        val noiseFile = extractResourceToTemp("white-noise.wav")
//        audioPlayerComponent.mediaPlayer().media().play(noiseFile.absolutePath)
//        val coerced = ((_audioLevel.value * maxAudio)).toInt().coerceIn(0, maxAudio)
//        audioPlayerComponent.mediaPlayer().audio().setVolume(coerced)

        val noiseFile = extractResourceToTemp("white-noise.wav")

        val media = Media(noiseFile.toURI().toString())

        player = MediaPlayer(media).apply {
            cycleCount = MediaPlayer.INDEFINITE
            volume = 0.0
            play()
        }


    }

    override fun modifyAudioLevel(audioLevel: Float) {
//        val coercedValue = (audioLevel * 30).toInt().coerceIn(0, 30)
//        println("white str is $audioLevel, coerced: $coercedValue")
//        audioPlayerComponent.mediaPlayer().audio().setVolume(coercedValue)
        player?.volume = audioLevel / 3.toDouble()
    }

    fun pause() {
//        audioPlayerComponent.mediaPlayer().controls().pause()
    }

    fun extractResourceToTemp(name: String): File {
        val stream = object {}.javaClass
            .getResourceAsStream("/$name")
            ?: error("Resource not found: $name")

        val temp = File.createTempFile("radio_", "_$name")
        temp.deleteOnExit()

        stream.use { input ->
            temp.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        return temp
    }


}