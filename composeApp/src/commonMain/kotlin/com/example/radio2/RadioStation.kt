package com.example.radio2

data class RadioStation(
    val id: String,
    val name: String,
    val streamUrl: String,
    val frequency: Int,
    val genre: String? = null
)

//val stations = listOf(
//    RadioStation("MOE", "moe","https://listen.moe/fallback", 88f, genre = "Jpop"),
//    RadioStation("OV", "obscura vintage", "https://radio.vintageobscura.net/stream", 98f),
//    RadioStation("FC", "france culture", "https://direct.franceculture.fr/live/franceculture-midfi.mp3", 108f)
//)

val stations = listOf(
    RadioStation("MOE", "moe","https://listen.moe/fallback", 8800, genre = "Jpop"),
    RadioStation("OV", "obscura vintage", "https://radio.vintageobscura.net/stream", 8940),
    RadioStation(
        id = "nrj_fr",
        name = "NRJ France",
        streamUrl = "https://streaming.nrjaudio.fm/oumvmk8fnozc",
        frequency = 8950,
        genre = "Pop"
    ),
    RadioStation(
        id = "SKY",
        name = "SkyRock",
        streamUrl = "https://icecast.skyrock.net/s/natio_mp3_128k",
        frequency = 9010,
        genre = "Pop"

    ),
    RadioStation(
        id = "fip",
        name = "FIP",
        streamUrl = "https://icecast.radiofrance.fr/fip-hifi.aac",
        frequency = 9120,
        genre = "Eclectic"
    ),
    RadioStation(
        id = "radio_swiss_jazz",
        name = "Radio Swiss Jazz",
        streamUrl = "https://stream.srg-ssr.ch/m/rsj/aacp_96",
        frequency = 9240,
        genre = "Jazz"
    ),
    RadioStation(
        id = "kexp",
        name = "KEXP Seattle",
        streamUrl = "https://kexp-mp3-128.streamguys1.com/kexp128.mp3",
        frequency = 9360,
        genre = "Indie"
    ),
    RadioStation(
        id = "classic_fm",
        name = "Classic FM UK",
        streamUrl = "https://media-ice.musicradio.com/ClassicFMMP3",
        frequency = 9480,
        genre = "Classical"
    ),
    RadioStation(
        id = "radio_paradise",
        name = "Radio Paradise",
        streamUrl = "https://stream.radioparadise.com/mp3-192",
        frequency = 9710,
        genre = "Eclectic"
    ),
    RadioStation(
        id = "npr",
        name = "NPR News",
        streamUrl = "https://npr-ice.streamguys1.com/live.mp3",
        frequency = 9840,
        genre = "News"
    ),
    RadioStation(
        id = "antenne_bayern",
        name = "Antenne Bayern",
        streamUrl = "https://stream.antenne.de/antenne",
        frequency = 9960,
        genre = "Pop"
    ),
    RadioStation(
        id = "rai_radio_2",
        name = "RAI Radio 2",
        streamUrl = "https://icestreaming.rai.it/2.mp3",
        frequency = 10420,
        genre = "Talk"
    ),
    RadioStation(
        id = "abc_radio_national",
        name = "ABC Radio National",
        streamUrl = "https://live-radio01.mediahubaustralia.com/2RNW/mp3/",
        frequency = 10550,
        genre = "News"
    ),
    RadioStation("FC", "france culture", "https://direct.franceculture.fr/live/franceculture-midfi.mp3", 10800)
    )
