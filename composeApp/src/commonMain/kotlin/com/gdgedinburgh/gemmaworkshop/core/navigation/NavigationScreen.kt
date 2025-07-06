package com.gdgedinburgh.gemmaworkshop.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gdgedinburgh.gemmaworkshop.screens.GenerateTextScreen
import com.gdgedinburgh.gemmaworkshop.screens.HomeScreen
import com.gdgedinburgh.gemmaworkshop.screens.SpeechToTextScreen
import com.gdgedinburgh.gemmaworkshop.screens.SummaryTextScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeDestination,
        modifier = Modifier,
    ) {
        composable<HomeDestination> {

            HomeScreen(
                navigateTo = navController::navigate
            )
        }

        composable<GenerateTextDestination> {
            GenerateTextScreen(
                navigateTo = navController::navigate
            )
        }

        composable<SpeechToTextDestination> {
            SpeechToTextScreen(
                navigateTo = navController::navigate,
            )
        }

        composable<SummaryTextDestination> {
            SummaryTextScreen(
                navigateTo = navController::navigate,
            )
        }
    }
}
