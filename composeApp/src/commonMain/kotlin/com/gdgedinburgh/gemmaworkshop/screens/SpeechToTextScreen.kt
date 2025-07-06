package com.gdgedinburgh.gemmaworkshop.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gdgedinburgh.gemmaworkshop.core.navigation.Destination

@Composable
fun SpeechToTextScreen(
    navigateTo: (Destination) -> Unit
) {
    Text("Speech to Text")
}