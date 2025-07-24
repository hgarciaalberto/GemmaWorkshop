package com.gdgedinburgh.gemmaworkshop.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gdgedinburgh.gemmaworkshop.screens.chat.ChatRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatDestination,
    ) {
        composable<ChatDestination> {
            ChatRoute()
        }
//        composable<HomeDestination> {
//            HomeScreen(
//                navigateTo = navController::navigate,
//            )
//        }
//
//        composable<GenerateTextDestination> {
//            GenerateTextScreen(
//                navigateTo = navController::navigate,
//                navigateUp = navController::navigateUp
//            )
//        }
//
//        composable<SpeechToTextDestination> {
//            SpeechToTextScreen(
//                navigateTo = navController::navigate,
//            )
//        }
//
//        composable<SummaryTextDestination> {
//            SummaryTextScreen(
//                navigateTo = navController::navigate,
//            )
//        }
    }
}
