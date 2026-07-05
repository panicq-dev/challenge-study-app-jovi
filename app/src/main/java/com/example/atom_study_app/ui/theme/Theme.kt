package com.example.atom_study_app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RoyalBlue,
    onPrimary = White,
    primaryContainer = RoyalBlueLight,
    onPrimaryContainer = RoyalBlue,
    secondary = BlueAccent,
    secondaryContainer = RoyalBlueLight,
    onSecondaryContainer = RoyalBlue,
    background = White,
    surface = White,
    surfaceVariant = White,
    onSurface = Black
)

private val DarkColorScheme = darkColorScheme(
    primary = RoyalBlue,
    onPrimary = White,
    primaryContainer = RoyalBlueDark,
    onPrimaryContainer = White,
    secondary = BlueAccent,
    background = DarkBackground,
    surface = DarkBackground,
    surfaceVariant = Color(0xFF1E1E1E),
    onSurface = White
)

@Composable
fun AtomstudyappTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}