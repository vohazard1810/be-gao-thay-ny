package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.LearningData
import com.example.model.CategoryType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Bé Gạo & Thầy Ny", appName)
  }

  @Test
  fun `verify flashcards data integrity`() {
    val flashcards = LearningData.flashcards
    assertTrue(flashcards.isNotEmpty())
    assertTrue(flashcards.any { it.category == CategoryType.ANIMALS })
    assertTrue(flashcards.any { it.category == CategoryType.FRUITS })
    assertTrue(flashcards.any { it.category == CategoryType.COLORS })
    assertTrue(flashcards.any { it.category == CategoryType.LETTERS_NUMBERS })
  }

  @Test
  fun `verify storytelling generation`() {
    val char = LearningData.storyCharacters.first()
    val setting = LearningData.storySettings.first()
    val story = LearningData.generateStory(char, setting)
    assertEquals(char.id, story.character.id)
    assertEquals(setting.id, story.setting.id)
    assertTrue(story.sentences.isNotEmpty())
  }

  @Test
  fun `verify multiple storybooks manifest integrity`() {
    val books = com.example.data.StoryAssetManifest.storyBooks
    val publishedBooks = books.filter { it.isPublished }
    assertEquals(4, publishedBooks.size)
    assertEquals(
      setOf("towel_story", "handwashing_story", "sharing_story", "bedtime_story"),
      publishedBooks.map { it.id }.toSet()
    )
    books.forEach { book ->
      assertEquals(book.totalScenes, book.scenes.size)
      assertTrue(book.titleVi.isNotBlank())
      assertTrue(book.moralLessonVi.isNotBlank())
      book.scenes.forEach { scene ->
        assertTrue(scene.narrationVi.isNotBlank())
        assertTrue(scene.titleVi.isNotBlank())
      }
    }
  }
}
