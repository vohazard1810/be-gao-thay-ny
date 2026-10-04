package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.SoundQuizOption
import com.example.model.SoundQuizQuestion
import com.example.ui.components.BeGaoMascot
import com.example.ui.components.BeGaoMood
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.ThoBongMascot
import com.example.ui.theme.*

/**
 * Màn chơi mini-game: "Đoán Âm Thanh Vui Nhộn"
 * - Lắng nghe âm thanh muôn loài (tiếng động vật, xe cộ).
 * - Chọn đáp án đúng từ các hình vẽ sinh động.
 * - Thưởng sao và pháo hoa giấy rộn rã cho bé mầm non.
 */
@Composable
fun SoundQuizScreen(
  question: SoundQuizQuestion,
  questionIndex: Int,
  totalQuestions: Int,
  totalStars: Int,
  isSpeaking: Boolean,
  showCelebration: Boolean,
  onPlaySound: () -> Unit,
  onSelectOption: (SoundQuizOption) -> Unit,
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  // Pulsing animation for speaker button
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_speaker")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.96f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_speaker_scale"
  )

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("sound_quiz_screen")
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        KidTopAppBar(
          title = "Đoán Âm Thanh Vui Nhộn",
          onHomeClick = onHomeClick,
          onReplaySpeech = onReplaySpeech,
          isSpeaking = isSpeaking
        )

        Column(
          modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 14.dp, vertical = 6.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Progress & Stars Bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color.White,
              shadowElevation = 2.dp,
              border = BorderStroke(1.5.dp, Color(0xFFCE93D8))
            ) {
              Text(
                text = "Câu ${questionIndex + 1}/$totalQuestions",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF8E24AA),
                  fontSize = 13.5.sp
                ),
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
              )
            }

            Surface(
              shape = RoundedCornerShape(16.dp),
              color = PastelGold,
              shadowElevation = 3.dp
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(text = "⭐", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "$totalStars Sao",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFBF360C),
                    fontSize = 14.5.sp
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Sound Hero Card with Mascots & Play Button
          Surface(
            shape = RoundedCornerShape(26.dp),
            color = Color.White,
            shadowElevation = 4.dp,
            border = BorderStroke(2.dp, Color(0xFFE1BEE7)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
              ) {
                BeGaoMascot(
                  size = 68.dp,
                  mood = if (showCelebration) BeGaoMood.CHEERING else BeGaoMood.LISTENING,
                  showNameBadge = false
                )
                Spacer(modifier = Modifier.width(8.dp))
                ThoBongMascot(
                  size = 54.dp,
                  showNameBadge = false
                )
              }

              Spacer(modifier = Modifier.height(12.dp))

              Text(
                text = question.promptVi,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF4A148C),
                  fontSize = 16.sp,
                  textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Big Sound Replay Button
              Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFF9C27B0),
                shadowElevation = 4.dp,
                modifier = Modifier
                  .scale(pulseScale)
                  .clickable { onPlaySound() }
                  .testTag("play_sound_btn")
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Nghe âm thanh",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "BẤM NGHE ÂM THANH 🔊",
                    style = MaterialTheme.typography.labelLarge.copy(
                      fontWeight = FontWeight.ExtraBold,
                      color = Color.White,
                      fontSize = 14.5.sp
                    )
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // 3 Sound Quiz Options
          Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            question.options.forEach { option ->
              SoundQuizOptionCard(
                option = option,
                onClick = { onSelectOption(option) }
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))
        }
      }

      // Confetti & Celebration when answered correctly
      if (showCelebration) {
        ConfettiOverlay()
      }
    }
  }
}

@Composable
private fun SoundQuizOptionCard(
  option: SoundQuizOption,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.94f else 1f,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
    label = "option_scale"
  )

  Surface(
    shape = RoundedCornerShape(22.dp),
    color = Color.White,
    shadowElevation = 3.dp,
    border = BorderStroke(2.dp, Color(0xFFCE93D8)),
    modifier = modifier
      .fillMaxWidth()
      .scale(scale)
      .clip(RoundedCornerShape(22.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(color = Color(0xFFBA68C8))
      ) { onClick() }
      .testTag("sound_option_${option.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        shape = CircleShape,
        color = Color(0xFFF3E5F5),
        border = BorderStroke(1.dp, Color(0xFFE1BEE7)),
        modifier = Modifier.size(56.dp)
      ) {
        val assetUrl = when (option.id) {
          "dog" -> "file:///android_asset/items/cun_dom.png"
          "cat" -> "file:///android_asset/items/meo_may.png"
          "squirrel" -> "file:///android_asset/items/soc_nau.png"
          "rabbit" -> "file:///android_asset/items/tho_bong.png"
          "car" -> "file:///android_asset/items/xe_o_to.png"
          "train" -> "file:///android_asset/items/duong_ray.png"
          "scarf" -> "file:///android_asset/items/chiec_khan.png"
          "be_gao" -> "file:///android_asset/items/be_gao.png"
          "thay_ny" -> "file:///android_asset/items/thay_ny.png"
          else -> null
        }
        if (assetUrl != null) {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(assetUrl)
              .crossfade(true)
              .build(),
            contentDescription = option.nameVi,
            contentScale = ContentScale.Fit,
            modifier = Modifier
              .fillMaxSize()
              .padding(4.dp)
          )
        } else {
          Box(contentAlignment = Alignment.Center) {
            Text(text = option.emoji, fontSize = 28.sp)
          }
        }
      }

      Spacer(modifier = Modifier.width(16.dp))

      Text(
        text = option.nameVi,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = TextDark,
          fontSize = 17.sp
        )
      )
    }
  }
}
