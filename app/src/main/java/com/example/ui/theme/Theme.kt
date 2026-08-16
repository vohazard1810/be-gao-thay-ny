package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ToddlerColorScheme = lightColorScheme(
  primary = SoftSkyBlue,
  onPrimary = TextLight,
  primaryContainer = BubbleBackground,
  onPrimaryContainer = TextDark,
  secondary = WarmOrange,
  onSecondary = TextLight,
  secondaryContainer = SunnyYellow,
  onSecondaryContainer = TextDark,
  tertiary = MintGreen,
  onTertiary = TextLight,
  background = CreamBackground,
  onBackground = TextDark,
  surface = CardBackground,
  onSurface = TextDark,
  surfaceVariant = BubbleBackground,
  onSurfaceVariant = TextDark
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep vibrant preschool colors consistent
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = ToddlerColorScheme,
    typography = Typography,
    content = content
  )
}
