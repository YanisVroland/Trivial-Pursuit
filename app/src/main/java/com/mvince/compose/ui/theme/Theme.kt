package com.mvince.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = primary
)

private val LightColorPalette = lightColorScheme(
    primary = primary,
    secondary = secondary,
    background = background,
    surface = Color.White, // surface blanche (par défaut)
    onPrimary = Color.White, // texte blanc sur fond primaire
    onSecondary = Color.Black, // texte noir sur fond secondaire
    onBackground = Color.Black, // texte noir sur fond de base
    onSurface = Color.Black // texte noir sur surface
)

@Composable
fun JetpackComposeBoilerplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}