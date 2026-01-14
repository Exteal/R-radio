package com.example.radio2

import kotlinx.coroutines.flow.StateFlow

interface WhiteNoisePlayer {

    val audioLevel : StateFlow<Float>
    fun modifyAudioLevel(audioLevel: Float)
}