package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * ToddlerBuddyBar:
 * Thanh bạn đồng hành tương tác cho bé: Bé Gạo & Thỏ Bông
 * Xuất hiện ở trang chủ hoặc màn chơi game.
 * Khi bé chạm vào Bé Gạo hoặc Thỏ Bông, các bạn sẽ nhún nhảy và nói chuyện đáng yêu.
 */
@Composable
fun ToddlerBuddyBar(
  isCheering: Boolean = false,
  bubbleText: String = "",
  onBeGaoTap: () -> Unit,
  onThoBongTap: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(24.dp),
    color = Color.White.copy(alpha = 0.95f),
    shadowElevation = 3.dp,
    border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Left: Bé Gạo & Thỏ Bông standing together
      Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        BeGaoMascot(
          size = 76.dp,
          mood = if (isCheering) BeGaoMood.CHEERING else BeGaoMood.HAPPY,
          onTap = onBeGaoTap,
          showNameBadge = false
        )

        ThoBongMascot(
          size = 62.dp,
          isCheering = isCheering,
          onTap = onThoBongTap,
          showNameBadge = false
        )
      }

      // Right: Cute Speech Bubble
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(start = 12.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFFFF9E6),
          border = BorderStroke(1.dp, Color(0xFFFFE082)),
          shadowElevation = 1.dp
        ) {
          Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)) {
            Text(
              text = if (isCheering) "🎉 Bé Gạo & Thỏ Bông khen bé!" else "🌾 Bạn đồng hành của bé",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color(0xFFE65100)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = bubbleText.ifBlank { "Chạm vào Gạo hoặc Thỏ Bông để cùng chơi nhé!" },
              fontSize = 12.5.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF3E2723),
              lineHeight = 16.sp
            )
          }
        }
      }
    }
  }
}
