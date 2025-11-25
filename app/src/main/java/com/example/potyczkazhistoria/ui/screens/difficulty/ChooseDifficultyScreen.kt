package com.example.potyczkazhistoria.ui.screens.difficulty

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChooseDifficultyScreen(
    onNavigateNext: (String) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Wybierz poziom trudności", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onNavigateNext("łatwy") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Łatwy (standardowe punkty)")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { onNavigateNext("normalny") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Normalny (standardowe punkty)")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { onNavigateNext("trudny") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Trudny (+5 pkt za poprawną, -10 pkt za błędną)")
            }
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { onNavigateNext("ekspert") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ekspert (+10 pkt za poprawną, -20 pkt za błędną)")
            }
        }
    }
}
