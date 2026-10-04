package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MemoryCard
import com.example.ui.components.BeGaoMascot
import com.example.ui.components.BeGaoMood
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.RealPhotoThumbnail
import com.example.ui.components.ThoBongMascot
import com.example.ui.theme.*

/**
 * Màn chơi Game Lật Thẻ Ghép Cặp (Memory Match Game cho bé 2-4 tuổi):
 * - 6 thẻ bài bo tròn lớn (3 cặp hình dễ thương).
 * - Hiệu ứng lật 3D 180 độ chân thực.
 * - Mặt úp: Ngôi sao bí mật lấp lánh.
 * - Mặt ngửa: Hình các bạn động vật/trái cây quen thuộc.
 * - Bé Gạo & Thỏ Bông đứng cổ vũ ở dưới chân màn hình.
 */
@Composable
fun MemoryMatchScreen(
  cards: List<MemoryCard>,
  matchedPairsCount: Int,
  totalPairs: Int,
  starsEarned: Int,
  isSpeaking: Boolean,
  showCelebration: Boolean,
  onCardClick: (MemoryCard) -> Unit,
  onResetGame: () -> Unit,
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isGameWon = matchedPairsCount == totalPairs && totalPairs > 0

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("memory_match_screen")
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // 1. Top Bar
      KidTopAppBar(
        title = "Lật Thẻ Tìm Bạn",
        onHomeClick = onHomeClick,
        onReplaySpeech = onReplaySpeech,
        isSpeaking = isSpeaking
      )

      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 14.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Progress status banner
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            border = BorderStroke(1.5.dp, PastelCardBorder)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("🧩", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Đã tìm: $matchedPairsCount/$totalPairs cặp",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF2E7D32),
                  fontSize = 13.5.sp
                )
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

        Spacer(modifier = Modifier.height(10.dp))

        // Guide text
        Text(
          text = if (isGameWon) "🎉 HOAN HÔ! BÉ ĐÃ TÌM ĐỦ TẤT CẢ CẶP BẠN! 🎉" else "👇 Bé chạm lật 2 thẻ giống nhau nhé! 👇",
          style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.ExtraBold,
            color = if (isGameWon) Color(0xFF2E7D32) else Color(0xFFD97706),
            fontSize = 13.5.sp
          ),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 6 3D Flip Memory Cards Grid (2 columns x 3 rows)
        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
          items(cards, key = { it.id }) { card ->
            FlipMemoryCardItem(
              card = card,
              onClick = { onCardClick(card) }
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottom Companion or Win Action
        if (isGameWon) {
          Button(
            onClick = onResetGame,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(58.dp)
              .testTag("memory_replay_button")
          ) {
            Text(
              text = "Chơi ván mới cùng Bé Gạo 🔄",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 16.5.sp,
              color = Color.White
            )
          }
        } else {
          // Companions encouraging the child
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
          ) {
            BeGaoMascot(
              size = 76.dp,
              mood = if (showCelebration) BeGaoMood.CHEERING else BeGaoMood.HAPPY,
              showNameBadge = false
            )
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color.White,
              border = BorderStroke(1.dp, Color(0xFFFFD54F)),
              shadowElevation = 2.dp,
              modifier = Modifier.padding(bottom = 8.dp)
            ) {
              Text(
                text = "“Cố lên bạn nhỏ ơi! Bạn tìm giỏi lắm!” 🌟",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5D4037),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
            ThoBongMascot(
              size = 64.dp,
              isCheering = showCelebration,
              showNameBadge = false
            )
          }
        }
      }
    }

    // Confetti celebration
    ConfettiOverlay(visible = showCelebration || isGameWon)
  }
}

/**
 * 3D Flip Card Component:
 * Sử dụng rotationY để tạo góc lật 3D mượt mà cho bé mầm non.
 */
@Composable
private fun FlipMemoryCardItem(
  card: MemoryCard,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val rotation by animateFloatAsState(
    targetValue = if (card.isFaceUp || card.isMatched) 180f else 0f,
    animationSpec = tween(durationMillis = 380, easing = FastOutSlowInEasing),
    label = "card_flip_${card.id}"
  )

  val isFrontVisible = rotation > 90f

  val bounceScale by animateFloatAsState(
    targetValue = if (card.isMatched) 1.04f else 1f,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
    label = "card_matched_scale"
  )

  Surface(
    shape = RoundedCornerShape(22.dp),
    color = if (isFrontVisible) card.cardColor else Color.White,
    shadowElevation = if (card.isMatched) 5.dp else 3.dp,
    border = BorderStroke(
      width = if (card.isMatched) 3.dp else 2.dp,
      color = if (card.isMatched) Color(0xFF4CAF50) else Color(0xFFFFE082)
    ),
    modifier = modifier
      .fillMaxWidth()
      .height(130.dp)
      .scale(bounceScale)
      .graphicsLayer {
        rotationY = rotation
        cameraDistance = 12f * density
      }
      .clip(RoundedCornerShape(22.dp))
      .clickable(enabled = !card.isFaceUp && !card.isMatched) { onClick() }
      .testTag("memory_card_${card.id}")
  ) {
    if (isFrontVisible) {
      // Mặt ngửa: Phải xoay ngược lại 180 độ để chữ và hình không bị lật gương
      Box(
        modifier = Modifier
          .fillMaxSize()
          .graphicsLayer { rotationY = 180f }
          .padding(8.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          RealPhotoThumbnail(
            photoUrl = card.photoUrl,
            fallbackEmoji = card.emoji,
            cardColor = card.cardColor,
            itemId = card.matchKey,
            size = 72.dp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White.copy(alpha = 0.95f),
            shadowElevation = 1.dp
          ) {
            Text(
              text = card.nameVi,
              fontWeight = FontWeight.ExtraBold,
              color = TextDark,
              fontSize = 13.5.sp,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
          }
        }
      }
    } else {
      // Mặt úp: Ngôi sao bí mật lấp lánh
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              listOf(Color(0xFFFFF9E6), Color(0xFFFFECB3))
            )
          )
          .padding(8.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFB703),
            shadowElevation = 2.dp,
            modifier = Modifier.size(52.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("⭐", fontSize = 26.sp)
            }
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Chạm lật quà ✨",
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFFB45309)
          )
        }
      }
    }
  }
}
