package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizOption
import com.example.model.QuizQuestion
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.RealPhotoThumbnail
import com.example.ui.components.TeacherHeroCard
import com.example.ui.components.TeacherMood
import com.example.ui.theme.*

@Composable
fun QuizGameScreen(
  currentQuestion: QuizQuestion,
  questionIndex: Int,
  totalQuestions: Int,
  starsEarned: Int,
  isSpeaking: Boolean,
  spokenText: String,
  showCelebration: Boolean,
  teacherMood: TeacherMood,
  onSelectOption: (QuizOption) -> Unit,
  onNextQuestion: () -> Unit = {},
  onReplayQuestion: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("quiz_game_screen")
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Top AppBar with Home button & Replay speaker
      KidTopAppBar(
        title = "Đố Vui Cùng Thầy",
        onHomeClick = onHomeClick,
        onReplaySpeech = onReplayQuestion,
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
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PastelCardBorder)
          ) {
            Text(
              text = "Câu ${questionIndex + 1}/$totalQuestions",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = PastelMint,
                fontSize = 13.5.sp
              ),
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(16.dp),
            color = PastelGold,
            shadowElevation = 3.dp
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "⭐", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "$starsEarned Sao",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFFBF360C),
                  fontSize = 14.5.sp
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Center Teacher Ny with Half-Body Illustration & Dynamic Mood (Pointing / Cheerful / Encouraging)
        TeacherHeroCard(
          isSpeaking = isSpeaking,
          shortSubtitle = spokenText.ifBlank { currentQuestion.spokenTextVi },
          mood = teacherMood,
          onTap = onReplayQuestion
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Big Question Prompt Card with Visual Clue
        Surface(
          shape = RoundedCornerShape(22.dp),
          color = Color(0xFFFFF9E6),
          shadowElevation = 3.dp,
          border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFD54F)),
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable { onReplayQuestion() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = CircleShape,
              color = PastelOrangeDark,
              modifier = Modifier.size(36.dp)
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.VolumeUp,
                  contentDescription = "Nghe câu hỏi",
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = currentQuestion.questionTextVi,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
                fontSize = 16.sp,
                lineHeight = 21.sp
              ),
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Heading for toddler: "BÉ CHẠM VÀO HÌNH ĐÚNG NHÉ! 👇"
        Text(
          text = "👇 BÉ CHẠM VÀO HÌNH ĐÚNG NHÉ! 👇",
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFD97706),
            fontSize = 13.sp,
            letterSpacing = 0.4.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 2 GIANT PICTURE ANSWER OPTIONS (Visual first, toddler identifies directly by picture)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          currentQuestion.options.take(2).forEach { option ->
            BigPictureQuizOptionCard(
              option = option,
              onClick = { onSelectOption(option) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Nút lớn "Chơi tiếp ⭐" xuất hiện khi trả lời đúng
        AnimatedVisibility(
          visible = showCelebration,
          enter = fadeIn() + expandVertically(),
          exit = fadeOut() + shrinkVertically()
        ) {
          Button(
            onClick = onNextQuestion,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color(0xFF2E7D32)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp)
              .testTag("quiz_next_question_button")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(
                text = "Chơi tiếp ⭐",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }

    // Confetti celebration overlay
    ConfettiOverlay(visible = showCelebration)
  }
}

/**
 * BigPictureQuizOptionCard:
 * - Huge central picture/emoji occupying the vast majority of the button area.
 * - Toddler-friendly tactile design with spring bouncy touch interaction.
 * - Secondary name caption below so child easily recognizes answer visually.
 */
@Composable
fun BigPictureQuizOptionCard(
  option: QuizOption,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.93f else 1f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "option_scale"
  )

  Surface(
    shape = RoundedCornerShape(24.dp),
    color = option.color,
    shadowElevation = 4.dp,
    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
    modifier = modifier
      .aspectRatio(0.86f)
      .scale(scale)
      .clip(RoundedCornerShape(24.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(color = Color.White.copy(alpha = 0.5f))
      ) {
        onClick()
      }
      .testTag("quiz_option_${option.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // GIANT Photorealistic Picture Box (Primary Focus for Toddler)
      RealPhotoThumbnail(
        photoUrl = option.photoUrl,
        fallbackEmoji = option.emoji,
        cardColor = option.color,
        itemId = option.id,
        modifier = Modifier.weight(1f),
        size = 118.dp,
        showRealBadge = false,
        isAlphabetOrNumber = false
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Center Column: Label pill
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = Color.White.copy(alpha = 0.95f),
          shadowElevation = 1.dp
        ) {
          Text(
            text = option.labelVi,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = TextDark,
              fontSize = 16.sp
            ),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.5.dp),
            maxLines = 1
          )
        }

      }
    }
  }
}
