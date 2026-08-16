package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 2D Preschool Cartoon Flashcard Illustration Engine
 * Renders 100% accurate, adorable, soft pastel 2D vectors for every learning item.
 */
@Composable
fun CartoonFlashcardArt(
  itemId: String,
  modifier: Modifier = Modifier,
  isAnimated: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "flashcard_art_anim")
  val bounce by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = if (isAnimated) 6f else 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "art_bounce"
  )
  val wingWiggle by infiniteTransition.animateFloat(
    initialValue = -0.08f,
    targetValue = if (isAnimated) 0.08f else 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "wing_wiggle"
  )

  val textMeasurer = rememberTextMeasurer()

  Canvas(modifier = modifier.fillMaxSize()) {
    val w = size.width
    val h = size.height
    val minDim = minOf(w, h)
    val cx = w / 2f
    val cy = h / 2f + bounce

    when {
      // 1. FARM ANIMALS
      itemId == "farm_chicken" -> drawChicken2D(cx, cy, minDim)
      itemId == "farm_duck" -> drawDuck2D(cx, cy, minDim)
      itemId == "farm_pig" -> drawPig2D(cx, cy, minDim)
      itemId == "farm_cow" -> drawCow2D(cx, cy, minDim)
      itemId == "farm_goat" -> drawGoat2D(cx, cy, minDim)
      itemId == "farm_horse" -> drawHorse2D(cx, cy, minDim)
      itemId == "farm_sheep" -> drawSheep2D(cx, cy, minDim)
      itemId == "farm_dog" -> drawDog2D(cx, cy, minDim)
      itemId == "farm_cat" -> drawCat2D(cx, cy, minDim)
      itemId == "farm_rabbit" -> drawRabbit2D(cx, cy, minDim)
      itemId == "farm_chick" -> drawChick2D(cx, cy, minDim)
      itemId == "farm_buffalo" -> drawBuffalo2D(cx, cy, minDim)

      // 2. WILD ANIMALS
      itemId == "wild_lion" -> drawLion2D(cx, cy, minDim)
      itemId == "wild_elephant" -> drawElephant2D(cx, cy, minDim)
      itemId == "wild_tiger" -> drawTiger2D(cx, cy, minDim)
      itemId == "wild_giraffe" -> drawGiraffe2D(cx, cy, minDim)
      itemId == "wild_monkey" -> drawMonkey2D(cx, cy, minDim)
      itemId == "wild_bear" -> drawBear2D(cx, cy, minDim)
      itemId == "wild_zebra" -> drawZebra2D(cx, cy, minDim)
      itemId == "wild_panda" -> drawPanda2D(cx, cy, minDim)
      itemId == "wild_fox" -> drawFox2D(cx, cy, minDim)
      itemId == "wild_squirrel" -> drawSquirrel2D(cx, cy, minDim)

      // 3. WATER ANIMALS
      itemId == "water_dolphin" -> drawDolphin2D(cx, cy, minDim)
      itemId == "water_whale" -> drawWhale2D(cx, cy, minDim)
      itemId == "water_crab" -> drawCrab2D(cx, cy, minDim)
      itemId == "water_turtle" -> drawTurtle2D(cx, cy, minDim)
      itemId == "water_octopus" -> drawOctopus2D(cx, cy, minDim)
      itemId == "water_starfish" -> drawStarfish2D(cx, cy, minDim)
      itemId == "water_clownfish" -> drawFish2D(cx, cy, minDim)
      itemId == "water_penguin" -> drawPenguin2D(cx, cy, minDim)

      // 4. INSECTS & BIRDS
      itemId == "insect_butterfly" -> drawButterfly2D(cx, cy, minDim, wingWiggle)
      itemId == "insect_bee" -> drawBee2D(cx, cy, minDim, wingWiggle)
      itemId == "insect_ladybug" -> drawLadybug2D(cx, cy, minDim)
      itemId == "insect_dragonfly" -> drawDragonfly2D(cx, cy, minDim, wingWiggle)
      itemId == "insect_parrot" -> drawParrot2D(cx, cy, minDim)
      itemId == "insect_dove" -> drawDove2D(cx, cy, minDim)

      // 5. FRUITS
      itemId == "fruit_apple" -> drawApple2D(cx, cy, minDim)
      itemId == "fruit_banana" -> drawBanana2D(cx, cy, minDim)
      itemId == "fruit_orange" -> drawOrange2D(cx, cy, minDim)
      itemId == "fruit_watermelon" -> drawWatermelon2D(cx, cy, minDim)
      itemId == "fruit_mango" -> drawMango2D(cx, cy, minDim)
      itemId == "fruit_strawberry" -> drawStrawberry2D(cx, cy, minDim)
      itemId == "fruit_grapes" -> drawGrapes2D(cx, cy, minDim)

      // 6. COLORS
      itemId == "col_red" -> drawColorBlob2D(cx, cy, minDim, Color(0xFFE53935), "Đỏ")
      itemId == "col_yellow" -> drawColorBlob2D(cx, cy, minDim, Color(0xFFFFD600), "Vàng")
      itemId == "col_green" -> drawColorBlob2D(cx, cy, minDim, Color(0xFF43A047), "Xanh Lá")
      itemId == "col_blue" -> drawColorBlob2D(cx, cy, minDim, Color(0xFF1E88E5), "Xanh Dương")

      // 7. NUMBERS
      itemId.startsWith("num_") -> {
        val numStr = itemId.removePrefix("num_")
        val numVal = numStr.toIntOrNull() ?: 1
        drawNumberCard2D(cx, cy, minDim, numStr, numVal, textMeasurer)
      }

      // 8. ALPHABET
      itemId.startsWith("alpha_") -> {
        val letter = when(itemId) {
          "alpha_a" -> "A"
          "alpha_aw" -> "Ă"
          "alpha_aa" -> "Â"
          "alpha_b" -> "B"
          "alpha_c" -> "C"
          "alpha_d" -> "D"
          "alpha_dd" -> "Đ"
          "alpha_e" -> "E"
          "alpha_ee" -> "Ê"
          "alpha_g" -> "G"
          "alpha_h" -> "H"
          "alpha_i" -> "I"
          "alpha_k" -> "K"
          "alpha_l" -> "L"
          "alpha_m" -> "M"
          "alpha_n" -> "N"
          "alpha_o" -> "O"
          "alpha_oo" -> "Ô"
          "alpha_ow" -> "Ơ"
          "alpha_p" -> "P"
          "alpha_q" -> "Q"
          "alpha_r" -> "R"
          "alpha_s" -> "S"
          "alpha_t" -> "T"
          "alpha_u" -> "U"
          "alpha_uw" -> "Ư"
          "alpha_v" -> "V"
          "alpha_x" -> "X"
          "alpha_y" -> "Y"
          else -> "A"
        }
        drawLetterCard2D(cx, cy, minDim, letter, textMeasurer)
      }

      else -> drawGenericCuteCard(cx, cy, minDim)
    }
  }
}

// ==========================================
// 2D DRAWING IMPLEMENTATIONS FOR PRESCHOOL
// ==========================================

private fun DrawScope.drawButterfly2D(cx: Float, cy: Float, size: Float, wingWiggle: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Glow halo
  drawCircle(Color(0xFFF3E5F5), center = Offset(cx, cy), radius = r * 1.15f)

  // Wings Left
  val leftWingUpper = Path().apply {
    moveTo(cx - r * 0.1f, cy - r * 0.1f)
    cubicTo(cx - r * 1.1f, cy - r * 0.9f - wingWiggle * 50f, cx - r * 1.2f, cy + r * 0.1f, cx - r * 0.1f, cy + r * 0.2f)
    close()
  }
  drawPath(leftWingUpper, Color(0xFFCE93D8))
  drawPath(leftWingUpper, outline, style = Stroke(strokeW))
  drawCircle(Color(0xFFFFD54F), center = Offset(cx - r * 0.6f, cy - r * 0.35f), radius = r * 0.22f)

  val leftWingLower = Path().apply {
    moveTo(cx - r * 0.1f, cy + r * 0.1f)
    cubicTo(cx - r * 0.9f, cy + r * 0.4f, cx - r * 0.7f, cy + r * 0.9f, cx - r * 0.1f, cy + r * 0.45f)
    close()
  }
  drawPath(leftWingLower, Color(0xFF81D4FA))
  drawPath(leftWingLower, outline, style = Stroke(strokeW))
  drawCircle(Color(0xFFF48FB1), center = Offset(cx - r * 0.45f, cy + r * 0.5f), radius = r * 0.14f)

  // Wings Right
  val rightWingUpper = Path().apply {
    moveTo(cx + r * 0.1f, cy - r * 0.1f)
    cubicTo(cx + r * 1.1f, cy - r * 0.9f + wingWiggle * 50f, cx + r * 1.2f, cy + r * 0.1f, cx + r * 0.1f, cy + r * 0.2f)
    close()
  }
  drawPath(rightWingUpper, Color(0xFFCE93D8))
  drawPath(rightWingUpper, outline, style = Stroke(strokeW))
  drawCircle(Color(0xFFFFD54F), center = Offset(cx + r * 0.6f, cy - r * 0.35f), radius = r * 0.22f)

  val rightWingLower = Path().apply {
    moveTo(cx + r * 0.1f, cy + r * 0.1f)
    cubicTo(cx + r * 0.9f, cy + r * 0.4f, cx + r * 0.7f, cy + r * 0.9f, cx + r * 0.1f, cy + r * 0.45f)
    close()
  }
  drawPath(rightWingLower, Color(0xFF81D4FA))
  drawPath(rightWingLower, outline, style = Stroke(strokeW))
  drawCircle(Color(0xFFF48FB1), center = Offset(cx + r * 0.45f, cy + r * 0.5f), radius = r * 0.14f)

  // Body
  drawRoundRect(
    color = Color(0xFF5D4037),
    topLeft = Offset(cx - r * 0.14f, cy - r * 0.45f),
    size = Size(r * 0.28f, r * 0.95f),
    cornerRadius = CornerRadius(r * 0.14f, r * 0.14f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx - r * 0.14f, cy - r * 0.45f),
    size = Size(r * 0.28f, r * 0.95f),
    cornerRadius = CornerRadius(r * 0.14f, r * 0.14f),
    style = Stroke(strokeW)
  )

  // Head & Eyes
  drawCircle(Color(0xFF5D4037), center = Offset(cx, cy - r * 0.55f), radius = r * 0.2f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.55f), radius = r * 0.2f, style = Stroke(strokeW))
  drawCircle(Color.White, center = Offset(cx - r * 0.08f, cy - r * 0.58f), radius = r * 0.06f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.08f, cy - r * 0.58f), radius = r * 0.035f)
  drawCircle(Color.White, center = Offset(cx + r * 0.08f, cy - r * 0.58f), radius = r * 0.06f)
  drawCircle(Color.Black, center = Offset(cx + r * 0.08f, cy - r * 0.58f), radius = r * 0.035f)

  // Smile
  drawArc(
    color = Color(0xFFFF8DA1),
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - r * 0.08f, cy - r * 0.52f),
    size = Size(r * 0.16f, r * 0.1f),
    style = Stroke(strokeW * 0.8f)
  )

  // Antennae
  drawLine(outline, Offset(cx - r * 0.05f, cy - r * 0.65f), Offset(cx - r * 0.25f, cy - r * 0.95f), strokeW)
  drawCircle(Color(0xFFFFB300), center = Offset(cx - r * 0.25f, cy - r * 0.95f), radius = r * 0.07f)
  drawLine(outline, Offset(cx + r * 0.05f, cy - r * 0.65f), Offset(cx + r * 0.25f, cy - r * 0.95f), strokeW)
  drawCircle(Color(0xFFFFB300), center = Offset(cx + r * 0.25f, cy - r * 0.95f), radius = r * 0.07f)
}

private fun DrawScope.drawBee2D(cx: Float, cy: Float, size: Float, wingWiggle: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Glow
  drawCircle(Color(0xFFFFFDE7), center = Offset(cx, cy), radius = r * 1.15f)

  // Wings (Top)
  val wingL = Path().apply {
    moveTo(cx - r * 0.2f, cy - r * 0.3f)
    cubicTo(cx - r * 0.7f, cy - r * 0.9f - wingWiggle * 40f, cx - r * 0.1f, cy - r * 0.9f, cx - r * 0.05f, cy - r * 0.3f)
    close()
  }
  drawPath(wingL, Color(0xFFE1F5FE).copy(alpha = 0.85f))
  drawPath(wingL, outline, style = Stroke(strokeW))

  val wingR = Path().apply {
    moveTo(cx + r * 0.05f, cy - r * 0.3f)
    cubicTo(cx + r * 0.1f, cy - r * 0.9f + wingWiggle * 40f, cx + r * 0.7f, cy - r * 0.9f, cx + r * 0.2f, cy - r * 0.3f)
    close()
  }
  drawPath(wingR, Color(0xFFE1F5FE).copy(alpha = 0.85f))
  drawPath(wingR, outline, style = Stroke(strokeW))

  // Bee Body (Oval Yellow)
  drawOval(
    color = Color(0xFFFFD54F),
    topLeft = Offset(cx - r * 0.75f, cy - r * 0.45f),
    size = Size(r * 1.5f, r * 0.9f)
  )

  // Stripes (Black)
  drawRoundRect(
    color = Color(0xFF263238),
    topLeft = Offset(cx - r * 0.2f, cy - r * 0.45f),
    size = Size(r * 0.22f, r * 0.9f),
    cornerRadius = CornerRadius(r * 0.05f, r * 0.05f)
  )
  drawRoundRect(
    color = Color(0xFF263238),
    topLeft = Offset(cx + r * 0.15f, cy - r * 0.43f),
    size = Size(r * 0.22f, r * 0.86f),
    cornerRadius = CornerRadius(r * 0.05f, r * 0.05f)
  )

  // Outline
  drawOval(
    color = outline,
    topLeft = Offset(cx - r * 0.75f, cy - r * 0.45f),
    size = Size(r * 1.5f, r * 0.9f),
    style = Stroke(strokeW)
  )

  // Stinger (Right)
  val stinger = Path().apply {
    moveTo(cx + r * 0.75f, cy - r * 0.08f)
    lineTo(cx + r * 0.95f, cy)
    lineTo(cx + r * 0.75f, cy + r * 0.08f)
    close()
  }
  drawPath(stinger, Color(0xFF263238))

  // Face (Left Side)
  drawCircle(Color.White, center = Offset(cx - r * 0.42f, cy - r * 0.15f), radius = r * 0.11f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.45f, cy - r * 0.15f), radius = r * 0.06f)
  drawCircle(Color.White, center = Offset(cx - r * 0.48f, cy - r * 0.18f), radius = r * 0.03f)

  // Rosy Cheek
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx - r * 0.35f, cy + r * 0.1f), radius = r * 0.1f)

  // Smile
  drawArc(
    color = outline,
    startAngle = 20f,
    sweepAngle = 140f,
    useCenter = false,
    topLeft = Offset(cx - r * 0.58f, cy - r * 0.02f),
    size = Size(r * 0.22f, r * 0.18f),
    style = Stroke(strokeW)
  )

  // Antennae
  drawLine(outline, Offset(cx - r * 0.45f, cy - r * 0.4f), Offset(cx - r * 0.6f, cy - r * 0.7f), strokeW)
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.6f, cy - r * 0.7f), radius = r * 0.06f)
}

private fun DrawScope.drawLadybug2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Green Leaf Background
  val leaf = Path().apply {
    moveTo(cx - r * 1.1f, cy + r * 0.7f)
    cubicTo(cx - r * 1.2f, cy - r * 0.5f, cx + r * 0.5f, cy - r * 1.1f, cx + r * 1.2f, cy - r * 0.6f)
    cubicTo(cx + r * 1.2f, cy + r * 0.5f, cx - r * 0.2f, cy + r * 1.1f, cx - r * 1.1f, cy + r * 0.7f)
    close()
  }
  drawPath(leaf, Color(0xFFA5D6A7))
  drawPath(leaf, Color(0xFF81C784), style = Stroke(strokeW))

  // Ladybug Red Shell
  drawCircle(Color(0xFFE53935), center = Offset(cx, cy + r * 0.05f), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy + r * 0.05f), radius = r * 0.65f, style = Stroke(strokeW))

  // Black Head
  drawCircle(Color(0xFF263238), center = Offset(cx, cy - r * 0.52f), radius = r * 0.3f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.52f), radius = r * 0.3f, style = Stroke(strokeW))

  // Eyes
  drawCircle(Color.White, center = Offset(cx - r * 0.12f, cy - r * 0.6f), radius = r * 0.08f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.12f, cy - r * 0.6f), radius = r * 0.045f)
  drawCircle(Color.White, center = Offset(cx + r * 0.12f, cy - r * 0.6f), radius = r * 0.08f)
  drawCircle(Color.Black, center = Offset(cx + r * 0.12f, cy - r * 0.6f), radius = r * 0.045f)

  // Center divider line
  drawLine(Color(0xFF263238), Offset(cx, cy - r * 0.25f), Offset(cx, cy + r * 0.7f), strokeW * 1.4f)

  // Black Polka Dots
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.32f, cy - r * 0.05f), radius = r * 0.12f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.32f, cy - r * 0.05f), radius = r * 0.12f)
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.35f, cy + r * 0.35f), radius = r * 0.13f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.35f, cy + r * 0.35f), radius = r * 0.13f)

  // Antennae
  drawLine(outline, Offset(cx - r * 0.1f, cy - r * 0.75f), Offset(cx - r * 0.25f, cy - r * 0.95f), strokeW)
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.25f, cy - r * 0.95f), radius = r * 0.05f)
  drawLine(outline, Offset(cx + r * 0.1f, cy - r * 0.75f), Offset(cx + r * 0.25f, cy - r * 0.95f), strokeW)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.25f, cy - r * 0.95f), radius = r * 0.05f)
}

private fun DrawScope.drawDragonfly2D(cx: Float, cy: Float, size: Float, wingWiggle: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // 4 Wings (Cyan Translucent)
  val wingTL = Path().apply {
    moveTo(cx - r * 0.1f, cy - r * 0.15f)
    cubicTo(cx - r * 1.1f, cy - r * 0.6f - wingWiggle * 40f, cx - r * 0.9f, cy - r * 0.1f, cx - r * 0.1f, cy - r * 0.05f)
    close()
  }
  drawPath(wingTL, Color(0xFF80DEEA).copy(alpha = 0.8f))
  drawPath(wingTL, outline, style = Stroke(strokeW))

  val wingBL = Path().apply {
    moveTo(cx - r * 0.1f, cy + r * 0.05f)
    cubicTo(cx - r * 0.9f, cy + r * 0.4f, cx - r * 0.7f, cy + r * 0.7f, cx - r * 0.05f, cy + r * 0.2f)
    close()
  }
  drawPath(wingBL, Color(0xFF80DEEA).copy(alpha = 0.8f))
  drawPath(wingBL, outline, style = Stroke(strokeW))

  val wingTR = Path().apply {
    moveTo(cx + r * 0.1f, cy - r * 0.15f)
    cubicTo(cx + r * 1.1f, cy - r * 0.6f + wingWiggle * 40f, cx + r * 0.9f, cy - r * 0.1f, cx + r * 0.1f, cy - r * 0.05f)
    close()
  }
  drawPath(wingTR, Color(0xFF80DEEA).copy(alpha = 0.8f))
  drawPath(wingTR, outline, style = Stroke(strokeW))

  val wingBR = Path().apply {
    moveTo(cx + r * 0.1f, cy + r * 0.05f)
    cubicTo(cx + r * 0.9f, cy + r * 0.4f, cx + r * 0.7f, cy + r * 0.7f, cx + r * 0.05f, cy + r * 0.2f)
    close()
  }
  drawPath(wingBR, Color(0xFF80DEEA).copy(alpha = 0.8f))
  drawPath(wingBR, outline, style = Stroke(strokeW))

  // Long Body (Red Dragonfly / Chuồn chuồn ớt)
  drawRoundRect(
    color = Color(0xFFFF5252),
    topLeft = Offset(cx - r * 0.1f, cy - r * 0.2f),
    size = Size(r * 0.2f, r * 1.1f),
    cornerRadius = CornerRadius(r * 0.1f, r * 0.1f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx - r * 0.1f, cy - r * 0.2f),
    size = Size(r * 0.2f, r * 1.1f),
    cornerRadius = CornerRadius(r * 0.1f, r * 0.1f),
    style = Stroke(strokeW)
  )

  // Head with big cute eyes
  drawCircle(Color(0xFFFF5252), center = Offset(cx, cy - r * 0.35f), radius = r * 0.2f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.35f), radius = r * 0.2f, style = Stroke(strokeW))
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.12f, cy - r * 0.38f), radius = r * 0.09f)
  drawCircle(Color.White, center = Offset(cx - r * 0.14f, cy - r * 0.4f), radius = r * 0.04f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.12f, cy - r * 0.38f), radius = r * 0.09f)
  drawCircle(Color.White, center = Offset(cx + r * 0.10f, cy - r * 0.4f), radius = r * 0.04f)
}

private fun DrawScope.drawStarfish2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.42f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Sandy Beach Background Glow
  drawCircle(Color(0xFFFFF8E1), center = Offset(cx, cy), radius = r * 1.15f)

  // 5-Pointed Star Path
  val starPath = Path()
  val numPoints = 5
  val outerR = r * 0.95f
  val innerR = r * 0.45f
  for (i in 0 until numPoints * 2) {
    val angle = (i * PI / numPoints - PI / 2).toFloat()
    val rad = if (i % 2 == 0) outerR else innerR
    val x = cx + rad * cos(angle)
    val y = cy + rad * sin(angle)
    if (i == 0) starPath.moveTo(x, y) else starPath.lineTo(x, y)
  }
  starPath.close()

  drawPath(starPath, Color(0xFFFF8A65))
  drawPath(starPath, outline, style = Stroke(strokeW))

  // Texture dots
  drawCircle(Color(0xFFFFCCBC), center = Offset(cx, cy - r * 0.5f), radius = r * 0.08f)
  drawCircle(Color(0xFFFFCCBC), center = Offset(cx - r * 0.45f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFFFFCCBC), center = Offset(cx + r * 0.45f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFFFFCCBC), center = Offset(cx - r * 0.3f, cy + r * 0.45f), radius = r * 0.08f)
  drawCircle(Color(0xFFFFCCBC), center = Offset(cx + r * 0.3f, cy + r * 0.45f), radius = r * 0.08f)

  // Cute Face
  drawCircle(Color.White, center = Offset(cx - r * 0.12f, cy - r * 0.05f), radius = r * 0.09f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.12f, cy - r * 0.05f), radius = r * 0.05f)
  drawCircle(Color.White, center = Offset(cx + r * 0.12f, cy - r * 0.05f), radius = r * 0.09f)
  drawCircle(Color.Black, center = Offset(cx + r * 0.12f, cy - r * 0.05f), radius = r * 0.05f)

  // Rosy cheeks & Smile
  drawCircle(Color(0xFFFF4081), center = Offset(cx - r * 0.22f, cy + r * 0.08f), radius = r * 0.07f)
  drawCircle(Color(0xFFFF4081), center = Offset(cx + r * 0.22f, cy + r * 0.08f), radius = r * 0.07f)
  drawArc(
    color = outline,
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - r * 0.12f, cy + r * 0.04f),
    size = Size(r * 0.24f, r * 0.15f),
    style = Stroke(strokeW)
  )
}

private fun DrawScope.drawCrab2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Big Claws Left & Right
  drawCircle(Color(0xFFFF5722), center = Offset(cx - r * 0.75f, cy - r * 0.45f), radius = r * 0.28f)
  drawCircle(outline, center = Offset(cx - r * 0.75f, cy - r * 0.45f), radius = r * 0.28f, style = Stroke(strokeW))
  drawLine(Color(0xFFFF5722), Offset(cx - r * 0.45f, cy - r * 0.1f), Offset(cx - r * 0.75f, cy - r * 0.45f), strokeW * 2f)

  drawCircle(Color(0xFFFF5722), center = Offset(cx + r * 0.75f, cy - r * 0.45f), radius = r * 0.28f)
  drawCircle(outline, center = Offset(cx + r * 0.75f, cy - r * 0.45f), radius = r * 0.28f, style = Stroke(strokeW))
  drawLine(Color(0xFFFF5722), Offset(cx + r * 0.45f, cy - r * 0.1f), Offset(cx + r * 0.75f, cy - r * 0.45f), strokeW * 2f)

  // Crab Oval Body
  drawOval(
    color = Color(0xFFFF7043),
    topLeft = Offset(cx - r * 0.75f, cy - r * 0.35f),
    size = Size(r * 1.5f, r * 0.85f)
  )
  drawOval(
    color = outline,
    topLeft = Offset(cx - r * 0.75f, cy - r * 0.35f),
    size = Size(r * 1.5f, r * 0.85f),
    style = Stroke(strokeW)
  )

  // 4 Little Walking Legs at Bottom
  drawLine(outline, Offset(cx - r * 0.5f, cy + r * 0.45f), Offset(cx - r * 0.7f, cy + r * 0.7f), strokeW * 1.2f)
  drawLine(outline, Offset(cx - r * 0.25f, cy + r * 0.48f), Offset(cx - r * 0.35f, cy + r * 0.75f), strokeW * 1.2f)
  drawLine(outline, Offset(cx + r * 0.25f, cy + r * 0.48f), Offset(cx + r * 0.35f, cy + r * 0.75f), strokeW * 1.2f)
  drawLine(outline, Offset(cx + r * 0.5f, cy + r * 0.45f), Offset(cx + r * 0.7f, cy + r * 0.7f), strokeW * 1.2f)

  // Stalk Eyes
  drawCircle(Color.White, center = Offset(cx - r * 0.22f, cy - r * 0.35f), radius = r * 0.15f)
  drawCircle(outline, center = Offset(cx - r * 0.22f, cy - r * 0.35f), radius = r * 0.15f, style = Stroke(strokeW))
  drawCircle(Color.Black, center = Offset(cx - r * 0.22f, cy - r * 0.35f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx - r * 0.25f, cy - r * 0.38f), radius = r * 0.04f)

  drawCircle(Color.White, center = Offset(cx + r * 0.22f, cy - r * 0.35f), radius = r * 0.15f)
  drawCircle(outline, center = Offset(cx + r * 0.22f, cy - r * 0.35f), radius = r * 0.15f, style = Stroke(strokeW))
  drawCircle(Color.Black, center = Offset(cx + r * 0.22f, cy - r * 0.35f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.20f, cy - r * 0.38f), radius = r * 0.04f)

  // Rosy cheeks & Smile
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx - r * 0.35f, cy + r * 0.05f), radius = r * 0.08f)
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx + r * 0.35f, cy + r * 0.05f), radius = r * 0.08f)
  drawArc(
    color = outline,
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - r * 0.2f, cy - r * 0.05f),
    size = Size(r * 0.4f, r * 0.25f),
    style = Stroke(strokeW)
  )
}

private fun DrawScope.drawDog2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Floppy Ears
  drawRoundRect(
    color = Color(0xFFD7CCC8),
    topLeft = Offset(cx - r * 0.85f, cy - r * 0.6f),
    size = Size(r * 0.35f, r * 0.65f),
    cornerRadius = CornerRadius(r * 0.18f, r * 0.18f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx - r * 0.85f, cy - r * 0.6f),
    size = Size(r * 0.35f, r * 0.65f),
    cornerRadius = CornerRadius(r * 0.18f, r * 0.18f),
    style = Stroke(strokeW)
  )
  drawRoundRect(
    color = Color(0xFFD7CCC8),
    topLeft = Offset(cx + r * 0.5f, cy - r * 0.6f),
    size = Size(r * 0.35f, r * 0.65f),
    cornerRadius = CornerRadius(r * 0.18f, r * 0.18f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx + r * 0.5f, cy - r * 0.6f),
    size = Size(r * 0.35f, r * 0.65f),
    cornerRadius = CornerRadius(r * 0.18f, r * 0.18f),
    style = Stroke(strokeW)
  )

  // Head
  drawCircle(Color(0xFFFFE082), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Muzzle (Cream White)
  drawOval(
    color = Color(0xFFFFF9C4),
    topLeft = Offset(cx - r * 0.35f, cy - r * 0.05f),
    size = Size(r * 0.7f, r * 0.55f)
  )

  // Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.18f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx - r * 0.24f, cy - r * 0.21f), radius = r * 0.035f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.18f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.20f, cy - r * 0.21f), radius = r * 0.035f)

  // Nose (Heart / Triangle Black)
  drawCircle(Color(0xFF263238), center = Offset(cx, cy + r * 0.08f), radius = r * 0.1f)

  // Tongue out
  drawRoundRect(
    color = Color(0xFFFF80AB),
    topLeft = Offset(cx - r * 0.08f, cy + r * 0.22f),
    size = Size(r * 0.16f, r * 0.22f),
    cornerRadius = CornerRadius(r * 0.08f, r * 0.08f)
  )

  // Smile
  drawArc(
    color = outline,
    startAngle = 10f,
    sweepAngle = 160f,
    useCenter = false,
    topLeft = Offset(cx - r * 0.18f, cy + r * 0.08f),
    size = Size(r * 0.36f, r * 0.2f),
    style = Stroke(strokeW)
  )
}

private fun DrawScope.drawCat2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Pointy Ears
  val earL = Path().apply {
    moveTo(cx - r * 0.55f, cy - r * 0.35f)
    lineTo(cx - r * 0.6f, cy - r * 0.85f)
    lineTo(cx - r * 0.15f, cy - r * 0.55f)
    close()
  }
  drawPath(earL, Color(0xFFFFCC80))
  drawPath(earL, outline, style = Stroke(strokeW))
  val innerEarL = Path().apply {
    moveTo(cx - r * 0.5f, cy - r * 0.4f)
    lineTo(cx - r * 0.55f, cy - r * 0.75f)
    lineTo(cx - r * 0.25f, cy - r * 0.52f)
    close()
  }
  drawPath(innerEarL, Color(0xFFFF80AB))

  val earR = Path().apply {
    moveTo(cx + r * 0.15f, cy - r * 0.55f)
    lineTo(cx + r * 0.6f, cy - r * 0.85f)
    lineTo(cx + r * 0.55f, cy - r * 0.35f)
    close()
  }
  drawPath(earR, Color(0xFFFFCC80))
  drawPath(earR, outline, style = Stroke(strokeW))
  val innerEarR = Path().apply {
    moveTo(cx + r * 0.25f, cy - r * 0.52f)
    lineTo(cx + r * 0.55f, cy - r * 0.75f)
    lineTo(cx + r * 0.5f, cy - r * 0.4f)
    close()
  }
  drawPath(innerEarR, Color(0xFFFF80AB))

  // Cat Head (Warm Cream)
  drawCircle(Color(0xFFFFE0B2), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Big Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.1f), radius = r * 0.1f)
  drawCircle(Color.White, center = Offset(cx - r * 0.25f, cy - r * 0.14f), radius = r * 0.045f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.1f), radius = r * 0.1f)
  drawCircle(Color.White, center = Offset(cx + r * 0.19f, cy - r * 0.14f), radius = r * 0.045f)

  // Pink Triangle Nose
  drawCircle(Color(0xFFFF4081), center = Offset(cx, cy + r * 0.1f), radius = r * 0.06f)

  // Whiskers
  drawLine(outline, Offset(cx - r * 0.35f, cy + r * 0.12f), Offset(cx - r * 0.8f, cy + r * 0.05f), strokeW * 0.8f)
  drawLine(outline, Offset(cx - r * 0.35f, cy + r * 0.2f), Offset(cx - r * 0.78f, cy + r * 0.25f), strokeW * 0.8f)
  drawLine(outline, Offset(cx + r * 0.35f, cy + r * 0.12f), Offset(cx + r * 0.8f, cy + r * 0.05f), strokeW * 0.8f)
  drawLine(outline, Offset(cx + r * 0.35f, cy + r * 0.2f), Offset(cx + r * 0.78f, cy + r * 0.25f), strokeW * 0.8f)

  // Rosy cheeks
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx - r * 0.32f, cy + r * 0.16f), radius = r * 0.08f)
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx + r * 0.32f, cy + r * 0.16f), radius = r * 0.08f)
}

private fun DrawScope.drawChicken2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Red Comb on Top
  drawCircle(Color(0xFFE53935), center = Offset(cx - r * 0.15f, cy - r * 0.75f), radius = r * 0.14f)
  drawCircle(Color(0xFFE53935), center = Offset(cx, cy - r * 0.82f), radius = r * 0.16f)
  drawCircle(Color(0xFFE53935), center = Offset(cx + r * 0.15f, cy - r * 0.75f), radius = r * 0.14f)

  // Chicken Body
  drawCircle(Color(0xFFFFF9C4), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Wing
  drawOval(Color(0xFFFFE082), topLeft = Offset(cx - r * 0.55f, cy - r * 0.1f), size = Size(r * 0.45f, r * 0.5f))
  drawOval(outline, topLeft = Offset(cx - r * 0.55f, cy - r * 0.1f), size = Size(r * 0.45f, r * 0.5f), style = Stroke(strokeW))

  // Eye
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.12f, cy - r * 0.18f), radius = r * 0.035f)

  // Yellow Beak
  val beak = Path().apply {
    moveTo(cx + r * 0.35f, cy - r * 0.1f)
    lineTo(cx + r * 0.75f, cy)
    lineTo(cx + r * 0.35f, cy + r * 0.1f)
    close()
  }
  drawPath(beak, Color(0xFFFFB300))
  drawPath(beak, outline, style = Stroke(strokeW))

  // Red Wattle below beak
  drawCircle(Color(0xFFE53935), center = Offset(cx + r * 0.35f, cy + r * 0.2f), radius = r * 0.1f)
}

private fun DrawScope.drawDuck2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Yellow Duck Body
  drawCircle(Color(0xFFFFEE58), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Wing
  drawOval(Color(0xFFFFD54F), topLeft = Offset(cx - r * 0.45f, cy - r * 0.05f), size = Size(r * 0.55f, r * 0.45f))
  drawOval(outline, topLeft = Offset(cx - r * 0.45f, cy - r * 0.05f), size = Size(r * 0.55f, r * 0.45f), style = Stroke(strokeW))

  // Eye
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.18f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.12f, cy - r * 0.21f), radius = r * 0.035f)

  // Orange Round Bill
  drawRoundRect(
    color = Color(0xFFFF9800),
    topLeft = Offset(cx + r * 0.25f, cy - r * 0.08f),
    size = Size(r * 0.55f, r * 0.28f),
    cornerRadius = CornerRadius(r * 0.14f, r * 0.14f)
  )
  drawRoundRect(
    color = outline,
    topLeft = Offset(cx + r * 0.25f, cy - r * 0.08f),
    size = Size(r * 0.55f, r * 0.28f),
    cornerRadius = CornerRadius(r * 0.14f, r * 0.14f),
    style = Stroke(strokeW)
  )

  // Rosy Cheek
  drawCircle(Color(0xFFFF8DA1), center = Offset(cx + r * 0.05f, cy + r * 0.08f), radius = r * 0.09f)
}

private fun DrawScope.drawPig2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Pig Pink Ears
  val earL = Path().apply {
    moveTo(cx - r * 0.55f, cy - r * 0.35f)
    lineTo(cx - r * 0.7f, cy - r * 0.75f)
    lineTo(cx - r * 0.25f, cy - r * 0.55f)
    close()
  }
  drawPath(earL, Color(0xFFF48FB1))
  drawPath(earL, outline, style = Stroke(strokeW))

  val earR = Path().apply {
    moveTo(cx + r * 0.25f, cy - r * 0.55f)
    lineTo(cx + r * 0.7f, cy - r * 0.75f)
    lineTo(cx + r * 0.55f, cy - r * 0.35f)
    close()
  }
  drawPath(earR, Color(0xFFF48FB1))
  drawPath(earR, outline, style = Stroke(strokeW))

  // Pig Head
  drawCircle(Color(0xFFF8BBD0), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Pig Oval Snout
  drawOval(Color(0xFFF06292), topLeft = Offset(cx - r * 0.3f, cy), size = Size(r * 0.6f, r * 0.42f))
  drawOval(outline, topLeft = Offset(cx - r * 0.3f, cy), size = Size(r * 0.6f, r * 0.42f), style = Stroke(strokeW))
  drawCircle(Color(0xFF880E4F), center = Offset(cx - r * 0.12f, cy + r * 0.2f), radius = r * 0.06f)
  drawCircle(Color(0xFF880E4F), center = Offset(cx + r * 0.12f, cy + r * 0.2f), radius = r * 0.06f)

  // Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.25f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx - r * 0.27f, cy - r * 0.18f), radius = r * 0.035f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.25f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.23f, cy - r * 0.18f), radius = r * 0.035f)

  // Cheeks
  drawCircle(Color(0xFFFF4081), center = Offset(cx - r * 0.42f, cy + r * 0.15f), radius = r * 0.09f)
  drawCircle(Color(0xFFFF4081), center = Offset(cx + r * 0.42f, cy + r * 0.15f), radius = r * 0.09f)
}

private fun DrawScope.drawCow2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Cow Horns
  drawCircle(Color(0xFFFFD54F), center = Offset(cx - r * 0.45f, cy - r * 0.6f), radius = r * 0.12f)
  drawCircle(Color(0xFFFFD54F), center = Offset(cx + r * 0.45f, cy - r * 0.6f), radius = r * 0.12f)

  // Head (White)
  drawCircle(Color.White, center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Black Spot on Left Eye
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.15f), radius = r * 0.22f)

  // Big Pink Muzzle
  drawOval(Color(0xFFF8BBD0), topLeft = Offset(cx - r * 0.45f, cy + r * 0.05f), size = Size(r * 0.9f, r * 0.55f))
  drawOval(outline, topLeft = Offset(cx - r * 0.45f, cy + r * 0.05f), size = Size(r * 0.9f, r * 0.55f), style = Stroke(strokeW))
  drawCircle(Color(0xFF880E4F), center = Offset(cx - r * 0.15f, cy + r * 0.3f), radius = r * 0.07f)
  drawCircle(Color(0xFF880E4F), center = Offset(cx + r * 0.15f, cy + r * 0.3f), radius = r * 0.07f)

  // Eyes
  drawCircle(Color.White, center = Offset(cx - r * 0.22f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.22f, cy - r * 0.15f), radius = r * 0.05f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.20f, cy - r * 0.18f), radius = r * 0.035f)
}

private fun DrawScope.drawGoat2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Horns
  drawRoundRect(Color(0xFFFFB74D), topLeft = Offset(cx - r * 0.4f, cy - r * 0.75f), size = Size(r * 0.15f, r * 0.4f), cornerRadius = CornerRadius(r * 0.08f, r * 0.08f))
  drawRoundRect(Color(0xFFFFB74D), topLeft = Offset(cx + r * 0.25f, cy - r * 0.75f), size = Size(r * 0.15f, r * 0.4f), cornerRadius = CornerRadius(r * 0.08f, r * 0.08f))

  // Head
  drawCircle(Color(0xFFFFF9C4), center = Offset(cx, cy), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.6f, style = Stroke(strokeW))

  // Little Beard
  drawRoundRect(Color.White, topLeft = Offset(cx - r * 0.12f, cy + r * 0.5f), size = Size(r * 0.24f, r * 0.3f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f))

  // Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.2f, cy - r * 0.1f), radius = r * 0.07f)

  // Smile
  drawArc(outline, 20f, 140f, false, Offset(cx - r * 0.15f, cy + r * 0.15f), Size(r * 0.3f, r * 0.2f), style = Stroke(strokeW))
}

private fun DrawScope.drawHorse2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Brown Mane
  drawCircle(Color(0xFF5D4037), center = Offset(cx, cy - r * 0.5f), radius = r * 0.35f)

  // Head
  drawOval(Color(0xFF8D6E63), topLeft = Offset(cx - r * 0.45f, cy - r * 0.55f), size = Size(r * 0.9f, r * 1.15f))
  drawOval(outline, topLeft = Offset(cx - r * 0.45f, cy - r * 0.55f), size = Size(r * 0.9f, r * 1.15f), style = Stroke(strokeW))

  // Ears
  drawRoundRect(Color(0xFF8D6E63), topLeft = Offset(cx - r * 0.35f, cy - r * 0.8f), size = Size(r * 0.2f, r * 0.35f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f))
  drawRoundRect(Color(0xFF8D6E63), topLeft = Offset(cx + r * 0.15f, cy - r * 0.8f), size = Size(r * 0.2f, r * 0.35f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f))

  // Eyes & Nostrils
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.2f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.2f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color(0xFF4E342E), center = Offset(cx - r * 0.15f, cy + r * 0.35f), radius = r * 0.06f)
  drawCircle(Color(0xFF4E342E), center = Offset(cx + r * 0.15f, cy + r * 0.35f), radius = r * 0.06f)
}

private fun DrawScope.drawSheep2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Fluffy Wool Circles
  drawCircle(Color(0xFFF5F5F5), center = Offset(cx - r * 0.45f, cy - r * 0.35f), radius = r * 0.3f)
  drawCircle(Color(0xFFF5F5F5), center = Offset(cx + r * 0.45f, cy - r * 0.35f), radius = r * 0.3f)
  drawCircle(Color(0xFFF5F5F5), center = Offset(cx - r * 0.45f, cy + r * 0.35f), radius = r * 0.3f)
  drawCircle(Color(0xFFF5F5F5), center = Offset(cx + r * 0.45f, cy + r * 0.35f), radius = r * 0.3f)
  drawCircle(Color(0xFFF5F5F5), center = Offset(cx, cy), radius = r * 0.6f)

  // Face (Soft Pinkish Gray)
  drawCircle(Color(0xFFFFECB3), center = Offset(cx, cy), radius = r * 0.4f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.4f, style = Stroke(strokeW))

  // Eyes & Smile
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.15f, cy - r * 0.05f), radius = r * 0.06f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.05f), radius = r * 0.06f)
  drawArc(outline, 20f, 140f, false, Offset(cx - r * 0.12f, cy + r * 0.05f), Size(r * 0.24f, r * 0.15f), style = Stroke(strokeW))
}

private fun DrawScope.drawRabbit2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Long Ears
  drawRoundRect(Color(0xFFFFFDF7), topLeft = Offset(cx - r * 0.4f, cy - r * 0.95f), size = Size(r * 0.25f, r * 0.65f), cornerRadius = CornerRadius(r * 0.12f, r * 0.12f))
  drawRoundRect(outline, topLeft = Offset(cx - r * 0.4f, cy - r * 0.95f), size = Size(r * 0.25f, r * 0.65f), cornerRadius = CornerRadius(r * 0.12f, r * 0.12f), style = Stroke(strokeW))
  drawRoundRect(Color(0xFFF48FB1), topLeft = Offset(cx - r * 0.35f, cy - r * 0.85f), size = Size(r * 0.15f, r * 0.45f), cornerRadius = CornerRadius(r * 0.08f, r * 0.08f))

  drawRoundRect(Color(0xFFFFFDF7), topLeft = Offset(cx + r * 0.15f, cy - r * 0.95f), size = Size(r * 0.25f, r * 0.65f), cornerRadius = CornerRadius(r * 0.12f, r * 0.12f))
  drawRoundRect(outline, topLeft = Offset(cx + r * 0.15f, cy - r * 0.95f), size = Size(r * 0.25f, r * 0.65f), cornerRadius = CornerRadius(r * 0.12f, r * 0.12f), style = Stroke(strokeW))
  drawRoundRect(Color(0xFFF48FB1), topLeft = Offset(cx + r * 0.2f, cy - r * 0.85f), size = Size(r * 0.15f, r * 0.45f), cornerRadius = CornerRadius(r * 0.08f, r * 0.08f))

  // Rabbit Head
  drawCircle(Color(0xFFFFFDF7), center = Offset(cx, cy + r * 0.1f), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx, cy + r * 0.1f), radius = r * 0.55f, style = Stroke(strokeW))

  // Eyes & Nose
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.18f, cy), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.18f, cy), radius = r * 0.07f)
  drawCircle(Color(0xFFFF4081), center = Offset(cx, cy + r * 0.15f), radius = r * 0.06f)

  // Carrot
  val carrot = Path().apply {
    moveTo(cx + r * 0.2f, cy + r * 0.35f)
    lineTo(cx + r * 0.65f, cy + r * 0.2f)
    lineTo(cx + r * 0.55f, cy + r * 0.55f)
    close()
  }
  drawPath(carrot, Color(0xFFFF9800))
}

private fun DrawScope.drawChick2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  drawCircle(Color(0xFFFFF59D), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Beak & Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.15f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color(0xFFFFB300), center = Offset(cx, cy + r * 0.05f), radius = r * 0.1f)
}

private fun DrawScope.drawBuffalo2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Curved Buffalo Horns
  drawArc(Color(0xFF455A64), 180f, 180f, false, Offset(cx - r * 0.8f, cy - r * 0.7f), Size(r * 1.6f, r * 0.8f), style = Stroke(strokeW * 3f))

  // Buffalo Head
  drawCircle(Color(0xFF78909C), center = Offset(cx, cy), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.6f, style = Stroke(strokeW))

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.2f, cy - r * 0.1f), radius = r * 0.07f)
}

private fun DrawScope.drawLion2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Golden Fluffy Mane
  for (i in 0 until 12) {
    val angle = (i * 2 * PI / 12).toFloat()
    val mx = cx + r * 0.65f * cos(angle)
    val my = cy + r * 0.65f * sin(angle)
    drawCircle(Color(0xFFFFB300), center = Offset(mx, my), radius = r * 0.32f)
  }

  // Head
  drawCircle(Color(0xFFFFE082), center = Offset(cx, cy), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.55f, style = Stroke(strokeW))

  // Face
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.18f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.18f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFFE65100), center = Offset(cx, cy + r * 0.12f), radius = r * 0.08f)
  drawArc(outline, 20f, 140f, false, Offset(cx - r * 0.15f, cy + r * 0.15f), Size(r * 0.3f, r * 0.2f), style = Stroke(strokeW))
}

private fun DrawScope.drawElephant2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Big Ears
  drawCircle(Color(0xFFB0BEC5), center = Offset(cx - r * 0.65f, cy - r * 0.1f), radius = r * 0.45f)
  drawCircle(outline, center = Offset(cx - r * 0.65f, cy - r * 0.1f), radius = r * 0.45f, style = Stroke(strokeW))
  drawCircle(Color(0xFFB0BEC5), center = Offset(cx + r * 0.65f, cy - r * 0.1f), radius = r * 0.45f)
  drawCircle(outline, center = Offset(cx + r * 0.65f, cy - r * 0.1f), radius = r * 0.45f, style = Stroke(strokeW))

  // Head
  drawCircle(Color(0xFFCFD8DC), center = Offset(cx, cy), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.55f, style = Stroke(strokeW))

  // Long Trunk Curving Up
  val trunk = Path().apply {
    moveTo(cx - r * 0.12f, cy + r * 0.15f)
    cubicTo(cx - r * 0.15f, cy + r * 0.75f, cx + r * 0.45f, cy + r * 0.75f, cx + r * 0.4f, cy + r * 0.45f)
    lineTo(cx + r * 0.25f, cy + r * 0.45f)
    cubicTo(cx + r * 0.25f, cy + r * 0.6f, cx + r * 0.05f, cy + r * 0.6f, cx + r * 0.05f, cy + r * 0.15f)
    close()
  }
  drawPath(trunk, Color(0xFFCFD8DC))
  drawPath(trunk, outline, style = Stroke(strokeW))

  // Eyes
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.12f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.12f), radius = r * 0.07f)
}

private fun DrawScope.drawTiger2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Tiger Head (Orange)
  drawCircle(Color(0xFFFF9800), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Black Stripes
  drawLine(Color(0xFF263238), Offset(cx - r * 0.5f, cy - r * 0.2f), Offset(cx - r * 0.25f, cy - r * 0.15f), strokeW * 2f)
  drawLine(Color(0xFF263238), Offset(cx + r * 0.5f, cy - r * 0.2f), Offset(cx + r * 0.25f, cy - r * 0.15f), strokeW * 2f)
  drawLine(Color(0xFF263238), Offset(cx, cy - r * 0.6f), Offset(cx, cy - r * 0.35f), strokeW * 2f)

  // White Muzzle
  drawOval(Color.White, topLeft = Offset(cx - r * 0.35f, cy), size = Size(r * 0.7f, r * 0.5f))

  // Eyes & Nose
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.2f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color(0xFFE65100), center = Offset(cx, cy + r * 0.15f), radius = r * 0.08f)
}

private fun DrawScope.drawGiraffe2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Long Neck
  drawRoundRect(Color(0xFFFFE082), topLeft = Offset(cx - r * 0.2f, cy - r * 0.1f), size = Size(r * 0.4f, r * 0.9f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f))
  drawRoundRect(outline, topLeft = Offset(cx - r * 0.2f, cy - r * 0.1f), size = Size(r * 0.4f, r * 0.9f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f), style = Stroke(strokeW))

  // Brown Spots
  drawCircle(Color(0xFF8D6E63), center = Offset(cx, cy + r * 0.2f), radius = r * 0.12f)
  drawCircle(Color(0xFF8D6E63), center = Offset(cx, cy + r * 0.55f), radius = r * 0.14f)

  // Head & Little Horns
  drawCircle(Color(0xFFFFE082), center = Offset(cx, cy - r * 0.35f), radius = r * 0.35f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.35f), radius = r * 0.35f, style = Stroke(strokeW))
  drawCircle(Color(0xFF8D6E63), center = Offset(cx - r * 0.18f, cy - r * 0.72f), radius = r * 0.07f)
  drawCircle(Color(0xFF8D6E63), center = Offset(cx + r * 0.18f, cy - r * 0.72f), radius = r * 0.07f)
  drawLine(outline, Offset(cx - r * 0.15f, cy - r * 0.55f), Offset(cx - r * 0.18f, cy - r * 0.72f), strokeW)
  drawLine(outline, Offset(cx + r * 0.15f, cy - r * 0.55f), Offset(cx + r * 0.18f, cy - r * 0.72f), strokeW)

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.12f, cy - r * 0.38f), radius = r * 0.06f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.12f, cy - r * 0.38f), radius = r * 0.06f)
}

private fun DrawScope.drawMonkey2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Big Round Ears
  drawCircle(Color(0xFF8D6E63), center = Offset(cx - r * 0.6f, cy), radius = r * 0.25f)
  drawCircle(Color(0xFFFFCC80), center = Offset(cx - r * 0.6f, cy), radius = r * 0.15f)
  drawCircle(Color(0xFF8D6E63), center = Offset(cx + r * 0.6f, cy), radius = r * 0.25f)
  drawCircle(Color(0xFFFFCC80), center = Offset(cx + r * 0.6f, cy), radius = r * 0.15f)

  // Head
  drawCircle(Color(0xFF6D4C41), center = Offset(cx, cy), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.55f, style = Stroke(strokeW))

  // Cream Mask
  drawOval(Color(0xFFFFE0B2), topLeft = Offset(cx - r * 0.38f, cy - r * 0.2f), size = Size(r * 0.76f, r * 0.65f))

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.15f, cy - r * 0.05f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.05f), radius = r * 0.07f)
  drawArc(outline, 20f, 140f, false, Offset(cx - r * 0.15f, cy + r * 0.12f), Size(r * 0.3f, r * 0.2f), style = Stroke(strokeW))
}

private fun DrawScope.drawBear2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Round Ears
  drawCircle(Color(0xFF795548), center = Offset(cx - r * 0.45f, cy - r * 0.55f), radius = r * 0.22f)
  drawCircle(Color(0xFFFFCC80), center = Offset(cx - r * 0.45f, cy - r * 0.55f), radius = r * 0.12f)
  drawCircle(Color(0xFF795548), center = Offset(cx + r * 0.45f, cy - r * 0.55f), radius = r * 0.22f)
  drawCircle(Color(0xFFFFCC80), center = Offset(cx + r * 0.45f, cy - r * 0.55f), radius = r * 0.12f)

  // Head
  drawCircle(Color(0xFF8D6E63), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Muzzle & Eyes
  drawOval(Color(0xFFFFE0B2), topLeft = Offset(cx - r * 0.32f, cy), size = Size(r * 0.64f, r * 0.45f))
  drawCircle(Color(0xFF263238), center = Offset(cx, cy + r * 0.12f), radius = r * 0.09f)

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.15f), radius = r * 0.08f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.15f), radius = r * 0.08f)
}

private fun DrawScope.drawZebra2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Zebra Head (White with Black Stripes)
  drawCircle(Color.White, center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  drawLine(Color(0xFF263238), Offset(cx - r * 0.6f, cy - r * 0.15f), Offset(cx - r * 0.2f, cy - r * 0.1f), strokeW * 2f)
  drawLine(Color(0xFF263238), Offset(cx + r * 0.6f, cy - r * 0.15f), Offset(cx + r * 0.2f, cy - r * 0.1f), strokeW * 2f)
  drawLine(Color(0xFF263238), Offset(cx - r * 0.5f, cy + r * 0.2f), Offset(cx - r * 0.2f, cy + r * 0.2f), strokeW * 2f)

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.2f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.2f, cy - r * 0.15f), radius = r * 0.07f)
}

private fun DrawScope.drawPanda2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Black Ears
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.5f, cy - r * 0.55f), radius = r * 0.22f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.5f, cy - r * 0.55f), radius = r * 0.22f)

  // White Head
  drawCircle(Color.White, center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Black Eye Patches
  drawOval(Color(0xFF263238), topLeft = Offset(cx - r * 0.42f, cy - r * 0.25f), size = Size(r * 0.32f, r * 0.4f))
  drawOval(Color(0xFF263238), topLeft = Offset(cx + r * 0.1f, cy - r * 0.25f), size = Size(r * 0.32f, r * 0.4f))

  drawCircle(Color.White, center = Offset(cx - r * 0.26f, cy - r * 0.1f), radius = r * 0.06f)
  drawCircle(Color.White, center = Offset(cx + r * 0.26f, cy - r * 0.1f), radius = r * 0.06f)

  drawCircle(Color(0xFF263238), center = Offset(cx, cy + r * 0.2f), radius = r * 0.08f)
}

private fun DrawScope.drawFox2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Pointy Fox Ears
  val earL = Path().apply {
    moveTo(cx - r * 0.55f, cy - r * 0.35f)
    lineTo(cx - r * 0.65f, cy - r * 0.9f)
    lineTo(cx - r * 0.15f, cy - r * 0.55f)
    close()
  }
  drawPath(earL, Color(0xFFFF7043))
  drawPath(earL, outline, style = Stroke(strokeW))

  val earR = Path().apply {
    moveTo(cx + r * 0.15f, cy - r * 0.55f)
    lineTo(cx + r * 0.65f, cy - r * 0.9f)
    lineTo(cx + r * 0.55f, cy - r * 0.35f)
    close()
  }
  drawPath(earR, Color(0xFFFF7043))
  drawPath(earR, outline, style = Stroke(strokeW))

  // Orange Head
  drawCircle(Color(0xFFFF7043), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // White Cheeks
  val whiteCheek = Path().apply {
    moveTo(cx - r * 0.6f, cy + r * 0.1f)
    cubicTo(cx - r * 0.3f, cy + r * 0.55f, cx + r * 0.3f, cy + r * 0.55f, cx + r * 0.6f, cy + r * 0.1f)
    lineTo(cx, cy + r * 0.55f)
    close()
  }
  drawPath(whiteCheek, Color.White)

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.22f, cy - r * 0.05f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.22f, cy - r * 0.05f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx, cy + r * 0.3f), radius = r * 0.08f)
}

private fun DrawScope.drawSquirrel2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Fluffy Tail
  drawCircle(Color(0xFFFF9800), center = Offset(cx + r * 0.65f, cy - r * 0.2f), radius = r * 0.42f)

  // Head
  drawCircle(Color(0xFFFFB74D), center = Offset(cx - r * 0.1f, cy), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx - r * 0.1f, cy), radius = r * 0.55f, style = Stroke(strokeW))

  // Acorn
  drawCircle(Color(0xFF8D6E63), center = Offset(cx - r * 0.1f, cy + r * 0.45f), radius = r * 0.16f)

  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.25f, cy - r * 0.1f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.05f, cy - r * 0.1f), radius = r * 0.07f)
}

private fun DrawScope.drawDolphin2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Dolphin Blue Body leaping
  val dolphin = Path().apply {
    moveTo(cx - r * 0.95f, cy + r * 0.2f)
    cubicTo(cx - r * 0.6f, cy - r * 0.85f, cx + r * 0.4f, cy - r * 0.85f, cx + r * 0.95f, cy + r * 0.25f)
    cubicTo(cx + r * 0.4f, cy - r * 0.1f, cx - r * 0.4f, cy + r * 0.3f, cx - r * 0.95f, cy + r * 0.2f)
    close()
  }
  drawPath(dolphin, Color(0xFF42A5F5))
  drawPath(dolphin, outline, style = Stroke(strokeW))

  // White Belly
  drawCircle(Color.White, center = Offset(cx - r * 0.1f, cy + r * 0.05f), radius = r * 0.3f)

  // Fin & Eye
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.55f, cy - r * 0.15f), radius = r * 0.07f)
  drawCircle(Color.White, center = Offset(cx + r * 0.52f, cy - r * 0.18f), radius = r * 0.035f)
}

private fun DrawScope.drawWhale2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Water Spout on Top
  drawCircle(Color(0xFF80DEEA), center = Offset(cx, cy - r * 0.85f), radius = r * 0.15f)
  drawCircle(Color(0xFF80DEEA), center = Offset(cx - r * 0.2f, cy - r * 0.75f), radius = r * 0.12f)
  drawCircle(Color(0xFF80DEEA), center = Offset(cx + r * 0.2f, cy - r * 0.75f), radius = r * 0.12f)

  // Round Whale Body
  drawOval(Color(0xFF1E88E5), topLeft = Offset(cx - r * 0.85f, cy - r * 0.45f), size = Size(r * 1.7f, r * 1.05f))
  drawOval(outline, topLeft = Offset(cx - r * 0.85f, cy - r * 0.45f), size = Size(r * 1.7f, r * 1.05f), style = Stroke(strokeW))

  // Belly & Eye
  drawOval(Color.White, topLeft = Offset(cx - r * 0.5f, cy + r * 0.15f), size = Size(r * 1.1f, r * 0.4f))
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.45f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.42f, cy - r * 0.13f), radius = r * 0.04f)
}

private fun DrawScope.drawTurtle2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Flipper Legs
  drawCircle(Color(0xFFA5D6A7), center = Offset(cx - r * 0.55f, cy - r * 0.35f), radius = r * 0.2f)
  drawCircle(Color(0xFFA5D6A7), center = Offset(cx + r * 0.55f, cy - r * 0.35f), radius = r * 0.2f)
  drawCircle(Color(0xFFA5D6A7), center = Offset(cx - r * 0.45f, cy + r * 0.45f), radius = r * 0.18f)
  drawCircle(Color(0xFFA5D6A7), center = Offset(cx + r * 0.45f, cy + r * 0.45f), radius = r * 0.18f)

  // Green Shell
  drawCircle(Color(0xFF43A047), center = Offset(cx, cy), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.6f, style = Stroke(strokeW))
  drawCircle(Color(0xFF81C784), center = Offset(cx, cy), radius = r * 0.35f)

  // Head
  drawCircle(Color(0xFFA5D6A7), center = Offset(cx, cy - r * 0.65f), radius = r * 0.25f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.65f), radius = r * 0.25f, style = Stroke(strokeW))
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.1f, cy - r * 0.7f), radius = r * 0.05f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.1f, cy - r * 0.7f), radius = r * 0.05f)
}

private fun DrawScope.drawOctopus2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // 6 Cartoon Tentacles below
  for (i in -2..2) {
    drawCircle(Color(0xFFBA68C8), center = Offset(cx + i * r * 0.28f, cy + r * 0.55f), radius = r * 0.18f)
  }

  // Big Head
  drawCircle(Color(0xFFCE93D8), center = Offset(cx, cy - r * 0.1f), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy - r * 0.1f), radius = r * 0.6f, style = Stroke(strokeW))

  // Big Kawaii Eyes
  drawCircle(Color.White, center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.14f)
  drawCircle(Color.Black, center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx - r * 0.24f, cy - r * 0.14f), radius = r * 0.04f)

  drawCircle(Color.White, center = Offset(cx + r * 0.2f, cy - r * 0.1f), radius = r * 0.14f)
  drawCircle(Color.Black, center = Offset(cx + r * 0.2f, cy - r * 0.1f), radius = r * 0.08f)
  drawCircle(Color.White, center = Offset(cx + r * 0.16f, cy - r * 0.14f), radius = r * 0.04f)

  drawCircle(Color(0xFFFF80AB), center = Offset(cx, cy + r * 0.2f), radius = r * 0.09f)
}

private fun DrawScope.drawFish2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Tail
  val tail = Path().apply {
    moveTo(cx - r * 0.5f, cy)
    lineTo(cx - r * 0.95f, cy - r * 0.45f)
    lineTo(cx - r * 0.95f, cy + r * 0.45f)
    close()
  }
  drawPath(tail, Color(0xFFFF9800))
  drawPath(tail, outline, style = Stroke(strokeW))

  // Oval Fish Body (Orange Nemo)
  drawOval(Color(0xFFFF7043), topLeft = Offset(cx - r * 0.6f, cy - r * 0.45f), size = Size(r * 1.3f, r * 0.9f))
  drawOval(outline, topLeft = Offset(cx - r * 0.6f, cy - r * 0.45f), size = Size(r * 1.3f, r * 0.9f), style = Stroke(strokeW))

  // White Stripes
  drawRoundRect(Color.White, topLeft = Offset(cx - r * 0.1f, cy - r * 0.45f), size = Size(r * 0.22f, r * 0.9f), cornerRadius = CornerRadius(r * 0.1f, r * 0.1f))

  drawCircle(Color.White, center = Offset(cx + r * 0.4f, cy - r * 0.12f), radius = r * 0.12f)
  drawCircle(Color.Black, center = Offset(cx + r * 0.42f, cy - r * 0.12f), radius = r * 0.06f)
}

private fun DrawScope.drawPenguin2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Black Body
  drawOval(Color(0xFF263238), topLeft = Offset(cx - r * 0.55f, cy - r * 0.6f), size = Size(r * 1.1f, r * 1.25f))

  // White Belly
  drawOval(Color.White, topLeft = Offset(cx - r * 0.38f, cy - r * 0.35f), size = Size(r * 0.76f, r * 0.95f))

  // Orange Feet
  drawCircle(Color(0xFFFF9800), center = Offset(cx - r * 0.25f, cy + r * 0.7f), radius = r * 0.14f)
  drawCircle(Color(0xFFFF9800), center = Offset(cx + r * 0.25f, cy + r * 0.7f), radius = r * 0.14f)

  // Eyes & Beak
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.18f, cy - r * 0.2f), radius = r * 0.07f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.18f, cy - r * 0.2f), radius = r * 0.07f)
  drawCircle(Color(0xFFFF9800), center = Offset(cx, cy - r * 0.05f), radius = r * 0.1f)
}

private fun DrawScope.drawParrot2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Green / Red Parrot Body
  drawCircle(Color(0xFF4CAF50), center = Offset(cx, cy), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.6f, style = Stroke(strokeW))

  // Red Wing
  drawOval(Color(0xFFE53935), topLeft = Offset(cx - r * 0.45f, cy - r * 0.1f), size = Size(r * 0.5f, r * 0.6f))

  // Yellow Curved Beak
  val beak = Path().apply {
    moveTo(cx + r * 0.25f, cy - r * 0.15f)
    cubicTo(cx + r * 0.75f, cy - r * 0.1f, cx + r * 0.7f, cy + r * 0.3f, cx + r * 0.3f, cy + r * 0.15f)
    close()
  }
  drawPath(beak, Color(0xFFFFD54F))
  drawPath(beak, outline, style = Stroke(strokeW))

  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.12f, cy - r * 0.22f), radius = r * 0.07f)
}

private fun DrawScope.drawDove2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // White Dove
  drawCircle(Color(0xFFFAFAFA), center = Offset(cx, cy), radius = r * 0.6f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.6f, style = Stroke(strokeW))

  // Olive Branch in Beak
  drawCircle(Color(0xFF81C784), center = Offset(cx + r * 0.6f, cy), radius = r * 0.12f)

  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.15f, cy - r * 0.15f), radius = r * 0.07f)
}

// ==========================================
// FRUITS (2D CARTOON)
// ==========================================

private fun DrawScope.drawApple2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Green Leaf on stem
  val leaf = Path().apply {
    moveTo(cx, cy - r * 0.65f)
    cubicTo(cx + r * 0.3f, cy - r * 0.95f, cx + r * 0.6f, cy - r * 0.8f, cx + r * 0.4f, cy - r * 0.55f)
    close()
  }
  drawPath(leaf, Color(0xFF66BB6A))
  drawPath(leaf, outline, style = Stroke(strokeW))

  // Brown Stem
  drawLine(Color(0xFF5D4037), Offset(cx, cy - r * 0.5f), Offset(cx, cy - r * 0.8f), strokeW * 2f)

  // Red Apple
  drawCircle(Color(0xFFE53935), center = Offset(cx - r * 0.25f, cy + r * 0.05f), radius = r * 0.55f)
  drawCircle(Color(0xFFE53935), center = Offset(cx + r * 0.25f, cy + r * 0.05f), radius = r * 0.55f)
  drawCircle(outline, center = Offset(cx - r * 0.25f, cy + r * 0.05f), radius = r * 0.55f, style = Stroke(strokeW))
  drawCircle(outline, center = Offset(cx + r * 0.25f, cy + r * 0.05f), radius = r * 0.55f, style = Stroke(strokeW))

  // Shine
  drawCircle(Color.White.copy(alpha = 0.6f), center = Offset(cx - r * 0.35f, cy - r * 0.2f), radius = r * 0.12f)
}

private fun DrawScope.drawBanana2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Yellow Curved Banana
  val banana = Path().apply {
    moveTo(cx - r * 0.8f, cy - r * 0.5f)
    cubicTo(cx - r * 0.2f, cy + r * 0.7f, cx + r * 0.6f, cy + r * 0.5f, cx + r * 0.85f, cy - r * 0.3f)
    cubicTo(cx + r * 0.5f, cy + r * 0.2f, cx - r * 0.1f, cy + r * 0.3f, cx - r * 0.8f, cy - r * 0.5f)
    close()
  }
  drawPath(banana, Color(0xFFFFEB3B))
  drawPath(banana, outline, style = Stroke(strokeW))

  // Stem Tip
  drawCircle(Color(0xFF689F38), center = Offset(cx - r * 0.8f, cy - r * 0.5f), radius = r * 0.1f)
}

private fun DrawScope.drawOrange2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Leaf
  drawCircle(Color(0xFF4CAF50), center = Offset(cx + r * 0.25f, cy - r * 0.65f), radius = r * 0.18f)

  // Orange Round Body
  drawCircle(Color(0xFFFF9800), center = Offset(cx, cy), radius = r * 0.65f)
  drawCircle(outline, center = Offset(cx, cy), radius = r * 0.65f, style = Stroke(strokeW))

  // Citrus Dots
  drawCircle(Color(0xFFF57C00), center = Offset(cx - r * 0.2f, cy - r * 0.1f), radius = r * 0.05f)
  drawCircle(Color(0xFFF57C00), center = Offset(cx + r * 0.2f, cy + r * 0.2f), radius = r * 0.05f)
}

private fun DrawScope.drawWatermelon2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Green Rind
  drawArc(Color(0xFF388E3C), 0f, 180f, true, Offset(cx - r * 0.8f, cy - r * 0.6f), Size(r * 1.6f, r * 1.4f))

  // Red Slice Inside
  drawArc(Color(0xFFE53935), 0f, 180f, true, Offset(cx - r * 0.7f, cy - r * 0.5f), Size(r * 1.4f, r * 1.2f))

  // Black Seeds
  drawCircle(Color(0xFF263238), center = Offset(cx - r * 0.35f, cy + r * 0.1f), radius = r * 0.05f)
  drawCircle(Color(0xFF263238), center = Offset(cx, cy + r * 0.3f), radius = r * 0.05f)
  drawCircle(Color(0xFF263238), center = Offset(cx + r * 0.35f, cy + r * 0.1f), radius = r * 0.05f)
}

private fun DrawScope.drawMango2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Mango Shape (Golden Yellow)
  drawOval(Color(0xFFFFC107), topLeft = Offset(cx - r * 0.5f, cy - r * 0.65f), size = Size(r * 1.0f, r * 1.3f))
  drawOval(outline, topLeft = Offset(cx - r * 0.5f, cy - r * 0.65f), size = Size(r * 1.0f, r * 1.3f), style = Stroke(strokeW))

  // Green Leaf
  drawCircle(Color(0xFF66BB6A), center = Offset(cx - r * 0.2f, cy - r * 0.7f), radius = r * 0.15f)
}

private fun DrawScope.drawStrawberry2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Green Top Leaves
  drawCircle(Color(0xFF43A047), center = Offset(cx - r * 0.25f, cy - r * 0.55f), radius = r * 0.15f)
  drawCircle(Color(0xFF43A047), center = Offset(cx, cy - r * 0.6f), radius = r * 0.16f)
  drawCircle(Color(0xFF43A047), center = Offset(cx + r * 0.25f, cy - r * 0.55f), radius = r * 0.15f)

  // Red Heart/Cone Shape
  val strawberry = Path().apply {
    moveTo(cx - r * 0.65f, cy - r * 0.4f)
    cubicTo(cx - r * 0.7f, cy + r * 0.3f, cx - r * 0.2f, cy + r * 0.75f, cx, cy + r * 0.85f)
    cubicTo(cx + r * 0.2f, cy + r * 0.75f, cx + r * 0.7f, cy + r * 0.3f, cx + r * 0.65f, cy - r * 0.4f)
    close()
  }
  drawPath(strawberry, Color(0xFFE91E63))
  drawPath(strawberry, outline, style = Stroke(strokeW))

  // Yellow Tiny Seeds
  drawCircle(Color(0xFFFFEE58), center = Offset(cx - r * 0.25f, cy - r * 0.1f), radius = r * 0.04f)
  drawCircle(Color(0xFFFFEE58), center = Offset(cx + r * 0.25f, cy - r * 0.1f), radius = r * 0.04f)
  drawCircle(Color(0xFFFFEE58), center = Offset(cx, cy + r * 0.25f), radius = r * 0.04f)
}

private fun DrawScope.drawGrapes2D(cx: Float, cy: Float, size: Float) {
  val r = size * 0.38f
  val strokeW = size * 0.025f
  val outline = Color(0xFF424242)

  // Leaf
  drawCircle(Color(0xFF4CAF50), center = Offset(cx, cy - r * 0.65f), radius = r * 0.18f)

  // Bunch of Purple Circles
  val grapeR = r * 0.22f
  drawCircle(Color(0xFF7E57C2), center = Offset(cx - r * 0.3f, cy - r * 0.3f), radius = grapeR)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx + r * 0.3f, cy - r * 0.3f), radius = grapeR)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx, cy - r * 0.2f), radius = grapeR)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx - r * 0.2f, cy + r * 0.1f), radius = grapeR)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx + r * 0.2f, cy + r * 0.1f), radius = grapeR)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx, cy + r * 0.4f), radius = grapeR)
}

// ==========================================
// COLORS, NUMBERS, LETTERS
// ==========================================

private fun DrawScope.drawColorBlob2D(cx: Float, cy: Float, size: Float, color: Color, label: String) {
  val r = size * 0.42f
  drawCircle(color, center = Offset(cx, cy), radius = r)
  drawCircle(Color.White, center = Offset(cx, cy), radius = r, style = Stroke(size * 0.04f))
  drawCircle(Color(0xFF424242), center = Offset(cx, cy), radius = r, style = Stroke(size * 0.015f))
  drawCircle(Color.White.copy(alpha = 0.5f), center = Offset(cx - r * 0.35f, cy - r * 0.35f), radius = r * 0.22f)
}

private fun DrawScope.drawNumberCard2D(
  cx: Float,
  cy: Float,
  size: Float,
  numStr: String,
  dotCount: Int,
  measurer: androidx.compose.ui.text.TextMeasurer
) {
  val r = size * 0.45f

  // Background Badge
  drawCircle(Color(0xFFFFF3E0), center = Offset(cx, cy), radius = r)
  drawCircle(Color(0xFFFF9800), center = Offset(cx, cy), radius = r, style = Stroke(size * 0.03f))

  // Big Bold Number Text
  val textLayout = measurer.measure(
    text = numStr,
    style = TextStyle(
      fontSize = (size * 0.45f).sp,
      fontWeight = FontWeight.Black,
      color = Color(0xFFE65100)
    )
  )
  drawText(
    textLayoutResult = textLayout,
    topLeft = Offset(cx - textLayout.size.width / 2f, cy - textLayout.size.height / 2f - r * 0.12f)
  )

  // Visual Counting Dots
  val dotR = size * 0.035f
  val spacing = size * 0.08f
  val startX = cx - ((minOf(dotCount, 5) - 1) * spacing) / 2f
  for (i in 0 until minOf(dotCount, 5)) {
    drawCircle(Color(0xFFFF5722), center = Offset(startX + i * spacing, cy + r * 0.55f), radius = dotR)
  }
}

private fun DrawScope.drawLetterCard2D(
  cx: Float,
  cy: Float,
  size: Float,
  letter: String,
  measurer: androidx.compose.ui.text.TextMeasurer
) {
  val r = size * 0.45f

  // Background Badge
  drawCircle(Color(0xFFEDE7F6), center = Offset(cx, cy), radius = r)
  drawCircle(Color(0xFF7E57C2), center = Offset(cx, cy), radius = r, style = Stroke(size * 0.03f))

  // Letter Text
  val textLayout = measurer.measure(
    text = letter,
    style = TextStyle(
      fontSize = (size * 0.52f).sp,
      fontWeight = FontWeight.Black,
      color = Color(0xFF4527A0)
    )
  )
  drawText(
    textLayoutResult = textLayout,
    topLeft = Offset(cx - textLayout.size.width / 2f, cy - textLayout.size.height / 2f)
  )
}

private fun DrawScope.drawGenericCuteCard(cx: Float, cy: Float, size: Float) {
  val r = size * 0.4f
  drawCircle(Color(0xFFFFECB3), center = Offset(cx, cy), radius = r)
  drawCircle(Color(0xFFFFB300), center = Offset(cx, cy), radius = r, style = Stroke(size * 0.02f))
}
