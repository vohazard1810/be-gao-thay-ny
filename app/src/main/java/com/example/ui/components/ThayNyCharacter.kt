package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class TeacherMood {
  HAPPY,         // Friendly greeting, waving hand
  CELEBRATING,   // Joyful thumbs-up, star sparkles, cheering eyes
  ENCOURAGING,   // Reassuring warm smile, gentle hug with golden star
  TALKING        // Curious teaching pose, pointing / reading storybook
}

/**
 * TeacherHeroCard: High-quality cartoon Teacher Ny card with dynamic poses,
 * soft glowing aura, animated live equalizer sound waves, and clear toddler subtitles.
 */
@Composable
fun TeacherHeroCard(
  isSpeaking: Boolean,
  shortSubtitle: String,
  mood: TeacherMood,
  onTap: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val cardScale by animateFloatAsState(
    targetValue = if (isPressed) 0.96f else 1f,
    animationSpec = spring(stiffness = Spring.StiffnessLow),
    label = "hero_card_scale"
  )

  Surface(
    shape = RoundedCornerShape(28.dp),
    color = Color.White,
    shadowElevation = 5.dp,
    border = androidx.compose.foundation.BorderStroke(2.dp, PastelCardBorder),
    modifier = modifier
      .fillMaxWidth()
      .scale(cardScale)
      .clip(RoundedCornerShape(28.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(color = PastelAmber.copy(alpha = 0.2f))
      ) { onTap() }
      .testTag("teacher_hero_card")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Left: Cute Cartoon Half-body Teacher Ny Illustration with dynamic aura glow
      Box(
        modifier = Modifier.size(132.dp),
        contentAlignment = Alignment.Center
      ) {
        ThayNyRaster(
          size = 124.dp,
          isSpeaking = isSpeaking,
          mood = mood,
          compact = true
        )

        // Pill badge "Thầy Ny" at bottom of character
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFFFB703),
          shadowElevation = 3.dp,
          border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White),
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .offset(y = 2.dp)
        ) {
          Text(
            text = "Thầy Ny 🌟",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF5D4037),
              fontSize = 11.5.sp
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.5.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Right: Title + Short Subtitle + Live Audio Wave Pill
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Text(
            text = when (mood) {
              TeacherMood.CELEBRATING -> "Thầy Khen Bé! 🎉"
              TeacherMood.ENCOURAGING -> "Thầy Cổ Vũ Bé! 💖"
              TeacherMood.TALKING -> "Thầy Đang Kể Chuyện ✨"
              TeacherMood.HAPPY -> "Thầy Ny Dễ Thương ✨"
            },
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = TextDark,
              fontSize = 16.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = if (isSpeaking) {
            "Thầy đang trò chuyện cùng bé... 🔊"
          } else {
            shortSubtitle.ifBlank { "Chào bé Gạo yêu quý! 🌾" }
          },
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (isSpeaking) PastelOrangeDark else TextDark.copy(alpha = 0.85f),
            fontSize = 13.sp
          ),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(6.dp))

        SpeakingStatusPill(isSpeaking = isSpeaking)
      }
    }
  }
}

/**
 * Animated Pill showing audio activity and dynamic equalizer wave
 */
@Composable
fun SpeakingStatusPill(
  isSpeaking: Boolean,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = if (isSpeaking) Color(0xFFFF5252) else Color(0xFFFF9E44),
    shadowElevation = 2.dp,
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      if (isSpeaking) {
        LiveEqualizerBars(modifier = Modifier.size(16.dp, 12.dp))
      } else {
        Icon(
          imageVector = Icons.Default.VolumeUp,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(15.dp)
        )
      }
      Text(
        text = if (isSpeaking) "Đang nói chuyện..." else "Chạm để nghe lại",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.ExtraBold,
          color = Color.White,
          fontSize = 11.5.sp
        )
      )
    }
  }
}

/**
 * 3-bar animated sound equalizer
 */
@Composable
fun LiveEqualizerBars(modifier: Modifier = Modifier) {
  val anim = rememberInfiniteTransition(label = "eq_bars")
  val bar1 by anim.animateFloat(
    initialValue = 0.25f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(tween(240, easing = LinearEasing), RepeatMode.Reverse),
    label = "bar1"
  )
  val bar2 by anim.animateFloat(
    initialValue = 0.85f,
    targetValue = 0.2f,
    animationSpec = infiniteRepeatable(tween(300, easing = LinearEasing), RepeatMode.Reverse),
    label = "bar2"
  )
  val bar3 by anim.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(tween(270, easing = LinearEasing), RepeatMode.Reverse),
    label = "bar3"
  )

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(2.dp),
    verticalAlignment = Alignment.Bottom
  ) {
    Box(
      modifier = Modifier
        .width(3.dp)
        .fillMaxHeight(bar1)
        .background(Color.White, RoundedCornerShape(1.5.dp))
    )
    Box(
      modifier = Modifier
        .width(3.dp)
        .fillMaxHeight(bar2)
        .background(Color.White, RoundedCornerShape(1.5.dp))
    )
    Box(
      modifier = Modifier
        .width(3.dp)
        .fillMaxHeight(bar3)
        .background(Color.White, RoundedCornerShape(1.5.dp))
    )
  }
}

/**
 * High-quality Duolingo ABC / Khan Kids style Cartoon Teacher Ny:
 * - Round, adorable, warm-toned cartoon face.
 * - Big bright sparkling expressive cartoon eyes with double sparkle reflections.
 * - Charming warm smile with upper teeth line and cute rosy cheeks.
 * - Soft layered cartoon black hair with a stylish bouncy front tuft.
 * - Bright teacher cardigan over a collared shirt with golden star emblem.
 * - Dynamic hands & props (Book, Thumbs-up, Waving, Pointing).
 */
@Composable
fun ThayNyCartoonHalfBody(
  size: Dp = 104.dp,
  isSpeaking: Boolean = false,
  mood: TeacherMood = TeacherMood.HAPPY,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "cartoon_ny_anim")

  val breathingBounce by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = if (isSpeaking) 1.05f else 1.01f,
    animationSpec = infiniteRepeatable(
      animation = tween(380, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "bounce"
  )

  val mouthMotion by infiniteTransition.animateFloat(
    initialValue = 0.20f,
    targetValue = 0.90f,
    animationSpec = infiniteRepeatable(
      animation = tween(160, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "mouth_anim"
  )

  val auraPulse by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.16f,
    animationSpec = infiniteRepeatable(
      animation = tween(650, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "aura_pulse"
  )

  val waveAngle by infiniteTransition.animateFloat(
    initialValue = -7f,
    targetValue = 7f,
    animationSpec = infiniteRepeatable(
      animation = tween(380, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "wave_angle"
  )

  val actualMouth = if (isSpeaking) mouthMotion else when (mood) {
    TeacherMood.CELEBRATING -> 0.75f
    TeacherMood.TALKING -> 0.50f
    TeacherMood.HAPPY -> 0.30f
    TeacherMood.ENCOURAGING -> 0.25f
  }

  Box(
    modifier = modifier
      .size(size)
      .scale(breathingBounce),
    contentAlignment = Alignment.Center
  ) {
    // 1. Dual Glowing Aura Rings when speaking
    if (isSpeaking) {
      Surface(
        shape = CircleShape,
        color = Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
          2.5.dp,
          Color(0xFFFF80AB).copy(alpha = (1.2f - auraPulse).coerceIn(0f, 0.75f))
        ),
        modifier = Modifier
          .fillMaxSize()
          .scale(auraPulse)
      ) {}

      Surface(
        shape = CircleShape,
        color = Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(
          2.dp,
          Color(0xFFFFD54F).copy(alpha = (1.1f - auraPulse).coerceIn(0f, 0.6f))
        ),
        modifier = Modifier
          .fillMaxSize()
          .scale(auraPulse * 1.08f)
      ) {}
    }

    // 2. Outer decorative circle border
    Surface(
      shape = CircleShape,
      color = Color.Transparent,
      border = androidx.compose.foundation.BorderStroke(
        2.5.dp,
        if (isSpeaking) Color(0xFFFF5252) else Color(0xFFFFB703)
      ),
      modifier = Modifier.fillMaxSize()
    ) {}

    // 3. Inner circle background with warm soft radial gradient
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(3.5.dp)
        .clip(CircleShape)
        .background(
          Brush.radialGradient(
            colors = listOf(
              Color(0xFFFFFDE7),
              Color(0xFFFFF8E1),
              Color(0xFFFFECB3)
            )
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Canvas(modifier = Modifier.fillMaxSize()) {
        drawCartoonTeacherNy(
          mouthOpen = actualMouth,
          mood = mood,
          isSpeaking = isSpeaking,
          waveAngle = waveAngle
        )
      }
    }
  }
}

/**
 * Cartoon Draw Scope for Teacher Ny:
 * High-end kid-friendly design with smooth curves and rich details.
 */
private fun DrawScope.drawCartoonTeacherNy(
  mouthOpen: Float,
  mood: TeacherMood,
  isSpeaking: Boolean,
  waveAngle: Float
) {
  val w = size.width
  val h = size.height
  val centerX = w / 2f
  val centerY = h * 0.43f
  val headRadius = w * 0.29f

  val skinColor = Color(0xFFFDE8D3)
  val skinShadow = Color(0xFFF5CEB0)
  val blushColor = Color(0xFFFF9EAA).copy(alpha = 0.55f)
  val hairColor = Color(0xFF42281D)
  val hairHighlight = Color(0xFF6B442A)
  val hairLight = Color(0xFF8D5B3A)
  val shirtSlateBlue = Color(0xFF3F647E)
  val shirtCollar = Color(0xFF4A728C)
  val cardiganMustard = Color(0xFFE5A93B)
  val cardiganShadow = Color(0xFFC88A20)
  val badgeTeal = Color(0xFF38A3A5)
  val buttonBrown = Color(0xFF4E342E)
  val starGold = Color(0xFFFFD700)

  // ----------------------------------------------------
  // 1. Shoulders & Upper Body (Teacher Outfit: Mustard Cardigan over Slate-Blue Shirt)
  // ----------------------------------------------------
  val bodyTop = centerY + headRadius * 0.68f
  val torsoPath = Path().apply {
    moveTo(centerX - w * 0.44f, h)
    cubicTo(
      centerX - w * 0.38f, bodyTop + h * 0.10f,
      centerX - w * 0.20f, bodyTop + h * 0.04f,
      centerX, bodyTop + h * 0.04f
    )
    cubicTo(
      centerX + w * 0.20f, bodyTop + h * 0.04f,
      centerX + w * 0.38f, bodyTop + h * 0.10f,
      centerX + w * 0.44f, h
    )
    close()
  }
  drawPath(path = torsoPath, color = cardiganMustard)

  // Cardigan Left and Right Rib Trim & Subtle Knit Texture
  drawPath(
    path = Path().apply {
      moveTo(centerX - w * 0.44f, h)
      cubicTo(
        centerX - w * 0.38f, bodyTop + h * 0.10f,
        centerX - w * 0.20f, bodyTop + h * 0.04f,
        centerX - w * 0.14f, bodyTop + h * 0.04f
      )
      lineTo(centerX - w * 0.10f, h)
      close()
    },
    color = cardiganShadow.copy(alpha = 0.35f)
  )

  // Slate-Blue Shirt Center Inset (V-Neck Cardigan opening)
  val shirtInset = Path().apply {
    moveTo(centerX - w * 0.14f, bodyTop + h * 0.04f)
    lineTo(centerX + w * 0.14f, bodyTop + h * 0.04f)
    lineTo(centerX + w * 0.08f, h)
    lineTo(centerX - w * 0.08f, h)
    close()
  }
  drawPath(path = shirtInset, color = shirtSlateBlue)

  // Shirt Button Placket line
  drawLine(
    color = shirtCollar.copy(alpha = 0.8f),
    start = Offset(centerX, bodyTop + h * 0.04f),
    end = Offset(centerX, h),
    strokeWidth = 2.5f
  )

  // ----------------------------------------------------
  // 2. Neck & Slate-Blue Collared Shirt
  // ----------------------------------------------------
  drawRoundRect(
    color = skinShadow,
    topLeft = Offset(centerX - w * 0.09f, centerY + headRadius * 0.45f),
    size = Size(w * 0.18f, h * 0.15f),
    cornerRadius = CornerRadius(8f, 8f)
  )

  // Slate-Blue Shirt Collar wings
  val collarL = Path().apply {
    moveTo(centerX, bodyTop + h * 0.12f)
    lineTo(centerX - w * 0.13f, bodyTop + h * 0.02f)
    lineTo(centerX - w * 0.03f, bodyTop + h * 0.06f)
    close()
  }
  drawPath(path = collarL, color = shirtCollar)

  val collarR = Path().apply {
    moveTo(centerX, bodyTop + h * 0.12f)
    lineTo(centerX + w * 0.13f, bodyTop + h * 0.02f)
    lineTo(centerX + w * 0.03f, bodyTop + h * 0.06f)
    close()
  }
  drawPath(path = collarR, color = shirtCollar)

  // Cardigan 4 Wooden Buttons down front
  for (i in 0..2) {
    val btnY = bodyTop + h * 0.15f + i * (h * 0.08f)
    drawCircle(color = buttonBrown, radius = w * 0.020f, center = Offset(centerX + w * 0.09f, btnY))
    drawCircle(color = Color(0xFF8D6E63), radius = w * 0.008f, center = Offset(centerX + w * 0.088f, btnY - 1.5f))
  }

  // Round Teal Enamel Badge on Thầy Ny's left chest
  val badgeCenter = Offset(centerX - w * 0.22f, bodyTop + h * 0.12f)
  drawCircle(color = Color(0xFF22577A), radius = w * 0.038f, center = badgeCenter)
  drawCircle(color = badgeTeal, radius = w * 0.032f, center = badgeCenter)
  drawCircle(color = Color.White.copy(alpha = 0.85f), radius = w * 0.010f, center = Offset(badgeCenter.x - 2f, badgeCenter.y - 2f))

  // ----------------------------------------------------
  // 3. Dynamic Gesturing Hands / Props based on TeacherMood
  // ----------------------------------------------------
  when (mood) {
    TeacherMood.CELEBRATING -> {
      // Right hand giving a cheerful thumbs-up 👍 + mini stars
      val handX = centerX + w * 0.36f
      val handY = centerY + h * 0.08f + waveAngle * 0.3f

      // Arm sleeve
      val armL = Path().apply {
        moveTo(centerX + w * 0.22f, bodyTop + h * 0.08f)
        lineTo(handX - w * 0.03f, handY + h * 0.10f)
        lineTo(handX + w * 0.03f, handY + h * 0.08f)
        lineTo(centerX + w * 0.38f, bodyTop + h * 0.16f)
        close()
      }
      drawPath(path = armL, color = cardiganMustard)

      // Palm
      drawCircle(color = skinColor, radius = w * 0.065f, center = Offset(handX, handY))
      // Thumbs-up
      drawRoundRect(
        color = skinColor,
        topLeft = Offset(handX - w * 0.025f, handY - h * 0.07f),
        size = Size(w * 0.05f, h * 0.065f),
        cornerRadius = CornerRadius(8f, 8f)
      )
      // Celebration sparkles
      drawCircle(color = starGold, radius = w * 0.035f, center = Offset(handX + w * 0.07f, handY - h * 0.08f))
      drawCircle(color = Color(0xFFFF4081), radius = w * 0.025f, center = Offset(handX - w * 0.07f, handY - h * 0.06f))
    }

    TeacherMood.TALKING -> {
      // Holding open colorful Storybook 📖 in hands
      val bookX = centerX
      val bookY = bodyTop + h * 0.18f
      val bookW = w * 0.44f
      val bookH = h * 0.18f

      // Book cover (soft lavender)
      drawRoundRect(
        color = Color(0xFF7E57C2),
        topLeft = Offset(bookX - bookW / 2, bookY),
        size = Size(bookW, bookH),
        cornerRadius = CornerRadius(6f, 6f)
      )
      // Book pages (crisp white)
      drawRoundRect(
        color = Color.White,
        topLeft = Offset(bookX - bookW / 2 + 3f, bookY + 3f),
        size = Size(bookW - 6f, bookH - 6f),
        cornerRadius = CornerRadius(4f, 4f)
      )
      // Book center spine
      drawLine(
        color = Color(0xFF5E35B1),
        start = Offset(bookX, bookY + 2f),
        end = Offset(bookX, bookY + bookH - 2f),
        strokeWidth = 2.5f
      )
      // Left and right cartoon thumbs holding the book
      drawCircle(color = skinColor, radius = w * 0.045f, center = Offset(bookX - bookW * 0.42f, bookY + bookH * 0.5f))
      drawCircle(color = skinColor, radius = w * 0.045f, center = Offset(bookX + bookW * 0.42f, bookY + bookH * 0.5f))
    }

    TeacherMood.ENCOURAGING -> {
      // Reassuring hands gently placed near heart offering a big golden star ⭐
      val starX = centerX
      val starY = bodyTop + h * 0.20f
      drawCircle(color = skinColor, radius = w * 0.05f, center = Offset(starX - w * 0.12f, starY))
      drawCircle(color = skinColor, radius = w * 0.05f, center = Offset(starX + w * 0.12f, starY))

      // Golden Heart / Star Badge
      drawCircle(color = starGold, radius = w * 0.06f, center = Offset(starX, starY))
      drawCircle(color = Color.White, radius = w * 0.02f, center = Offset(starX - w * 0.015f, starY - h * 0.015f))
    }

    TeacherMood.HAPPY -> {
      // Friendly waving cartoon hand 👋
      val handX = centerX - w * 0.35f
      val handY = centerY + h * 0.12f + waveAngle * 0.3f

      val armPath = Path().apply {
        moveTo(centerX - w * 0.20f, bodyTop + h * 0.08f)
        lineTo(handX + w * 0.04f, handY + h * 0.10f)
        lineTo(handX - w * 0.04f, handY + h * 0.08f)
        lineTo(centerX - w * 0.35f, bodyTop + h * 0.15f)
        close()
      }
      drawPath(path = armPath, color = cardiganMustard)

      // Palm
      drawCircle(color = skinColor, radius = w * 0.06f, center = Offset(handX, handY))
      // Cute Fingers
      drawCircle(color = skinColor, radius = w * 0.022f, center = Offset(handX - w * 0.035f, handY - h * 0.03f))
      drawCircle(color = skinColor, radius = w * 0.022f, center = Offset(handX, handY - h * 0.045f))
      drawCircle(color = skinColor, radius = w * 0.022f, center = Offset(handX + w * 0.035f, handY - h * 0.035f))
    }
  }

  // ----------------------------------------------------
  // 4. Ears
  // ----------------------------------------------------
  drawCircle(
    color = skinShadow,
    radius = w * 0.075f,
    center = Offset(centerX - headRadius * 0.94f, centerY + h * 0.02f)
  )
  drawCircle(
    color = skinShadow,
    radius = w * 0.075f,
    center = Offset(centerX + headRadius * 0.94f, centerY + h * 0.02f)
  )

  // ----------------------------------------------------
  // 5. Round Chubby Face (Duolingo / Khan Kids style)
  // ----------------------------------------------------
  drawCircle(
    color = skinColor,
    center = Offset(centerX, centerY),
    radius = headRadius
  )

  // ----------------------------------------------------
  // 6. Hair: Wavy Dark Brown Hair with Soft Voluminous Waves
  // ----------------------------------------------------
  val hairPath = Path().apply {
    moveTo(centerX - headRadius * 1.05f, centerY - h * 0.02f)
    cubicTo(
      centerX - headRadius * 1.18f, centerY - headRadius * 1.38f,
      centerX + headRadius * 1.18f, centerY - headRadius * 1.38f,
      centerX + headRadius * 1.05f, centerY - h * 0.02f
    )
    cubicTo(
      centerX + headRadius * 0.85f, centerY - headRadius * 0.38f,
      centerX + headRadius * 0.40f, centerY - headRadius * 0.80f,
      centerX + w * 0.05f, centerY - headRadius * 0.60f
    )
    cubicTo(
      centerX - headRadius * 0.30f, centerY - headRadius * 0.85f,
      centerX - headRadius * 0.80f, centerY - headRadius * 0.38f,
      centerX - headRadius * 1.05f, centerY - h * 0.02f
    )
    close()
  }
  drawPath(path = hairPath, color = hairColor)

  // Wavy Hair Left & Right Curls
  drawCircle(color = hairColor, radius = w * 0.12f, center = Offset(centerX - headRadius * 0.85f, centerY - headRadius * 0.75f))
  drawCircle(color = hairColor, radius = w * 0.14f, center = Offset(centerX + headRadius * 0.82f, centerY - headRadius * 0.78f))
  drawCircle(color = hairHighlight, radius = w * 0.10f, center = Offset(centerX + headRadius * 0.45f, centerY - headRadius * 1.15f))
  drawCircle(color = hairHighlight, radius = w * 0.11f, center = Offset(centerX - headRadius * 0.35f, centerY - headRadius * 1.12f))

  // Front Wavy Bangs / Tuft swept to side
  val frontTuft = Path().apply {
    moveTo(centerX - w * 0.18f, centerY - headRadius * 0.75f)
    quadraticTo(
      centerX - w * 0.04f, centerY - headRadius * 0.35f,
      centerX + w * 0.14f, centerY - headRadius * 0.70f
    )
    quadraticTo(
      centerX + w * 0.02f, centerY - headRadius * 0.88f,
      centerX - w * 0.18f, centerY - headRadius * 0.75f
    )
    close()
  }
  drawPath(path = frontTuft, color = hairHighlight)

  // Subtle Wavy Shine Highlights
  drawArc(
    color = hairLight.copy(alpha = 0.85f),
    startAngle = 200f,
    sweepAngle = 100f,
    useCenter = false,
    topLeft = Offset(centerX - w * 0.22f, centerY - headRadius * 1.25f),
    size = Size(w * 0.44f, h * 0.16f),
    style = Stroke(width = 3.5f)
  )

  // ----------------------------------------------------
  // 7. Eyebrows
  // ----------------------------------------------------
  val leftBrowY = centerY - h * 0.14f
  val rightBrowY = centerY - h * 0.14f
  drawArc(
    color = hairColor,
    startAngle = 200f,
    sweepAngle = 140f,
    useCenter = false,
    topLeft = Offset(centerX - w * 0.19f, leftBrowY),
    size = Size(w * 0.13f, h * 0.05f),
    style = Stroke(width = 3.5f)
  )
  drawArc(
    color = hairColor,
    startAngle = 200f,
    sweepAngle = 140f,
    useCenter = false,
    topLeft = Offset(centerX + w * 0.06f, rightBrowY),
    size = Size(w * 0.13f, h * 0.05f),
    style = Stroke(width = 3.5f)
  )

  // ----------------------------------------------------
  // 8. Big Expressive Cartoon Eyes with Sparkling Highlights
  // ----------------------------------------------------
  val eyesY = centerY - h * 0.045f
  val eyeRadius = w * 0.052f

  if (mood == TeacherMood.CELEBRATING) {
    // Joyful crescent laughing eyes (^_^)
    drawArc(
      color = hairColor,
      startAngle = 200f,
      sweepAngle = 140f,
      useCenter = false,
      topLeft = Offset(centerX - w * 0.19f, eyesY - h * 0.02f),
      size = Size(w * 0.13f, h * 0.07f),
      style = Stroke(width = 4.5f)
    )
    drawArc(
      color = hairColor,
      startAngle = 200f,
      sweepAngle = 140f,
      useCenter = false,
      topLeft = Offset(centerX + w * 0.06f, eyesY - h * 0.02f),
      size = Size(w * 0.13f, h * 0.07f),
      style = Stroke(width = 4.5f)
    )
  } else {
    // Left Eye Outer Pupil
    drawCircle(
      color = hairColor,
      radius = eyeRadius,
      center = Offset(centerX - w * 0.125f, eyesY)
    )
    // Left Eye Double Sparkles
    drawCircle(
      color = Color.White,
      radius = eyeRadius * 0.42f,
      center = Offset(centerX - w * 0.140f, eyesY - h * 0.012f)
    )
    drawCircle(
      color = Color.White,
      radius = eyeRadius * 0.22f,
      center = Offset(centerX - w * 0.110f, eyesY + h * 0.010f)
    )

    // Right Eye Outer Pupil
    drawCircle(
      color = hairColor,
      radius = eyeRadius,
      center = Offset(centerX + w * 0.125f, eyesY)
    )
    // Right Eye Double Sparkles
    drawCircle(
      color = Color.White,
      radius = eyeRadius * 0.42f,
      center = Offset(centerX + w * 0.110f, eyesY - h * 0.012f)
    )
    drawCircle(
      color = Color.White,
      radius = eyeRadius * 0.22f,
      center = Offset(centerX + w * 0.140f, eyesY + h * 0.010f)
    )
  }

  // ----------------------------------------------------
  // 9. Rosy Glowing Cheeks
  // ----------------------------------------------------
  drawCircle(
    color = blushColor,
    radius = w * 0.065f,
    center = Offset(centerX - w * 0.21f, centerY + h * 0.04f)
  )
  drawCircle(
    color = blushColor,
    radius = w * 0.065f,
    center = Offset(centerX + w * 0.21f, centerY + h * 0.04f)
  )

  // ----------------------------------------------------
  // 10. Cute Button Nose
  // ----------------------------------------------------
  drawArc(
    color = Color(0xFFE65100).copy(alpha = 0.55f),
    startAngle = 0f,
    sweepAngle = 180f,
    useCenter = false,
    topLeft = Offset(centerX - w * 0.035f, centerY + h * 0.012f),
    size = Size(w * 0.07f, h * 0.035f),
    style = Stroke(width = 2.5f)
  )

  // ----------------------------------------------------
  // 11. Animated Warm Smile with Upper Teeth & Pink Tongue
  // ----------------------------------------------------
  val mouthY = centerY + h * 0.085f
  val mouthWidth = w * 0.26f
  val mouthHeight = (h * 0.12f) * mouthOpen.coerceIn(0.20f, 1f)

  val mouthPath = Path().apply {
    moveTo(centerX - mouthWidth / 2, mouthY)
    quadraticTo(
      centerX, mouthY + mouthHeight * 1.45f,
      centerX + mouthWidth / 2, mouthY
    )
    close()
  }
  drawPath(path = mouthPath, color = Color(0xFFD81B60))

  // Upper White Teeth
  val teethPath = Path().apply {
    moveTo(centerX - mouthWidth * 0.40f, mouthY)
    lineTo(centerX + mouthWidth * 0.40f, mouthY)
    quadraticTo(
      centerX, mouthY + mouthHeight * 0.55f,
      centerX - mouthWidth * 0.40f, mouthY
    )
    close()
  }
  drawPath(path = teethPath, color = Color.White)

  // Cute Pink Tongue
  val tonguePath = Path().apply {
    moveTo(centerX - mouthWidth * 0.25f, mouthY + mouthHeight * 0.65f)
    quadraticTo(
      centerX, mouthY + mouthHeight * 1.35f,
      centerX + mouthWidth * 0.25f, mouthY + mouthHeight * 0.65f
    )
    close()
  }
  drawPath(path = tonguePath, color = Color(0xFFFF8DA1))

  // Smile Outline
  drawArc(
    color = Color(0xFFAD1457),
    startAngle = 5f,
    sweepAngle = 170f,
    useCenter = false,
    topLeft = Offset(centerX - mouthWidth / 2, mouthY - h * 0.008f),
    size = Size(mouthWidth, h * 0.055f),
    style = Stroke(width = 3f)
  )
}

/**
 * General helper component when ThayNyCharacter is needed standalone
 */
@Composable
fun ThayNyCharacter(
  modifier: Modifier = Modifier,
  size: Dp = 120.dp,
  isSpeaking: Boolean = false,
  speechText: String = "",
  mood: TeacherMood = TeacherMood.HAPPY,
  onTap: () -> Unit = {}
) {
  TeacherHeroCard(
    isSpeaking = isSpeaking,
    shortSubtitle = speechText,
    mood = mood,
    onTap = onTap,
    modifier = modifier
  )
}
