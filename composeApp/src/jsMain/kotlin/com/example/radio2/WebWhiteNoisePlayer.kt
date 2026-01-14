package com.example.radio2

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.w3c.dom.Audio

class WebWhiteNoisePlayer : WhiteNoisePlayer {

    private var player : Audio? = null

    private var _audioLevel = MutableStateFlow(0.0f)
    override val audioLevel: StateFlow<Float>  = _audioLevel

    init {
        println("[web] initializing white noise")
        player = Audio("white-noise.wav")

        player?.apply {
            player?.play()
        }

    }

    override fun modifyAudioLevel(audioLevel: Float) {
        val coerced = audioLevel
        _audioLevel.value = coerced
        player?.volume = coerced.toDouble()
        println("[web] noise level ${player?.volume}")

    }

}