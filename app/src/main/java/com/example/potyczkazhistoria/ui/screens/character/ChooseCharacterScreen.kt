package com.example.potyczkazhistoria.ui.screens.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.potyczkazhistoria.R

@Composable
fun ChooseCharacterScreen(onCharacterSelected: (Int) -> Unit) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Wybierz swoją postać", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CharacterButton(
                    imageRes = R.drawable.knight,
                    label = "Rycerz 1",
                    onClick = { onCharacterSelected(R.drawable.knight) }
                )
                CharacterButton(
                    imageRes = R.drawable.knight_1,
                    label = "Rycerz 2",
                    onClick = { onCharacterSelected(R.drawable.knight_1) }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CharacterButton(
                    imageRes = R.drawable.knight_2,
                    label = "Rycerka 1",
                    onClick = { onCharacterSelected(R.drawable.knight_2) }
                )
                CharacterButton(
                    imageRes = R.drawable.knight_3,
                    label = "Rycerka 2",
                    onClick = { onCharacterSelected(R.drawable.knight_3) }
                )
            }
        }
    }
}

@Composable
fun CharacterButton(imageRes: Int, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = onClick) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = label,
                modifier = Modifier.size(64.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}
