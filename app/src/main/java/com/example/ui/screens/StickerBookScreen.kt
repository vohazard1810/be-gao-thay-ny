package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.model.StickerReward
import com.example.ui.components.BeGaoMascot
import com.example.ui.components.BeGaoMood
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.theme.*

/**
 * StickerBookScreen: Sổ Dán Sticker Bé Ngoan
 * Trẻ mầm non thu thập các sticker ngộ nghĩnh qua các bài học và trò chơi.
 */
@Composable
fun StickerBookScreen(
  totalStars: Int,
  stickers: List<StickerReward>,
  isSpeaking: Boolean,
  onStickerTap: (StickerReward) -> Unit,
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val unlockedCount = stickers.count { it.isUnlocked }

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("sticker_book_screen")
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      KidTopAppBar(
        title = "Sổ Dán Sticker Bé Ngoan",
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
        // Banner progress
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFFF8E1),
            border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
            shadowElevation = 2.dp
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("🎨", fontSize = 16.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Đã dán: $unlockedCount/${stickers.size} hình",
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
                fontSize = 14.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "🌟 Chơi trò chơi để nhận thêm sticker dán vào sổ nhé! 🌟",
          fontWeight = FontWeight.Bold,
          color = Color(0xFF5D4037),
          fontSize = 12.5.sp,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Sticker Grid
        LazyVerticalGrid(
          columns = GridCells.Fixed(3),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {
          items(stickers, key = { it.id }) { sticker ->
            StickerCardItem(
              sticker = sticker,
              onClick = { onStickerTap(sticker) }
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Bottom Companion
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White,
          shadowElevation = 2.dp,
          border = BorderStroke(1.dp, Color(0xFFFFE082)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            BeGaoMascot(
              size = 54.dp,
              mood = BeGaoMood.CHEERING,
              showNameBadge = false
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "“Bé Gạo thích nhất là cùng bạn dán thật nhiều sticker lấp lánh!” ✨",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF5D4037)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun StickerCardItem(
  sticker: StickerReward,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(18.dp),
    color = if (sticker.isUnlocked) Color.White else Color(0xFFF5F5F5),
    shadowElevation = if (sticker.isUnlocked) 3.dp else 1.dp,
    border = BorderStroke(
      width = if (sticker.isUnlocked) 2.dp else 1.dp,
      color = if (sticker.isUnlocked) Color(0xFFFFD54F) else Color(0xFFE0E0E0)
    ),
    modifier = modifier
      .fillMaxWidth()
      .height(115.dp)
      .clip(RoundedCornerShape(18.dp))
      .clickable { onClick() }
      .testTag("sticker_${sticker.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      if (sticker.isUnlocked) {
        val assetUrl = when (sticker.id) {
          "stk_begao" -> "file:///android_asset/items/be_gao.png"
          "stk_thobong" -> "file:///android_asset/items/tho_bong.png"
          "stk_meomay" -> "file:///android_asset/items/meo_may.png"
          "stk_cundom" -> "file:///android_asset/items/cun_dom.png"
          "stk_socnau" -> "file:///android_asset/items/soc_nau.png"
          "stk_thayny" -> "file:///android_asset/items/thay_ny.png"
          "stk_xeoto" -> "file:///android_asset/items/xe_o_to.png"
          "stk_chieckhan" -> "file:///android_asset/items/chiec_khan.png"
          "stk_duongray" -> "file:///android_asset/items/duong_ray.png"
          "stk_quyen_sach" -> "file:///android_asset/items/quyen_sach.png"
          "stk_gio_do_choi" -> "file:///android_asset/items/gio_do_choi.png"
          else -> null
        }

        Surface(
          shape = CircleShape,
          color = Color(0xFFFFF9E6),
          border = BorderStroke(1.5.dp, Color(0xFFFFCA28)),
          modifier = Modifier.size(54.dp)
        ) {
          if (assetUrl != null) {
            AsyncImage(
              model = ImageRequest.Builder(LocalContext.current)
                .data(assetUrl)
                .crossfade(true)
                .build(),
              contentDescription = sticker.titleVi,
              contentScale = ContentScale.Fit,
              modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
            )
          } else {
            Box(contentAlignment = Alignment.Center) {
              Text(sticker.emoji, fontSize = 28.sp)
            }
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = sticker.titleVi,
          fontWeight = FontWeight.ExtraBold,
          color = TextDark,
          fontSize = 11.5.sp,
          maxLines = 1,
          textAlign = TextAlign.Center
        )
      } else {
        Surface(
          shape = CircleShape,
          color = Color(0xFFEEEEEE),
          modifier = Modifier.size(50.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Text("🔒", fontSize = 20.sp)
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Cần ${sticker.requiredStars} ⭐",
          fontWeight = FontWeight.Bold,
          color = Color(0xFF9E9E9E),
          fontSize = 10.5.sp,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}
