package com.example.ui.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.FlashcardItem
import com.example.model.FlashcardOption
import com.example.ui.theme.PastelAmber
import com.example.ui.theme.PastelMint
import com.example.ui.theme.TextDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class FlashcardPopupStep {
  DISCOVERY, // Bước A: Khám phá ảnh thật lớn, nghe tên và tiếng kêu
  QUIZ       // Bước B: Chọn hình (2 thẻ ảnh lớn cho trẻ 3 tuổi)
}

enum class QuizDifficulty {
  EASY,   // 2 lựa chọn ảnh thật
  NORMAL  // 3 lựa chọn ảnh thật (khi đã đúng >= 3 câu EASY)
}

/**
 * Popup Flashcard Quiz được thiết kế chuyên biệt cho trẻ 3 tuổi:
 * 1. Tách làm 2 bước: Bước A (Khám phá) và Bước B (Chọn hình)
 * 2. Cỡ chữ lớn (>= 20-22sp), nút bấm cao >= 56dp, vùng chạm >= 56x56dp
 * 3. Chế độ EASY mặc định: 2 thẻ ảnh thật lớn (chiều cao >= 140dp), khoảng cách >= 12dp
 * 4. Phản hồi sư phạm nhẹ nhàng: viền xanh khi đúng, nhắc nhở dịu dàng khi chưa đúng, không hiện X đỏ
 */
@Composable
fun FlashcardQuizPopup(
  card: FlashcardItem,
  easyCorrectCount: Int = 0,
  isSpeaking: Boolean,
  spokenText: String,
  onClose: () -> Unit,
  onListenSound: (FlashcardItem) -> Unit,
  onStartQuiz: (FlashcardItem) -> Unit,
  onAnswerOption: (FlashcardOption) -> Unit,
  onReplaySpeech: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  var currentStep by remember(card.id) { mutableStateOf(FlashcardPopupStep.DISCOVERY) }
  var selectedOptionId by remember(card.id) { mutableStateOf<String?>(null) }
  var isCorrectAnswer by remember(card.id) { mutableStateOf<Boolean?>(null) }
  var wrongAttempts by remember(card.id) { mutableIntStateOf(0) }

  // Trẻ 3 tuổi luôn dùng 2 lựa chọn lớn. Không tự tăng lên 3 đáp án giữa buổi học.
  val difficulty = QuizDifficulty.EASY

  val quizOptions = remember(card.id, difficulty) {
    val correctOpt = card.distractors.firstOrNull { it.isCorrect }
      ?: FlashcardOption(card.id, card.nameVi, card.emoji, card.photoUrl, true)
    
    // Lấy các distractor hợp lệ có ảnh thật
    val validDistractors = card.distractors.filter { !it.isCorrect && !it.photoUrl.isNullOrBlank() }
    
    if (difficulty == QuizDifficulty.EASY) {
      // 2 lựa chọn: 1 đúng + 1 sai rõ ràng (ưu tiên Mèo hoặc đối tượng khác biệt)
      val chosenDistractor = validDistractors.firstOrNull { it.id == "cat" }
        ?: validDistractors.firstOrNull()
        ?: FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false)
      
      listOf(correctOpt, chosenDistractor).shuffled()
    } else {
      // 3 lựa chọn
      val otherDistractors = validDistractors.take(2)
      (listOf(correctOpt) + otherDistractors).shuffled()
    }
  }

  // Kiểm tra tính toàn vẹn của asset ảnh
  val hasMissingAsset = remember(quizOptions) {
    quizOptions.any { opt ->
      if (opt.photoUrl.isNullOrBlank()) {
        Log.e("FlashcardQuizPopup", "MISSING_ASSET: Option ${opt.id} lacks a valid photoUrl!")
        true
      } else false
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Black.copy(alpha = 0.52f))
      .clickable { onClose() },
    contentAlignment = Alignment.Center
  ) {
    Surface(
      shape = RoundedCornerShape(28.dp),
      color = Color.White,
      shadowElevation = 10.dp,
      border = BorderStroke(3.dp, card.cardColor),
      modifier = Modifier
        .fillMaxWidth(0.94f)
        .widthIn(max = 440.dp)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null
        ) {} // Chặn click xuyên qua dialog
        .testTag("flashcard_detail_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // =====================================================================
        // HEADER BAR CHUNG (Nút đóng X >= 48dp, Tag trạng thái, Nút Loa >= 48dp)
        // =====================================================================
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Nút Đóng (Vùng chạm >= 48dp)
          Surface(
            shape = CircleShape,
            color = Color(0xFFF5F5F5),
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .clickable { onClose() }
              .testTag("flashcard_close_button")
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Đóng hộp thoại",
                tint = TextDark,
                modifier = Modifier.size(24.dp)
              )
            }
          }

          // Huy hiệu chỉ dẫn bước
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (currentStep == FlashcardPopupStep.DISCOVERY) Color(0xFFFFF3E0) else Color(0xFFE1F5FE),
            border = BorderStroke(
              1.dp,
              if (currentStep == FlashcardPopupStep.DISCOVERY) Color(0xFFFFB74D) else Color(0xFF4FC3F7)
            )
          ) {
            Text(
              text = if (currentStep == FlashcardPopupStep.DISCOVERY) "✨ Khám Phá" else "🎮 Chọn Hình",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = if (currentStep == FlashcardPopupStep.DISCOVERY) Color(0xFFE65100) else Color(0xFF0277BD),
                fontSize = 13.sp
              ),
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
            )
          }

          // Nút Loa Nghe Lại (Vùng chạm >= 48dp)
          Surface(
            shape = CircleShape,
            color = if (isSpeaking) Color(0xFFFF5252) else PastelMint,
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .clickable { onReplaySpeech() }
              .testTag("flashcard_detail_replay")
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Phát lại giọng đọc",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }

        // =====================================================================
        // BƯỚC A: KHÁM PHÁ (ẢNH THẬT LỚN 50-55%, TÊN LỚN, NÚT TIẾNG KÊU & CHƠI)
        // =====================================================================
        if (currentStep == FlashcardPopupStep.DISCOVERY) {
          // 1. KHUNG ẢNH THẬT LỚN (50-55% chiều cao popup, ContentScale.Fit)
          Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFFFAFAFA),
            border = BorderStroke(2.dp, Color(0xFFEEEEEE)),
            modifier = Modifier
              .fillMaxWidth()
              .height(210.dp)
          ) {
            Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
            ) {
              AsyncImage(
                model = ImageRequest.Builder(context)
                  .data(card.photoUrl)
                  .crossfade(true)
                  .build(),
                contentDescription = "Ảnh thật ${card.nameVi}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp)
                  .testTag("flashcard_real_image_discovery")
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // 2. TÊN ĐỐI TƯỢNG (>= 20sp, ở đây dùng 24sp ExtraBold cho trẻ 3 tuổi)
          Text(
            text = card.nameVi,
            style = MaterialTheme.typography.headlineSmall.copy(
              fontWeight = FontWeight.Black,
              color = TextDark,
              fontSize = 24.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("flashcard_object_title")
          )

          Spacer(modifier = Modifier.height(2.dp))

          // Lời mô tả âm thanh ngắn gọn (<= 12 từ)
          Text(
            text = "${card.nameVi} gáy ${card.soundEffectVi}",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF5D4037),
              fontSize = 15.sp
            ),
            textAlign = TextAlign.Center
          )

          Spacer(modifier = Modifier.height(14.dp))

          // 3. NÚT 1: 🔊 NGHE TIẾNG KÊU (Cao >= 56dp, Vùng chạm >= 56dp)
          Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFFFFF8E1),
            border = BorderStroke(1.5.dp, Color(0xFFFFD54F)),
            shadowElevation = 1.dp,
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp)
              .clip(RoundedCornerShape(18.dp))
              .clickable { onListenSound(card) }
              .testTag("btn_listen_sound")
          ) {
            Row(
              modifier = Modifier.fillMaxSize(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(text = "🔊", fontSize = 20.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Nghe tiếng ${card.nameVi.lowercase()}",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFFE65100),
                  fontSize = 16.sp
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // 4. NÚT 2: ✨ CHƠI TÌM HÌNH (CTA chính, Cao >= 56dp, Vùng chạm >= 56dp)
          Surface(
            shape = RoundedCornerShape(18.dp),
            color = PastelAmber,
            shadowElevation = 3.dp,
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp)
              .clip(RoundedCornerShape(18.dp))
              .clickable {
                currentStep = FlashcardPopupStep.QUIZ
                onStartQuiz(card)
              }
              .testTag("btn_start_quiz")
          ) {
            Row(
              modifier = Modifier.fillMaxSize(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text(text = "🎮", fontSize = 22.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Chơi tìm hình",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = Color.White,
                  fontSize = 18.sp
                )
              )
            }
          }
        }

        // =====================================================================
        // BƯỚC B: CHỌN HÌNH (2 THẺ ẢNH THẬT LỚN >= 140dp, CÂU HỎI >= 22sp)
        // =====================================================================
        if (currentStep == FlashcardPopupStep.QUIZ) {
          if (hasMissingAsset) {
            // Trường hợp có asset bị thiếu: Báo log và không mở câu hỏi sai
            Text(
              text = "⚠️ Tạm dừng câu hỏi do thiếu ảnh thật chuẩn",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Red
              ),
              modifier = Modifier.padding(16.dp)
            )
          } else {
            // 1. CÂU HỎI LỚN (>= 22sp, ở đây dùng 23sp Black)
            Text(
              text = "Đâu là ${card.nameVi.lowercase()}?",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Black,
                color = Color(0xFF1565C0),
                fontSize = 23.sp
              ),
              textAlign = TextAlign.Center,
              modifier = Modifier.testTag("flashcard_quiz_question")
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
              text = "Bé chạm vào bức hình đúng nhé!",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF757575),
                fontSize = 13.sp
              ),
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 2. CÁC THẺ ĐÁP ÁN ẢNH THẬT (Cao >= 140dp, Spacing >= 12dp)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
              horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
              quizOptions.forEach { opt ->
                val isSelected = selectedOptionId == opt.id
                val isAnsweredCorrect = isCorrectAnswer == true && isSelected
                val isGentleGuidance = wrongAttempts >= 2 && opt.isCorrect

                // Dynamic Border dựa trên trạng thái
                val cardBorder = when {
                  isAnsweredCorrect -> BorderStroke(3.5.dp, Color(0xFF4CAF50))
                  isGentleGuidance -> BorderStroke(3.5.dp, Color(0xFFFFB300))
                  isSelected && isCorrectAnswer == false -> BorderStroke(2.dp, Color(0xFFFFB74D))
                  else -> BorderStroke(2.dp, Color(0xFFE0E0E0))
                }

                // Dynamic Background Color
                val cardBgColor = when {
                  isAnsweredCorrect -> Color(0xFFE8F5E9)
                  isGentleGuidance -> Color(0xFFFFF8E1)
                  else -> Color.White
                }

                Surface(
                  shape = RoundedCornerShape(20.dp),
                  color = cardBgColor,
                  shadowElevation = if (isSelected || isGentleGuidance) 4.dp else 1.5.dp,
                  border = cardBorder,
                  modifier = Modifier
                    .weight(1f)
                    .height(155.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(
                      enabled = isCorrectAnswer != true,
                      interactionSource = remember { MutableInteractionSource() },
                      indication = ripple()
                    ) {
                      selectedOptionId = opt.id
                      onAnswerOption(opt)

                      if (opt.isCorrect) {
                        isCorrectAnswer = true
                        coroutineScope.launch {
                          delay(1800)
                          onClose()
                        }
                      } else {
                        isCorrectAnswer = false
                        wrongAttempts += 1
                      }
                    }
                    .testTag("quiz_option_${opt.id}")
                ) {
                  Column(
                    modifier = Modifier
                      .fillMaxSize()
                      .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                  ) {
                    // Vùng ảnh thật
                    Box(
                      modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                      contentAlignment = Alignment.Center
                    ) {
                      AsyncImage(
                        model = ImageRequest.Builder(context)
                          .data(opt.photoUrl)
                          .crossfade(true)
                          .build(),
                        contentDescription = "Đáp án ảnh thật ${opt.nameVi}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                          .fillMaxSize()
                          .padding(2.dp)
                      )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Tên phụ nhỏ dưới ảnh (hình ảnh là thông tin chính)
                    Text(
                      text = opt.nameVi,
                      style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        fontSize = 13.5.sp
                      ),
                      textAlign = TextAlign.Center,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3. KHU VỰC PHẢN HỒI SƯ PHẠM (Feedback Area)
            AnimatedVisibility(
              visible = isCorrectAnswer != null,
              enter = fadeIn() + expandVertically(),
              exit = fadeOut() + shrinkVertically()
            ) {
              if (isCorrectAnswer == true) {
                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = Color(0xFFE8F5E9),
                  border = BorderStroke(1.5.dp, Color(0xFF81C784)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(text = "🌟", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Đúng rồi! Đây là chú ${card.nameVi}!",
                      style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2E7D32),
                        fontSize = 15.5.sp
                      ),
                      textAlign = TextAlign.Center
                    )
                  }
                }
              } else if (isCorrectAnswer == false) {
                Surface(
                  shape = RoundedCornerShape(16.dp),
                  color = Color(0xFFFFF3E0),
                  border = BorderStroke(1.5.dp, Color(0xFFFFB74D)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(text = "😊", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Bé nhìn lại một lần nữa nhé!",
                      style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100),
                        fontSize = 14.5.sp
                      ),
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
