package com.george.tvstreamapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// iOS Dark Mode Color Palette
private val IosBackground = Color(0xFF000000) // True black for OLED screens
private val IosSurface = Color(0xFF1C1C1E)    // Elevated surface gray
private val IosSystemBlue = Color(0xFF0A84FF) // Standard iOS Blue for focus/accents
private val IosTextPrimary = Color(0xFFFFFFFF)
private val IosTextSecondary = Color(0xFF8E8E93)

private val DarkColors = darkColorScheme(
    background = IosBackground,
    surface = IosSurface,
    primary = IosSystemBlue,
    onBackground = IosTextPrimary,
    onSurface = IosTextSecondary
)

// Clean iOS-like Typography
private val PremiumTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

@Composable
fun TVStreamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = PremiumTypography,
        content = content
    )
}