package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SimpleStoryScene
import com.example.data.StoryBook
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.RasterStorySceneViewer
import com.example.ui.components.ThayNyRaster
import com.example.ui.theme.*

/**
 * PHASE 3 STORY READER (Toddler-optimized for 3-year-olds):
 * - Mỗi cảnh chỉ hiển thị 1 câu chính (8-12 từ, Font 21sp ExtraBold, tối đa 2 dòng).
 * - TTS autoplay chỉ kích hoạt một lần duy nhất qua LaunchedEffect ở đây.
 * - Khung ảnh vuông chuẩn 1:1 với ContentScale.Fit.
 * - Progress Indicator dạng 4 sao/chấm tròn to rõ ràng với Accessibility Semantic.
 * - Hàng nút điều khiển: Trước (56dp), Loa nghe lại (56dp), Tiếp theo/Hoàn thành (CTA 60dp).
 */
@Composable
fun StorytellingScreen(
  storyBook: StoryBook,
  currentSceneIndex: Int = 0,
  isSpeaking: Boolean = false,
  onNextScene: (Int) -> Unit = {},
  onPrevScene: (Int) -> Unit = {},
  onReplayScene: (SimpleStoryScene) -> Unit = {},
  onHotspotTap: (String) -> Unit = {},
  onBackToMenu: () -> Unit,
  modifier: Modifier = Modifier
) {
  var activeIndex by remember(storyBook.id) {
    mutableIntStateOf(currentSceneIndex.coerceIn(0, storyBook.scenes.size - 1))
  }
  val totalScenes = storyBook.scenes.size
  val currentScene = remember(activeIndex, storyBook.id) {
    storyBook.scenes.getOrElse(activeIndex) { storyBook.scenes.first() }
  }
  val nextScene = remember(activeIndex, storyBook.id) {
    if (activeIndex < totalScenes - 1) storyBook.scenes[activeIndex + 1] else null
  }

  // Tự động phát TTS duy nhất khi mở/đổi cảnh
  LaunchedEffect(activeIndex, storyBook.id) {
    onReplayScene(currentScene)
  }

  val isLastScene = activeIndex == totalScenes - 1

  // Câu hiển thị duy nhất (8-12 từ, ưu tiên dialogueVi nếu có)
  val mainDisplayText = remember(currentScene) {
    if (currentScene.dialogueVi.isNotBlank()) {
      currentScene.dialogueVi.trim('“', '”', '"', ' ')
    } else {
      currentScene.narrationVi
    }
  }

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("storytelling_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .padding(horizontal = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // 1. Header tinh gọn
      StoryHeader(
        title = storyBook.titleVi,
        sceneNumber = currentScene.sceneNumber,
        totalScenes = totalScenes,
        isSpeaking = isSpeaking,
        onBackClick = onBackToMenu,
        onReplay = { onReplayScene(currentScene) }
      )

      Spacer(modifier = Modifier.height(4.dp))

      // 2. Progress indicator: Sao/Chấm tròn trực quan cho bé 3 tuổi
      StoryProgressStars(
        currentIndex = activeIndex,
        totalScenes = totalScenes,
        accentColor = storyBook.accentColor,
        modifier = Modifier.padding(vertical = 2.dp)
      )

      Spacer(modifier = Modifier.height(4.dp))

      // 3. KHU VỰC TRANH RASTER TỶ LỆ 1:1
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(vertical = 2.dp),
        contentAlignment = Alignment.Center
      ) {
        RasterStorySceneViewer(
          scene = currentScene,
          nextScene = nextScene,
          accentColor = storyBook.accentColor,
          onHotspotTap = onHotspotTap,
          modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f)
        )
      }

      // 4. THẺ 1 CÂU DUY NHẤT (Font 21sp ExtraBold, tối đa 2 dòng)
      Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        border = BorderStroke(2.dp, storyBook.accentColor.copy(alpha = 0.65f)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp)
          .testTag("story_narration_card")
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          StorySpeakerBadge(
            speakerId = currentScene.speakerId,
            isSpeaking = isSpeaking
          )
          Text(
            text = mainDisplayText,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF2C3E50),
              fontSize = 20.sp,
              lineHeight = 26.sp,
              textAlign = TextAlign.Center
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )
        }
      }

      // 5. HÀNG ĐIỀU KHIỂN CHO BÉ 3 TUỔI (Trước: 56dp, Loa: 56dp, Tiếp/Xong: CTA 60dp)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp, top = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Nút Cảnh Trước (56dp)
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = if (activeIndex > 0) Color.White else Color(0xFFEEEEEE),
          border = BorderStroke(1.5.dp, if (activeIndex > 0) Color(0xFFBDBDBD) else Color(0xFFE0E0E0)),
          shadowElevation = if (activeIndex > 0) 2.dp else 0.dp,
          modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
              enabled = activeIndex > 0,
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple()
            ) {
              if (activeIndex > 0) {
                activeIndex--
                onPrevScene(activeIndex)
              }
            }
            .testTag("story_prev_button")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Cảnh trước",
              tint = if (activeIndex > 0) Color(0xFF424242) else Color(0xFF9E9E9E),
              modifier = Modifier.size(26.dp)
            )
          }
        }

        // Nút Nghe Lại Giọng Kể (56dp)
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = if (isSpeaking) Color(0xFF26A69A) else Color(0xFFFFF3E0),
          border = BorderStroke(1.5.dp, if (isSpeaking) Color(0xFF00897B) else Color(0xFFFFB74D)),
          shadowElevation = 2.dp,
          modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple()
            ) {
              onReplayScene(currentScene)
            }
            .testTag("story_replay_button")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.Replay,
              contentDescription = if (isSpeaking) "Đang đọc" else "Nghe lại",
              tint = if (isSpeaking) Color.White else Color(0xFFE65100),
              modifier = Modifier.size(26.dp)
            )
          }
        }

        // Nút Cảnh Tiếp Theo / Hoàn Thành (CTA to nhất hàng, cao 60dp)
        Button(
          onClick = {
            if (activeIndex < totalScenes - 1) {
              activeIndex++
              onNextScene(activeIndex)
            } else {
              onBackToMenu()
            }
          },
          shape = RoundedCornerShape(18.dp),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isLastScene) Color(0xFF2E7D32) else Color(0xFFFB8C00)
          ),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
          modifier = Modifier
            .weight(1f)
            .height(60.dp)
            .testTag("story_next_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = if (isLastScene) "Hoàn thành ⭐" else "Tiếp theo",
              color = Color.White,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 17.sp,
              maxLines = 1
            )
            if (!isLastScene) {
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Cảnh sau",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
              )
            }
          }
        }
      }
    }

    // Hiệu ứng pháo hoa chúc mừng ở cảnh cuối
    ConfettiOverlay(visible = isLastScene)
  }
}

/**
 * Visual speaker identity without adding a second story sentence.
 * Thầy Ny uses the approved raster; story characters use stable color/name cues.
 */
@Composable
private fun StorySpeakerBadge(
  speakerId: String,
  isSpeaking: Boolean,
  modifier: Modifier = Modifier
) {
  if (speakerId == "thay_ny") {
    Column(
      modifier = modifier.width(58.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      ThayNyRaster(
        size = 52.dp,
        isSpeaking = isSpeaking,
        storytelling = true,
        compact = true
      )
      Text("Thầy Ny", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF8D6E00))
    }
    return
  }

  val (shortName, fullName, badgeColor) = when (speakerId) {
    "be_gao" -> Triple("Gạo", "Bé Gạo", Color(0xFF81C784))
    "tho_bong" -> Triple("Thỏ", "Thỏ Bông", Color(0xFFBA68C8))
    "meo_may" -> Triple("Mây", "Mèo Mây", Color(0xFFF48FB1))
    "cun_dom" -> Triple("Cún", "Cún Đốm", Color(0xFF64B5F6))
    else -> Triple("Ny", "Thầy Ny", Color(0xFFFFC857))
  }

  Column(
    modifier = modifier.width(58.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Surface(
      shape = CircleShape,
      color = badgeColor,
      shadowElevation = 2.dp,
      border = BorderStroke(2.dp, Color.White),
      modifier = Modifier.size(46.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(shortName, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
      }
    }
    Text(
      fullName,
      fontSize = 9.sp,
      fontWeight = FontWeight.ExtraBold,
      color = badgeColor,
      maxLines = 1
    )
  }
}

/**
 * Progress Indicator dạng 4 sao/chấm tròn to rõ ràng cho bé 3 tuổi
 */
@Composable
private fun StoryProgressStars(
  currentIndex: Int,
  totalScenes: Int,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  val accessibilityDesc = "Cảnh ${currentIndex + 1} trên $totalScenes"

  Row(
    modifier = modifier
      .semantics { contentDescription = accessibilityDesc }
      .testTag("story_progress_stars"),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 0 until totalScenes) {
      val isPassed = i < currentIndex
      val isCurrent = i == currentIndex

      if (isPassed) {
        // Ngôi sao vàng đã hoàn thành
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = "Cảnh ${i + 1} đã xem",
          tint = Color(0xFFFFB300),
          modifier = Modifier.size(20.dp)
        )
      } else if (isCurrent) {
        // Chấm tròn hiện tại to hơn và viền nổi bật
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(accentColor)
            .border(2.dp, Color.White, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(Color.White)
          )
        }
      } else {
        // Chấm tròn chưa xem pastel xám nhạt
        Box(
          modifier = Modifier
            .size(14.dp)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0))
        )
      }
    }
  }
}

/**
 * Header tinh gọn với chiều cao chuẩn ~44dp
 */
@Composable
private fun StoryHeader(
  title: String,
  sceneNumber: Int,
  totalScenes: Int,
  isSpeaking: Boolean,
  onBackClick: () -> Unit,
  onReplay: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
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
        ) { onBackClick() }
        .testTag("story_header_back")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Danh sách truyện",
          tint = TextDark,
          modifier = Modifier.size(22.dp)
        )
      }
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.ExtraBold,
          color = TextDark,
          fontSize = 15.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = "Cảnh $sceneNumber / $totalScenes",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = Color(0xFF757575),
          fontSize = 12.sp
        )
      )
    }

    Surface(
      shape = CircleShape,
      color = if (isSpeaking) Color(0xFF26A69A) else Color.White,
      shadowElevation = 1.5.dp,
      border = BorderStroke(1.dp, if (isSpeaking) Color(0xFF00897B) else Color(0xFFE0E0E0)),
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple()
        ) { onReplay() }
        .testTag("story_header_voice")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          if (isSpeaking) Icons.Default.VolumeUp else Icons.Default.Replay,
          contentDescription = if (isSpeaking) "Đang kể chuyện" else "Nghe lại cảnh",
          tint = if (isSpeaking) Color.White else TextDark,
          modifier = Modifier.size(22.dp)
        )
      }
    }
  }
}
