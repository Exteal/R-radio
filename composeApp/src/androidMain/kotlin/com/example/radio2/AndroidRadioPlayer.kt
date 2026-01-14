package com.example.radio2

import kotlinx.coroutines.flow.StateFlow

class AndroidRadioPlayer : RadioPlayer {
    override fun play(streamUrl: String) {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override val state: StateFlow<PlayerState>
        get() = TODO("Not yet implemented")
    override val audioLevel: StateFlow<Float>
        get() = TODO("Not yet implemented")
    override val currentFrequency: StateFlow<Int>
        get() = TODO("Not yet implemented")
    override val signalStrength: StateFlow<Float>
        get() = TODO("Not yet implemented")

    override fun modifySignalStrength(strength: Float) {
        TODO("Not yet implemented")
    }

    override fun modifyAudioLevel(value: Float) {
        TODO("Not yet implemented")
    }

    override fun modifyFrequency(value: Int) {
        TODO("Not yet implemented")
    }
}