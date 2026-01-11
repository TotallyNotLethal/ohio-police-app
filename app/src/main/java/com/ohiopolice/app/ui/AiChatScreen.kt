package com.ohiopolice.app.ui

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
import androidx.compose.ui.res.stringResource
import com.ohiopolice.app.R
import com.ohiopolice.app.viewmodel.AiChatViewModel
import com.ohiopolice.app.viewmodel.OrcViewModel

@Composable
fun AiChatScreen(
    aiChatViewModel: AiChatViewModel,
    orcViewModel: OrcViewModel,
    onBack: () -> Unit
) {
    val uiState by aiChatViewModel.uiState.collectAsState()
    val chat by aiChatViewModel.chatHistory.collectAsState(initial = emptyList())
    val statutes by orcViewModel.statutes.collectAsState()

    val orcContext = statutes.take(5).joinToString(separator = "\n\n") {
        "${it.section} ${it.heading}: ${it.body}"
    }

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
                text = "Ohio Revised Code AI Guidance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = onBack) {
                Text(text = "Back")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.orc_disclaimer),
            style = MaterialTheme.typography.bodyMedium
        )
        if (uiState.isOffline) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Offline: AI guidance disabled.", color = MaterialTheme.colorScheme.error)
        }
        uiState.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chat) { message ->
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = if (message.role == "user") "Officer" else "AI",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(text = message.content)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.input,
            onValueChange = aiChatViewModel::updateInput,
            label = { Text("Ask about Ohio Revised Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { aiChatViewModel.sendMessage(orcContext) },
                enabled = !uiState.isLoading && !uiState.isOffline
            ) {
                Text(text = if (uiState.isLoading) "Sending..." else "Send")
            }
            Button(onClick = aiChatViewModel::clearChat) {
                Text(text = "Clear")
            }
        }
    }
}
