package com.ohiopolice.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Color(0xFF0D47A1),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF90CAF9)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    secondary = Color(0xFF1976D2),
    tertiary = Color(0xFF0D47A1)
)

@Composable
fun OhioPoliceTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    SideEffect {
        WindowCompat.getInsetsController(view.context as android.app.Activity, view).isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
