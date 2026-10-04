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

/**
 * Thỏ Bông Mascot (Tuân thủ 100% Character Bible & Visual Guidelines):
 * - Bạn thỏ bông trắng nhồi bông đáng yêu với đôi tai dài rủ mềm mại (floppy ears), lòng tai hồng phấn, đuôi tròn xù.
 * - Trang phục vĩnh viễn: Áo gile len sát nách màu tím hoa cà / lavender (lilac/lavender knitted sleeveless sweater vest).
 * - Chiều cao nhỏ hơn Bé Gạo (chạm khoảng ngang hông Bé Gạo).
 * - Tranh vẽ 2D thủ công ấm áp chuẩn sách tranh mầm non, bundled local raster asset.
 */
@Composable
fun ThoBongMascot(
  size: Dp = 80.dp,
  isCheering: Boolean = false,
  onTap: (() -> Unit)? = null,
  showNameBadge: Boolean = true,
  modifier: Modifier = Modifier
) {
  val assetName = if (isCheering) "tho_bong_cheering.png" else "tho_bong_happy.png"

  val transition = rememberInfiniteTransition(label = "tho_bong_raster_anim")
  val hopOffset by transition.animateFloat(
    initialValue = 0f,
    targetValue = if (isCheering) 7f else 3f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (isCheering) 280 else 600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "tho_hop"
  )

  val gentleScale by transition.animateFloat(
    initialValue = 0.98f,
    targetValue = if (isCheering) 1.05f else 1.02f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (isCheering) 280 else 650, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "tho_scale"
  )

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .width(size)
      .testTag("tho_bong_mascot")
  ) {
    Box(
      modifier = Modifier
        .size(size)
        .offset(y = -hopOffset.dp)
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
          .data("file:///android_asset/characters/tho_bong/$assetName")
          .crossfade(true)
          .build(),
        contentDescription = "Thỏ Bông",
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
        color = Color(0xFFBA68C8),
        shadowElevation = 1.5.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White)
      ) {
        Text(
          text = "Thỏ Bông 🐰",
          fontWeight = FontWeight.ExtraBold,
          color = Color.White,
          fontSize = (size.value * 0.12f).coerceIn(9f, 12f).sp,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
      }
    }
  }
}
