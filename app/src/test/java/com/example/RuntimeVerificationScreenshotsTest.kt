package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.data.LearningData
import com.example.data.StoryAssetManifest
import com.example.model.CategoryType
import com.example.model.QuizOption
import com.example.model.QuizQuestion
import com.example.ui.components.FlashcardQuizPopup
import com.example.ui.components.TeacherMood
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuizGameScreen
import com.example.ui.screens.StoryMenuScreen
import com.example.ui.screens.StorytellingScreen
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [34])
class RuntimeVerificationScreenshotsTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun capture_01_home_screen() {
    composeTestRule.setContent {
      MyApplicationTheme {
        HomeScreen(
          isSpeaking = false,
          spokenText = "Thầy chào bé Gạo yêu quý! Hôm nay chúng mình cùng học và nghe kể chuyện nhé!",
          totalStars = 12,
          onOpenFlashcards = {},
          onOpenStory = {},
          onOpenQuiz = {},
          onTeacherTap = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_01_home.png")
  }

  @Test
  fun capture_02_story_menu() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StoryMenuScreen(
          isSpeaking = false,
          totalStars = 12,
          onSelectStory = {},
          onHomeClick = {},
          onSpeakerClick = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_02_story_menu.png")
  }

  @Test
  fun capture_03_towel_story_scene_01() {
    val story = StoryAssetManifest.getStoryBook("towel_story")
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = story,
          currentSceneIndex = 0,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_03_towel_scene_01.png")
  }

  @Test
  fun capture_04_handwashing_story_scene_01() {
    val story = StoryAssetManifest.getStoryBook("handwashing_story")
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = story,
          currentSceneIndex = 0,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_04_handwashing_scene_01.png")
  }

  @Test
  fun capture_05_sharing_story_scene_01() {
    val story = StoryAssetManifest.getStoryBook("sharing_story")
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = story,
          currentSceneIndex = 0,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_05_sharing_scene_01.png")
  }

  @Test
  fun capture_06_bedtime_story_scene_01() {
    val story = StoryAssetManifest.getStoryBook("bedtime_story")
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = story,
          currentSceneIndex = 0,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_06_bedtime_scene_01.png")
  }

  @Test
  fun capture_07_flashcards_screen() {
    composeTestRule.setContent {
      MyApplicationTheme {
        FlashcardsScreen(
          selectedCategory = CategoryType.ANIMALS,
          selectedSubCategoryId = "sub_farm",
          selectedCard = null,
          isSpeaking = false,
          spokenText = "Chủ đề Nông Trại có rất nhiều bạn động vật dễ thương!",
          showCelebration = false,
          onSelectCategory = {},
          onSelectSubCategory = {},
          onSelectCard = {},
          onCloseCardDetail = {},
          onAnswerMiniQuiz = {},
          onReplaySpeech = {},
          onHomeClick = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_07_flashcards.png")
  }

  @Test
  fun capture_08_quiz_2_options() {
    val roosterCard = LearningData.flashcards.first { it.id == "farm_chicken" }
    composeTestRule.setContent {
      MyApplicationTheme {
        FlashcardQuizPopup(
          card = roosterCard,
          easyCorrectCount = 0,
          isSpeaking = false,
          spokenText = "Đố bé tìm thấy Chú Gà Trống mào đỏ gáy ò ó o?",
          onClose = {},
          onListenSound = {},
          onStartQuiz = {},
          onAnswerOption = {},
          onReplaySpeech = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_08_quiz_2_options.png")
  }

  @Test
  fun capture_09_quiz_game_screen_2_options() {
    val question = LearningData.quizQuestions.first()
    composeTestRule.setContent {
      MyApplicationTheme {
        QuizGameScreen(
          currentQuestion = question,
          questionIndex = 0,
          totalQuestions = 8,
          starsEarned = 3,
          isSpeaking = false,
          spokenText = question.spokenTextVi,
          showCelebration = false,
          teacherMood = TeacherMood.TALKING,
          onSelectOption = {},
          onReplayQuestion = {},
          onHomeClick = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/runtime_09_quiz_game.png")
  }
}
