package com.example.radio2

enum class AudioBand {
    AM,
    FM
}

data class RadioBand(
    val audioBand: AudioBand,
    val minFrequency: Int,
    val maxFrequency: Int,
    val unit : String
)


val FM_BAND = RadioBand(
    audioBand = AudioBand.FM,
    minFrequency = 8800,
    maxFrequency = 10800,
    unit = "MHz"
)

val AM_BAND = RadioBand(
    audioBand = AudioBand.AM,
    minFrequency = 53000,
    maxFrequency = 170000,
    unit = "kHz"
)
