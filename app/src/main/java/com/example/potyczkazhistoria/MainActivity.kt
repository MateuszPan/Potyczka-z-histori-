package com.example.potyczkazhistoria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.potyczkazhistoria.data.AppDatabase
import com.example.potyczkazhistoria.data.DatabaseSeeder
import com.example.potyczkazhistoria.ui.navigation.AppNavigation
import com.example.potyczkazhistoria.ui.theme.PotyczkaZHistoriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MusicPlayer.start(this)
        val db = AppDatabase.getDatabase(this)
        DatabaseSeeder.seed(db)
        setContent {
            PotyczkaZHistoriaTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(navController)
                    MusicControls()
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        MusicPlayer.start(this)
    }
    override fun onStop() {
        super.onStop()
        MusicPlayer.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        MusicPlayer.stop()
    }
}

