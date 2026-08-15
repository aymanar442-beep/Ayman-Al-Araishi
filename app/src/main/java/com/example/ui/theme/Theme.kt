package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ShaheenDarkColorScheme = darkColorScheme(
  primary = FalconBlue,
  onPrimary = TextWhite,
  primaryContainer = ShaheenSurfaceElevated,
  onPrimaryContainer = FalconCyan,
  secondary = FalconCyan,
  onSecondary = ShaheenBackground,
  secondaryContainer = ShaheenSurfaceCard,
  onSecondaryContainer = TextWhite,
  tertiary = ActiveEmerald,
  onTertiary = ShaheenBackground,
  background = ShaheenBackground,
  onBackground = TextWhite,
  surface = ShaheenSurfaceDark,
  onSurface = TextWhite,
  surfaceVariant = ShaheenSurfaceCard,
  onSurfaceVariant = TextMuted,
  outline = ShaheenMetallicBorder,
  outlineVariant = ShaheenMetallicBorderLight,
  error = InactiveCrimson,
  onError = TextWhite
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  val colorScheme = ShaheenDarkColorScheme
  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = ShaheenBackground.toArgb()
        window.navigationBarColor = ShaheenBackground.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
