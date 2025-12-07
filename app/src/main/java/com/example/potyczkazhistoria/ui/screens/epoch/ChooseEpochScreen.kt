package com.example.potyczkazhistoria.ui.screens.epoch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.potyczkazhistoria.R

@Composable
fun ChooseEpochScreen(onNavigateNext: (Int) -> Unit) {
    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.tlo_epoki),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Wybierz epokę", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { onNavigateNext(1) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Przez epoki o Polsce (Klasa IV)")
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onNavigateNext(2) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Od pierwszych cywilizacji do Polski XIII - XV w. (Klasa V)")
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onNavigateNext(3) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Od wielkich odkryć geograficznych przez epokę napoleońską. (Klasa VI)")
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onNavigateNext(4) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Od Europy po kongresie wiedeńskim do wybuchu II wojny światowej. (Klasa VII)")
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onNavigateNext(5) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Od Wojny obronnej we Wrześniu 1939 r. do współczesności (Klasa VIII)")
                }
            }
        }
    }
}
