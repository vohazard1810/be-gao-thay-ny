package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppHeader
import com.example.ui.components.MainGameOptionCard
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.TeacherHeroCard
import com.example.ui.components.TeacherMood
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  isSpeaking: Boolean,
  spokenText: String,
  totalStars: Int,
  onOpenFlashcards: () -> Unit,
  onOpenStory: () -> Unit,
  onOpenQuiz: () -> Unit,
  onTeacherTap: () -> Unit,
  modifier: Modifier = Modifier
) {
  val verticalScrollState = rememberScrollState()

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("home_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(verticalScrollState)
        .padding(horizontal = 14.dp, vertical = 6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // 1. Top Header (Logo + Baby Name + Audio Speaker)
      AppHeader(
        totalStars = totalStars,
        isSpeaking = isSpeaking,
        onSpeakerClick = onTeacherTap
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Teacher Ny Hero Speech Card
      TeacherHeroCard(
        isSpeaking = isSpeaking,
        shortSubtitle = "Bé chọn 1 trò chơi bên dưới nhé! ✨",
        mood = if (isSpeaking) TeacherMood.TALKING else TeacherMood.HAPPY,
        onTap = onTeacherTap
      )

      Spacer(modifier = Modifier.height(16.dp))

      // 3. Section Title
      Text(
        text = "⭐ 3 TRÒ CHƠI HỌC VUI CÙNG THẦY ⭐",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFFB45309),
          fontSize = 14.5.sp,
          letterSpacing = 0.5.sp
        ),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      // 4. Cả 3 trò chơi hiển thị rõ ràng
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Game 1: Thẻ Học Vui
        MainGameOptionCard(
          title = "1. Thẻ Học Vui",
          subtitle = "Khám phá Con vật, Quả, Màu sắc, Số",
          mainIconEmoji = "🎴",
          subEmojis = listOf("🐶", "🍎", "🎨", "🔢"),
          cardColor = Color(0xFFFFECB3),
          accentColor = PastelOrangeDark,
          testTag = "menu_flashcards_button",
          onClick = onOpenFlashcards
        )

        // Game 2: Nghe Thầy Kể Chuyện
        MainGameOptionCard(
          title = "2. Nghe Thầy Kể Chuyện",
          subtitle = "Truyện cổ tích & bài học bé ngoan",
          mainIconEmoji = "📖",
          subEmojis = listOf("🐰", "🐻", "🐱", "✨"),
          cardColor = Color(0xFFF3E5F5),
          accentColor = PastelLavender,
          testTag = "menu_story_button",
          onClick = onOpenStory
        )

        // Game 3: Đố Vui Cùng Thầy
        MainGameOptionCard(
          title = "3. Đố Vui Cùng Thầy",
          subtitle = "Câu đố vui nhộn nhận sao thưởng",
          mainIconEmoji = "🎯",
          subEmojis = listOf("⭐", "💡", "🎉", "🏆"),
          cardColor = Color(0xFFE0F2F1),
          accentColor = PastelMint,
          testTag = "menu_quiz_button",
          onClick = onOpenQuiz
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
