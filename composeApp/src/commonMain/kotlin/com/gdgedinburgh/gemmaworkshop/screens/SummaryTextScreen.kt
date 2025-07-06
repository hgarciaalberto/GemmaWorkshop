package com.gdgedinburgh.gemmaworkshop.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gdgedinburgh.gemmaworkshop.core.navigation.Destination

@Composable
fun SummaryTextScreen(
    navigateTo: (Destination) -> Unit
) {
    Text("Summary Text")
}