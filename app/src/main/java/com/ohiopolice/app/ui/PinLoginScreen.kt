package com.ohiopolice.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ohiopolice.app.viewmodel.PinViewModel

@Composable
fun PinLoginScreen(
    pinViewModel: PinViewModel,
    onAuthenticated: () -> Unit
) {
    val state by pinViewModel.uiState.collectAsState()

    if (state.isAuthenticated) {
        onAuthenticated()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "OHIO POLICE APP",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = if (state.isPinSet) "Enter PIN" else "Create a local PIN",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.pinInput,
            onValueChange = pinViewModel::onPinChanged,
            label = { Text("PIN") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )
        state.error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = pinViewModel::submitPin) {
            Text(text = "Continue")
        }
    }
}
