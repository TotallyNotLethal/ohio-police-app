package com.ohiopolice.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onSearch: () -> Unit,
    onAi: () -> Unit,
    onFavorites: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ohio Revised Code Quick Access",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onSearch, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Search ORC")
        }
        Button(onClick = onAi, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Ask AI")
        }
        Button(onClick = onFavorites, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Favorites")
        }
    }
}
