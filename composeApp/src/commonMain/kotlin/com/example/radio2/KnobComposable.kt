package com.example.radio2

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


@Composable
fun KnobComposable(
    knobAngle: MutableState<Int>,
    positiveAngle: Int,
    callbackDuringDrag: ((Int) -> Unit),
    callbackAfterDrag: (() -> Unit),
    modifier: Modifier = Modifier
) {

    Canvas(modifier = modifier.aspectRatio(1f).rotate(knobAngle.value.toFloat()).draggable(
        orientation = Orientation.Horizontal,
        state = rememberDraggableState { pixelsDelta ->
            val newValue = knobAngle.value + (pixelsDelta / 2)
                knobAngle.value = newValue.toInt().coerceIn(-positiveAngle, positiveAngle)
            callbackDuringDrag.invoke(knobAngle.value)
        },
        onDragStopped = {
            callbackAfterDrag.invoke()
        }
    )) {
        drawCircle(color = Color.Yellow)
        drawLine(
            color = Color.Red,
            start = Offset(x = size.width / 2, y = size.height / 2),
            end = Offset(x = size.width / 2, y = 0f),
            strokeWidth = 5f
        )
    }
}