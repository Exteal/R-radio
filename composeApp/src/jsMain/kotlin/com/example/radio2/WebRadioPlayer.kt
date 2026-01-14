package com.example.radio2

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.w3c.dom.Audio

class WebRadioPlayer : RadioPlayer {

    var player : Audio? = null

    private val _state = MutableStateFlow<PlayerState>(PlayerState.Paused)
    override val state: StateFlow<PlayerState> = _state

    private val _audioLevel = MutableStateFlow(0.3f)
    override val audioLevel: StateFlow<Float> = _audioLevel

    private val _currentFrequency : MutableStateFlow<Int> = MutableStateFlow(0)
    override val currentFrequency: StateFlow<Int> = _currentFrequency

    private val _signalStrength = MutableStateFlow(0f)
    override val signalStrength: StateFlow<Float> = _signalStrength

    override fun modifySignalStrength(strength: Float) {
        _signalStrength.value = strength
        modifyAudioLevel(_audioLevel.value)
    }

    override fun modifyAudioLevel(value: Float) {
        val coercedValue = value.coerceIn(0f, 1f)

        _audioLevel.value = coercedValue
        player?.volume = coercedValue.toDouble()
    }

    override fun modifyFrequency(value: Int) {
        _currentFrequency.value = value
    }

    override fun play(streamUrl: String) {
        stop()
        println("[web] play")

        _state.value = PlayerState.Connecting
        player = Audio(streamUrl)
        player?.play()
        modifyAudioLevel(_audioLevel.value)
        _state.value = PlayerState.Playing
    }

    override fun pause() {

        when(_state.value) {
            PlayerState.Paused -> {
                player?.play()
                _state.value = PlayerState.Playing
            }
            PlayerState.Playing -> {
                player?.pause()
                _state.value = PlayerState.Paused
            }
            else -> {}
        }
    }

    override fun stop() {
        player?.pause()
        player?.removeAttribute("src")

        player = null
        _state.value = PlayerState.Stopped
    }
}