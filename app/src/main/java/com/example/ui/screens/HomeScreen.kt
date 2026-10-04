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
  onOpenMemoryMatch: () -> Unit = {},
  onOpenStickerBook: () -> Unit = {},
  onOpenColoring: () -> Unit = {},
  onBeGaoTap: () -> Unit = {},
  onThoBongTap: () -> Unit = {},
  isBgmEnabled: Boolean = false,
  onBgmToggle: () -> Unit = {},
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
      // 1. Top Header (Logo + Baby Name + BGM + Audio Speaker)
      AppHeader(
        totalStars = totalStars,
        isSpeaking = isSpeaking,
        onSpeakerClick = onTeacherTap,
        isBgmEnabled = isBgmEnabled,
        onBgmToggle = onBgmToggle
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Teacher Ny Hero Speech Card
      TeacherHeroCard(
        isSpeaking = isSpeaking,
        shortSubtitle = "Bé chọn 1 trò chơi bên dưới nhé! ✨",
        mood = if (isSpeaking) TeacherMood.TALKING else TeacherMood.HAPPY,
        onTap = onTeacherTap
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 3. Toddler Buddy Bar: Bé Gạo & Thỏ Bông đồng hành cùng bé!
      ToddlerBuddyBar(
        isCheering = isSpeaking,
        bubbleText = spokenText.ifBlank { "Chào bạn nhỏ! Cùng Bé Gạo và Thầy Ny chơi nào! 🌸" },
        onBeGaoTap = onBeGaoTap,
        onThoBongTap = onThoBongTap
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 4. Section Title
      Text(
        text = "⭐ KHU TRÒ CHƠI HỌC VUI CỦA BÉ ⭐",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.ExtraBold,
          color = Color(0xFFB45309),
          fontSize = 14.5.sp,
          letterSpacing = 0.5.sp
        ),
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(12.dp))

      // 5. Danh sách trò chơi phong phú
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Game 1: Lật Thẻ Tìm Bạn (MỚI NHẤT)
        MainGameOptionCard(
          title = "1. Lật Thẻ Tìm Bạn",
          subtitle = "Lật thẻ 3D rèn trí nhớ tìm bạn giống nhau",
          mainIconEmoji = "🧩",
          subEmojis = listOf("🐰", "🐶", "⭐", "🎉"),
          cardColor = Color(0xFFE8F5E9),
          accentColor = Color(0xFF43A047),
          testTag = "menu_memory_match_button",
          onClick = onOpenMemoryMatch
        )

        // Game 2: Thẻ Học Vui
        MainGameOptionCard(
          title = "2. Thẻ Học Vui",
          subtitle = "Khám phá Con vật, Quả, Màu sắc, Số",
          mainIconEmoji = "🎴",
          subEmojis = listOf("🐶", "🍎", "🎨", "🔢"),
          cardColor = Color(0xFFFFECB3),
          accentColor = PastelOrangeDark,
          testTag = "menu_flashcards_button",
          onClick = onOpenFlashcards
        )

        // Game 3: Nghe Thầy Kể Chuyện
        MainGameOptionCard(
          title = "3. Nghe Thầy Kể Chuyện",
          subtitle = "Truyện cổ tích & bài học bé ngoan",
          mainIconEmoji = "📖",
          subEmojis = listOf("🐰", "🐻", "🐱", "✨"),
          cardColor = Color(0xFFF3E5F5),
          accentColor = PastelLavender,
          testTag = "menu_story_button",
          onClick = onOpenStory
        )

        // Game 4: Đố Vui Cùng Thầy
        MainGameOptionCard(
          title = "4. Đố Vui Cùng Thầy",
          subtitle = "Câu đố vui nhộn nhận sao thưởng",
          mainIconEmoji = "🎯",
          subEmojis = listOf("⭐", "💡", "🎉", "🏆"),
          cardColor = Color(0xFFE0F2F1),
          accentColor = PastelMint,
          testTag = "menu_quiz_button",
          onClick = onOpenQuiz
        )

        // Game 5: Sổ Dán Sticker Bé Ngoan
        MainGameOptionCard(
          title = "5. Sổ Dán Sticker",
          subtitle = "Bộ sưu tập sticker đáng yêu đổi bằng sao",
          mainIconEmoji = "📒",
          subEmojis = listOf("🎨", "🌟", "🌸", "🐰"),
          cardColor = Color(0xFFFFF3E0),
          accentColor = Color(0xFFFB8C00),
          testTag = "menu_sticker_button",
          onClick = onOpenStickerBook
        )

        // Game 6: Bé Tập Tô Màu
        MainGameOptionCard(
          title = "6. Bé Tập Tô Màu",
          subtitle = "Tô màu pastel ngộ nghĩnh cùng Bé Gạo & Thỏ Bông",
          mainIconEmoji = "🎨",
          subEmojis = listOf("🌸", "🐰", "🐿️", "✨"),
          cardColor = Color(0xFFFCE4EC),
          accentColor = Color(0xFFEC407A),
          testTag = "menu_coloring_button",
          onClick = onOpenColoring
        )
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
