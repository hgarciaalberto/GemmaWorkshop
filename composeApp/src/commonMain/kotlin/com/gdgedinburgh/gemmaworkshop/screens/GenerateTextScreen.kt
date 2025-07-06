package com.gdgedinburgh.gemmaworkshop.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gdgedinburgh.gemmaworkshop.core.navigation.Destination

@Composable
fun GenerateTextScreen(
    navigateTo: (Destination) -> Unit
) {
    Text(
        text = "Generate Text",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}