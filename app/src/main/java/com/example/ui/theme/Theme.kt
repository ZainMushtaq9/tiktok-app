package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SlateGray = Color(0xFF1E212B)
val LightSlate = Color(0xFF2C303E)
val PrimaryBlue = Color(0xFF5B8FE0)
val GoldAccent = Color(0xFFF2C94C)
val DeepCharcoal = Color(0xFF12141A)
val CardBackground = Color(0xFF252836)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = GoldAccent,
    background = DeepCharcoal,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE5E7EB),
    onSurface = Color(0xFFE5E7EB),
    surfaceVariant = LightSlate,
    onSurfaceVariant = Color(0xFF9CA3AF)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = GoldAccent,
    background = Color(0xFFF3F4F6),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF1F2937),
    onSurface = Color(0xFF1F2937),
    surfaceVariant = Color(0xFFE5E7EB),
    onSurfaceVariant = Color(0xFF4B5563)
)

@Composable
fun ReelStackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
