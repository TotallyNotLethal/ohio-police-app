package com.ohiopolice.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ohiopolice.app.viewmodel.OrcViewModel

@Composable
fun OrcSearchScreen(
    orcViewModel: OrcViewModel,
    onSelect: (String) -> Unit,
    onBack: () -> Unit
) {
    val query by orcViewModel.query.collectAsState()
    val results by orcViewModel.results.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ohio Revised Code Search",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onBack) {
                Text(text = "Back")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = orcViewModel::updateQuery,
            label = { Text("Search ORC title, section, or text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(results) { statute ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(statute.section) }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = "${statute.section} ${statute.heading}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = statute.title, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Chapter ${statute.chapter}")
                }
            }
        }
    }
}
