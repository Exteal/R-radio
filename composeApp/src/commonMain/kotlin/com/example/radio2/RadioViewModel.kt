package com.example.radio2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

class RadioViewModel (
    private val player : RadioPlayer,
    private val whiteNoisePlayer : WhiteNoisePlayer,
    private val stationStore: LastStationStore,
    val stations : List<RadioStation>
) {
    val currentStation: StateFlow<RadioStation?> = MutableStateFlow(null)
    val audioLevel : StateFlow<Float> get() = player.audioLevel
    val currentFrequency : StateFlow<Int> get() = player.currentFrequency
    val currentState : StateFlow<PlayerState> get() = player.state

    val avgSpacing = (stations.last().frequency - stations.first().frequency) /
            (stations.size - 1)

    val captureRange = avgSpacing * 0.5f

    init {
        println("capturing range $captureRange")
    }
    suspend fun restoreLastStation()  {
        val id = stationStore.loadLastStation() ?: return
        val station = stations.find { it.id == id } ?: return
        modifyFrequency(station.frequency)
        play(station)
        return
    }

    fun modifySignalStrength(strength: Float) {
        player.modifySignalStrength(strength)
        whiteNoisePlayer.modifyAudioLevel(1 - strength)
    }

    fun play(station: RadioStation) {
        (currentStation as MutableStateFlow).value = station
        player.play(station.streamUrl)
        println("[model] playing ${station.streamUrl}")

        CoroutineScope(Dispatchers.Default).launch {
            stationStore.saveLastStation(station.id)
        }
    }

    fun findNearestStation() : RadioStation {
        var nearestStation : RadioStation? = null
        var distanceToNearestStation = Int.MAX_VALUE

        for (pair in stations.map { Pair(it, it.frequency) }) {
            val currentDistance = abs(currentFrequency.value - pair.second)
            if (currentDistance < distanceToNearestStation ) {
                distanceToNearestStation = currentDistance
                nearestStation = pair.first
            }
        }

        val normalizedDistance = (distanceToNearestStation / captureRange)
        val signalStrength = (1f - normalizedDistance).coerceIn(0f, 1f)

//        val x = (distanceToNearestStation / 0.3f).coerceIn(0f, 1f)
//        val signalStrength = (1f - x).pow(2)

        modifySignalStrength(signalStrength)
        return nearestStation!!
    }

    fun stop() = player.stop()
    fun modifyAudioLevel(value: Float) = player.modifyAudioLevel(value)
    fun pause() = player.pause()

    fun modifyFrequency(value: Int) {
        player.modifyFrequency(value)
        val station = findNearestStation()

        if (station != currentStation.value) {
            play(station)
        }
    }
}