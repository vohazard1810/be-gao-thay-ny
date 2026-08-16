package com.example

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.data.StoryAssetManifest
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
class StoryTowelScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  private val towelStory = StoryAssetManifest.storyBooks.first { it.id == "towel_story" }

  @Test
  fun capture_scene_01() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = towelStory,
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
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/towel_scene_01.png")
  }

  @Test
  fun capture_scene_02() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = towelStory,
          currentSceneIndex = 1,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/towel_scene_02.png")
  }

  @Test
  fun capture_scene_03() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = towelStory,
          currentSceneIndex = 2,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/towel_scene_03.png")
  }

  @Test
  fun capture_scene_04() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = towelStory,
          currentSceneIndex = 3,
          isSpeaking = false,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/towel_scene_04.png")
  }

  @Test
  fun capture_scene_04_hotspot_tapped() {
    composeTestRule.setContent {
      MyApplicationTheme {
        StorytellingScreen(
          storyBook = towelStory,
          currentSceneIndex = 3,
          isSpeaking = true,
          onPrevScene = {},
          onNextScene = {},
          onReplayScene = {},
          onBackToMenu = {}
        )
      }
    }
    // Tap the hotspot on scene 4
    composeTestRule.onNodeWithTag("scene_hotspot_chiec_khan").performClick()
    composeTestRule.waitForIdle()
    composeTestRule.onRoot().captureRoboImage(filePath = "build/outputs/roborazzi/towel_scene_04_hotspot.png")
  }
}
