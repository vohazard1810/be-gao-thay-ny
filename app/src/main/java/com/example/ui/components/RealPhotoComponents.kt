package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.data.FlashcardAssetManifest

/**
 * THẺ HỌC VUI: ẢNH THẬT (REAL PHOTO DISPLAY)
 * - Sử dụng ảnh chụp thực tế chất lượng cao (Coil AsyncImage)
 * - Tỷ lệ chủ thể 65-80% vùng ảnh
 * - Không dùng Canvas vector vẽ lại con vật/trái cây
 * - Không dùng emoji
 * - Nếu chưa có ảnh chính xác: Hiển thị placeholder "Đang cập nhật"
 * - Màu sắc: Card màu chuẩn giáo dục
 * - Chữ cái / Số đếm: Card đồ họa giáo dục rõ nét
 */
@Composable
fun RealPhotoThumbnail(
  photoUrl: String?,
  fallbackEmoji: String,
  cardColor: Color,
  modifier: Modifier = Modifier,
  itemId: String = "",
  size: Dp = 90.dp,
  showRealBadge: Boolean = false,
  isAlphabetOrNumber: Boolean = false
) {
  val manifestEntry = remember(itemId) { FlashcardAssetManifest.getAsset(itemId) }
  val effectiveUrl = when (itemId) {
    "wild_panda", "panda" -> "file:///android_asset/flashcards/wild/wild_panda.jpg"
    "farm_chick", "chick" -> "file:///android_asset/flashcards/farm/farm_chick.jpg"
    else -> photoUrl ?: manifestEntry?.photoUrl
  }

  // 1. Chữ cái & Số đếm: Thiết kế giáo dục rõ nét
  if (isAlphabetOrNumber || itemId.startsWith("alpha_") || itemId.startsWith("num_")) {
    EducationalLetterOrNumberCard(
      itemId = itemId,
      fallbackText = fallbackEmoji,
      cardColor = cardColor,
      modifier = modifier.size(size)
    )
    return
  }

  // 2. Màu sắc: Mảng màu mẫu giáo dục chuẩn
  if (itemId.startsWith("col_")) {
    EducationalColorSwatchCard(
      colorId = itemId,
      modifier = modifier.size(size)
    )
    return
  }

  // 3. Động vật, Côn trùng, Trái cây: ẢNH THẬT CHỤP NGOÀI ĐỜI
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = Color.White,
    shadowElevation = 2.dp,
    border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(16.dp))
  ) {
    if (!effectiveUrl.isNullOrBlank()) {
      SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(effectiveUrl)
          .crossfade(true)
          .build(),
        contentDescription = manifestEntry?.altText ?: "Ảnh thật $itemId",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxSize()
          .padding(4.dp),
        loading = {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(20.dp),
              strokeWidth = 2.dp,
              color = Color(0xFFFFB74D)
            )
          }
        },
        error = {
          android.util.Log.w("MISSING_FLASHCARD_ASSET", "Failed to load real photo asset for itemId: $itemId url: $effectiveUrl")
          UpdatingPhotoPlaceholder(isLarge = false)
        }
      )
    } else {
      android.util.Log.w("MISSING_FLASHCARD_ASSET", "Missing real photo asset for itemId: $itemId")
      UpdatingPhotoPlaceholder(isLarge = false)
    }
  }
}

/**
 * Large Real Photo Display for Flashcard Detail Dialog
 */
@Composable
fun RealPhotoDetailDisplay(
  photoUrl: String?,
  fallbackEmoji: String,
  cardColor: Color,
  itemId: String = "",
  isAlphabetOrNumber: Boolean = false,
  modifier: Modifier = Modifier,
  size: Dp = 160.dp
) {
  val manifestEntry = remember(itemId) { FlashcardAssetManifest.getAsset(itemId) }
  val effectiveUrl = when (itemId) {
    "wild_panda", "panda" -> "file:///android_asset/flashcards/wild/wild_panda.jpg"
    "farm_chick", "chick" -> "file:///android_asset/flashcards/farm/farm_chick.jpg"
    else -> photoUrl ?: manifestEntry?.photoUrl
  }

  if (isAlphabetOrNumber || itemId.startsWith("alpha_") || itemId.startsWith("num_")) {
    EducationalLetterOrNumberCard(
      itemId = itemId,
      fallbackText = fallbackEmoji,
      cardColor = cardColor,
      isLarge = true,
      modifier = modifier.size(size)
    )
    return
  }

  if (itemId.startsWith("col_")) {
    EducationalColorSwatchCard(
      colorId = itemId,
      isLarge = true,
      modifier = modifier.size(size)
    )
    return
  }

  Surface(
    shape = RoundedCornerShape(22.dp),
    color = Color.White,
    shadowElevation = 4.dp,
    border = BorderStroke(2.dp, Color(0xFFEEEEEE)),
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(22.dp))
  ) {
    if (!effectiveUrl.isNullOrBlank()) {
      SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(effectiveUrl)
          .crossfade(true)
          .build(),
        contentDescription = manifestEntry?.altText ?: "Ảnh thật $itemId",
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp),
        loading = {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(28.dp),
              strokeWidth = 3.dp,
              color = Color(0xFFFFB74D)
            )
          }
        },
        error = {
          android.util.Log.w("MISSING_FLASHCARD_ASSET", "Failed to load detail real photo asset for itemId: $itemId url: $effectiveUrl")
          UpdatingPhotoPlaceholder(isLarge = true)
        }
      )
    } else {
      android.util.Log.w("MISSING_FLASHCARD_ASSET", "Missing detail real photo asset for itemId: $itemId")
      UpdatingPhotoPlaceholder(isLarge = true)
    }
  }
}

/**
 * Placeholder "Ảnh đang được cập nhật" khi chưa có ảnh chụp thật đạt chuẩn
 */
@Composable
fun UpdatingPhotoPlaceholder(
  isLarge: Boolean = false,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFFAFAFA))
      .padding(4.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        text = "🖼️",
        fontSize = if (isLarge) 32.sp else 20.sp
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Ảnh đang được cập nhật",
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = Color(0xFF9E9E9E),
          fontSize = if (isLarge) 12.sp else 10.sp
        ),
        textAlign = TextAlign.Center
      )
    }
  }
}

/**
 * Thẻ màu sắc giáo dục: Mảng màu nguyên bản + hình mẫu trực quan
 */
@Composable
fun EducationalColorSwatchCard(
  colorId: String,
  isLarge: Boolean = false,
  modifier: Modifier = Modifier
) {
  val (colorHex, colorName) = when (colorId) {
    "col_red" -> Pair(Color(0xFFE53935), "Đỏ")
    "col_yellow" -> Pair(Color(0xFFFFD600), "Vàng")
    "col_green" -> Pair(Color(0xFF43A047), "Xanh Lá")
    "col_blue" -> Pair(Color(0xFF1E88E5), "Xanh Dương")
    "col_orange" -> Pair(Color(0xFFFB8C00), "Cam")
    "col_purple" -> Pair(Color(0xFF8E24AA), "Tím")
    "col_pink" -> Pair(Color(0xFFF06292), "Hồng")
    else -> Pair(Color(0xFF757575), "Màu")
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = Color.White,
    border = BorderStroke(1.5.dp, colorHex.copy(alpha = 0.4f)),
    modifier = modifier.clip(RoundedCornerShape(16.dp))
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      contentAlignment = Alignment.Center
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize(0.85f)
          .clip(CircleShape)
          .background(colorHex)
          .border(BorderStroke(2.dp, Color.White), CircleShape)
      )
    }
  }
}

/**
 * Thẻ chữ cái & số đếm giáo dục chuẩn mầm non
 */
@Composable
fun EducationalLetterOrNumberCard(
  itemId: String,
  fallbackText: String,
  cardColor: Color,
  isLarge: Boolean = false,
  modifier: Modifier = Modifier
) {
  val displayText = when {
    itemId.startsWith("num_") -> itemId.removePrefix("num_")
    itemId.startsWith("alpha_") -> {
      when (itemId) {
        "alpha_a" -> "A"
        "alpha_aw" -> "Ă"
        "alpha_aa" -> "Â"
        "alpha_b" -> "B"
        "alpha_c" -> "C"
        "alpha_d" -> "D"
        "alpha_dd" -> "Đ"
        "alpha_e" -> "E"
        "alpha_ee" -> "Ê"
        "alpha_g" -> "G"
        "alpha_h" -> "H"
        "alpha_i" -> "I"
        "alpha_k" -> "K"
        "alpha_l" -> "L"
        "alpha_m" -> "M"
        "alpha_n" -> "N"
        "alpha_o" -> "O"
        "alpha_ow" -> "Ơ"
        "alpha_oo" -> "Ô"
        "alpha_p" -> "P"
        "alpha_q" -> "Q"
        "alpha_r" -> "R"
        "alpha_s" -> "S"
        "alpha_t" -> "T"
        "alpha_u" -> "U"
        "alpha_uw" -> "Ư"
        "alpha_v" -> "V"
        "alpha_x" -> "X"
        "alpha_y" -> "Y"
        else -> fallbackText.take(2).uppercase()
      }
    }
    else -> fallbackText.take(2).uppercase()
  }

  Surface(
    shape = RoundedCornerShape(16.dp),
    color = Color.White,
    border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
    modifier = modifier.clip(RoundedCornerShape(16.dp))
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.verticalGradient(
            listOf(cardColor.copy(alpha = 0.25f), Color.White)
          )
        ),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = displayText,
        style = MaterialTheme.typography.displayMedium.copy(
          fontWeight = FontWeight.Black,
          color = Color(0xFF2C3E50),
          fontSize = if (isLarge) 64.sp else 38.sp
        ),
        textAlign = TextAlign.Center
      )
    }
  }
}
