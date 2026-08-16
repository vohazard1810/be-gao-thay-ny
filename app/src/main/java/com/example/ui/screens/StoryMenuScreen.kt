package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoryAssetManifest
import com.example.data.StoryBook
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.theme.TextDark

@Composable
fun StoryMenuScreen(
  isSpeaking: Boolean,
  totalStars: Int,
  onSelectStory: (StoryBook) -> Unit,
  onHomeClick: () -> Unit,
  onSpeakerClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Chỉ đưa cho trẻ những truyện đã đủ toàn bộ ảnh và được duyệt.
  val storyBooks = StoryAssetManifest.storyBooks.filter { it.isPublished }

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("story_menu_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
      // 1. Header
      StoryMenuHeader(
        totalStars = totalStars,
        isSpeaking = isSpeaking,
        onHomeClick = onHomeClick,
        onSpeakerClick = onSpeakerClick
      )

      Spacer(modifier = Modifier.height(10.dp))

      // 2. Banner giới thiệu Thầy Ny
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFFFF8E1),
        shadowElevation = 2.dp,
        border = BorderStroke(1.5.dp, Color(0xFFFFE082)),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple()
          ) { onSpeakerClick() }
          .testTag("story_menu_banner")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFD54F),
            modifier = Modifier.size(46.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "📖",
                fontSize = 24.sp
              )
            }
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "KHO TRUYỆN TRANH CỦA BÉ",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFB45309),
                fontSize = 14.sp
              )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Bé chọn 1 cuốn truyện để cùng nghe Thầy Ny kể nhé! ✨",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF5D4037),
                fontSize = 12.5.sp
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // 3. Danh sách các bộ truyện
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
      ) {
        items(storyBooks, key = { it.id }) { story ->
          StoryBookCard(
            story = story,
            onClick = { onSelectStory(story) }
          )
        }
      }
    }
  }
}

@Composable
private fun StoryBookCard(
  story: StoryBook,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = story.themeColor),
    border = BorderStroke(2.dp, story.accentColor.copy(alpha = 0.5f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    modifier = modifier
      .fillMaxWidth()
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple()
      ) { onClick() }
      .testTag("storybook_card_${story.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Cover Emoji Icon
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.5.dp, story.accentColor.copy(alpha = 0.4f)),
        modifier = Modifier.size(68.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = story.coverEmoji,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
          )
        }
      }

      // Title & Moral lesson
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = story.titleVi,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2C3E50),
            fontSize = 15.sp,
            lineHeight = 20.sp
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Badge Bài học
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = story.accentColor.copy(alpha = 0.15f),
          modifier = Modifier.wrapContentSize()
        ) {
          Text(
            text = "🌱 ${story.moralLessonVi}",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = story.accentColor,
              fontSize = 11.sp
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "${story.totalScenes} Cảnh tranh sống động",
          style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Medium,
            color = Color(0xFF757575),
            fontSize = 11.5.sp
          )
        )
      }

      // Play action button
      Surface(
        shape = CircleShape,
        color = story.accentColor,
        shadowElevation = 2.dp,
        modifier = Modifier.size(40.dp)
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            Icons.Default.PlayArrow,
            contentDescription = "Đọc truyện",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }
  }
}

@Composable
private fun StoryMenuHeader(
  totalStars: Int,
  isSpeaking: Boolean,
  onHomeClick: () -> Unit,
  onSpeakerClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 4.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    // Home button
    Surface(
      shape = CircleShape,
      color = Color.White,
      shadowElevation = 1.5.dp,
      border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple()
        ) { onHomeClick() }
        .testTag("story_menu_home_btn")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          Icons.Default.Home,
          contentDescription = "Trang chủ",
          tint = TextDark,
          modifier = Modifier.size(22.dp)
        )
      }
    }

    // Title
    Text(
      text = "Kể Chuyện Cho Bé",
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.ExtraBold,
        color = TextDark,
        fontSize = 17.sp
      )
    )

    // Right speaker
    Surface(
      shape = CircleShape,
      color = if (isSpeaking) Color(0xFFFF5252) else Color.White,
      shadowElevation = 1.5.dp,
      border = BorderStroke(1.dp, if (isSpeaking) Color(0xFFFF5252) else Color(0xFFE0E0E0)),
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple()
        ) { onSpeakerClick() }
        .testTag("story_menu_speaker_btn")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          Icons.Default.VolumeUp,
          contentDescription = "Giọng đọc",
          tint = if (isSpeaking) Color.White else TextDark,
          modifier = Modifier.size(22.dp)
        )
      }
    }
  }
}
