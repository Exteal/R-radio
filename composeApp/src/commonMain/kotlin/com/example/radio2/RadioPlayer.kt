package com.example.radio2

import kotlinx.coroutines.flow.StateFlow

interface RadioPlayer {
    fun play(streamUrl : String)
    fun pause()
    fun stop()
    val state: StateFlow<PlayerState>
    val audioLevel : StateFlow<Float>
    val currentFrequency: StateFlow<Int>

    val signalStrength : StateFlow<Float>

    fun modifySignalStrength(strength: Float)
    fun modifyAudioLevel(value: Float)
    fun modifyFrequency(value: Int)
}