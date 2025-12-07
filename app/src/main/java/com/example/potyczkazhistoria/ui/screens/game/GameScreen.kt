package com.example.potyczkazhistoria.ui.screens.game
import android.media.MediaPlayer
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.potyczkazhistoria.R
import com.example.potyczkazhistoria.data.AppDatabase
import com.example.potyczkazhistoria.data.AnswerEntity
import com.example.potyczkazhistoria.data.QuestionEntity
import kotlinx.coroutines.delay
import kotlin.math.hypot
import kotlin.math.roundToInt
import kotlin.random.Random

enum class GamePhase {
    Chase, Question, GameOver
}

@Composable
fun GameScreen(
    chapterId: Int,
    difficulty: String,
    characterId: Int,
    onGameFinished: (score: Int, correct: Int, wrong: Int, bestScore: Int) -> Unit
) {
    val context = LocalContext.current
    val successPlayer = remember { MediaPlayer.create(context, R.raw.success) }
    val failPlayer = remember { MediaPlayer.create(context, R.raw.fail) }
    val medalPlayer = remember { MediaPlayer.create(context, R.raw.medal) }
    val bombPlayer = remember { MediaPlayer.create(context, R.raw.bomb) }
    val db = AppDatabase.getDatabase(context)
    var phase by remember { mutableStateOf(GamePhase.Chase) }
    var questions by remember { mutableStateOf<List<QuestionEntity>>(emptyList()) }
    var answersMap by remember { mutableStateOf<Map<Int, List<AnswerEntity>>>(emptyMap()) }
    var currentIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(100) }
    var correctAnswers by remember { mutableStateOf(0) }
    var wrongAnswers by remember { mutableStateOf(0) }
    var correctStreak by remember { mutableStateOf(0) }
    var lastQuestionId by remember { mutableStateOf<Int?>(null) }
    var playerPos by remember { mutableStateOf(Offset(300f, 800f)) }
    var enemyPos by remember { mutableStateOf(Offset(100f, 100f)) }
    val enemyBaseSpeed = 2f
    val enemySpeedMultiplier = when (difficulty.lowercase()) {
        "łatwy" -> 1f
        "normalny" -> 1.25f
        "trudny" -> 1.5f
        "ekspert" -> 2f
        else -> 1f
    }
    val enemySpeed = enemyBaseSpeed * enemySpeedMultiplier
    var canDrag by remember { mutableStateOf(false) }
    var powerUpPos by remember { mutableStateOf<Offset?>(null) }
    var powerUpColor by remember { mutableStateOf<Color?>(null) }
    var powerUpActive by remember { mutableStateOf(false) }
    LaunchedEffect(phase) {
        while (phase == GamePhase.Chase) {
            delay(5000)
            if (!powerUpActive) {
                powerUpColor = if (Random.nextBoolean()) Color.Red else Color.Black
                powerUpPos = Offset(
                    Random.nextInt(100, 800).toFloat(),
                    Random.nextInt(200, 1200).toFloat()
                )
                powerUpActive = true
            }
        }
    }
    LaunchedEffect(powerUpActive) {
        if (powerUpActive) {
            delay(3000)
            if (powerUpActive) {
                powerUpActive = false
                powerUpPos = null
            }
        }
    }
    LaunchedEffect(powerUpPos, playerPos) {
        powerUpPos?.let { pos ->
            val dist = hypot(playerPos.x - pos.x, playerPos.y - pos.y)
            if (dist < 80 && powerUpActive) {
                if (powerUpColor == Color.Red) {
                    score += 20
                    medalPlayer.start()
                }
                if (powerUpColor == Color.Black) {
                    score -= 20
                    bombPlayer.start()
                }
                powerUpActive = false
                powerUpPos = null
            }
        }
    }
    LaunchedEffect(chapterId, difficulty) {
        try {
            val qList = db.questionDao().getQuestionsByChapterAndDifficulty(chapterId, difficulty).shuffled()
            val aMap = qList.associate { q -> q.id to db.answerDao().getAnswersByQuestionId(q.id).shuffled() }
            questions = qList
            answersMap = aMap
        } catch (e: Exception) {
            Log.e("GameScreen", "Błąd pobierania pytań: ${e.message}")
        }
    }
    LaunchedEffect(phase) {
        while (phase == GamePhase.Chase) {
            delay(16)
            val dx = playerPos.x - enemyPos.x
            val dy = playerPos.y - enemyPos.y
            val dist = hypot(dx, dy)
            if (dist > 5) {
                enemyPos = Offset(
                    enemyPos.x + (dx / dist) * enemySpeed,
                    enemyPos.y + (dy / dist) * enemySpeed
                )
            }
            if (dist < 80) {
                phase = GamePhase.Question
            }
        }
    }
    when (phase) {
        GamePhase.Chase -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                val playerCenter = Offset(
                                    playerPos.x + 40f - 20f,
                                    playerPos.y + 40f + 30f
                                )
                                val distance = hypot(
                                    offset.x - playerCenter.x,
                                    offset.y - playerCenter.y
                                )
                                canDrag = distance < 160f
                            },
                            onDrag = { change, dragAmount ->
                                if (canDrag) {
                                    change.consume()
                                    playerPos = Offset(
                                        (playerPos.x + dragAmount.x)
                                            .coerceIn(60f, size.width.toFloat() - 180f),
                                        (playerPos.y + dragAmount.y)
                                            .coerceIn(100f, size.height.toFloat() - 280f)
                                    )
                                }
                            },
                            onDragEnd = {
                                canDrag = false
                            }
                        )
                    }
            ) {
                Image(
                    painter = painterResource(R.drawable.tlo_gonitwa),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
                )
                Image(
                    painter = painterResource(id = characterId),
                    contentDescription = "Gracz",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(playerPos.x.roundToInt(), playerPos.y.roundToInt()) }
                )
                Image(
                    painter = painterResource(R.drawable.enemy_knight),
                    contentDescription = "Wróg",
                    modifier = Modifier
                        .size(80.dp)
                        .offset { IntOffset(enemyPos.x.roundToInt(), enemyPos.y.roundToInt()) }
                )
                powerUpPos?.let { pos ->
                    Text(
                        text = if (powerUpColor == Color.Red) "🥇" else "💣",
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        modifier = Modifier
                            .offset { IntOffset(pos.x.roundToInt(), pos.y.roundToInt()) }
                    )
                }
                Text(
                    text = "Punkty: $score",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = {
                        val best = updateBestScore(context, score)
                        onGameFinished(score, correctAnswers, wrongAnswers, best)
                        phase = GamePhase.GameOver
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text("Wyjście z gry")
                }
            }
        }
        GamePhase.Question -> {
            if (questions.isEmpty()) {
                val best = updateBestScore(context, score)
                onGameFinished(score, correctAnswers, wrongAnswers, best)
                phase = GamePhase.GameOver
                return
            }
            val question = remember(currentIndex, questions, lastQuestionId) {
                if (questions.size <= 1) {
                    questions.first()
                } else {
                    var q: QuestionEntity
                    do {
                        q = questions.random()
                    } while (q.id == lastQuestionId)
                    q
                }
            }
            val answers = answersMap[question.id] ?: emptyList()
            var selectedAnswerId by remember { mutableStateOf<Int?>(null) }
            var showNext by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Pytanie ${currentIndex + 1}", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))
                Text("Seria poprawnych: $correctStreak / 5", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Text(question.text, style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                Spacer(Modifier.height(24.dp))
                answers.forEach { answer ->
                    val backgroundColor = when {
                        selectedAnswerId == null -> Color.Transparent
                        answer.id == selectedAnswerId && !answer.isCorrect -> Color(0xFFFFCDD2)
                        answer.isCorrect && selectedAnswerId != null -> Color(0xFFC8E6C9)
                        else -> Color.Transparent
                    }
                    Button(
                        onClick = {
                            if (selectedAnswerId == null) {
                                selectedAnswerId = answer.id
                                val basePoints = if (answer.isCorrect) 10 else -5
                                val bonus = when (difficulty.lowercase()) {
                                    "trudny" -> if (answer.isCorrect) 5 else -10
                                    "ekspert" -> if (answer.isCorrect) 10 else -20
                                    else -> 0
                                }
                                score += basePoints + bonus
                                if (answer.isCorrect) {
                                    correctAnswers++
                                    correctStreak++
                                    successPlayer.start()
                                } else {
                                    wrongAnswers++
                                    correctStreak = 0
                                    failPlayer.start()
                                }
                                showNext = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(backgroundColor),
                        enabled = selectedAnswerId == null
                    ) {
                        Text(
                            answer.text,
                            color = if (selectedAnswerId == null) Color.White else Color.Black
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                if (showNext) {
                    Button(
                        onClick = {
                            lastQuestionId = question.id
                            if (correctStreak >= 5) {
                                val best = updateBestScore(context, score)
                                onGameFinished(score, correctAnswers, wrongAnswers, best)
                                phase = GamePhase.GameOver
                            } else {
                                currentIndex++
                                enemyPos = Offset(
                                    Random.nextInt(50, 500).toFloat(),
                                    Random.nextInt(50, 800).toFloat()
                                )
                                phase = GamePhase.Chase
                            }
                            selectedAnswerId = null
                            showNext = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Dalej")
                    }
                }
                Spacer(Modifier.height(24.dp))
                OutlinedButton(
                    onClick = {
                        val best = updateBestScore(context, score)
                        onGameFinished(score, correctAnswers, wrongAnswers, best)
                        phase = GamePhase.GameOver
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Wyjście z gry")
                }
            }
        }
        GamePhase.GameOver -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Koniec gry!", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            successPlayer.release()
            failPlayer.release()
            medalPlayer.release()
            bombPlayer.release()
        }
    }
}
fun updateBestScore(context: Context, currentScore: Int): Int {
    val prefs = context.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
    val best = prefs.getInt("best_score", Int.MIN_VALUE).let { if (it == Int.MIN_VALUE) 0 else it }
    if (currentScore > best) {
        prefs.edit().putInt("best_score", currentScore).apply()
        return currentScore
    }
    return best
}
