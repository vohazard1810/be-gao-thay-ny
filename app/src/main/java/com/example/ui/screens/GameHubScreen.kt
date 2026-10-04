package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BeGaoMascot
import com.example.ui.components.BeGaoMood
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.ThoBongMascot
import com.example.ui.theme.*

/**
 * Game Hub Screen: Khu vui chơi mầm non đa dạng của Bé Gạo & Thầy Ny
 */
@Composable
fun GameHubScreen(
  totalStars: Int,
  isSpeaking: Boolean,
  onOpenMemoryMatch: () -> Unit,
  onOpenQuiz: () -> Unit,
  onOpenFlashcards: () -> Unit,
  onOpenStory: () -> Unit,
  onOpenStickerBook: () -> Unit,
  onOpenColoring: () -> Unit = {},
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("game_hub_screen")
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      KidTopAppBar(
        title = "Khu Vui Chơi Của Bé",
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
        // Star & Reward Badge Banner
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFFF3E0),
            border = BorderStroke(1.5.dp, Color(0xFFFFB74D)),
            shadowElevation = 2.dp,
            modifier = Modifier.clickable { onOpenStickerBook() }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("📒", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Sổ Dán Sticker",
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFE65100),
                fontSize = 13.sp
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(16.dp),
            color = PastelGold,
            shadowElevation = 3.dp
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("⭐", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "$totalStars Sao",
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFBF360C),
                fontSize = 14.5.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Center Playground Welcome with Bé Gạo & Thỏ Bông
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color.White,
          shadowElevation = 3.dp,
          border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.Bottom) {
              BeGaoMascot(
                size = 72.dp,
                mood = BeGaoMood.HAPPY,
                showNameBadge = false
              )
              Spacer(modifier = Modifier.width(4.dp))
              ThoBongMascot(
                size = 58.dp,
                showNameBadge = false
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "BÉ CHỌN TRÒ CHƠI NHÉ!",
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFB45309),
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Cùng chơi đố vui, lật thẻ bài và gom sticker xinh xắn nè! ✨",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF5D4037),
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4 Game Choices
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Game 1: Lật Thẻ Tìm Bạn (NEW!)
          HubGameItemCard(
            title = "1. Lật Thẻ Tìm Bạn 🧩",
            subtitle = "Lật thẻ 3D tìm cặp hình giống nhau",
            badgeText = "MỚI NHẤT ⭐",
            color = Color(0xFFE8F5E9),
            accentColor = Color(0xFF43A047),
            testTag = "hub_memory_match_btn",
            onClick = onOpenMemoryMatch
          )

          // Game 2: Đố Vui Cùng Thầy
          HubGameItemCard(
            title = "2. Đố Vui Cùng Thầy 🎯",
            subtitle = "Trả lời câu đố nhận sao sáng",
            badgeText = "THƯỞNG SAO 🌟",
            color = Color(0xFFE0F2F1),
            accentColor = Color(0xFF00897B),
            testTag = "hub_quiz_btn",
            onClick = onOpenQuiz
          )

          // Game 3: Thẻ Học Thần Kỳ
          HubGameItemCard(
            title = "3. Thẻ Học Vui 🎴",
            subtitle = "Học con vật, trái cây, màu sắc, số",
            badgeText = "KHÁM PHÁ 🔍",
            color = Color(0xFFFFF8E1),
            accentColor = Color(0xFFFFA000),
            testTag = "hub_flashcards_btn",
            onClick = onOpenFlashcards
          )

          // Game 4: Nghe Thầy Kể Chuyện
          HubGameItemCard(
            title = "4. Nghe Kể Chuyện 📖",
            subtitle = "Truyện tranh ý nghĩa cho bé ngoan",
            badgeText = "TRUYỆN HAY 🐰",
            color = Color(0xFFF3E5F5),
            accentColor = Color(0xFF8E24AA),
            testTag = "hub_story_btn",
            onClick = onOpenStory
          )

          // Game 5: Bé Tập Tô Màu
          HubGameItemCard(
            title = "5. Bé Tập Tô Màu 🎨",
            subtitle = "Tô màu pastel Bé Gạo & Thỏ Bông",
            badgeText = "SÁNG TẠO 🌸",
            color = Color(0xFFFCE4EC),
            accentColor = Color(0xFFEC407A),
            testTag = "hub_coloring_btn",
            onClick = onOpenColoring
          )
        }

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

@Composable
private fun HubGameItemCard(
  title: String,
  subtitle: String,
  badgeText: String,
  color: Color,
  accentColor: Color,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by androidx.compose.animation.core.animateFloatAsState(
    targetValue = if (isPressed) 0.95f else 1f,
    animationSpec = androidx.compose.animation.core.spring(
      dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
    ),
    label = "hub_item_scale"
  )

  Surface(
    shape = RoundedCornerShape(22.dp),
    color = color,
    shadowElevation = 3.dp,
    border = BorderStroke(2.dp, accentColor.copy(alpha = 0.5f)),
    modifier = modifier
      .fillMaxWidth()
      .scale(scale)
      .clip(RoundedCornerShape(22.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple(color = accentColor.copy(alpha = 0.3f))
      ) { onClick() }
      .testTag(testTag)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = TextDark,
              fontSize = 16.sp
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = accentColor.copy(alpha = 0.15f)
          ) {
            Text(
              text = badgeText,
              color = accentColor,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 10.sp,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(
            color = Color(0xFF616161),
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
          )
        )
      }

      Surface(
        shape = CircleShape,
        color = accentColor,
        shadowElevation = 2.dp,
        modifier = Modifier.size(38.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text("▶", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
