package com.example.potyczkazhistoria.ui.screens.chapter

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.potyczkazhistoria.R
import com.example.potyczkazhistoria.data.AppDatabase
import com.example.potyczkazhistoria.data.ChapterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.layout.ContentScale

@Composable
fun ChooseChapterScreen(
    epochId: Int,
    onNavigateNext: (chapterId: Int) -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val chapterDao = db.chapterDao()

    var chapters by remember { mutableStateOf<List<ChapterEntity>>(emptyList()) }

    LaunchedEffect(epochId) {
        val list = withContext(Dispatchers.IO) {
            chapterDao.getChaptersForEpoch(epochId)
        }
        chapters = list
    }

    // -------------------------------
    //   TŁO + ZAWARTOŚĆ EKRANU
    // -------------------------------
    Box(modifier = Modifier.fillMaxSize()) {

        // --------- TŁO ---------
        Image(
            painter = painterResource(id = R.drawable.tlo_rozdzialy),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // --------- TREŚĆ ---------
        Scaffold(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f), // przezroczyste tło Scaffold
            modifier = Modifier.fillMaxSize()
        ) { padding ->

            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.wrapContentHeight()
                ) {

                    Text("Wybierz rozdział",
                        style = MaterialTheme.typography.headlineMedium)

                    if (chapters.isEmpty()) {
                        Text("Ładuję rozdziały...", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.wrapContentHeight()
                        ) {
                            items(chapters) { chapter ->
                                Button(
                                    onClick = { onNavigateNext(chapter.id) },
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                ) {
                                    Text(chapter.title)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
