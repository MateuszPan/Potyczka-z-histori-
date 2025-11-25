package com.example.potyczkazhistoria.ui.screens.newgame

import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var obstacles by remember { mutableStateOf(listOf<Rect>()) }

    var points by remember { mutableStateOf(200) }
    var gameOver by remember { mutableStateOf(false) }
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

    fun loadRandomQuestion() {
        coroutineScope.launch {
            val questions = questionDao.getAllQuestions()
            if (questions.isNotEmpty()) {
                val q = questions.random()
                val a = answerDao.getAnswersByQuestionId(q.id)
                currentQuestion = q
                currentAnswers = a
                showQuestion = true
            }
        }
    }

    fun nextSpawnDelay(): Long = Random.nextLong(600L, 1800L)
    var nextSpawnTime by remember { mutableStateOf(System.currentTimeMillis() + nextSpawnDelay()) }

    var nextQuestionTime by remember { mutableStateOf(System.currentTimeMillis() + Random.nextLong(5000L, 10000L)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)

            if (showQuestion || gameOver) continue

            val now = System.currentTimeMillis()
            if (points > 0 && now - lastBonusTime >= 3000) {
                points += 10
                lastBonusTime = now
            }

            if (now >= nextQuestionTime) {
                loadRandomQuestion()
                nextQuestionTime = now + Random.nextLong(5000L, 10000L)
            }

            if (isJumping) {
                velocity += gravity
                dinoY += velocity
                if (dinoY > 0f) {
                    dinoY = 0f
                    velocity = 0f
                    isJumping = false
                }
            }

            obstacles = obstacles.map {
                Rect(
                    it.left - obstacleSpeed,
                    it.top,
                    it.right - obstacleSpeed,
                    it.bottom
                )
            }.filter { it.right > 0 }

            if (now >= nextSpawnTime) {
                val height = 120f
                val width = 60f

                obstacles = obstacles + Rect(
                    left = 1500f,
                    top = -height,
                    right = 1500f + width,
                    bottom = 0f
                )

                nextSpawnTime = now + nextSpawnDelay()
            }

            val dinoRect = Rect(
                left = dinoX,
                top = -dinoSize + dinoY,
                right = dinoX + dinoSize,
                bottom = dinoY
            )

            obstacles.forEach {
                if (dinoRect.overlaps(it)) {
                    if (now > collisionCooldown) {
                        failSound.start()
                        points -= 10
                        collisionCooldown = now + 800
                        if (points <= 0) {
                            showQuestion = false
                            gameOver = true
                        }
                    }
                }
            }

            xFar -= obstacleSpeed * 0.06f
            xMiddle -= obstacleSpeed * 0.12f
            xNear -= obstacleSpeed * 0.22f
            xBack -= obstacleSpeed * 0.34f
            xTiles -= obstacleSpeed * 0.32f
            xFg -= obstacleSpeed * 0.6f
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

        if (gameOver) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "Koniec gry!\nPunkty = 0",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )

                    Button(
                        onClick = {
                            points = 200
                            gameOver = false
                            obstacles = emptyList()
                            showQuestion = false
                            dinoY = 0f
                            velocity = 0f
                            isJumping = false
                            nextSpawnTime = System.currentTimeMillis() + 6000
                            nextQuestionTime = System.currentTimeMillis() + 8000
                        },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ) {
                        Text("Zagraj ponownie")
                    }

                    Button(
                        onClick = {
                            android.os.Process.killProcess(android.os.Process.myPid())
                        },
                        modifier = Modifier.fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text("Wyjście z gry")
                    }
                }
            }
            return@Box
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

            obstacles.forEach {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(it.left, groundY + it.top),
                    size = Size(it.width, it.height)
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

                                        if (answer.isCorrect) points += 10 else points -= 10

                                        kotlinx.coroutines.GlobalScope.launch {
                                            kotlinx.coroutines.delay(1000)
                                            selectedAnswerId = null
                                            showQuestion = false
                                            nextSpawnTime = System.currentTimeMillis() + 4000
                                            nextQuestionTime = System.currentTimeMillis() + Random.nextLong(5000L, 10000L)
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
    }
}
