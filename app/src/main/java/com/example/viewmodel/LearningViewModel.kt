package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.VoiceManager
import com.example.data.LearningData
import com.example.data.SimpleStoryScene
import com.example.data.StoryAssetManifest
import com.example.data.StoryBook
import com.example.model.*
import com.example.ui.components.TeacherMood
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LearningViewModel(application: Application) : AndroidViewModel(application) {

  private val voiceManager = VoiceManager(application)

  val isSpeaking: StateFlow<Boolean> = voiceManager.isSpeaking
  val spokenText: StateFlow<String> = voiceManager.spokenText

  private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
  val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

  private val _selectedCategory = MutableStateFlow(CategoryType.ANIMALS)
  val selectedCategory: StateFlow<CategoryType> = _selectedCategory.asStateFlow()

  private val _selectedSubCategoryId = MutableStateFlow<String?>("sub_farm")
  val selectedSubCategoryId: StateFlow<String?> = _selectedSubCategoryId.asStateFlow()

  private val _selectedCard = MutableStateFlow<FlashcardItem?>(null)
  val selectedCard: StateFlow<FlashcardItem?> = _selectedCard.asStateFlow()

  private val _selectedStoryBook = MutableStateFlow(StoryAssetManifest.storyBooks.first())
  val selectedStoryBook: StateFlow<StoryBook> = _selectedStoryBook.asStateFlow()

  private val _currentSceneIndex = MutableStateFlow(0)
  val currentSceneIndex: StateFlow<Int> = _currentSceneIndex.asStateFlow()

  private val _quizQuestions = MutableStateFlow(LearningData.quizQuestions)
  val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

  private val _quizIndex = MutableStateFlow(0)
  val quizIndex: StateFlow<Int> = _quizIndex.asStateFlow()

  private val _totalStars = MutableStateFlow(0)
  val totalStars: StateFlow<Int> = _totalStars.asStateFlow()

  private val _easyQuizCorrectCount = MutableStateFlow(0)
  val easyQuizCorrectCount: StateFlow<Int> = _easyQuizCorrectCount.asStateFlow()

  private val _showCelebration = MutableStateFlow(false)
  val showCelebration: StateFlow<Boolean> = _showCelebration.asStateFlow()

  private val _teacherMood = MutableStateFlow(TeacherMood.HAPPY)
  val teacherMood: StateFlow<TeacherMood> = _teacherMood.asStateFlow()

  init {
    viewModelScope.launch {
      delay(500)
      greetHome()
    }
  }

  fun greetHome() {
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.speak(
      "Thầy chào bé Gạo yêu quý! Con hãy chọn 1 trò chơi bên dưới nhé!"
    )
  }

  fun navigateToHome() {
    voiceManager.stop()
    _currentScreen.value = ScreenDestination.Home
    _selectedCard.value = null
    _showCelebration.value = false
    greetHome()
  }

  // ==================== THẺ HỌC VUI ====================
  fun openFlashcards(category: CategoryType = CategoryType.ANIMALS) {
    _selectedCategory.value = category
    val defaultSubCat = LearningData.subCategories.firstOrNull { it.category == category }?.id
    _selectedSubCategoryId.value = defaultSubCat
    _selectedCard.value = null
    _currentScreen.value = ScreenDestination.FlashcardList(category, defaultSubCat)
    voiceManager.speak("Thẻ học ${category.titleVi}! Bé chạm vào hình nào con thích nhé!")
  }

  fun selectCategory(category: CategoryType) {
    _selectedCategory.value = category
    val defaultSubCat = LearningData.subCategories.firstOrNull { it.category == category }?.id
    _selectedSubCategoryId.value = defaultSubCat
    _selectedCard.value = null
    voiceManager.playPopTone()
    voiceManager.speak("Chủ đề ${category.titleVi}!")
  }

  fun selectSubCategory(subCatId: String) {
    _selectedSubCategoryId.value = subCatId
    _selectedCard.value = null
    val sub = LearningData.subCategories.firstOrNull { it.id == subCatId }
    voiceManager.playPopTone()
    if (sub != null) {
      voiceManager.speak("Nhóm ${sub.titleVi}!")
    }
  }

  fun selectFlashcard(card: FlashcardItem) {
    _selectedCard.value = card
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.stop()
    voiceManager.playPopTone()
    val speech = LearningData.getFlashcardIntroSpeech(card)
    voiceManager.speak(speech)
  }

  fun speakFlashcardSound(card: FlashcardItem) {
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.stop()
    val soundSpeech = LearningData.getFlashcardSoundSpeech(card)
    voiceManager.speak(soundSpeech)
  }

  fun startFlashcardQuiz(card: FlashcardItem) {
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.stop()
    voiceManager.playPopTone()
    voiceManager.speak("Đâu là ${LearningData.formatCleanName(card.nameVi)}? Bé chạm vào hình đúng nhé!")
  }

  fun closeFlashcardDetail() {
    voiceManager.stop()
    _selectedCard.value = null
    _showCelebration.value = false
    voiceManager.speak("Bé chọn thẻ khác nhé!")
  }

  fun answerFlashcardMiniQuiz(option: FlashcardOption) {
    val currentCard = _selectedCard.value ?: return
    if (option.isCorrect) {
      _teacherMood.value = TeacherMood.CELEBRATING
      _showCelebration.value = true
      _totalStars.value += 1
      _easyQuizCorrectCount.value += 1
      voiceManager.stop()
      voiceManager.playSuccessChime()
      val praiseText = LearningData.getPraiseForCard(currentCard)
      voiceManager.speak(praiseText) {
        viewModelScope.launch {
          delay(1500)
          _showCelebration.value = false
        }
      }
    } else {
      _teacherMood.value = TeacherMood.ENCOURAGING
      voiceManager.stop()
      voiceManager.playEncourageTone()
      voiceManager.speak("Mình thử lại nhé! Bé nhìn kỹ từng hình nào.")
    }
  }

  // ==================== KHO TRUYỆN TRANH & KỂ CHUYỆN ====================
  fun openStoryMenu() {
    voiceManager.stop()
    _currentScreen.value = ScreenDestination.StoryMenu
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.playPopTone()
    voiceManager.speak("Kho truyện tranh của bé! Con chọn 1 cuốn truyện để cùng nghe thầy kể nhé!")
  }

  fun selectStoryBook(storyBook: StoryBook) {
    voiceManager.stop()
    _selectedStoryBook.value = storyBook
    _currentSceneIndex.value = 0
    _currentScreen.value = ScreenDestination.StoryPlay(storyBook)
    voiceManager.playPopTone()
    // TTS is activated solely by LaunchedEffect in StorytellingScreen to eliminate double autoplay
  }

  fun backToStoryMenu() {
    voiceManager.stop()
    _currentScreen.value = ScreenDestination.StoryMenu
    _showCelebration.value = false
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.speak("Bé chọn cuốn truyện khác nhé!")
  }

  fun narrateScene(scene: SimpleStoryScene) {
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.stop()
    voiceManager.playPopTone()
    val mainSentence = if (scene.dialogueVi.isNotBlank()) {
      scene.dialogueVi.trim('“', '”', '"', ' ')
    } else {
      scene.narrationVi
    }
    val (pitch, rate) = when (scene.speakerId) {
      "be_gao" -> 1.12f to 0.90f
      "tho_bong" -> 1.18f to 0.87f
      "meo_may" -> 1.15f to 0.90f
      "cun_dom" -> 1.08f to 0.92f
      else -> 0.92f to 0.86f
    }
    voiceManager.speak(mainSentence, pitch = pitch, rate = rate)
  }

  fun onSceneChanged(storyBook: StoryBook, newIndex: Int) {
    voiceManager.stop()
    _currentSceneIndex.value = newIndex
    val scene = storyBook.scenes.getOrElse(newIndex) { storyBook.scenes.first() }
    narrateScene(scene)
  }

  fun onHotspotTap(interactionKey: String) {
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.stop()
    voiceManager.playPopTone()
    val speech = when (interactionKey) {
      "hotspot_tho_bong", "hotspot_co_tho_bong" -> "Cảm ơn Bé Gạo!"
      "hotspot_chiec_khan" -> "Bạn tìm thấy mình rồi!"
      "hotspot_bui_hoa" -> "Bụi hoa thơm ngát nè!"
      "hotspot_tay_dinh_dat" -> "Rửa tay cùng Thầy Ny nào!"
      "hotspot_voi_nuoc" -> "Nước mát róc rách!"
      "hotspot_bot_xa_phong" -> "Xoa đều hai bàn tay nhé!"
      "hotspot_ban_tay_sach" -> "Đôi bàn tay sạch bong rồi!"
      "hotspot_xe_do" -> "Pim pim! Chiếc xe đỏ chạy bon bon!"
      "hotspot_be_gao" -> "Chúng mình cùng chơi nhé!"
      "hotspot_cay_cau" -> "Cây cầu gỗ thật là cao!"
      "hotspot_hai_ban" -> "Cùng chơi vui gấp nhiều lần!"
      "hotspot_gio_do_choi" -> "Đồ chơi vào giỏ ngủ ngon nhé!"
      "hotspot_ban_chai" -> "Xoay tròn bàn chải, răng trắng xinh!"
      "hotspot_quyen_sach" -> "Cuốn sách truyện tranh kỳ diệu!"
      else -> "Chúng mình cùng chơi nhé!"
    }
    voiceManager.speak(speech)
  }

  // ==================== ĐỐ VUI CÙNG THẦY ====================
  fun openQuizGame() {
    voiceManager.stop()
    _quizQuestions.value = LearningData.quizQuestions.shuffled()
    _quizIndex.value = 0
    _currentScreen.value = ScreenDestination.QuizPlay
    _teacherMood.value = TeacherMood.TALKING
    val q = _quizQuestions.value.first()
    voiceManager.speak(q.spokenTextVi)
  }

  fun nextQuizQuestion() {
    voiceManager.stop()
    val questions = _quizQuestions.value
    if (_quizIndex.value < questions.size - 1) {
      _quizIndex.value += 1
      val nextQ = questions[_quizIndex.value]
      _teacherMood.value = TeacherMood.TALKING
      voiceManager.speak(nextQ.spokenTextVi)
    } else {
      _quizIndex.value = 0
      _teacherMood.value = TeacherMood.CELEBRATING
      voiceManager.speak("Hoan hô bé Gạo! Con đã hoàn thành tất cả câu đố rồi! Bé Gạo thật là tuyệt vời!")
    }
  }

  fun answerQuiz(option: QuizOption) {
    val questions = _quizQuestions.value
    val currentQ = questions.getOrNull(_quizIndex.value) ?: return
    if (option.id == currentQ.correctId) {
      _teacherMood.value = TeacherMood.CELEBRATING
      _showCelebration.value = true
      _totalStars.value += 1
      voiceManager.stop()
      voiceManager.playSuccessChime()
      voiceManager.speak(currentQ.praiseSpeechVi) {
        viewModelScope.launch {
          delay(1500)
          _showCelebration.value = false
        }
      }
    } else {
      _teacherMood.value = TeacherMood.ENCOURAGING
      voiceManager.stop()
      voiceManager.playEncourageTone()
      voiceManager.speak("Mình thử lại nhé! Bé nhìn kỹ từng hình nào.")
    }
  }

  fun replayQuizQuestion() {
    val questions = _quizQuestions.value
    val currentQ = questions.getOrNull(_quizIndex.value) ?: return
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.speak(currentQ.spokenTextVi)
  }

  fun onTeacherTapGeneral() {
    voiceManager.playPopTone()
    when (val screen = _currentScreen.value) {
      is ScreenDestination.Home -> greetHome()
      is ScreenDestination.FlashcardList -> voiceManager.speak("Bé Gạo chạm vào hình nào con thích để cùng học với thầy nhé!")
      is ScreenDestination.FlashcardDetail -> {
        val c = _selectedCard.value
        if (c != null) voiceManager.speak("${c.nameVi}! ${c.soundEffectVi}. ${c.questionVi}")
      }
      is ScreenDestination.StoryMenu -> voiceManager.speak("Kho truyện tranh của bé! Con chọn 1 cuốn truyện nhé!")
      is ScreenDestination.StoryPlay -> {
        val currentStory = screen.storyBook
        val scene = currentStory.scenes.getOrElse(_currentSceneIndex.value) { currentStory.scenes.first() }
        narrateScene(scene)
      }
      is ScreenDestination.QuizPlay -> replayQuizQuestion()
    }
  }

  override fun onCleared() {
    super.onCleared()
    voiceManager.shutdown()
  }
}
