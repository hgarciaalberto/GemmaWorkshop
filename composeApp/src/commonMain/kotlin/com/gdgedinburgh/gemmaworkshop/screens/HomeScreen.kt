package com.gdgedinburgh.gemmaworkshop.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gdgedinburgh.gemmaworkshop.core.navigation.Destination
import com.gdgedinburgh.gemmaworkshop.core.navigation.GenerateTextDestination
import com.gdgedinburgh.gemmaworkshop.core.navigation.SpeechToTextDestination
import com.gdgedinburgh.gemmaworkshop.core.navigation.SummaryTextDestination

typealias NavigateTo = (Destination) -> Unit

val LIST_ITEMS: List<Pair<String, Destination>> = listOf(
    "Generate Text" to GenerateTextDestination,
    "Speech to Text" to SpeechToTextDestination,
    "Summary Text" to SummaryTextDestination,
)

@Composable
fun HomeScreen(
    navigateTo: NavigateTo
) {
    LazyColumn {
        items(LIST_ITEMS) { item ->
            ListItem(
                headlineContent = {
                    Text(item.first)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .clickable {
                        navigateTo(item.second)
                    },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )
        }
    }
}