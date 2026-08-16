package com.example.model

import androidx.compose.ui.graphics.Color

enum class CategoryType(val titleVi: String, val emoji: String, val colorHex: Long) {
  ANIMALS("Con Vật", "🐶", 0xFFFFB703),
  FRUITS("Trái Cây", "🍎", 0xFFFB8500),
  COLORS("Màu Sắc", "🎨", 0xFF0288D1),
  LETTERS_NUMBERS("Chữ & Số", "🔢", 0xFF43A047)
}

data class SubCategory(
  val id: String,
  val category: CategoryType,
  val titleVi: String,
  val emoji: String,
  val color: Color
)

data class FlashcardItem(
  val id: String,
  val category: CategoryType,
  val subCategoryId: String? = null,
  val nameVi: String,
  val pronunciationVi: String,
  val emoji: String,
  val photoUrl: String? = null, // High-quality photorealistic studio real photo
  val soundEffectVi: String, // e.g. "Gâu gâu!", "Meo meo!", "Chíp chíp!"
  val funFactVi: String,
  val cardColor: Color,
  val questionVi: String, // "Đây là con gì nhỉ?", "Đây là quả gì nhỉ?"
  val dotCount: Int? = null, // for counting numbers 1-10
  val exampleWord: String? = null, // for Vietnamese alphabet letters
  val distractors: List<FlashcardOption>
)

data class FlashcardOption(
  val id: String,
  val nameVi: String,
  val emoji: String,
  val photoUrl: String? = null,
  val isCorrect: Boolean
)

data class StoryCharacter(
  val id: String,
  val nameVi: String,
  val speciesVi: String = "Gấu nhỏ màu kem",
  val emoji: String,
  val traitVi: String,
  val outfitVi: String, // Anthropomorphic outfit description
  val facialFeaturesVi: String = "Tai tròn, má hồng nhẹ, mắt to tròn biểu cảm",
  val color: Color
)

data class StorySetting(
  val id: String,
  val titleVi: String,
  val emoji: String,
  val descriptionVi: String,
  val color: Color
)

data class StorySentence(
  val sceneId: String = "scene_0",
  val sentenceIndex: Int,
  val narration: String, // Lời kể của Thầy Ny (1-2 câu ngắn, dưới 15 từ)
  val characterDialogue: String? = null, // Lời nói của nhân vật
  val imagePrompt: String = "",
  val backgroundDescription: String = "",
  val characterPosition: String = "center",
  val characterExpression: String = "happy",
  val interactiveObject: String? = "Túi Ngôi Sao Vàng ⭐",
  val interactiveEmoji: String = "⭐",
  val interactionInstruction: String = "Bé chạm vào Túi Ngôi Sao hoặc bạn nhỏ nhé!",
  val soundEffect: String = "Ting ting lấp lánh!",
  val animationSuggestion: String = "Bé Mây vẫy tay và chớp mắt tươi cười",
  val textVi: String = narration,
  val visualSceneEmoji: String = "⭐",
  val characterActionVi: String = ""
)

data class GeneratedStory(
  val id: String,
  val titleVi: String,
  val character: StoryCharacter,
  val setting: StorySetting,
  val moralLessonVi: String,
  val sentences: List<StorySentence>
)

data class QuizQuestion(
  val id: String,
  val questionTextVi: String,
  val spokenTextVi: String,
  val visualClueEmoji: String? = null, // Visual clue hint
  val cluePhotoUrl: String? = null, // Real photo clue
  val options: List<QuizOption>,
  val correctId: String,
  val praiseSpeechVi: String = "Hoan hô bé Gạo! Con giỏi quá!",
  val encourageSpeechVi: String = "Chưa đúng rồi bé Gạo ơi, con chọn lại nhé!"
)

data class QuizOption(
  val id: String,
  val labelVi: String,
  val emoji: String,
  val photoUrl: String? = null, // Photorealistic matching Flashcards
  val color: Color
)

sealed class ScreenDestination {
  object Home : ScreenDestination()
  data class FlashcardList(val category: CategoryType, val subCategoryId: String? = null) : ScreenDestination()
  data class FlashcardDetail(val item: FlashcardItem) : ScreenDestination()
  object StoryMenu : ScreenDestination()
  data class StoryPlay(val storyBook: com.example.data.StoryBook) : ScreenDestination()
  object QuizPlay : ScreenDestination()
}
