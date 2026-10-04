package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

enum class BeGaoMood {
  HAPPY,       // Đứng vui tươi, vẫy tay chào bé
  CHEERING,    // Nhảy cẫng lên ăn mừng, mắt cười tít
  WAVING,      // Vẫy tay gọi bé cùng chơi
  CURIOUS,     // Nghiêng đầu tò mò lắng nghe
  LISTENING    // Chăm chú lắng nghe âm thanh
}

/**
 * Bé Gạo Mascot (Tuân thủ 100% Character Bible & Visual Guidelines):
 * - Bé gái người Việt 3 tuổi, má phúng phính bụ bẫm, mắt to tròn long lanh, tóc bob ngắn chấm cằm có mái ngố.
 * - Đúng 1 chiếc kẹp tóc hoa vàng bên tai.
 * - Váy yếm màu xanh xô thơm (sage-green) với đúng 2 cúc vàng ở quai áo, áo thun tay ngắn màu kem bên trong.
 * - Túi đeo chéo hình ngôi sao vàng mỉm cười.
 * - Tranh vẽ 2D thủ công mộc mạc chuẩn sách tranh mầm non, bundled local raster asset.
 */
@Composable
fun BeGaoMascot(
  size: Dp = 100.dp,
  mood: BeGaoMood = BeGaoMood.HAPPY,
  onTap: (() -> Unit)? = null,
  showNameBadge: Boolean = true,
  modifier: Modifier = Modifier
) {
  val assetName = when (mood) {
    BeGaoMood.CHEERING -> "be_gao_cheering.png"
    BeGaoMood.CURIOUS, BeGaoMood.LISTENING -> "be_gao_listening.png"
    BeGaoMood.WAVING -> "be_gao_waving.png"
    BeGaoMood.HAPPY -> "be_gao_happy.png"
  }

  val transition = rememberInfiniteTransition(label = "be_gao_raster_anim")
  val bounceOffset by transition.animateFloat(
    initialValue = 0f,
    targetValue = if (mood == BeGaoMood.CHEERING) 6f else 2.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (mood == BeGaoMood.CHEERING) 320 else 750, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "be_gao_bounce"
  )

  val gentleScale by transition.animateFloat(
    initialValue = 0.985f,
    targetValue = if (mood == BeGaoMood.CHEERING) 1.04f else 1.015f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (mood == BeGaoMood.CHEERING) 320 else 800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "be_gao_scale"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .width(size)
      .testTag("be_gao_mascot")
  ) {
    Box(
      modifier = Modifier
        .size(size)
        .offset(y = -bounceOffset.dp)
        .clipToBounds()
        .then(
          if (onTap != null) {
            Modifier.clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) { onTap() }
          } else Modifier
        ),
      contentAlignment = Alignment.Center
    ) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data("file:///android_asset/characters/be_gao/$assetName")
          .crossfade(true)
          .build(),
        contentDescription = "Bé Gạo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxSize()
          .scale(gentleScale)
      )
    }

    if (showNameBadge) {
      Spacer(modifier = Modifier.height(2.dp))
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF81C784),
        shadowElevation = 1.5.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White)
      ) {
        Text(
          text = "Bé Gạo 🌸",
          fontWeight = FontWeight.ExtraBold,
          color = Color.White,
          fontSize = (size.value * 0.11f).coerceIn(10f, 13f).sp,
          modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
        )
      }
    }
  }
}
