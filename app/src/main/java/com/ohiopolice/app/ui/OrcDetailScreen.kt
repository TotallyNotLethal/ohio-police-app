package com.ohiopolice.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ohiopolice.app.viewmodel.OrcViewModel

@Composable
fun OrcDetailScreen(
    section: String,
    orcViewModel: OrcViewModel,
    onBack: () -> Unit
) {
    val results by orcViewModel.results.collectAsState()
    val statute = results.firstOrNull { it.section == section }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(section) {
        isFavorite = orcViewModel.isFavorite(section)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ohio Revised Code ${section}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onBack) {
                Text(text = "Back")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (statute == null) {
            Text(text = "Statute not found in Ohio Revised Code dataset.")
            return
        }
        Text(text = statute.heading, style = MaterialTheme.typography.titleMedium)
        Text(text = statute.title)
        Text(text = "Chapter ${statute.chapter}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = statute.body, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            orcViewModel.toggleFavorite(statute, isFavorite)
            isFavorite = !isFavorite
        }) {
            Text(text = if (isFavorite) "Remove Favorite" else "Save Favorite")
        }
    }
}
