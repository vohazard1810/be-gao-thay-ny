package com.example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.data.LearningData
import com.example.ui.components.FlashcardQuizPopup
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PastelCreamBg
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
class FlashcardQuizPopupScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val roosterCard = LearningData.flashcards.first { it.id == "farm_chicken" }

  @Test
  fun capture_flashcard_rooster_step_a_discovery() {
    composeTestRule.setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = PastelCreamBg
        ) {
          FlashcardQuizPopup(
            card = roosterCard,
            easyCorrectCount = 0,
            isSpeaking = true,
            spokenText = "Đây là chú gà trống. Gà trống gáy Ò ó o o!",
            onClose = {},
            onListenSound = {},
            onStartQuiz = {},
            onAnswerOption = {},
            onReplaySpeech = {}
          )
        }
      }
    }

    composeTestRule.waitForIdle()

    // Verify UI components in Step A
    composeTestRule.onNodeWithTag("flashcard_object_title", useUnmergedTree = true).assertIsDisplayed()
    composeTestRule.onNodeWithTag("btn_listen_sound", useUnmergedTree = true).assertIsDisplayed()
    composeTestRule.onNodeWithTag("btn_start_quiz", useUnmergedTree = true).assertIsDisplayed()

    // Capture screenshot of Step A: Khám Phá
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/flashcard_rooster_step_a_discovery.png")
  }

  @Test
  fun capture_flashcard_rooster_step_b_quiz() {
    composeTestRule.setContent {
      MyApplicationTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = PastelCreamBg
        ) {
          FlashcardQuizPopup(
            card = roosterCard,
            easyCorrectCount = 0,
            isSpeaking = false,
            spokenText = "Đâu là gà trống?",
            onClose = {},
            onListenSound = {},
            onStartQuiz = {},
            onAnswerOption = {},
            onReplaySpeech = {}
          )
        }
      }
    }

    composeTestRule.waitForIdle()

    // Click "Chơi tìm hình" to transition to Step B
    composeTestRule.onNodeWithTag("btn_start_quiz", useUnmergedTree = true).performClick()
    composeTestRule.waitForIdle()

    // Verify UI components in Step B
    composeTestRule.onNodeWithTag("flashcard_quiz_question", useUnmergedTree = true).assertIsDisplayed()
    composeTestRule.onNodeWithTag("quiz_option_chicken", useUnmergedTree = true).assertIsDisplayed()

    // Capture screenshot of Step B: Chọn Hình (EASY mode - 2 big picture cards)
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/flashcard_rooster_step_b_quiz.png")
  }
}

