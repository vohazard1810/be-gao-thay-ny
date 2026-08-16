package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.random.Random

/**
 * Top App Header for Home Screen:
 * - Left: Backpack Logo + Title "Lớp Học Thầy Ny"
 * - Right: Baby Name badge "🌾 bé Gạo" + Big animated Speaker button
 * (No confusing '0' score number)
 */
@Composable
fun AppHeader(
  totalStars: Int = 0,
  isSpeaking: Boolean,
  onSpeakerClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    // Left: Backpack Logo + Title
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier
        .clip(RoundedCornerShape(18.dp))
        .testTag("app_header_logo_title")
    ) {
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = PastelOrangeDark,
        shadowElevation = 3.dp,
        modifier = Modifier.size(46.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(text = "🎒", fontSize = 24.sp)
        }
      }

      Column {
        Text(
          text = "Lớp Học Thầy Ny",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextDark,
            fontSize = 18.sp
          )
        )
        Text(
          text = "Bé 3 tuổi vui học tiếng Việt",
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFD97706),
            fontSize = 12.sp
          )
        )
      }
    }

    // Right Action: Baby Badge + Speaker Toggle Button
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // User name Badge: "🌾 bé Gạo"
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF8DA1)),
        shadowElevation = 1.dp,
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(text = "🌾", fontSize = 13.sp)
          Text(
            text = "bé Gạo",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFD81B60),
              fontSize = 12.5.sp
            )
          )
        }
      }

      // Speaker Toggle Circle Button
      val pulse = rememberInfiniteTransition(label = "speaker_pulse")
      val speakerScale by pulse.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
          animation = tween(380, easing = FastOutSlowInEasing),
          repeatMode = RepeatMode.Reverse
        ),
        label = "spk_scale"
      )

      Surface(
        shape = CircleShape,
        color = if (isSpeaking) Color(0xFFFF5252) else PastelMint,
        shadowElevation = 3.dp,
        modifier = Modifier
          .size(44.dp)
          .scale(speakerScale)
          .clip(CircleShape)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple()
          ) { onSpeakerClick() }
          .testTag("header_speaker_toggle")
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Nghe Thầy Ny nói",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }
  }
}

/**
 * Game Mode Card matching the clean, friendly rounded aesthetic of Flashcards screen:
 * - Large touch target for 3-year-olds
 * - 3D dual ring circular emoji badge
 * - Clear title and fun description
 * - "BÉ CHẠM VÀO ĐÂY ▶" pill button
 */
@Composable
fun MainGameOptionCard(
  title: String,
  subtitle: String,
  mainIconEmoji: String,
  subEmojis: List<String>,
  cardColor: Color,
  accentColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.95f else 1f,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
    label = "game_card_scale"
  )

  Surface(
    shape = RoundedCornerShape(26.dp),
    color = cardColor,
    shadowElevation = 4.dp,
    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White.copy(alpha = 0.9f)),
    modifier = modifier
      .fillMaxWidth()
      .height(98.dp)
      .scale(scale)
      .clip(RoundedCornerShape(26.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(color = accentColor.copy(alpha = 0.3f))
      ) { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Big Circular Emoji Badge with Double Ring
      Surface(
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 3.dp,
        border = androidx.compose.foundation.BorderStroke(2.dp, accentColor),
        modifier = Modifier.size(72.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(text = mainIconEmoji, fontSize = 38.sp)
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Middle Info: Title + Subtitle + Sub-emojis
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextDark,
            fontSize = 17.5.sp
          ),
          maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
              fontWeight = FontWeight.Bold,
              color = TextDark.copy(alpha = 0.75f),
              fontSize = 12.sp
            ),
            maxLines = 1
          )
          subEmojis.take(3).forEach { e ->
            Text(text = e, fontSize = 12.sp)
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Small Pill "CHƠI NGAY ▶"
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = accentColor,
          modifier = Modifier.padding(top = 2.dp)
        ) {
          Text(
            text = "CHƠI CÙNG THẦY ▶",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color.White,
              fontSize = 10.5.sp,
              letterSpacing = 0.3.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }
      }

      // Right: Big Touch Hand Pointer
      Text(
        text = "👉",
        fontSize = 24.sp,
        modifier = Modifier.padding(end = 4.dp)
      )
    }
  }
}

/**
 * Top App Bar for Sub-screens (Flashcards, Storytelling, Quiz)
 * Unified clean bar: Big Home button on left, Title in center, Speaker on right.
 */
@Composable
fun KidTopAppBar(
  title: String,
  onHomeClick: () -> Unit,
  onReplaySpeech: () -> Unit,
  isSpeaking: Boolean = false,
  modifier: Modifier = Modifier
) {
  Surface(
    color = Color.White.copy(alpha = 0.95f),
    shadowElevation = 3.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(horizontal = 14.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Big Home Button
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = PastelAmber,
        shadowElevation = 3.dp,
        modifier = Modifier
          .size(52.dp)
          .clip(RoundedCornerShape(18.dp))
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple()
          ) { onHomeClick() }
          .testTag("home_button")
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Về trang chủ",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
          )
        }
      }

      // Title
      Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.ExtraBold,
          color = TextDark,
          fontSize = 19.sp
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 8.dp)
      )

      // Audio Speaker Button to replay instruction
      val pulseTransition = rememberInfiniteTransition(label = "sub_speaker_pulse")
      val speakerScale by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.18f else 1f,
        animationSpec = infiniteRepeatable(
          animation = tween(380, easing = FastOutSlowInEasing),
          repeatMode = RepeatMode.Reverse
        ),
        label = "sub_speaker_scale"
      )

      Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (isSpeaking) Color(0xFFFF5252) else PastelMint,
        shadowElevation = 3.dp,
        modifier = Modifier
          .size(52.dp)
          .scale(speakerScale)
          .clip(RoundedCornerShape(18.dp))
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple()
          ) { onReplaySpeech() }
          .testTag("replay_speech_button")
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Nghe Thầy Ny nói lại",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
          )
        }
      }
    }
  }
}

/**
 * Pastel Dotted Background
 */
@Composable
fun PastelBackgroundWithDots(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            PastelCreamBg,
            Color(0xFFFFF7E2),
            Color(0xFFFFF2D6)
          )
        )
      )
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val dotColors = listOf(
        Color(0xFFFFCC80).copy(alpha = 0.25f),
        Color(0xFFCE93D8).copy(alpha = 0.20f),
        Color(0xFF80CBC4).copy(alpha = 0.22f),
        Color(0xFFFFAB91).copy(alpha = 0.22f)
      )
      val stepX = 54.dp.toPx()
      val stepY = 54.dp.toPx()

      var colorIdx = 0
      var x = stepX / 2
      while (x < size.width) {
        var y = stepY / 2
        while (y < size.height) {
          drawCircle(
            color = dotColors[colorIdx % dotColors.size],
            radius = 3.5f,
            center = Offset(x, y)
          )
          colorIdx++
          y += stepY
        }
        x += stepX
      }
    }

    content()
  }
}

/**
 * Confetti overlay for celebrations
 */
@Composable
fun ConfettiOverlay(
  visible: Boolean,
  modifier: Modifier = Modifier
) {
  if (!visible) return

  val infiniteTransition = rememberInfiniteTransition(label = "confetti")
  val progress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "confetti_progress"
  )

  val particles = remember {
    List(40) {
      ConfettiParticle(
        startX = Random.nextFloat(),
        speed = 0.7f + Random.nextFloat() * 0.6f,
        color = listOf(
          PastelAmber,
          PastelLavender,
          PastelMint,
          PastelRose,
          PastelGold,
          Color(0xFF42A5F5)
        ).random(),
        size = 8f + Random.nextFloat() * 12f
      )
    }
  }

  Canvas(modifier = modifier.fillMaxSize()) {
    particles.forEach { p ->
      val currentY = (p.speed * progress * size.height * 1.2f) % size.height
      val currentX = p.startX * size.width + kotlin.math.sin(progress * 8f + p.startX * 10f) * 20f

      drawCircle(
        color = p.color,
        radius = p.size / 2,
        center = Offset(currentX, currentY)
      )
    }
  }
}

private data class ConfettiParticle(
  val startX: Float,
  val speed: Float,
  val color: Color,
  val size: Float
)
