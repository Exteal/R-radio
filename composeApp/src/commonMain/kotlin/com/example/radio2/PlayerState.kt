package com.example.radio2

sealed class PlayerState {
    object Playing : PlayerState()
    object Paused : PlayerState()
    object Connecting : PlayerState()

    object Stopped : PlayerState()

    data class Error(val message: String) : PlayerState()
}