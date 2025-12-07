package com.example.potyczkazhistoria.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.potyczkazhistoria.ui.screens.welcome.WelcomeScreen
import com.example.potyczkazhistoria.ui.screens.character.ChooseCharacterScreen
import com.example.potyczkazhistoria.ui.screens.epoch.ChooseEpochScreen
import com.example.potyczkazhistoria.ui.screens.chapter.ChooseChapterScreen
import com.example.potyczkazhistoria.ui.screens.difficulty.ChooseDifficultyScreen
import com.example.potyczkazhistoria.ui.screens.game.GameScreen
import com.example.potyczkazhistoria.ui.screens.summary.SummaryScreen
import com.example.potyczkazhistoria.ui.screens.newgame.NewGameScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onNavigateNext = { navController.navigate("chooseCharacter") },
                onNavigateSecond = { navController.navigate("newGame") }
            )
        }
        composable("chooseCharacter") {
            ChooseCharacterScreen { characterId ->
                navController.navigate("chooseEpoch/$characterId")
            }
        }
        composable(
            route = "chooseEpoch/{characterId}",
            arguments = listOf(
                navArgument("characterId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            ChooseEpochScreen(
                onNavigateNext = { epochId ->
                    navController.navigate("chooseChapter/$epochId/$characterId")
                }
            )
        }
        composable(
            route = "chooseChapter/{epochId}/{characterId}",
            arguments = listOf(
                navArgument("epochId") { type = NavType.IntType },
                navArgument("characterId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val epochId = backStackEntry.arguments?.getInt("epochId") ?: 0
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            ChooseChapterScreen(
                epochId = epochId,
                onNavigateNext = { chapterId ->
                    navController.navigate("chooseDifficulty/$chapterId/$characterId")
                }
            )
        }
        composable(
            route = "chooseDifficulty/{chapterId}/{characterId}",
            arguments = listOf(
                navArgument("chapterId") { type = NavType.IntType },
                navArgument("characterId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            ChooseDifficultyScreen(
                onNavigateNext = { difficulty ->
                    navController.navigate("game/$chapterId/$difficulty/$characterId")
                }
            )
        }
        composable(
            route = "game/{chapterId}/{difficulty}/{characterId}",
            arguments = listOf(
                navArgument("chapterId") { type = NavType.IntType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("characterId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 0
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "łatwy"
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0

            GameScreen(
                chapterId = chapterId,
                difficulty = difficulty,
                characterId = characterId,
                onGameFinished = { score, correct, wrong, bestScore ->
                    navController.navigate("summary/$score/$correct/$wrong/$bestScore")
                }
            )
        }
        composable(
            route = "summary/{score}/{correct}/{wrong}/{bestScore}",
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("correct") { type = NavType.IntType },
                navArgument("wrong") { type = NavType.IntType },
                navArgument("bestScore") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val correct = backStackEntry.arguments?.getInt("correct") ?: 0
            val wrong = backStackEntry.arguments?.getInt("wrong") ?: 0
            val bestScore = backStackEntry.arguments?.getInt("bestScore") ?: 0

            SummaryScreen(
                score = score,
                correctAnswers = correct,
                wrongAnswers = wrong,
                bestScore = bestScore,
                onPlayAgain = {
                    navController.navigate("welcome") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        composable("newGame") {
            NewGameScreen()
        }
    }
}
