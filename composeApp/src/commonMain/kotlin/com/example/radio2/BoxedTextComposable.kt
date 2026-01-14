package com.example.radio2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BoxedText(displayedText : String, modifier: Modifier = Modifier) {

    Box(modifier = modifier.background(Color.Black)
        .border(3.dp, Color.LightGray, shape = RoundedCornerShape(5.dp))
        .padding(4.dp)
        .fillMaxWidth(0.8f)
        , contentAlignment = Alignment.BottomCenter) {

        Text(displayedText, color = Color.Green, maxLines = 1)
    }
}