package com.example.projeto_sistema_barbearia
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Ink = Color(0xFF16233A)
val Pole = Color(0xFFC8372D)
val PoleBlue = Color(0xFF2F5D9E)

private val Light = lightColorScheme(
    primary = Ink, onPrimary = Color.White,
    secondary = Pole, onSecondary = Color.White,
    background = Color(0xFFF3F5F7), onBackground = Ink,
    surface = Color.White, onSurface = Ink,
    surfaceVariant = Color(0xFFDCE3EB), onSurfaceVariant = Color(0xFF52627A),
    outline = Color(0xFFB9C4D2)
)
private val Dark = darkColorScheme(
    primary = Color(0xFFE8EDF4), onPrimary = Ink,
    secondary = Color(0xFFFF6B5E), onSecondary = Color(0xFF3A0905),
    background = Color(0xFF0F1828), onBackground = Color(0xFFE8EDF4),
    surface = Color(0xFF182338), onSurface = Color(0xFFE8EDF4),
    surfaceVariant = Color(0xFF24324B), onSurfaceVariant = Color(0xFFA9B6C9),
    outline = Color(0xFF3A4A66)
)

private val Type = Typography(
    headlineLarge = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 40.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 21.sp, lineHeight = 27.sp)
)

@Composable
fun BarbeariaTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) Dark else Light, typography = Type, content = content)
}
