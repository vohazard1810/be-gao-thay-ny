package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.R
import com.example.data.SimpleStoryScene
import kotlinx.coroutines.delay

/**
 * Phase 2 Raster Scene Image Viewer for Story Reader:
 * - Dùng ảnh raster (PNG/WebP 624x624) làm lớp tranh chính với ContentScale.Fit.
 * - Khi chưa có ảnh chính thức (hoặc load lỗi), hiển thị `story_placeholder.webp`.
 * - Tự động preload ảnh của cảnh tiếp theo nếu có.
 * - Hotspot Compose overlay trên ảnh raster (tối thiểu 48dp, không vẽ lại nhân vật, không khung kỹ thuật).
 * - Hiệu ứng chạm nhẹ: highlight viền óng ánh nhẹ và xuất hiện vài ngôi sao nhỏ tinh tế.
 */
@Composable
fun RasterStorySceneViewer(
  scene: SimpleStoryScene,
  nextScene: SimpleStoryScene? = null,
  accentColor: Color = Color(0xFFFFB74D),
  onHotspotTap: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var isHotspotActive by remember(scene.sceneId) { mutableStateOf(false) }

  // Tự động tắt highlight sau 1.5 giây
  LaunchedEffect(isHotspotActive) {
    if (isHotspotActive) {
      delay(1500)
      isHotspotActive = false
    }
  }

  // Preload ảnh cảnh tiếp theo nếu có đường dẫn
  LaunchedEffect(nextScene?.imageAsset) {
    val nextUrl = nextScene?.imageAsset
    if (!nextUrl.isNullOrBlank()) {
      try {
        val request = ImageRequest.Builder(context)
          .data(nextUrl)
          .build()
        context.imageLoader.enqueue(request)
      } catch (_: Exception) {
        // Fallback an toàn
      }
    }
  }

  Box(
    modifier = modifier
      .aspectRatio(1f)
      .clip(RoundedCornerShape(22.dp))
      .background(Color(0xFFF9F6EE))
      .border(BorderStroke(2.5.dp, accentColor.copy(alpha = 0.65f)), RoundedCornerShape(22.dp))
      .testTag("raster_story_scene_viewer"),
    contentAlignment = Alignment.Center
  ) {
    // 1. LỚP TRANH CHÍNH RASTER (ContentScale.Fit, không méo, không crop mất chi tiết)
    var imageLoadFailed by remember(scene.sceneId) { mutableStateOf(false) }

    if (!scene.imageAsset.isNullOrBlank() && !imageLoadFailed) {
      AsyncImage(
        model = ImageRequest.Builder(context)
          .data(scene.imageAsset)
          .crossfade(true)
          .listener(
            onError = { _, result ->
              android.util.Log.e("StoryReader", "Failed to load image asset: ${scene.imageAsset}", result.throwable)
              imageLoadFailed = true
            },
            onSuccess = { _, result ->
              android.util.Log.i(
                "StoryReader",
                "STORY_ASSET_LOADED: assetPath=${scene.imageAsset}, width=${result.drawable.intrinsicWidth}, height=${result.drawable.intrinsicHeight}"
              )
            }
          )
          .build(),
        contentDescription = scene.titleVi.ifBlank { "Tranh minh họa cảnh truyện" },
        contentScale = ContentScale.Fit,
        modifier = Modifier
          .fillMaxSize()
          .padding(4.dp)
      )
    } else {
      // Thông báo quản trị rõ ràng khi không tìm thấy file hoặc load lỗi
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "⚠️ Không tìm thấy ảnh scene",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD32F2F)
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = scene.imageAsset ?: "Đường dẫn trống (null)",
          style = MaterialTheme.typography.bodySmall.copy(
            color = Color(0xFF5D4037)
          )
        )
      }
    }

    // Hotspot Pulse Animation Ring
    val infiniteTransition = rememberInfiniteTransition(label = "hotspot_pulse")
    val pulseScale by infiniteTransition.animateFloat(
      initialValue = 0.92f,
      targetValue = 1.08f,
      animationSpec = infiniteRepeatable(
        animation = tween(900, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
      ),
      label = "pulse_scale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
      initialValue = 0.35f,
      targetValue = 0.85f,
      animationSpec = infiniteRepeatable(
        animation = tween(900, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
      ),
      label = "pulse_alpha"
    )

    // 2. LỚP HOTSPOT TƯƠNG TÁC OVERLAY (Rộng tối thiểu 48dp, có vòng sáng dịu nhẹ)
    when (scene.interaction) {
      "hotspot_tho_bong", "hotspot_co_tho_bong" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(0.48f)
            .align(Alignment.BottomStart)
            .padding(start = 12.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_tho_bong")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFD54F).copy(alpha = pulseAlpha * 0.25f),
            border = BorderStroke(2.dp, Color(0xFFFFB300).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_bui_hoa" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(0.48f)
            .align(Alignment.BottomEnd)
            .padding(end = 12.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_bui_hoa")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFD54F).copy(alpha = pulseAlpha * 0.25f),
            border = BorderStroke(2.dp, Color(0xFFFFB300).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_chiec_khan" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.45f)
            .align(Alignment.BottomEnd)
            .padding(end = 16.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_chiec_khan")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFF64B5F6).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFF1E88E5).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_tay_dinh_dat" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.45f)
            .fillMaxWidth(0.7f)
            .align(Alignment.BottomCenter)
            .padding(bottom = 36.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_tay_dinh_dat")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFD54F).copy(alpha = pulseAlpha * 0.25f),
            border = BorderStroke(2.dp, Color(0xFFFFB300).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_voi_nuoc" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.45f)
            .align(Alignment.BottomStart)
            .padding(start = 16.dp, bottom = 28.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_voi_nuoc")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFF4FC3F7).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFF0288D1).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("💧", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_bot_xa_phong" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.6f)
            .align(Alignment.Center)
            .padding(top = 28.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_bot_xa_phong")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFF81D4FA).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFF039BE5).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("🫧", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_ban_tay_sach" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.48f)
            .align(Alignment.BottomStart)
            .padding(start = 24.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_ban_tay_sach")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFA5D6A7).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFF43A047).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_xe_do" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.5f)
            .align(Alignment.BottomStart)
            .padding(start = 16.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_xe_do")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFAB91).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFFE64A19).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("🚗", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_be_gao" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(0.5f)
            .align(Alignment.BottomEnd)
            .padding(end = 16.dp, bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_be_gao")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFE082).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFFFFB300).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_cay_cau", "hotspot_hai_ban" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.6f)
            .align(Alignment.Center)
            .padding(top = 24.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_cay_cau")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFFFD54F).copy(alpha = pulseAlpha * 0.25f),
            border = BorderStroke(2.dp, Color(0xFFFFB300).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("⭐", fontSize = 16.sp)
            }
          }
        }
      }
      "hotspot_gio_do_choi", "hotspot_ban_chai", "hotspot_quyen_sach" -> {
        Box(
          modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.5f)
            .align(Alignment.BottomCenter)
            .padding(bottom = 32.dp)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              isHotspotActive = true
              onHotspotTap(scene.interaction)
            }
            .testTag("scene_hotspot_night")
        ) {
          Surface(
            shape = CircleShape,
            color = Color(0xFFB39DDB).copy(alpha = pulseAlpha * 0.3f),
            border = BorderStroke(2.dp, Color(0xFF7E57C2).copy(alpha = pulseAlpha)),
            modifier = Modifier
              .size(52.dp)
              .align(Alignment.Center)
              .scale(pulseScale)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text("✨", fontSize = 16.sp)
            }
          }
        }
      }
    }

    // 3. HIỆU ỨNG CHẠM NHẸ: Sao nhỏ hoặc ánh sáng dịu lấp lánh (Không che mặt nhân vật)
    AnimatedVisibility(
      visible = isHotspotActive,
      enter = fadeIn(),
      exit = fadeOut(),
      modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFFF9C4).copy(alpha = 0.92f),
        border = BorderStroke(1.dp, Color(0xFFFFD54F)),
        shadowElevation = 2.dp
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text("✨", fontSize = 14.sp)
          Text("⭐", fontSize = 14.sp)
          Text("✨", fontSize = 14.sp)
        }
      }
    }
  }
}
