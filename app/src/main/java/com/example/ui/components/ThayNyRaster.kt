package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Approved raster Character Bible for Thầy Ny.
 * Assets are bundled with the APK so the mascot never depends on the network.
 */
@Composable
fun ThayNyRaster(
  size: Dp = 104.dp,
  isSpeaking: Boolean = false,
  mood: TeacherMood = TeacherMood.HAPPY,
  storytelling: Boolean = false,
  compact: Boolean = false,
  modifier: Modifier = Modifier
) {
  val assetName = when {
    storytelling -> "thay_ny_storytelling.png"
    isSpeaking -> "thay_ny_speaking.png"
    mood == TeacherMood.CELEBRATING -> "thay_ny_celebrating.png"
    mood == TeacherMood.ENCOURAGING -> "thay_ny_encouraging.png"
    mood == TeacherMood.TALKING -> "thay_ny_pointing.png"
    else -> "thay_ny_idle.png"
  }

  val transition = rememberInfiniteTransition(label = "thay_ny_raster_idle")
  val gentleScale by transition.animateFloat(
    initialValue = 0.985f,
    targetValue = if (isSpeaking) 1.035f else 1.01f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (isSpeaking) 420 else 1250, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "thay_ny_raster_scale"
  )

  Box(
    modifier = modifier
      .size(size)
      .clipToBounds(),
    contentAlignment = if (compact) Alignment.TopCenter else Alignment.Center
  ) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data("file:///android_asset/characters/thay_ny/$assetName")
        .crossfade(true)
        .build(),
      contentDescription = "Thầy Ny",
      contentScale = ContentScale.Fit,
      modifier = Modifier
        .size(if (compact) size * 1.5f else size)
        .scale(gentleScale)
    )
  }
}
