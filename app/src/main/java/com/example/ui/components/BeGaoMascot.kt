package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import kotlin.math.sin

enum class BeGaoMood {
  HAPPY,       // Đứng vui tươi, vẫy tay chào bé
  CHEERING,    // Nhảy cẫng lên ăn mừng, mắt cười tít
  WAVING,      // Vẫy tay gọi bé cùng chơi
  CURIOUS      // Nghiêng đầu tò mò lắng nghe
}

/**
 * Bé Gạo Mascot (Tuân thủ 100% Character Bible & Visual Guidelines):
 * - Bé gái người Việt 3 tuổi, má phúng phính bụ bẫm, mắt to tròn long lanh, tóc bob ngắn chấm cằm có mái ngố.
 * - Đúng 1 chiếc kẹp tóc hoa vàng bên tai.
 * - Váy yếm màu xanh xô thơm (sage-green) với đúng 2 cúc vàng ở quai áo, áo thun tay ngắn màu kem bên trong.
 * - Túi đeo chéo hình ngôi sao vàng mỉm cười.
 * - Nét vẽ 2D sách tranh mầm non hữu cơ ấm áp.
 */
@Composable
fun BeGaoMascot(
  size: Dp = 100.dp,
  mood: BeGaoMood = BeGaoMood.HAPPY,
  onTap: (() -> Unit)? = null,
  showNameBadge: Boolean = true,
  modifier: Modifier = Modifier
) {
  val transition = rememberInfiniteTransition(label = "be_gao_anim")

  val breathingOffset by transition.animateFloat(
    initialValue = 0f,
    targetValue = if (mood == BeGaoMood.CHEERING) 7f else 3f,
    animationSpec = infiniteRepeatable(
      animation = tween(if (mood == BeGaoMood.CHEERING) 320 else 750, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "be_gao_bounce"
  )

  val armSway by transition.animateFloat(
    initialValue = -5f,
    targetValue = 6f,
    animationSpec = infiniteRepeatable(
      animation = tween(420, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "be_gao_arm"
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
        .offset(y = -breathingOffset.dp)
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
        drawBeGao2D(
          mood = mood,
          armSway = armSway
        )
      }
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

private fun DrawScope.drawBeGao2D(
  mood: BeGaoMood,
  armSway: Float
) {
  val w = size.width
  val h = size.height
  val cx = w / 2f
  val cy = h * 0.46f

  // Colors according to AGENTS.md
  val skin = Color(0xFFFEE8D6)
  val blush = Color(0xFFFF8DA1).copy(alpha = 0.55f)
  val darkHair = Color(0xFF2C2421)
  val hairShine = Color(0xFF4A3E39)
  val flowerYellow = Color(0xFFFFD54F)
  val flowerCenter = Color(0xFFFF8F00)
  val sageGreenDress = Color(0xFF7FA988)
  val creamShirt = Color(0xFFFFF8E7)
  val buttonYellow = Color(0xFFFFCA28)
  val outline = Color(0xFF3E2723)
  val starBagYellow = Color(0xFFFFD54F)

  val headR = w * 0.28f
  val strokeW = w * 0.022f

  // 1. CHUBBY TODDLER BODY & DRESS
  val dressTop = cy + headR * 0.55f
  val dressBottom = h * 0.88f
  val dressW = w * 0.44f

  // Cream short-sleeved undershirt visible at shoulders
  drawRoundRect(
    color = creamShirt,
    topLeft = Offset(cx - dressW * 0.58f, dressTop - h * 0.02f),
    size = Size(dressW * 1.16f, h * 0.16f),
    cornerRadius = CornerRadius(12f, 12f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx - dressW * 0.58f, dressTop - h * 0.02f),
    size = Size(dressW * 1.16f, h * 0.16f),
    cornerRadius = CornerRadius(12f, 12f),
    style = Stroke(strokeW)
  )

  // Sage-Green Pinafore Dress (Váy yếm xòe nhẹ)
  val dressPath = Path().apply {
    moveTo(cx - dressW * 0.38f, dressTop)
    lineTo(cx + dressW * 0.38f, dressTop)
    lineTo(cx + dressW * 0.55f, dressBottom)
    quadraticTo(cx, dressBottom + h * 0.025f, cx - dressW * 0.55f, dressBottom)
    close()
  }
  drawPath(dressPath, sageGreenDress)
  drawPath(dressPath, outline, style = Stroke(strokeW))

  // Pinafore Straps with 2 yellow buttons
  // Left strap
  drawRoundRect(
    color = sageGreenDress,
    topLeft = Offset(cx - dressW * 0.34f, dressTop - h * 0.035f),
    size = Size(dressW * 0.18f, h * 0.08f),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawCircle(buttonYellow, radius = w * 0.025f, center = Offset(cx - dressW * 0.25f, dressTop + h * 0.02f))
  drawCircle(Color(0xFF8D6E63), radius = w * 0.008f, center = Offset(cx - dressW * 0.25f, dressTop + h * 0.02f))

  // Right strap
  drawRoundRect(
    color = sageGreenDress,
    topLeft = Offset(cx + dressW * 0.16f, dressTop - h * 0.035f),
    size = Size(dressW * 0.18f, h * 0.08f),
    cornerRadius = CornerRadius(6f, 6f)
  )
  drawCircle(buttonYellow, radius = w * 0.025f, center = Offset(cx + dressW * 0.25f, dressTop + h * 0.02f))
  drawCircle(Color(0xFF8D6E63), radius = w * 0.008f, center = Offset(cx + dressW * 0.25f, dressTop + h * 0.02f))

  // Yellow Smiling Star Crossbody Bag (Túi đeo chéo ngôi sao cười)
  val bagCenter = Offset(cx + dressW * 0.42f, dressBottom - h * 0.06f)
  // Crossbody bag strap
  drawLine(
    color = Color(0xFFFFB300),
    start = Offset(cx - dressW * 0.28f, dressTop - h * 0.02f),
    end = bagCenter,
    strokeWidth = strokeW * 1.2f
  )
  // Star bag body
  drawCircle(starBagYellow, radius = w * 0.075f, center = bagCenter)
  drawCircle(outline, radius = w * 0.075f, center = bagCenter, style = Stroke(strokeW * 0.9f))
  // Star smiling face
  drawCircle(Color.Black, radius = w * 0.012f, center = Offset(bagCenter.x - w * 0.025f, bagCenter.y - h * 0.01f))
  drawCircle(Color.Black, radius = w * 0.012f, center = Offset(bagCenter.x + w * 0.025f, bagCenter.y - h * 0.01f))
  drawArc(
    color = Color(0xFFD84315),
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(bagCenter.x - w * 0.025f, bagCenter.y - h * 0.005f),
    size = Size(w * 0.05f, h * 0.025f),
    style = Stroke(strokeW * 0.8f)
  )

  // Chubby toddler legs & Cream walking shoes
  // Left foot
  drawRoundRect(
    color = Color(0xFFFDD835), // Mustard yellow ankle socks
    topLeft = Offset(cx - dressW * 0.32f, dressBottom),
    size = Size(dressW * 0.22f, h * 0.05f),
    cornerRadius = CornerRadius(4f, 4f)
  )
  drawCircle(Color(0xFFFFF9C4), radius = w * 0.045f, center = Offset(cx - dressW * 0.21f, dressBottom + h * 0.05f)) // shoe
  drawCircle(outline, radius = w * 0.045f, center = Offset(cx - dressW * 0.21f, dressBottom + h * 0.05f), style = Stroke(strokeW * 0.8f))

  // Right foot
  drawRoundRect(
    color = Color(0xFFFDD835),
    topLeft = Offset(cx + dressW * 0.10f, dressBottom),
    size = Size(dressW * 0.22f, h * 0.05f),
    cornerRadius = CornerRadius(4f, 4f)
  )
  drawCircle(Color(0xFFFFF9C4), radius = w * 0.045f, center = Offset(cx + dressW * 0.21f, dressBottom + h * 0.05f))
  drawCircle(outline, radius = w * 0.045f, center = Offset(cx + dressW * 0.21f, dressBottom + h * 0.05f), style = Stroke(strokeW * 0.8f))

  // 2. TODDLER ARMS
  if (mood == BeGaoMood.CHEERING) {
    // Both arms up cheering!
    drawCircle(skin, radius = w * 0.045f, center = Offset(cx - dressW * 0.55f, dressTop - h * 0.06f))
    drawCircle(outline, radius = w * 0.045f, center = Offset(cx - dressW * 0.55f, dressTop - h * 0.06f), style = Stroke(strokeW * 0.8f))
    drawCircle(skin, radius = w * 0.045f, center = Offset(cx + dressW * 0.55f, dressTop - h * 0.06f))
    drawCircle(outline, radius = w * 0.045f, center = Offset(cx + dressW * 0.55f, dressTop - h * 0.06f), style = Stroke(strokeW * 0.8f))
  } else {
    // Left arm gently waving
    val armHandX = cx - dressW * 0.55f
    val armHandY = dressTop + h * 0.05f + armSway * 0.5f
    drawCircle(skin, radius = w * 0.045f, center = Offset(armHandX, armHandY))
    drawCircle(outline, radius = w * 0.045f, center = Offset(armHandX, armHandY), style = Stroke(strokeW * 0.8f))

    // Right arm relaxing by side
    drawCircle(skin, radius = w * 0.045f, center = Offset(cx + dressW * 0.52f, dressTop + h * 0.08f))
    drawCircle(outline, radius = w * 0.045f, center = Offset(cx + dressW * 0.52f, dressTop + h * 0.08f), style = Stroke(strokeW * 0.8f))
  }

  // 3. ROUND CHUBBY TODDLER HEAD & CHEEKS
  drawCircle(skin, center = Offset(cx, cy), radius = headR)
  drawCircle(outline, center = Offset(cx, cy), radius = headR, style = Stroke(strokeW))

  // 4. CHIN-LENGTH BOB HAIRCUT WITH BANGS (Tóc bob chấm cằm & mái ngố)
  val hairBack = Path().apply {
    moveTo(cx - headR * 1.05f, cy + headR * 0.45f)
    cubicTo(cx - headR * 1.25f, cy - headR * 1.15f, cx + headR * 1.25f, cy - headR * 1.15f, cx + headR * 1.05f, cy + headR * 0.45f)
    quadraticTo(cx + headR * 0.85f, cy + headR * 0.75f, cx + headR * 0.65f, cy + headR * 0.55f)
    lineTo(cx - headR * 0.65f, cy + headR * 0.55f)
    quadraticTo(cx - headR * 0.85f, cy + headR * 0.75f, cx - headR * 1.05f, cy + headR * 0.45f)
    close()
  }
  drawPath(hairBack, darkHair)
  drawPath(hairBack, outline, style = Stroke(strokeW))

  // Front Straight Bangs (Mái ngố tròn trên trán)
  val bangs = Path().apply {
    moveTo(cx - headR * 0.85f, cy - headR * 0.35f)
    cubicTo(cx - headR * 0.45f, cy - headR * 0.15f, cx + headR * 0.45f, cy - headR * 0.15f, cx + headR * 0.85f, cy - headR * 0.35f)
    cubicTo(cx + headR * 0.95f, cy - headR * 0.95f, cx - headR * 0.95f, cy - headR * 0.95f, cx - headR * 0.85f, cy - headR * 0.35f)
    close()
  }
  drawPath(bangs, darkHair)
  drawPath(bangs, outline, style = Stroke(strokeW))

  // Hair shine highlight
  drawArc(
    color = hairShine,
    startAngle = 200f,
    sweepAngle = 140f,
    useCenter = false,
    topLeft = Offset(cx - headR * 0.65f, cy - headR * 0.95f),
    size = Size(headR * 1.3f, headR * 0.45f),
    style = Stroke(strokeW * 1.3f)
  )

  // 5. EXACTLY ONE YELLOW FLOWER HAIR CLIP ON THE SIDE
  val flowerX = cx + headR * 0.65f
  val flowerY = cy - headR * 0.45f
  val petalR = w * 0.026f
  // 5 flower petals
  for (i in 0 until 5) {
    val angle = i * (2 * kotlin.math.PI / 5)
    val px = flowerX + (petalR * 1.3f * kotlin.math.cos(angle)).toFloat()
    val py = flowerY + (petalR * 1.3f * kotlin.math.sin(angle)).toFloat()
    drawCircle(flowerYellow, radius = petalR, center = Offset(px, py))
  }
  // Flower center dot
  drawCircle(flowerCenter, radius = petalR * 0.85f, center = Offset(flowerX, flowerY))

  // 6. BIG EXPRESSIVE TODDLER EYES WITH DOUBLE SPARKLES
  val eyeY = cy - headR * 0.02f
  val eyeR = headR * 0.19f

  if (mood == BeGaoMood.CHEERING) {
    // Laughing crescent eyes (^_^)
    drawArc(
      color = outline,
      startAngle = 190f,
      sweepAngle = 160f,
      useCenter = false,
      topLeft = Offset(cx - headR * 0.46f, eyeY - headR * 0.08f),
      size = Size(headR * 0.35f, headR * 0.22f),
      style = Stroke(strokeW * 1.5f)
    )
    drawArc(
      color = outline,
      startAngle = 190f,
      sweepAngle = 160f,
      useCenter = false,
      topLeft = Offset(cx + headR * 0.11f, eyeY - headR * 0.08f),
      size = Size(headR * 0.35f, headR * 0.22f),
      style = Stroke(strokeW * 1.5f)
    )
  } else {
    // Left eye
    drawCircle(outline, radius = eyeR, center = Offset(cx - headR * 0.28f, eyeY))
    drawCircle(Color.White, radius = eyeR * 0.42f, center = Offset(cx - headR * 0.32f, eyeY - eyeR * 0.25f))
    drawCircle(Color.White, radius = eyeR * 0.20f, center = Offset(cx - headR * 0.24f, eyeY + eyeR * 0.25f))

    // Right eye
    drawCircle(outline, radius = eyeR, center = Offset(cx + headR * 0.28f, eyeY))
    drawCircle(Color.White, radius = eyeR * 0.42f, center = Offset(cx + headR * 0.24f, eyeY - eyeR * 0.25f))
    drawCircle(Color.White, radius = eyeR * 0.20f, center = Offset(cx + headR * 0.32f, eyeY + eyeR * 0.25f))
  }

  // 7. CUTE BUTTON NOSE & ROSY TODDLER CHEEKS
  // Button nose
  drawCircle(Color(0xFFE59866), radius = w * 0.018f, center = Offset(cx, cy + headR * 0.14f))

  // Rosy cheeks
  drawCircle(blush, radius = headR * 0.26f, center = Offset(cx - headR * 0.45f, cy + headR * 0.16f))
  drawCircle(blush, radius = headR * 0.26f, center = Offset(cx + headR * 0.45f, cy + headR * 0.16f))

  // 8. CUTE HAPPY SMILE
  drawArc(
    color = Color(0xFFD81B60),
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - headR * 0.20f, cy + headR * 0.22f),
    size = Size(headR * 0.40f, headR * 0.24f),
    style = Stroke(strokeW * 1.3f)
  )
}
