package com.gdgedinburgh.gemmaworkshop.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.gdgedinburgh.gemmaworkshop.core.navigation.Destination
import com.gdgedinburgh.gemmaworkshop.screens.composables.CustomTopAppBar

@Composable
fun GenerateTextScreen(
    navigateTo: (Destination) -> Unit = {},
    navigateUp: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Generate Textt",
                navigateUp = navigateUp
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Generate Text",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}