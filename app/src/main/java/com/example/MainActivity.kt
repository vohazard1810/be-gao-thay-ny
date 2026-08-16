package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ScreenDestination
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuizGameScreen
import com.example.ui.screens.StoryMenuScreen
import com.example.ui.screens.StorytellingScreen
import com.example.ui.theme.PastelCreamBg
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.LearningViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = PastelCreamBg
        ) {
          ToddlerAppNavigation()
        }
      }
    }
  }
}

@Composable
fun ToddlerAppNavigation(
  viewModel: LearningViewModel = viewModel()
) {
  val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
  val isSpeaking by viewModel.isSpeaking.collectAsStateWithLifecycle()
  val spokenText by viewModel.spokenText.collectAsStateWithLifecycle()
  val totalStars by viewModel.totalStars.collectAsStateWithLifecycle()
  val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
  val selectedSubCategoryId by viewModel.selectedSubCategoryId.collectAsStateWithLifecycle()
  val selectedCard by viewModel.selectedCard.collectAsStateWithLifecycle()
  val showCelebration by viewModel.showCelebration.collectAsStateWithLifecycle()
  val teacherMood by viewModel.teacherMood.collectAsStateWithLifecycle()
  val easyQuizCorrectCount by viewModel.easyQuizCorrectCount.collectAsStateWithLifecycle()

  val currentSceneIndex by viewModel.currentSceneIndex.collectAsStateWithLifecycle()

  val quizQuestions by viewModel.quizQuestions.collectAsStateWithLifecycle()
  val quizIndex by viewModel.quizIndex.collectAsStateWithLifecycle()

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = {
      fadeIn(animationSpec = tween(280)) togetherWith fadeOut(animationSpec = tween(280))
    },
    label = "screen_transition"
  ) { target ->
    when (target) {
      is ScreenDestination.Home -> {
        HomeScreen(
          isSpeaking = isSpeaking,
          spokenText = spokenText,
          totalStars = totalStars,
          onOpenFlashcards = { viewModel.openFlashcards() },
          onOpenStory = { viewModel.openStoryMenu() },
          onOpenQuiz = { viewModel.openQuizGame() },
          onTeacherTap = { viewModel.onTeacherTapGeneral() }
        )
      }

      is ScreenDestination.FlashcardList, is ScreenDestination.FlashcardDetail -> {
        FlashcardsScreen(
          selectedCategory = selectedCategory,
          selectedSubCategoryId = selectedSubCategoryId,
          selectedCard = selectedCard,
          easyCorrectCount = easyQuizCorrectCount,
          isSpeaking = isSpeaking,
          spokenText = spokenText,
          showCelebration = showCelebration,
          onSelectCategory = { viewModel.selectCategory(it) },
          onSelectSubCategory = { viewModel.selectSubCategory(it) },
          onSelectCard = { viewModel.selectFlashcard(it) },
          onCloseCardDetail = { viewModel.closeFlashcardDetail() },
          onListenSound = { viewModel.speakFlashcardSound(it) },
          onStartQuiz = { viewModel.startFlashcardQuiz(it) },
          onAnswerMiniQuiz = { viewModel.answerFlashcardMiniQuiz(it) },
          onReplaySpeech = { viewModel.onTeacherTapGeneral() },
          onHomeClick = { viewModel.navigateToHome() }
        )
      }

      is ScreenDestination.StoryMenu -> {
        StoryMenuScreen(
          isSpeaking = isSpeaking,
          totalStars = totalStars,
          onSelectStory = { viewModel.selectStoryBook(it) },
          onHomeClick = { viewModel.navigateToHome() },
          onSpeakerClick = { viewModel.onTeacherTapGeneral() }
        )
      }

      is ScreenDestination.StoryPlay -> {
        StorytellingScreen(
          storyBook = target.storyBook,
          currentSceneIndex = currentSceneIndex,
          isSpeaking = isSpeaking,
          onNextScene = { viewModel.onSceneChanged(target.storyBook, it) },
          onPrevScene = { viewModel.onSceneChanged(target.storyBook, it) },
          onReplayScene = { viewModel.narrateScene(it) },
          onBackToMenu = { viewModel.backToStoryMenu() }
        )
      }

      is ScreenDestination.QuizPlay -> {
        val currentQ = quizQuestions.getOrElse(quizIndex) { quizQuestions.first() }
        QuizGameScreen(
          currentQuestion = currentQ,
          questionIndex = quizIndex,
          totalQuestions = quizQuestions.size,
          starsEarned = totalStars,
          isSpeaking = isSpeaking,
          spokenText = spokenText,
          showCelebration = showCelebration,
          teacherMood = teacherMood,
          onSelectOption = { viewModel.answerQuiz(it) },
          onReplayQuestion = { viewModel.replayQuizQuestion() },
          onHomeClick = { viewModel.navigateToHome() }
        )
      }
    }
  }
}
