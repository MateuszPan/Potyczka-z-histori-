package com.example.potyczkazhistoria
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

@Composable
fun MusicControls() {
    val context = LocalContext.current
    var isMuted by remember { mutableStateOf(false) }
    var volume by remember { mutableStateOf(0.5f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(onClick = {
                isMuted = !isMuted
                if (isMuted) MusicPlayer.setVolume(0f)
                else MusicPlayer.setVolume(volume)
            }) {
                Text(if (isMuted) "🔇" else "🔊")
            }
            Slider(
                value = volume,
                onValueChange = {
                    volume = it
                    if (!isMuted) MusicPlayer.setVolume(it)
                },
                valueRange = 0f..1f,
                modifier = Modifier.width(120.dp)
            )
        }
    }
}

