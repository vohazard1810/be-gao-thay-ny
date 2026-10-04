package com.example.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
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
  val isBgmEnabled: StateFlow<Boolean> = voiceManager.isBgmEnabled

  fun toggleBgm() {
    voiceManager.toggleBgm()
  }

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

  // ==================== LẬT THẺ TÌM BẠN (MEMORY MATCH) ====================
  private val _memoryCards = MutableStateFlow<List<MemoryCard>>(emptyList())
  val memoryCards: StateFlow<List<MemoryCard>> = _memoryCards.asStateFlow()

  private val _matchedPairsCount = MutableStateFlow(0)
  val matchedPairsCount: StateFlow<Int> = _matchedPairsCount.asStateFlow()

  val totalMemoryPairs: Int = 3

  private val flippedCardIds = mutableListOf<String>()
  private var isCheckingMatch = false

  // ==================== SỔ DÁN STICKER BÉ NGOAN ====================
  private val _stickers = MutableStateFlow<List<StickerReward>>(createDefaultStickers())
  val stickers: StateFlow<List<StickerReward>> = _stickers.asStateFlow()

  // ==================== ĐOÁN ÂM THANH VUI NHỘN ====================
  private val _soundQuizQuestions = MutableStateFlow<List<SoundQuizQuestion>>(LearningData.soundQuizQuestions)
  val soundQuizQuestions: StateFlow<List<SoundQuizQuestion>> = _soundQuizQuestions.asStateFlow()

  private val _soundQuizIndex = MutableStateFlow(0)
  val soundQuizIndex: StateFlow<Int> = _soundQuizIndex.asStateFlow()

  init {
    _memoryCards.value = generateMemoryCards()
    refreshUnlockedStickers()
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
    when (card.id) {
      "farm_dog" -> voiceManager.playDogBark()
      "farm_cat" -> voiceManager.playCatMeow()
      "farm_duck" -> voiceManager.playDuckQuack()
      "vehicle_car" -> voiceManager.playCarHorn()
      "vehicle_train" -> voiceManager.playTrainChug()
      "vehicle_bicycle" -> voiceManager.playBicycleBell()
      "vehicle_plane" -> voiceManager.playAirplaneWhoosh()
      else -> voiceManager.playPopTone()
    }
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
      refreshUnlockedStickers()
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
      "soc_nau" -> 1.14f to 0.91f
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
    when (interactionKey) {
      "hotspot_voi_nuoc", "hotspot_tay_dinh_dat", "hotspot_ban_tay_sach" -> voiceManager.playWaterSplash()
      "hotspot_bot_xa_phong" -> voiceManager.playBubbleFoam()
      "hotspot_xe_do" -> voiceManager.playCarHorn()
      "hotspot_bui_hoa", "hotspot_la_cay" -> voiceManager.playNatureChirp()
      "hotspot_bua_trua" -> voiceManager.playCelebrationFanfare()
      "hotspot_chiec_khan", "hotspot_quyen_sach", "hotspot_gio_do_choi", "hotspot_ban_chai", "hotspot_gio_banh", "hotspot_qua_thong" -> voiceManager.playMagicTwinkle()
      else -> voiceManager.playPopTone()
    }
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
      "hotspot_gio_banh" -> "Giỏ bánh dã ngoại thơm phức!"
      "hotspot_la_cay" -> "Tán cây xào xạc trong gió mát!"
      "hotspot_qua_thong" -> "Quả thông nâu xinh xắn của rừng xanh!"
      "hotspot_bua_trua" -> "Bữa trưa ngon lành cùng bạn thân!"
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
      refreshUnlockedStickers()
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
      is ScreenDestination.GameHub -> openGameHub()
      is ScreenDestination.MemoryMatch -> voiceManager.speak("Bé hãy lật 2 thẻ giống nhau nhé!")
      is ScreenDestination.StickerBook -> voiceManager.speak("Sổ Dán Sticker Bé Ngoan của bé!")
      is ScreenDestination.Coloring -> voiceManager.speak("Bé hãy chọn màu pastel con thích để tô tranh nhé!")
      is ScreenDestination.SoundQuiz -> replaySoundQuizSpeech()
    }
  }

  // ==================== KHU VUI CHƠI (GAME HUB) ====================
  fun openGameHub() {
    voiceManager.stop()
    _currentScreen.value = ScreenDestination.GameHub
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.playPopTone()
    voiceManager.speak("Khu Vui Chơi Của Bé! Bé muốn chơi trò gì nào?")
  }

  // ==================== LẬT THẺ TÌM BẠN (MEMORY MATCH) ====================
  fun openMemoryMatch() {
    voiceManager.stop()
    resetMemoryMatch()
    _currentScreen.value = ScreenDestination.MemoryMatch
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.playPopTone()
    voiceManager.speak("Trò chơi Lật Thẻ Tìm Bạn! Bé hãy lật 2 thẻ giống nhau nhé!")
  }

  fun resetMemoryMatch() {
    flippedCardIds.clear()
    isCheckingMatch = false
    _matchedPairsCount.value = 0
    _showCelebration.value = false
    _memoryCards.value = generateMemoryCards()
  }

  fun onMemoryCardClick(clickedCard: MemoryCard) {
    if (isCheckingMatch) return
    if (clickedCard.isMatched || clickedCard.isFaceUp) return

    voiceManager.playFlipSound()

    _memoryCards.value = _memoryCards.value.map { card ->
      if (card.id == clickedCard.id) card.copy(isFaceUp = true) else card
    }
    flippedCardIds.add(clickedCard.id)

    if (flippedCardIds.size == 2) {
      isCheckingMatch = true
      val firstId = flippedCardIds[0]
      val secondId = flippedCardIds[1]
      val firstCard = _memoryCards.value.find { it.id == firstId }
      val secondCard = _memoryCards.value.find { it.id == secondId }

      if (firstCard != null && secondCard != null && firstCard.matchKey == secondCard.matchKey) {
        viewModelScope.launch {
          delay(350)
          _memoryCards.value = _memoryCards.value.map { card ->
            if (card.id == firstId || card.id == secondId) {
              card.copy(isMatched = true, isFaceUp = true)
            } else card
          }
          _matchedPairsCount.value += 1
          _totalStars.value += 1
          refreshUnlockedStickers()
          flippedCardIds.clear()
          isCheckingMatch = false

          voiceManager.playSuccessChime()
          if (_matchedPairsCount.value >= totalMemoryPairs) {
            _teacherMood.value = TeacherMood.CELEBRATING
            _showCelebration.value = true
            voiceManager.playCelebrationFanfare()
            voiceManager.speak("Hoan hô bé Gạo! Con đã tìm thấy tất cả các cặp bạn rồi! Thật là xuất sắc!")
          } else {
            _teacherMood.value = TeacherMood.HAPPY
            voiceManager.speak("Đúng rồi! Bạn ${firstCard.nameVi}!")
          }
        }
      } else {
        viewModelScope.launch {
          delay(900)
          _memoryCards.value = _memoryCards.value.map { card ->
            if (card.id == firstId || card.id == secondId) {
              card.copy(isFaceUp = false)
            } else card
          }
          flippedCardIds.clear()
          isCheckingMatch = false
          voiceManager.playEncourageTone()
        }
      }
    }
  }

  // ==================== SỔ DÁN STICKER BÉ NGOAN ====================
  fun openStickerBook() {
    voiceManager.stop()
    refreshUnlockedStickers()
    _currentScreen.value = ScreenDestination.StickerBook
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.playPopTone()
    voiceManager.speak("Sổ Dán Sticker Bé Ngoan! Càng chơi nhiều bé càng có nhiều sticker đẹp!")
  }

  fun onStickerTap(sticker: StickerReward) {
    voiceManager.stop()
    if (sticker.isUnlocked) {
      voiceManager.playPopTone()
      voiceManager.speak("Sticker ${sticker.titleVi}! ${sticker.descriptionVi}")
    } else {
      voiceManager.playEncourageTone()
      voiceManager.speak("Sticker này cần ${sticker.requiredStars} ngôi sao. Bé hãy chơi thêm để mở khóa nhé!")
    }
  }

  private fun refreshUnlockedStickers() {
    val currentStars = _totalStars.value
    _stickers.value = _stickers.value.map { sticker ->
      sticker.copy(isUnlocked = sticker.isUnlocked || currentStars >= sticker.requiredStars)
    }
  }

  // ==================== BÉ TẬP TÔ MÀU ====================
  fun openColoring() {
    voiceManager.stop()
    _currentScreen.value = ScreenDestination.Coloring
    _teacherMood.value = TeacherMood.HAPPY
    voiceManager.playPopTone()
    voiceManager.speak("Góc Bé Tập Tô Màu! Bé hãy chọn màu sắc xinh xắn để tô cho các bạn nhé!")
  }

  fun onBrushStroke() {
    voiceManager.playColorBrushSound()
  }

  fun onCompleteColoring() {
    _totalStars.value += 1
    refreshUnlockedStickers()
    voiceManager.playCelebrationFanfare()
    voiceManager.speak("Oa! Bức tranh của bé Gạo đẹp quá chừng! Thầy tặng con một ngôi sao sáng nhé!")
  }

  // ==================== ĐOÁN ÂM THANH VUI NHỘN ====================
  fun openSoundQuiz() {
    voiceManager.stop()
    _soundQuizQuestions.value = LearningData.soundQuizQuestions.shuffled()
    _soundQuizIndex.value = 0
    _currentScreen.value = ScreenDestination.SoundQuiz
    _teacherMood.value = TeacherMood.TALKING
    voiceManager.playPopTone()
    val currentQ = _soundQuizQuestions.value.first()
    playSoundQuizAudioKey(currentQ.soundKey)
    voiceManager.speak(currentQ.promptVi)
  }

  fun playSoundQuizCurrentAudio() {
    val questions = _soundQuizQuestions.value
    val currentQ = questions.getOrNull(_soundQuizIndex.value) ?: return
    playSoundQuizAudioKey(currentQ.soundKey)
  }

  private fun playSoundQuizAudioKey(soundKey: String) {
    when (soundKey) {
      "dog" -> voiceManager.playDogBark()
      "cat" -> voiceManager.playCatMeow()
      "duck" -> voiceManager.playDuckQuack()
      "car" -> voiceManager.playCarHorn()
      "train" -> voiceManager.playTrainChug()
      "bicycle" -> voiceManager.playBicycleBell()
      else -> voiceManager.playNatureChirp()
    }
  }

  fun answerSoundQuiz(option: SoundQuizOption) {
    val questions = _soundQuizQuestions.value
    val currentQ = questions.getOrNull(_soundQuizIndex.value) ?: return
    if (option.isCorrect) {
      _teacherMood.value = TeacherMood.CELEBRATING
      _showCelebration.value = true
      _totalStars.value += 1
      refreshUnlockedStickers()
      voiceManager.stop()
      voiceManager.playSuccessChime()
      voiceManager.speak(currentQ.praiseVi) {
        viewModelScope.launch {
          delay(1500)
          _showCelebration.value = false
          nextSoundQuizQuestion()
        }
      }
    } else {
      _teacherMood.value = TeacherMood.ENCOURAGING
      voiceManager.stop()
      voiceManager.playEncourageTone()
      voiceManager.speak("Chưa đúng rồi con ơi! Bé bấm nút nghe lại âm thanh nhé!")
    }
  }

  fun nextSoundQuizQuestion() {
    voiceManager.stop()
    val questions = _soundQuizQuestions.value
    if (_soundQuizIndex.value < questions.size - 1) {
      _soundQuizIndex.value += 1
      val nextQ = questions[_soundQuizIndex.value]
      _teacherMood.value = TeacherMood.TALKING
      playSoundQuizAudioKey(nextQ.soundKey)
      voiceManager.speak(nextQ.promptVi)
    } else {
      _soundQuizIndex.value = 0
      _teacherMood.value = TeacherMood.CELEBRATING
      voiceManager.playCelebrationFanfare()
      voiceManager.speak("Hoan hô bé Gạo! Con đã hoàn thành tất cả câu đố âm thanh rồi! Bé Gạo thật là cừ khôi!")
    }
  }

  fun replaySoundQuizSpeech() {
    val questions = _soundQuizQuestions.value
    val currentQ = questions.getOrNull(_soundQuizIndex.value) ?: return
    _teacherMood.value = TeacherMood.TALKING
    playSoundQuizAudioKey(currentQ.soundKey)
    voiceManager.speak(currentQ.promptVi)
  }

  // ==================== TƯƠNG TÁC BÉ GẠO & THỎ BÔNG ====================
  private val beGaoQuotes = listOf(
    "Dạ! Bé Gạo chào bạn! Chúng mình cùng học thật vui nhé! 🌸",
    "Bé Gạo thích túi ngôi sao vàng lắm nè! ⭐",
    "Bạn Thỏ Bông mặc áo len tím xinh ghê! 🐰",
    "Cố lên bạn ơi, bạn giỏi lắm đó! 💖",
    "Bé Gạo thích nghe Thầy Ny kể chuyện nhất trên đời!"
  )
  private var beGaoQuoteIndex = 0

  fun onBeGaoTap() {
    voiceManager.stop()
    voiceManager.playPopTone()
    val text = beGaoQuotes[beGaoQuoteIndex % beGaoQuotes.size]
    beGaoQuoteIndex++
    voiceManager.speak(text, pitch = 1.15f, rate = 0.90f)
  }

  private val thoBongQuotes = listOf(
    "Thỏ Bông chào bé! Lỗ tai dài của thỏ ngoe nguẩy nè! 🐰",
    "Thỏ Bông thích ăn cà rốt ngọt lắm! 🥕",
    "Cùng Bé Gạo khám phá thêm nhiều điều vui nhé! ✨",
    "Áo len tím của thỏ ấm ơi là ấm!"
  )
  private var thoBongQuoteIndex = 0

  fun onThoBongTap() {
    voiceManager.stop()
    voiceManager.playPopTone()
    val text = thoBongQuotes[thoBongQuoteIndex % thoBongQuotes.size]
    thoBongQuoteIndex++
    voiceManager.speak(text, pitch = 1.20f, rate = 0.88f)
  }

  // ==================== GENERATORS ====================
  private data class MemoryCandidate(
    val key: String,
    val name: String,
    val emoji: String,
    val photoUrl: String
  )

  private fun generateMemoryCards(): List<MemoryCard> {
    val candidates = listOf(
      MemoryCandidate("cat", "Mèo Mây", "🐱", "file:///android_asset/items/meo_may.png"),
      MemoryCandidate("dog", "Cún Đốm", "🐶", "file:///android_asset/items/cun_dom.png"),
      MemoryCandidate("rabbit", "Thỏ Bông", "🐰", "file:///android_asset/items/tho_bong.png"),
      MemoryCandidate("squirrel", "Sóc Nâu", "🐿️", "file:///android_asset/items/soc_nau.png"),
      MemoryCandidate("car", "Xe Ô Tô Đỏ", "🚗", "file:///android_asset/items/xe_o_to.png"),
      MemoryCandidate("scarf", "Chiếc Khăn", "🧣", "file:///android_asset/items/chiec_khan.png")
    ).shuffled().take(3)

    val colors = listOf(
      Color(0xFFFFD1DC),
      Color(0xFFB5EAD7),
      Color(0xFFFFF2B2)
    )

    val cards = mutableListOf<MemoryCard>()
    candidates.forEachIndexed { index, candidate ->
      val color = colors[index % colors.size]
      cards.add(
        MemoryCard(
          id = "${candidate.key}_1",
          matchKey = candidate.key,
          nameVi = candidate.name,
          emoji = candidate.emoji,
          photoUrl = candidate.photoUrl,
          cardColor = color,
          isFaceUp = false,
          isMatched = false
        )
      )
      cards.add(
        MemoryCard(
          id = "${candidate.key}_2",
          matchKey = candidate.key,
          nameVi = candidate.name,
          emoji = candidate.emoji,
          photoUrl = candidate.photoUrl,
          cardColor = color,
          isFaceUp = false,
          isMatched = false
        )
      )
    }
    return cards.shuffled()
  }

  private fun createDefaultStickers(): List<StickerReward> {
    return listOf(
      StickerReward("stk_begao", "Bé Gạo Chăm Ngoan", "🌸", 0, "Bé Gạo tóc ngắn cài hoa vàng đáng yêu!", true),
      StickerReward("stk_thobong", "Thỏ Bông Áo Tím", "🐰", 1, "Bạn thỏ bông trắng mặc áo len tím!", false),
      StickerReward("stk_star", "Ngôi Sao Lấp Lánh", "⭐", 2, "Ngôi sao may mắn thưởng cho bé thông minh!", false),
      StickerReward("stk_meomay", "Mèo Mây Ngọt Ngào", "🐱", 3, "Bạn mèo mây xám sọc thích chơi đùa!", false),
      StickerReward("stk_cundom", "Cún Đốm Vui Vẻ", "🐶", 5, "Bạn cún đốm tai nâu rất trung thành!", false),
      StickerReward("stk_vitmo", "Vịt Mơ Lông Vàng", "🐥", 7, "Bạn vịt mơ lông vàng bơi lội tung tăng!", false),
      StickerReward("stk_socnau", "Sóc Nâu Nhanh Nhẹn", "🐿️", 9, "Bạn sóc nâu thích ăn hạt dẻ!", false),
      StickerReward("stk_cauvong", "Cầu Vồng Tươi Vui", "🌈", 12, "Cầu vồng bảy sắc sau cơn mưa rào!", false),
      StickerReward("stk_traitim", "Trái Tim Yêu Thương", "💖", 15, "Thầy Ny và Bé Gạo gửi triệu yêu thương!", false)
    )
  }

  override fun onCleared() {
    super.onCleared()
    voiceManager.shutdown()
  }
}
