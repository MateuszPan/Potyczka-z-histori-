package com.example.potyczkazhistoria.ui.screens.newgame
import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.potyczkazhistoria.R
import com.example.potyczkazhistoria.data.AppDatabase
import com.example.potyczkazhistoria.data.AnswerEntity
import com.example.potyczkazhistoria.data.QuestionEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

enum class GameEndState {
    WIN,
    LOSE_POINTS,
    LOSE_TIMEOUT
}

@Composable
fun NewGameScreen() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val questionDao = db.questionDao()
    val answerDao = db.answerDao()
    val coroutineScope = rememberCoroutineScope()
    val failSound = remember { MediaPlayer.create(context, R.raw.fail2) }
    DisposableEffect(failSound) { onDispose { failSound.release() } }
    val knightImage = ImageBitmap.imageResource(R.drawable.knight)
    val imgFar = ImageBitmap.imageResource(R.drawable.far)
    val imgMiddle = ImageBitmap.imageResource(R.drawable.middle)
    val imgNear = ImageBitmap.imageResource(R.drawable.near)
    val imgBack = ImageBitmap.imageResource(R.drawable.back)
    val imgForeground = ImageBitmap.imageResource(R.drawable.foreground)
    val tilesetImg = ImageBitmap.imageResource(R.drawable.tileset)
    val torchSheet = ImageBitmap.imageResource(R.drawable.torch_sheet)
    val columnImage = ImageBitmap.imageResource(R.drawable.column)
    var xFar by remember { mutableStateOf(0f) }
    var xMiddle by remember { mutableStateOf(0f) }
    var xNear by remember { mutableStateOf(0f) }
    var xBack by remember { mutableStateOf(0f) }
    var xFg by remember { mutableStateOf(0f) }
    var xTiles by remember { mutableStateOf(0f) }
    val torchFrameWidth = torchSheet.width / 4
    val torchFrameHeight = torchSheet.height
    var torchFrame by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(120)
            torchFrame = (torchFrame + 1) % 4
        }
    }
    var dinoY by remember { mutableStateOf(0f) }
    var velocity by remember { mutableStateOf(0f) }
    var isJumping by remember { mutableStateOf(false) }
    val obstacles = remember { mutableStateListOf<Rect>() }
    var points by remember { mutableStateOf(100) }
    var gameOver by remember { mutableStateOf(false) }
    var gameEndState by remember { mutableStateOf<GameEndState?>(null) }
    var lastBonusTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var collisionCooldown by remember { mutableStateOf(0L) }
    val gravity = 2f
    val jumpStrength = -35f
    val obstacleSpeed = 14f
    val dinoX = 200f
    val dinoSize = 270f
    var showQuestion by remember { mutableStateOf(false) }
    var currentQuestion by remember { mutableStateOf<QuestionEntity?>(null) }
    var currentAnswers by remember { mutableStateOf<List<AnswerEntity>>(emptyList()) }
    var selectedAnswerId by remember { mutableStateOf<Int?>(null) }
    var wrongAnswers by remember { mutableStateOf(0) }
    var totalQuestionsAsked by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    val AUTO_POINTS_INTERVAL = 1000L
    val AUTO_POINTS_REWARD = 10
    val COLLISION_PENALTY = 50
    val CORRECT_ANSWER_REWARD = 200
    val WRONG_ANSWER_PENALTY = 100
    val WIN_POINTS = 3000
    val MAX_WRONG_ANSWERS = 3
    val KNOWLEDGE_WIN_THRESHOLD = 0.7
    val MIN_QUESTIONS_FOR_KNOWLEDGE_WIN = 5
    val maxGameDurationMs = 12 * 60 * 1000L
    var startTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var nextQuestionTime by remember {
        mutableStateOf(System.currentTimeMillis() + Random.nextLong(10_000, 20_000))
    }
    fun loadRandomQuestion() {
        coroutineScope.launch {
            val questions = questionDao.getAllQuestions()
            if (questions.isNotEmpty()) {
                val q = questions.random()
                val a = answerDao.getAnswersByQuestionId(q.id)
                currentQuestion = q
                currentAnswers = a.shuffled()
                selectedAnswerId = null
                showQuestion = true
            }
        }
    }
    fun restartGame() {
        points = 100
        gameOver = false
        gameEndState = null
        obstacles.clear()
        showQuestion = false
        dinoY = 0f
        velocity = 0f
        isJumping = false
        nextQuestionTime = System.currentTimeMillis() + Random.nextLong(10_000, 15_000)
        startTime = System.currentTimeMillis()
        lastBonusTime = startTime
        collisionCooldown = 0L
        wrongAnswers = 0
        totalQuestionsAsked = 0
        correctAnswers = 0
        selectedAnswerId = null
    }
    LaunchedEffect(Unit) {
        var lastFrameNanos = System.nanoTime()
        while (true) {
            val frameTime = withFrameNanos { it }
            val deltaNs = frameTime - lastFrameNanos
            lastFrameNanos = frameTime
            val deltaMs = (deltaNs / 1_000_000L).coerceAtLeast(1L).toFloat()
            val now = System.currentTimeMillis()
            if (!gameOver && now - startTime >= maxGameDurationMs) {
                gameOver = true
                gameEndState = GameEndState.LOSE_TIMEOUT
            }
            if (showQuestion || gameOver) {
                continue
            }
            val elapsed = now - lastBonusTime
            if (elapsed >= AUTO_POINTS_INTERVAL) {
                val ticks = (elapsed / AUTO_POINTS_INTERVAL).toInt()
                if (ticks > 0) {
                    points += ticks * AUTO_POINTS_REWARD
                    lastBonusTime += ticks * AUTO_POINTS_INTERVAL
                }
            }
            if (!showQuestion && now >= nextQuestionTime) {
                val obstacleNear = obstacles.any { it.left < dinoX + 300f }
                if (!isJumping && !obstacleNear) {
                    loadRandomQuestion()
                    nextQuestionTime = now + Random.nextLong(10_000, 20_000)
                } else {
                    nextQuestionTime = now + 1000L
                }
            }
            val frameFactor = deltaMs / 16f
            if (isJumping) {
                velocity += gravity * frameFactor
                dinoY += velocity * frameFactor
                if (dinoY > 0f) {
                    dinoY = 0f
                    velocity = 0f
                    isJumping = false
                }
            }
            val moveBy = obstacleSpeed * frameFactor
            if (obstacles.isNotEmpty()) {
                for (i in 0 until obstacles.size) {
                    val r = obstacles[i]
                    val newRect = Rect(
                        left = r.left - moveBy,
                        top = r.top,
                        right = r.right - moveBy,
                        bottom = r.bottom
                    )
                    obstacles[i] = newRect
                }
                obstacles.removeAll { it.right <= 0f }
            }
            if (now >= nextQuestionTime + 1 && obstacles.isEmpty().not()) {
            }
            val dinoRect = Rect(
                left = dinoX,
                top = -dinoSize + dinoY,
                right = dinoX + dinoSize,
                bottom = dinoY
            )
            val collidedObstacles = obstacles.filter { dinoRect.overlaps(it) }
            if (collidedObstacles.isNotEmpty() && now > collisionCooldown) {
                failSound.start()
                points -= COLLISION_PENALTY
                if (points < 0) points = 0
                collisionCooldown = now + 800
                obstacles.removeAll { it in collidedObstacles }
                if (!gameOver && points <= 0) {
                    points = 0
                }
            }
            xFar -= obstacleSpeed * 0.06f * frameFactor
            xMiddle -= obstacleSpeed * 0.12f * frameFactor
            xNear -= obstacleSpeed * 0.22f * frameFactor
            xBack -= obstacleSpeed * 0.34f * frameFactor
            xTiles -= obstacleSpeed * 0.32f * frameFactor
            xFg -= obstacleSpeed * 0.6f * frameFactor
        }
    }
    var nextSpawnTimeObstacles by remember { mutableStateOf(System.currentTimeMillis() + Random.nextLong(600L, 1800L)) }

    LaunchedEffect(Unit) {
        while (true) {
            val now = System.currentTimeMillis()
            if (!gameOver && !showQuestion && now >= nextSpawnTimeObstacles) {
                val height = 120f
                val width = 60f
                obstacles.add(
                    Rect(
                        left = 1500f,
                        top = -height,
                        right = 1500f + width,
                        bottom = 0f
                    )
                )
                nextSpawnTimeObstacles = now + Random.nextLong(600L, 1800L)
            }
            delay(50)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        if (!isJumping && !showQuestion && !gameOver) {
                            isJumping = true
                            velocity = jumpStrength
                        }
                    }
                )
            }
    ) {
        if (!gameOver && !showQuestion) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                ) {
                    Text("Wyjście z gry")
                }
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            val screenWidth = size.width
            val screenHeight = size.height
            val groundY = screenHeight * 0.75f
            val tilesetTargetHeight = (screenHeight * 0.12f).coerceAtLeast(20f)
            val tilesetScale = tilesetTargetHeight / tilesetImg.height.toFloat()
            val tilesetDstW = (tilesetImg.width * tilesetScale).toInt()
            val tilesetDstH = tilesetTargetHeight.toInt()
            val tilesetTop = groundY - tilesetDstH
            val globalScale = groundY / imgBack.height.toFloat()
            fun drawParallax(
                img: ImageBitmap,
                offset: Float
            ) {
                val dstW = (img.width * globalScale).toInt()
                val dstH = (img.height * globalScale).toInt()
                if (dstW <= 0 || dstH <= 0) return
                val y = (groundY - dstH).toInt()
                val screenW = size.width.toInt()
                var x = (offset % dstW).toInt()
                if (x > 0) x -= dstW
                while (x < screenW) {
                    drawImage(
                        image = img,
                        dstOffset = IntOffset(x, y),
                        dstSize = IntSize(dstW, dstH)
                    )
                    x += dstW
                }
            }
            drawParallax(imgBack, xBack)
            drawParallax(imgFar, xFar)
            var tx = ((xTiles % tilesetDstW) + tilesetDstW) % tilesetDstW
            var drawX = -tx
            while (drawX < screenWidth) {
                drawImage(
                    image = tilesetImg,
                    dstOffset = IntOffset(drawX.toInt(), tilesetTop.toInt()),
                    dstSize = IntSize(tilesetDstW, tilesetDstH)
                )
                drawX += tilesetDstW.toFloat()
            }
            drawParallax(imgMiddle, xMiddle)
            run {
                val dstW = (imgMiddle.width * globalScale).toInt()
                val dstH = (imgMiddle.height * globalScale).toInt()
                if (dstW > 0 && dstH > 0) {
                    val screenW = size.width
                    var startX = (xMiddle % dstW).toInt()
                    if (startX > 0) startX -= dstW
                    var colIndex = 0
                    var tileX = startX
                    while (tileX < screenW + dstW) {
                        if (colIndex == 1 || colIndex == 3) {
                            val torchX = tileX + dstW / 2f - (torchFrameWidth / 1f) / 2f
                            val torchY = groundY - dstH * 0.65f
                            val frameSrcX = torchFrame * torchFrameWidth
                            drawImage(
                                image = torchSheet,
                                srcOffset = IntOffset(frameSrcX, 0),
                                srcSize = IntSize(torchFrameWidth, torchFrameHeight),
                                dstOffset = IntOffset(torchX.toInt(), torchY.toInt()),
                                dstSize = IntSize(torchFrameWidth, torchFrameHeight)
                            )
                        }
                        colIndex++
                        tileX += dstW
                    }
                }
            }
            drawParallax(imgNear, xNear)
            drawParallax(imgForeground, xFg)
            drawRect(
                color = Color.DarkGray,
                topLeft = Offset(0f, groundY),
                size = Size(screenWidth, 8f)
            )
            val knightTop = groundY - dinoSize + dinoY
            drawImage(
                image = knightImage,
                dstSize = IntSize(dinoSize.toInt(), dinoSize.toInt()),
                dstOffset = IntOffset(dinoX.toInt(), knightTop.toInt())
            )
            for (r in obstacles) {
                val dstOffset = IntOffset(r.left.toInt(), (groundY + r.top).toInt())
                val dstSize = IntSize(r.width.toInt(), r.height.toInt())
                drawImage(
                    image = columnImage,
                    dstOffset = dstOffset,
                    dstSize = dstSize
                )
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                "Punkty: $points",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
        if (showQuestion && currentQuestion != null) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        currentQuestion!!.text,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        currentAnswers.forEach { answer ->
                            val isSelected = selectedAnswerId == answer.id
                            val backgroundColor =
                                when {
                                    isSelected && answer.isCorrect -> Color(0xFF4CAF50)
                                    isSelected && !answer.isCorrect -> Color(0xFFF44336)
                                    selectedAnswerId != null && answer.isCorrect -> Color(0xFF4CAF50)
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            Button(
                                onClick = {
                                    if (selectedAnswerId == null) {
                                        selectedAnswerId = answer.id
                                        totalQuestionsAsked += 1
                                        if (answer.isCorrect) {
                                            points += CORRECT_ANSWER_REWARD
                                            correctAnswers += 1
                                        } else {
                                            points -= WRONG_ANSWER_PENALTY
                                            if (points < 0) points = 0
                                            wrongAnswers += 1
                                        }
                                        var endTriggered = false
                                        if (!endTriggered && points >= WIN_POINTS) {
                                            gameOver = true
                                            gameEndState = GameEndState.WIN
                                            endTriggered = true
                                        }
                                        if (!endTriggered && totalQuestionsAsked >= MIN_QUESTIONS_FOR_KNOWLEDGE_WIN) {
                                            val ratio = correctAnswers.toDouble() / totalQuestionsAsked.toDouble()
                                            if (ratio >= KNOWLEDGE_WIN_THRESHOLD) {
                                                gameOver = true
                                                gameEndState = GameEndState.WIN
                                                endTriggered = true
                                            }
                                        }
                                        if (!endTriggered && wrongAnswers >= MAX_WRONG_ANSWERS) {
                                            gameOver = true
                                            gameEndState = GameEndState.LOSE_POINTS
                                            endTriggered = true
                                        }
                                        if (endTriggered) {
                                            showQuestion = false
                                            selectedAnswerId = null
                                        } else {
                                            coroutineScope.launch {
                                                delay(1000)
                                                selectedAnswerId = null
                                                showQuestion = false
                                                nextQuestionTime = System.currentTimeMillis() + if (answer.isCorrect) {
                                                    Random.nextLong(10_000, 30_000)
                                                } else {
                                                    Random.nextLong(5_000, 10_000)
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
                            ) {
                                Text(answer.text)
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }
        if (gameEndState != null) {
            val message = when (gameEndState) {
                GameEndState.WIN -> "Gratulacje, wygrałeś! Jesteś prawdziwym mistrzem historii! Chcesz zagrać jeszcze raz, Mistrzu?"
                GameEndState.LOSE_POINTS,
                GameEndState.LOSE_TIMEOUT -> "Przykro mi, przegrałeś. Jest jeszcze nad czym popracować. Chcesz zagrać jeszcze raz?"
                null -> ""
            }
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = when (gameEndState) {
                            GameEndState.WIN -> "Wygrana!"
                            GameEndState.LOSE_POINTS,
                            GameEndState.LOSE_TIMEOUT -> "Koniec gry"
                            null -> ""
                        },
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                confirmButton = {
                    Button(onClick = { restartGame() }) {
                        Text("Tak")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }) {
                        Text("Nie")
                    }
                }
            )
        }
    }
}
