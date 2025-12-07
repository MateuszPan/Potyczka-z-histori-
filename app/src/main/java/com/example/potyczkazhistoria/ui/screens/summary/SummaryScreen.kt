package com.example.potyczkazhistoria.ui.screens.summary
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SummaryScreen(
    score: Int,
    correctAnswers: Int,
    wrongAnswers: Int,
    bestScore: Int,
    onPlayAgain: () -> Unit,
    onExit: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as? Activity
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Podsumowanie gry", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Text("Twój wynik: $score", style = MaterialTheme.typography.titleLarge)
            Text("Najlepszy wynik: $bestScore", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Text("Poprawne odpowiedzi: $correctAnswers")
            Text("Błędne odpowiedzi: $wrongAnswers")
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zagraj ponownie")
            }
            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    onExit()
                    activity?.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Wyjdź z gry")
            }
        }
    }
}
