package com.example.radio2

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

class DesktopRadioPlayer : RadioPlayer {

    private var audioPlayerComponent: AudioPlayerComponent = AudioPlayerComponent()
    private val _state = MutableStateFlow<PlayerState>(PlayerState.Stopped)
    override val state: StateFlow<PlayerState> = _state

    private val _audioLevel = MutableStateFlow(0.3f)
    override val audioLevel: StateFlow<Float> = _audioLevel

    private val _currentFrequency : MutableStateFlow<Int> = MutableStateFlow(0)
    override val currentFrequency: StateFlow<Int> = _currentFrequency

    private val _signalStrength: MutableStateFlow<Float> = MutableStateFlow(0f)
    override val signalStrength: StateFlow<Float> get() = _signalStrength

    private val maxAudio = 100


    init {
//        audioPlayerComponent
//            .mediaPlayer()
//            .events()
//            .addMediaEventListener(object : MediaEventAdapter() {
//
//                override fun mediaMetaChanged(
//                    media: Media,
//                    newMeta: Meta
//                ) {
//                    println("------------------------")
//                    println("PARSE : ${media.info().mrl()}")
//                    println("PARSE : ${media.meta().asMetaData()}")
//                    println("PARSE : ${media.meta()[Meta.TITLE]}")
//                }
//            })
    }
    override fun modifySignalStrength(strength: Float) {
        _signalStrength.value = strength
        modifyAudioLevel(_audioLevel.value)
    }


    override fun play(streamUrl: String) {
        stop()
        println("starting $streamUrl")
        _state.value = PlayerState.Connecting

        audioPlayerComponent.mediaPlayer().media().play(streamUrl)
        println("title : ${audioPlayerComponent.mediaPlayer().titles().title()}")

        _state.value = PlayerState.Playing
    }


    override fun pause() {
        when (_state.value) {
            PlayerState.Playing -> {
                audioPlayerComponent.mediaPlayer().controls().pause()
                println("paused")
                _state.value = PlayerState.Paused
            }
            PlayerState.Paused -> {
                audioPlayerComponent.mediaPlayer().controls().pause()
                println("unpaused")
                _state.value = PlayerState.Playing
            }
            else -> {}
        }

    }

    override fun stop() {
        println("stopping")
        audioPlayerComponent.mediaPlayer().controls().stop()
       // player?.dispose()
       // player = null
        _state.value = PlayerState.Stopped
    }

    override fun modifyAudioLevel(value : Float) {

        val coercedValue = ((value * _signalStrength.value) * maxAudio).toInt().coerceIn(0, maxAudio)
        println("coerced $coercedValue, val ${value} sig${_signalStrength.value}")
        _audioLevel.value = value
        audioPlayerComponent.mediaPlayer().audio().setVolume(coercedValue)
    }

    override fun modifyFrequency(value: Int) {
        _currentFrequency.value = value
    }

}