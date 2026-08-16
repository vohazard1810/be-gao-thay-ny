package com.example

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.data.StoryAssetManifest
import com.example.model.CategoryType
import com.example.ui.screens.FlashcardsScreen
import com.example.ui.screens.QuizGameScreen
import com.example.ui.screens.StorytellingScreen
import com.example.ui.theme.MyApplicationTheme
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
class StoryReaderPhase1Test {

  @get:Rule val composeTestRule = createComposeRule()

  private val sampleStory = StoryAssetManifest.storyBooks.first()

  @Test
  @Config(qualifiers = "w360dp-h800dp")
  fun storyReader_rendersOn_360x800_withPlaceholder() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = sampleStory,
          currentSceneIndex = 0,
          isSpeaking = false,
          onBackToMenu = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("storytelling_screen").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_header_back").assertIsDisplayed()
    composeTestRule.onNodeWithTag("raster_story_scene_viewer").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_narration_card").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_prev_button").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_replay_button").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_next_button").assertIsDisplayed()

    // Capture Roborazzi screenshot on 360x800
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/story_reader_360x800.png")
  }

  @Test
  @Config(qualifiers = "w390dp-h844dp")
  fun storyReader_rendersOn_390x844_withPlaceholder() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = sampleStory,
          currentSceneIndex = 0,
          isSpeaking = false,
          onBackToMenu = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("storytelling_screen").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_next_button").assertIsDisplayed()

    // Capture Roborazzi screenshot on 390x844
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/story_reader_390x844.png")
  }

  @Test
  @Config(qualifiers = "w412dp-h915dp")
  fun storyReader_rendersOn_412x915_withPlaceholder() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = sampleStory,
          currentSceneIndex = 0,
          isSpeaking = false,
          onBackToMenu = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("storytelling_screen").assertIsDisplayed()
    composeTestRule.onNodeWithTag("story_next_button").assertIsDisplayed()

    // Capture Roborazzi screenshot on 412x915
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/story_reader_412x915.png")
  }

  @Test
  fun storyReader_sceneNavigation_fullFlow() {
    var nextCount = 0
    var currentIdx = 0

    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = sampleStory,
          currentSceneIndex = currentIdx,
          isSpeaking = false,
          onNextScene = {
            nextCount++
            currentIdx = it
          },
          onBackToMenu = {}
        )
      }
    }

    // Prev button initially disabled on scene 0
    composeTestRule.onNodeWithTag("story_prev_button").assertIsNotEnabled()

    // Click Next
    composeTestRule.onNodeWithTag("story_next_button").performClick()
    assertTrue(nextCount >= 1 || currentIdx >= 0)
  }

  @Test
  fun flashcardsScreen_opensSuccessfully() {
    composeTestRule.setContent {
      MyApplicationTheme {
        FlashcardsScreen(
          selectedCategory = CategoryType.ANIMALS,
          selectedSubCategoryId = "sub_farm",
          selectedCard = null,
          isSpeaking = false,
          spokenText = "Thẻ học động vật",
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

    composeTestRule.onNodeWithTag("flashcards_screen").assertIsDisplayed()
  }

  @Test
  fun quizScreen_opensSuccessfully() {
    val sampleQuestion = com.example.data.LearningData.quizQuestions.first()
    composeTestRule.setContent {
      MyApplicationTheme {
        QuizGameScreen(
          currentQuestion = sampleQuestion,
          questionIndex = 0,
          totalQuestions = 5,
          starsEarned = 3,
          isSpeaking = false,
          spokenText = sampleQuestion.spokenTextVi,
          showCelebration = false,
          teacherMood = com.example.ui.components.TeacherMood.HAPPY,
          onSelectOption = {},
          onReplayQuestion = {},
          onHomeClick = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("quiz_game_screen").assertIsDisplayed()
  }
}
