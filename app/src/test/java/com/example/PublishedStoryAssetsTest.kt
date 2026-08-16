package com.example

import com.example.data.StoryAssetManifest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class PublishedStoryAssetsTest {

  @Test
  fun everyPublishedStoryHasFourCompleteRasterScenes() {
    val published = StoryAssetManifest.storyBooks.filter { it.isPublished }
    assertTrue("The story shelf must not be empty", published.isNotEmpty())

    published.forEach { story ->
      assertEquals("${story.id} must contain 4 toddler-sized scenes", 4, story.totalScenes)
      assertEquals("${story.id} scene list must match totalScenes", story.totalScenes, story.scenes.size)

      story.scenes.forEachIndexed { index, scene ->
        assertEquals(index + 1, scene.sceneNumber)
        assertTrue("${scene.sceneId} narration is too long", scene.narrationVi.length <= 70)
        assertTrue("${scene.sceneId} dialogue is too long", scene.dialogueVi.length <= 55)

        val uri = scene.imageAsset
        assertNotNull("${scene.sceneId} is missing an image", uri)
        assertTrue("${scene.sceneId} must use an Android asset", uri!!.startsWith("file:///android_asset/"))
        assertTrue("${scene.sceneId} must use PNG", uri.endsWith(".png"))
      }
    }
  }
}
