package com.example.radio2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import radio2.composeapp.generated.resources.Res
import radio2.composeapp.generated.resources.oak

@Composable
fun App(viewModel: RadioViewModel) {




    MaterialTheme {


        val audioLevel by viewModel.audioLevel.collectAsState()

        val selectedStation by viewModel.currentStation.collectAsState()
        val currentBand = FM_BAND
        val angular = 140
        val frequency by viewModel.currentFrequency.collectAsState()
        val playerState by viewModel.currentState.collectAsState()

        val frequenceKnobAngle by remember {
            derivedStateOf {
                frequencyToAngle(frequency, currentBand, angular)
            }
        }
//        val frequenceKnobAngle = remember {
//            mutableStateOf(frequencyToAngle(
//                frequency, currentBand, angular
//            ))
//        }
       // val audioKnobAngle = remember { mutableStateOf(0) }

        val audioKnobAngle by remember {
            derivedStateOf {
                audioLevelToAngle(audioLevel, angular)
            }
        }



        Column(
            modifier = Modifier
                //.background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize()
                .paint(painterResource(Res.drawable.oak), contentScale = ContentScale.FillBounds),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Row(modifier = Modifier.border(1.dp, Color.Black), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(enabled = when (playerState) {
                    PlayerState.Stopped -> true
                    is PlayerState.Error -> true
                    else -> false
                }, onClick = {
                    selectedStation?.let { viewModel.play(it) }
                }, modifier = Modifier.padding(8.dp)) {
                    Text("Play")
                }

                ElevatedButton(elevation = when(playerState) {
                    PlayerState.Paused ->  ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp)
                    else -> ButtonDefaults.elevatedButtonElevation(defaultElevation = 100.dp)
                }, enabled = when(playerState) {
                    PlayerState.Playing -> true
                    PlayerState.Paused -> true
                    else -> false
                }, onClick = {viewModel.pause()

                }, modifier = Modifier.padding(8.dp)) {
                    Text("Pause")
                }

                Button(enabled = when(playerState) {
                    PlayerState.Playing -> true
                    PlayerState.Paused -> true
                    PlayerState.Connecting -> true
                    else -> false
                },onClick = {viewModel.stop()}, modifier = Modifier.padding(8.dp)) {
                    Text("Stop")
                }

            }

//            Column {
//                Slider(
//                    value = audioLevel,
//                    onValueChange = {viewModel.modifyAudioLevel(it) },
//                    modifier = Modifier.padding(horizontal = 32.dp)
//                )
//
//                Text(text = "Volume : ${(audioLevel * 100).toInt()} %")
//            }

//            if (playerState == PlayerState.Playing) {
//                Text("Currently playing : ${selectedStation?.name}")
//            }

            //val str = if (playerState == PlayerState.Playing) "Currently playing : ${selectedStation?.name}" else ""

//            Box(modifier = Modifier.background(Color.Black)
//                .border(3.dp, Color.LightGray, shape = RoundedCornerShape(5.dp))
//                .padding(4.dp)
//                .fillMaxWidth(0.7f)
//                , contentAlignment = Alignment.BottomCenter) {
//
//                Text(if (playerState == PlayerState.Playing) "Currently playing : ${selectedStation?.name}" else "------------",
//                    color = Color.Green)
//            }

            BoxedText(if (playerState == PlayerState.Playing)
                "Currently playing : ${selectedStation?.name}" else "------------")


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                Column(Modifier.width(150.dp).height(180.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {

                    KnobComposable(mutableStateOf(audioKnobAngle), angular, callbackDuringDrag = {
                            value -> viewModel.modifyAudioLevel(angleToAudioLevel(value, angular = angular))
                    }, callbackAfterDrag = {},  modifier = Modifier.weight(1f).padding(vertical = 24.dp))


                    BoxedText("Volume : ${(audioLevel * 100).toInt()} %")

                }


                Column(Modifier.width(150.dp).height(180.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

                    KnobComposable(
                        mutableStateOf(frequenceKnobAngle), angular,
                        callbackDuringDrag = {
                                value -> viewModel.modifyFrequency(angleToFrequency(value, bandUsed = currentBand, angular = angular))
                        },
                        callbackAfterDrag = {
                            viewModel.modifyFrequency(selectedStation!!.frequency)
                            snapBack(mutableStateOf(frequenceKnobAngle), selectedStation!!.frequency, currentBand, angular)
                        }, modifier = Modifier.weight(1f).padding(vertical = 24.dp)
                    )

                    BoxedText("${frequency / 100}.${(frequency % 100).toString().padStart(2, '0')} ${currentBand.unit}")
                }
            }

        }
    }


}

fun snapBack(angle : MutableState<Int>, frequency : Int, bandUsed : RadioBand, angular: Int) {
    angle.value = frequencyToAngle(frequency, bandUsed = bandUsed, angular = angular)
}
fun angleToFrequency(currentAngle: Int, bandUsed: RadioBand ,angular : Int) : Int {
    val normalized = ((currentAngle - -angular) / (angular - -angular).toFloat()).coerceIn(0f, 1f)
    return bandUsed.minFrequency + (normalized * (bandUsed.maxFrequency - bandUsed.minFrequency)).toInt()
}

fun angleToAudioLevel(currentAngle: Int,angular : Int) : Float{
    val normalized = ((currentAngle - -angular) / (angular - -angular).toFloat()).coerceIn(0f, 1f)
    return normalized
}


//            Column {
//                Slider(
//                    value = frequency,
//                    onValueChange = {
//                        viewModel.modifyFrequency(it)
//                        val angle = frequencyToAngle(frequency, currentBand, angular.value)
//                        knobAngle.value = angle
//                        },
//                    valueRange = currentBand.minFrequency..currentBand.maxFrequency,
//                    modifier = Modifier.padding(horizontal = 32.dp),
//                    steps = ((currentBand.maxFrequency - currentBand.minFrequency) / currentBand.step).toInt() - 1
//                )
//
//                Text(text = "$frequency ${currentBand.unit}", textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
//            }


fun frequencyToAngle(currentFrequency: Int, bandUsed: RadioBand, angular : Int): Int {
    val normalized = ((currentFrequency - bandUsed.minFrequency).toFloat() / (bandUsed.maxFrequency - bandUsed.minFrequency)).coerceIn(0f, 1f)
    return -angular + (normalized * (angular - -angular)).toInt()
}

fun audioLevelToAngle(audioLevel : Float, angular: Int) : Int {
    return (-angular + audioLevel * (angular - -angular)).toInt()

}
expect fun createLastStationStore(): LastStationStore

expect fun createRadioPlayer() : RadioPlayer

expect fun createWhiteNoisePlayer() : WhiteNoisePlayer
@Composable
fun createViewModel(stations : List<RadioStation>) : RadioViewModel {
    val store = remember { createLastStationStore() }
    val radioPlayer = remember { createRadioPlayer() }
    val whiteNoisePlayer = remember { createWhiteNoisePlayer() }
    val model = remember { RadioViewModel(radioPlayer, whiteNoisePlayer, store, stations) }

    LaunchedEffect(Unit) {
        model.restoreLastStation()
    }

    return model
}