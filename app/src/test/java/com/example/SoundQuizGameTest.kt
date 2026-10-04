package com.example

import com.example.data.LearningData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SoundQuizGameTest {

  @Test
  fun testSoundQuizQuestionsIntegrity() {
    val questions = LearningData.soundQuizQuestions
    assertTrue("Must have at least 6 sound quiz questions", questions.size >= 6)

    val validSoundKeys = setOf("dog", "cat", "duck", "car", "train", "bicycle")
    val actualKeys = questions.map { it.soundKey }.toSet()
    assertTrue("All standard animal & vehicle sound keys must be covered", actualKeys.containsAll(validSoundKeys))

    questions.forEach { q ->
      assertTrue("${q.id} promptVi must not be blank", q.promptVi.isNotBlank())
      assertTrue("${q.id} answerNameVi must not be blank", q.answerNameVi.isNotBlank())
      assertTrue("${q.id} soundDescriptionVi must not be blank", q.soundDescriptionVi.isNotBlank())
      assertTrue("${q.id} praiseVi must not be blank", q.praiseVi.isNotBlank())
      assertEquals("${q.id} must have exactly 3 options for toddlers", 3, q.options.size)

      val correctCount = q.options.count { it.isCorrect }
      assertEquals("${q.id} must have exactly 1 correct option", 1, correctCount)

      q.options.forEach { opt ->
        assertTrue("${q.id} option ${opt.id} name must not be blank", opt.nameVi.isNotBlank())
        assertTrue("${q.id} option ${opt.id} emoji must not be blank", opt.emoji.isNotBlank())
      }
    }
  }
}
