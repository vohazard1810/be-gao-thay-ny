package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LearningData
import com.example.model.CategoryType
import com.example.model.FlashcardItem
import com.example.model.FlashcardOption
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.FlashcardQuizPopup
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.RealPhotoThumbnail
import com.example.ui.components.ThayNyRaster
import com.example.ui.components.TeacherMood
import com.example.ui.theme.*

// Single-row topic descriptor
data class FlatTopic(
  val id: String,
  val titleVi: String,
  val iconEmoji: String,
  val category: CategoryType,
  val subCategoryId: String? = null,
  val accentColor: Color
)

@Composable
fun FlashcardsScreen(
  selectedCategory: CategoryType,
  selectedSubCategoryId: String?,
  selectedCard: FlashcardItem?,
  easyCorrectCount: Int = 0,
  isSpeaking: Boolean,
  spokenText: String,
  showCelebration: Boolean,
  onSelectCategory: (CategoryType) -> Unit,
  onSelectSubCategory: (String) -> Unit,
  onSelectCard: (FlashcardItem) -> Unit,
  onCloseCardDetail: () -> Unit,
  onListenSound: (FlashcardItem) -> Unit = {},
  onStartQuiz: (FlashcardItem) -> Unit = {},
  onAnswerMiniQuiz: (FlashcardOption) -> Unit,
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Topics in 1 horizontal scrollable row
  val flatTopics = remember {
    listOf(
      FlatTopic("farm", "Nông Trại", "🐶", CategoryType.ANIMALS, "sub_farm", Color(0xFFFFB74D)),
      FlatTopic("wild", "Hoang Dã", "🦁", CategoryType.ANIMALS, "sub_wild", Color(0xFFFF8A65)),
      FlatTopic("water", "Dưới Nước", "🐬", CategoryType.ANIMALS, "sub_water", Color(0xFF4FC3F7)),
      FlatTopic("fruit", "Trái Cây", "🍎", CategoryType.FRUITS, null, Color(0xFF81C784)),
      FlatTopic("color", "Màu Sắc", "🎨", CategoryType.COLORS, null, Color(0xFFFF8DA1)),
      FlatTopic("alphabet", "Chữ Cái", "🔤", CategoryType.LETTERS_NUMBERS, "sub_alphabet", Color(0xFF9575CD)),
      FlatTopic("numbers", "Số Đếm", "🔢", CategoryType.LETTERS_NUMBERS, "sub_numbers", Color(0xFF4DD0E1))
    )
  }

  val activeTopic = remember(selectedCategory, selectedSubCategoryId) {
    flatTopics.firstOrNull { topic ->
      if (topic.subCategoryId != null) {
        topic.category == selectedCategory && topic.subCategoryId == selectedSubCategoryId
      } else {
        topic.category == selectedCategory
      }
    } ?: flatTopics.first()
  }

  val allTopicCards = remember(activeTopic) {
    if (activeTopic.subCategoryId != null) {
      LearningData.flashcards.filter { it.category == activeTopic.category && it.subCategoryId == activeTopic.subCategoryId }
    } else {
      LearningData.flashcards.filter { it.category == activeTopic.category }
    }
  }
  var deckOffset by remember(activeTopic.id) { mutableIntStateOf(0) }
  val currentCards = remember(allTopicCards, deckOffset) {
    if (allTopicCards.size <= 6) allTopicCards
    else List(6) { index -> allTopicCards[(deckOffset + index) % allTopicCards.size] }
  }

  val isAlphabetMode = activeTopic.id == "alphabet"

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("flashcards_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // 1. Compact Header (≤ 8% screen height)
      KidTopAppBar(
        title = "Thẻ Học Vui",
        onHomeClick = onHomeClick,
        onReplaySpeech = onReplaySpeech,
        isSpeaking = isSpeaking
      )

      Spacer(modifier = Modifier.height(4.dp))

      // 2. Single-row Horizontal Scroll Topic Bar
      val topicScrollState = rememberScrollState()
      Box(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(topicScrollState)
            .padding(start = 12.dp, end = 38.dp, top = 2.dp, bottom = 2.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        flatTopics.forEach { topic ->
          val isSelected = topic.id == activeTopic.id
          val interactionSource = remember { MutableInteractionSource() }
          val isPressed by interactionSource.collectIsPressedAsState()
          val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.94f else 1f,
            animationSpec = spring(stiffness = Spring.StiffnessMedium),
            label = "topic_scale"
          )

          Surface(
            shape = RoundedCornerShape(18.dp),
            color = if (isSelected) topic.accentColor else Color.White,
            shadowElevation = if (isSelected) 3.dp else 1.dp,
            border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE0E0E0)),
            modifier = Modifier
              .scale(scale)
              .clip(RoundedCornerShape(18.dp))
              .clickable(
                interactionSource = interactionSource,
                indication = ripple()
              ) {
                onSelectCategory(topic.category)
                if (topic.subCategoryId != null) {
                  onSelectSubCategory(topic.subCategoryId)
                }
              }
              .testTag("topic_${topic.id}")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(text = topic.iconEmoji, fontSize = 16.sp)
              Text(
                text = topic.titleVi,
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else TextDark,
                  fontSize = 13.sp
                )
              )
            }
          }
          }
        }
        if (topicScrollState.canScrollForward) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFFF3E0),
            shadowElevation = 2.dp,
            modifier = Modifier
              .align(Alignment.CenterEnd)
              .padding(end = 6.dp)
              .width(72.dp)
              .height(32.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(text = "Vuốt  ›", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = PastelOrangeDark)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      // 3. Compact Voice Mascot Bar
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 1.5.dp,
        border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp)
          .clickable { onReplaySpeech() }
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          ThayNyRaster(
            size = 48.dp,
            isSpeaking = isSpeaking,
            mood = if (isSpeaking) TeacherMood.TALKING else TeacherMood.HAPPY,
            compact = true
          )

          Spacer(modifier = Modifier.width(8.dp))

          Text(
            text = spokenText.ifBlank { "Bé chạm vào ảnh thật để học tên nhé!" },
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = if (isSpeaking) PastelOrangeDark else TextDark,
              fontSize = 12.5.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
          )

          if (isSpeaking) {
            Icon(
              imageVector = Icons.Default.VolumeUp,
              contentDescription = "Đang phát âm thanh",
              tint = Color(0xFFFF5252),
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // 4. Uniform 2-Column Grid of Real Photo Cards
      LazyVerticalGrid(
        columns = GridCells.Fixed(if (isAlphabetMode) 3 else 2),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 2.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        items(currentCards) { card ->
          RealPhotoCardItem(
            card = card,
            isAlphabetMode = isAlphabetMode,
            onClick = { onSelectCard(card) }
          )
        }
        if (allTopicCards.size > 6) {
          item(span = { GridItemSpan(maxLineSpan) }) {
            Button(
              onClick = { deckOffset = (deckOffset + 6) % allTopicCards.size },
              shape = RoundedCornerShape(18.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
              modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("flashcards_next_deck")
            ) {
              Text("Đổi bộ hình 🔄", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            }
          }
        }
      }
    }

    // Flashcard Full Detail & Interaction Dialog for Toddlers
    AnimatedVisibility(
      visible = selectedCard != null,
      enter = fadeIn() + scaleIn(initialScale = 0.88f),
      exit = fadeOut() + scaleOut(targetScale = 0.88f)
    ) {
      if (selectedCard != null) {
        FlashcardQuizPopup(
          card = selectedCard,
          easyCorrectCount = easyCorrectCount,
          isSpeaking = isSpeaking,
          spokenText = spokenText,
          onClose = onCloseCardDetail,
          onListenSound = onListenSound,
          onStartQuiz = onStartQuiz,
          onAnswerOption = onAnswerMiniQuiz,
          onReplaySpeech = onReplaySpeech
        )
      }
    }

    // Confetti celebration overlay
    ConfettiOverlay(visible = showCelebration)
  }
}

/**
 * THẺ HỌC VUI: ẢNH THẬT 2 CỘT
 * - Ảnh chụp thật chiếm 65-75% diện tích phía trên
 * - Tên đối tượng nằm gọn gàng bên dưới ảnh
 * - Không có emoji trang trí che ảnh
 */
@Composable
fun RealPhotoCardItem(
  card: FlashcardItem,
  isAlphabetMode: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.93f else 1f,
    animationSpec = spring(stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioMediumBouncy),
    label = "item_scale"
  )

  Surface(
    shape = RoundedCornerShape(18.dp),
    color = Color.White,
    shadowElevation = 2.5.dp,
    border = BorderStroke(1.5.dp, Color(0xFFE8E8E8)),
    modifier = modifier
      .fillMaxWidth()
      .height(if (isAlphabetMode) 120.dp else 170.dp)
      .scale(scale)
      .clip(RoundedCornerShape(18.dp))
      .clickable(
        interactionSource = interactionSource,
        indication = ripple()
      ) { onClick() }
      .testTag("flashcard_item_${card.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // ẢNH THẬT (chiếm 70% chiều cao)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        RealPhotoThumbnail(
          photoUrl = card.photoUrl,
          fallbackEmoji = card.emoji,
          cardColor = card.cardColor,
          itemId = card.id,
          size = if (isAlphabetMode) 80.dp else 115.dp,
          isAlphabetOrNumber = isAlphabetMode
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      // TÊN TIẾNG VIỆT (Nằm bên dưới ảnh)
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = card.cardColor.copy(alpha = 0.35f),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = card.nameVi,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.ExtraBold,
            color = TextDark,
            fontSize = if (isAlphabetMode) 13.5.sp else 14.5.sp
          ),
          textAlign = TextAlign.Center,
          maxLines = 1,
          modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
        )
      }
    }
  }
}
