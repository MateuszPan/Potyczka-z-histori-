package com.example.potyczkazhistoria.ui.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.potyczkazhistoria.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(
    onNavigateNext: () -> Unit,
    onNavigateSecond: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    val brightness by animateFloatAsState(
        targetValue = if (visible) 1f else 0.6f,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
    )

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { -200 }
                ) + fadeIn(animationSpec = tween(1500, easing = FastOutSlowInEasing))
            ) {
                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxWidth()
                        .background(Color(0xFFDDDDDD)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Witaj w potyczce z historią!",
                        color = Color(0xFFFF0000),
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tlo1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit,
                    alpha = brightness
                )
            }

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { it / 2 }
                ) + fadeIn(animationSpec = tween(1500, delayMillis = 800))
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .background(Color(0xFFDDDDDD)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = onNavigateNext,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Potyczka")
                    }

                    Button(
                        onClick = onNavigateSecond,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Pojedynek Mistrza")
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}