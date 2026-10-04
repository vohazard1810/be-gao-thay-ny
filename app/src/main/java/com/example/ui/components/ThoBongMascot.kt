package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Thỏ Bông Mascot (Tuân thủ 100% Character Bible & Visual Guidelines):
 * - Bạn thỏ bông trắng nhồi bông đáng yêu với đôi tai dài rủ mềm mại (floppy ears), lòng tai hồng phấn, đuôi tròn xù.
 * - Trang phục vĩnh viễn: Áo gile len sát nách màu tím hoa cà / lavender (lilac/lavender knitted sleeveless sweater vest).
 * - Chiều cao nhỏ hơn Bé Gạo (chạm khoảng ngang hông Bé Gạo).
 * - Nét vẽ 2D tranh truyện mềm mại, biểu cảm dễ thương.
 */
@Composable
fun ThoBongMascot(
  size: Dp = 80.dp,
  isCheering: Boolean = false,
  onTap: (() -> Unit)? = null,
  showNameBadge: Boolean = true,
  modifier: Modifier = Modifier
) {
  val transition = rememberInfiniteTransition(label = "tho_bong_anim")

  val hopOffset by transition.animateFloat(
    initialValue = 0f,
    targetValue = if (isCheering) 8f else 3f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (isCheering) 280 else 600, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "tho_hop"
  )

  val earWiggle by transition.animateFloat(
    initialValue = -4f,
    targetValue = 5f,
    animationSpec = infiniteRepeatable(
      animation = tween(450, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "tho_ear"
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
      Canvas(modifier = Modifier.fillMaxSize()) {
        drawThoBong2D(
          isCheering = isCheering,
          earWiggle = earWiggle
        )
      }
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

private fun DrawScope.drawThoBong2D(
  isCheering: Boolean,
  earWiggle: Float
) {
  val w = size.width
  val h = size.height
  val cx = w / 2f
  val cy = h * 0.52f

  val plushWhite = Color(0xFFFFFFFF)
  val plushShadow = Color(0xFFF0EAE1)
  val pinkInner = Color(0xFFFFB6C1)
  val lavenderVest = Color(0xFFCE93D8)
  val lavenderDark = Color(0xFFAB47BC)
  val outline = Color(0xFF4E342E)
  val blush = Color(0xFFFF8DA1).copy(alpha = 0.55f)

  val headR = w * 0.26f
  val strokeW = w * 0.024f

  // 1. PLUSH BODY & LILAC SWEATER VEST
  val bodyTop = cy + headR * 0.5f
  val bodyBottom = h * 0.90f
  val bodyW = w * 0.44f

  // White plush tummy
  drawOval(
    color = plushWhite,
    topLeft = Offset(cx - bodyW * 0.5f, bodyTop - h * 0.02f),
    size = Size(bodyW, bodyBottom - bodyTop + h * 0.02f)
  )
  drawOval(
    color = outline,
    topLeft = Offset(cx - bodyW * 0.5f, bodyTop - h * 0.02f),
    size = Size(bodyW, bodyBottom - bodyTop + h * 0.02f),
    style = Stroke(strokeW)
  )

  // Lilac / Lavender Knitted Sleeveless Sweater Vest (Áo gile len sát nách màu tím hoa cà)
  val vestPath = Path().apply {
    moveTo(cx - bodyW * 0.45f, bodyTop)
    lineTo(cx + bodyW * 0.45f, bodyTop)
    lineTo(cx + bodyW * 0.48f, bodyBottom - h * 0.03f)
    quadraticTo(cx, bodyBottom, cx - bodyW * 0.48f, bodyBottom - h * 0.03f)
    close()
  }
  drawPath(vestPath, lavenderVest)
  drawPath(vestPath, outline, style = Stroke(strokeW))

  // V-Neck opening on vest
  val vNeck = Path().apply {
    moveTo(cx - bodyW * 0.20f, bodyTop)
    lineTo(cx, bodyTop + h * 0.09f)
    lineTo(cx + bodyW * 0.20f, bodyTop)
    close()
  }
  drawPath(vNeck, plushWhite)
  drawPath(vNeck, outline, style = Stroke(strokeW * 0.9f))

  // Knitted vest subtle rib lines
  drawLine(lavenderDark.copy(alpha = 0.4f), Offset(cx - bodyW * 0.15f, bodyTop + h * 0.12f), Offset(cx - bodyW * 0.15f, bodyBottom - h * 0.04f), strokeW * 0.8f)
  drawLine(lavenderDark.copy(alpha = 0.4f), Offset(cx + bodyW * 0.15f, bodyTop + h * 0.12f), Offset(cx + bodyW * 0.15f, bodyBottom - h * 0.04f), strokeW * 0.8f)

  // Cute plush bunny feet
  drawCircle(plushWhite, radius = w * 0.065f, center = Offset(cx - bodyW * 0.32f, bodyBottom + h * 0.02f))
  drawCircle(outline, radius = w * 0.065f, center = Offset(cx - bodyW * 0.32f, bodyBottom + h * 0.02f), style = Stroke(strokeW))
  drawCircle(plushWhite, radius = w * 0.065f, center = Offset(cx + bodyW * 0.32f, bodyBottom + h * 0.02f))
  drawCircle(outline, radius = w * 0.065f, center = Offset(cx + bodyW * 0.32f, bodyBottom + h * 0.02f), style = Stroke(strokeW))

  // Bunny paws / arms
  if (isCheering) {
    drawCircle(plushWhite, radius = w * 0.05f, center = Offset(cx - bodyW * 0.52f, bodyTop - h * 0.04f))
    drawCircle(outline, radius = w * 0.05f, center = Offset(cx - bodyW * 0.52f, bodyTop - h * 0.04f), style = Stroke(strokeW))
    drawCircle(plushWhite, radius = w * 0.05f, center = Offset(cx + bodyW * 0.52f, bodyTop - h * 0.04f))
    drawCircle(outline, radius = w * 0.05f, center = Offset(cx + bodyW * 0.52f, bodyTop - h * 0.04f), style = Stroke(strokeW))
  } else {
    drawCircle(plushWhite, radius = w * 0.05f, center = Offset(cx - bodyW * 0.42f, bodyTop + h * 0.08f))
    drawCircle(outline, radius = w * 0.05f, center = Offset(cx - bodyW * 0.42f, bodyTop + h * 0.08f), style = Stroke(strokeW))
    drawCircle(plushWhite, radius = w * 0.05f, center = Offset(cx + bodyW * 0.42f, bodyTop + h * 0.08f))
    drawCircle(outline, radius = w * 0.05f, center = Offset(cx + bodyW * 0.42f, bodyTop + h * 0.08f), style = Stroke(strokeW))
  }

  // 2. LONG FLOPPY EARS (Đôi tai dài rủ mềm mại)
  // Left ear (floppy downward curving)
  val earL = Path().apply {
    moveTo(cx - headR * 0.45f, cy - headR * 0.85f)
    cubicTo(
      cx - headR * 1.35f, cy - headR * 1.45f + earWiggle * 2f,
      cx - headR * 1.45f, cy + headR * 0.15f,
      cx - headR * 0.75f, cy + headR * 0.15f
    )
    cubicTo(
      cx - headR * 0.95f, cy - headR * 0.45f,
      cx - headR * 0.70f, cy - headR * 0.80f,
      cx - headR * 0.45f, cy - headR * 0.85f
    )
    close()
  }
  drawPath(earL, plushWhite)
  drawPath(earL, outline, style = Stroke(strokeW))

  // Inner pink left ear
  val innerEarL = Path().apply {
    moveTo(cx - headR * 0.55f, cy - headR * 0.80f)
    cubicTo(
      cx - headR * 1.20f, cy - headR * 1.25f + earWiggle * 2f,
      cx - headR * 1.25f, cy + headR * 0.05f,
      cx - headR * 0.80f, cy + headR * 0.05f
    )
    close()
  }
  drawPath(innerEarL, pinkInner)

  // Right ear (floppy)
  val earR = Path().apply {
    moveTo(cx + headR * 0.45f, cy - headR * 0.85f)
    cubicTo(
      cx + headR * 1.35f, cy - headR * 1.45f - earWiggle * 2f,
      cx + headR * 1.45f, cy + headR * 0.15f,
      cx + headR * 0.75f, cy + headR * 0.15f
    )
    cubicTo(
      cx + headR * 0.95f, cy - headR * 0.45f,
      cx + headR * 0.70f, cy - headR * 0.80f,
      cx + headR * 0.45f, cy - headR * 0.85f
    )
    close()
  }
  drawPath(earR, plushWhite)
  drawPath(earR, outline, style = Stroke(strokeW))

  // Inner pink right ear
  val innerEarR = Path().apply {
    moveTo(cx + headR * 0.55f, cy - headR * 0.80f)
    cubicTo(
      cx + headR * 1.20f, cy - headR * 1.25f - earWiggle * 2f,
      cx + headR * 1.25f, cy + headR * 0.05f,
      cx + headR * 0.80f, cy + headR * 0.05f
    )
    close()
  }
  drawPath(innerEarR, pinkInner)

  // 3. ROUND PLUSH HEAD
  drawCircle(plushWhite, center = Offset(cx, cy), radius = headR)
  drawCircle(outline, center = Offset(cx, cy), radius = headR, style = Stroke(strokeW))

  // 4. BIG INNOCENT BUNNY EYES
  val eyeY = cy - headR * 0.06f
  val eyeR = headR * 0.18f
  drawCircle(outline, radius = eyeR, center = Offset(cx - headR * 0.32f, eyeY))
  drawCircle(Color.White, radius = eyeR * 0.40f, center = Offset(cx - headR * 0.36f, eyeY - eyeR * 0.2f))
  drawCircle(outline, radius = eyeR, center = Offset(cx + headR * 0.32f, eyeY))
  drawCircle(Color.White, radius = eyeR * 0.40f, center = Offset(cx + headR * 0.28f, eyeY - eyeR * 0.2f))

  // 5. CUTE PINK NOSE & WHISKERS
  // Tiny pink heart/triangle nose
  drawCircle(Color(0xFFFF8DA1), radius = w * 0.024f, center = Offset(cx, cy + headR * 0.12f))

  // Rosy cheeks
  drawCircle(blush, radius = headR * 0.22f, center = Offset(cx - headR * 0.48f, cy + headR * 0.16f))
  drawCircle(blush, radius = headR * 0.22f, center = Offset(cx + headR * 0.48f, cy + headR * 0.16f))

  // Whiskers (2 on each cheek)
  drawLine(outline.copy(alpha = 0.7f), Offset(cx - headR * 0.35f, cy + headR * 0.15f), Offset(cx - headR * 0.75f, cy + headR * 0.10f), strokeW * 0.7f)
  drawLine(outline.copy(alpha = 0.7f), Offset(cx - headR * 0.35f, cy + headR * 0.22f), Offset(cx - headR * 0.72f, cy + headR * 0.28f), strokeW * 0.7f)
  drawLine(outline.copy(alpha = 0.7f), Offset(cx + headR * 0.35f, cy + headR * 0.15f), Offset(cx + headR * 0.75f, cy + headR * 0.10f), strokeW * 0.7f)
  drawLine(outline.copy(alpha = 0.7f), Offset(cx + headR * 0.35f, cy + headR * 0.22f), Offset(cx + headR * 0.72f, cy + headR * 0.28f), strokeW * 0.7f)

  // Smile
  drawArc(
    color = outline,
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - headR * 0.16f, cy + headR * 0.16f),
    size = Size(headR * 0.32f, headR * 0.18f),
    style = Stroke(strokeW * 1.1f)
  )
}
