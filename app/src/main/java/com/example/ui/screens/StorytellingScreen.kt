package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SimpleStoryScene
import com.example.data.StoryBook
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.RasterStorySceneViewer
import com.example.ui.theme.*

/**
 * PHASE 1 STORY READER:
 * - Sử dụng ảnh raster (PNG/WebP) làm lớp tranh chính với ContentScale.Fit.
 * - Không dùng Canvas để dựng nhân vật, bối cảnh hay chi tiết cảnh.
 * - Khi chưa có ảnh chính thức, hiển thị raster placeholder (story_placeholder.webp).
 * - Cân đối tỷ lệ giao diện: Header ~8-10%, Tranh ~68-72%, Thẻ lời kể ~20-22%, Nút điều khiển trong Safe Area.
 * - Không có thanh cuộn dọc, các nút hiển thị đầy đủ không bị crop trên mọi kích thước màn hình.
 */
@Composable
fun StorytellingScreen(
  storyBook: StoryBook,
  currentSceneIndex: Int = 0,
  isSpeaking: Boolean = false,
  onNextScene: (Int) -> Unit = {},
  onPrevScene: (Int) -> Unit = {},
  onReplayScene: (SimpleStoryScene) -> Unit = {},
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

  // Tự động phát giọng kể khi đổi cảnh
  LaunchedEffect(activeIndex, storyBook.id) {
    onReplayScene(currentScene)
  }

  val showCelebration = activeIndex == totalScenes - 1

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
    ) {
      // 1. Header tinh gọn (8-10% chiều cao màn hình)
      StoryHeader(
        title = storyBook.titleVi,
        sceneNumber = currentScene.sceneNumber,
        totalScenes = totalScenes,
        isSpeaking = isSpeaking,
        onBackClick = onBackToMenu,
        onReplay = { onReplayScene(currentScene) }
      )

      // Thanh tiến trình mỏng 4dp
      val progress = (activeIndex + 1).toFloat() / totalScenes.toFloat()
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(4.dp)
          .padding(horizontal = 14.dp)
          .clip(RoundedCornerShape(2.dp)),
        color = storyBook.accentColor,
        trackColor = Color(0xFFE0E0E0)
      )

      Spacer(modifier = Modifier.height(4.dp))

      // 2. KHU VỰC TRANH RASTER. Phần chữ nằm riêng bên dưới để không che tranh.
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 10.dp, vertical = 2.dp)
      ) {
        RasterStorySceneViewer(
          scene = currentScene,
          nextScene = nextScene,
          accentColor = storyBook.accentColor,
          onHotspotTap = {
            onReplayScene(currentScene)
          },
          modifier = Modifier.fillMaxSize()
        )
      }

      // 3. THẺ LỜI KỂ TÁCH KHỎI TRANH: luôn nhìn trọn mặt và hành động nhân vật.
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.5.dp, storyBook.accentColor.copy(alpha = 0.55f)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 5.dp)
          .testTag("story_narration_card")
      ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp)) {
          Text(
            text = currentScene.narrationVi,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFF2C3E50),
              fontSize = 15.sp,
              lineHeight = 20.sp
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
          )
          if (currentScene.dialogueVi.isNotBlank()) {
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = currentScene.dialogueVi,
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = storyBook.accentColor,
                fontSize = 14.sp,
                lineHeight = 18.sp
              ),
              maxLines = 2,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }

      // 4. THANH ĐIỀU KHIỂN NẰM TRONG SAFE AREA (Tránh crop nút trên mọi kích thước)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Nút Cảnh Trước
        OutlinedButton(
          onClick = {
            if (activeIndex > 0) {
              activeIndex--
              onPrevScene(activeIndex)
            }
          },
          enabled = activeIndex > 0,
          shape = RoundedCornerShape(14.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
          border = BorderStroke(1.5.dp, if (activeIndex > 0) Color(0xFFBDBDBD) else Color(0xFFE0E0E0)),
          modifier = Modifier
            .weight(1f)
            .height(46.dp)
            .testTag("story_prev_button")
        ) {
          Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Cảnh trước",
            tint = if (activeIndex > 0) Color(0xFF616161) else Color(0xFFBDBDBD),
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            "Trước",
            color = if (activeIndex > 0) Color(0xFF616161) else Color(0xFFBDBDBD),
            fontWeight = FontWeight.Bold,
            fontSize = 13.5.sp
          )
        }

        // Nút Nghe Lại Giọng Kể
        FilledTonalButton(
          onClick = { onReplayScene(currentScene) },
          shape = RoundedCornerShape(14.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
          colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (isSpeaking) Color(0xFFFF5252) else Color(0xFFFFE082)
          ),
          modifier = Modifier
            .weight(1.1f)
            .height(46.dp)
            .testTag("story_replay_button")
        ) {
          Icon(
            Icons.Default.VolumeUp,
            contentDescription = "Nghe lại",
            tint = if (isSpeaking) Color.White else Color(0xFF5D4037),
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(
            if (isSpeaking) "Đang đọc" else "Nghe lại",
            color = if (isSpeaking) Color.White else Color(0xFF5D4037),
            fontWeight = FontWeight.Bold,
            fontSize = 13.5.sp
          )
        }

        // Nút Cảnh Tiếp Theo / Hoàn Thành
        Button(
          onClick = {
            if (activeIndex < totalScenes - 1) {
              activeIndex++
              onNextScene(activeIndex)
            } else {
              onBackToMenu()
            }
          },
          shape = RoundedCornerShape(14.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (activeIndex == totalScenes - 1) Color(0xFFE65100) else Color(0xFF43A047)
          ),
          modifier = Modifier
            .weight(1.25f)
            .height(46.dp)
            .testTag("story_next_button")
        ) {
          Text(
            if (activeIndex == totalScenes - 1) "Xong ⭐" else "Tiếp theo",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.5.sp,
            maxLines = 1
          )
          if (activeIndex < totalScenes - 1) {
            Spacer(modifier = Modifier.width(3.dp))
            Icon(
              Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = "Cảnh sau",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }

    // Hiệu ứng micro duy nhất: Pháo hoa chúc mừng ở cảnh cuối
    ConfettiOverlay(visible = showCelebration)
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
      .padding(horizontal = 10.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Surface(
      shape = CircleShape,
      color = Color.White,
      shadowElevation = 1.5.dp,
      border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
      modifier = Modifier
        .size(38.dp)
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
          modifier = Modifier.size(20.dp)
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
          fontSize = 14.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = "Cảnh $sceneNumber / $totalScenes",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = Color(0xFF757575),
          fontSize = 11.sp
        )
      )
    }

    Surface(
      shape = CircleShape,
      color = if (isSpeaking) Color(0xFFFF5252) else Color.White,
      shadowElevation = 1.5.dp,
      border = BorderStroke(1.dp, if (isSpeaking) Color(0xFFFF5252) else Color(0xFFE0E0E0)),
      modifier = Modifier
        .size(38.dp)
        .clip(CircleShape)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = ripple()
        ) { onReplay() }
        .testTag("story_header_voice")
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          Icons.Default.VolumeUp,
          contentDescription = "Giọng kể",
          tint = if (isSpeaking) Color.White else TextDark,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}
