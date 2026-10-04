package com.example

import com.example.data.LearningData
import com.example.model.CategoryType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class VehiclesFlashcardsTest {

  @Test
  fun testVehiclesCategoryAndCardsIntegrity() {
    val vehicleCards = LearningData.flashcards.filter { it.category == CategoryType.VEHICLES }
    assertTrue("Vehicles category must have at least 6 flashcard items", vehicleCards.size >= 6)

    val expectedIds = setOf("vehicle_car", "vehicle_bus", "vehicle_train", "vehicle_plane", "vehicle_bicycle", "vehicle_boat")
    val actualIds = vehicleCards.map { it.id }.toSet()
    assertTrue("All expected vehicle IDs must exist", actualIds.containsAll(expectedIds))

    vehicleCards.forEach { card ->
      assertTrue("${card.id} nameVi must not be blank", card.nameVi.isNotBlank())
      assertTrue("${card.id} emoji must not be blank", card.emoji.isNotBlank())
      assertTrue("${card.id} soundEffectVi must not be blank", card.soundEffectVi.isNotBlank())
      assertTrue("${card.id} funFactVi must not be blank", card.funFactVi.isNotBlank())
      assertTrue("${card.id} questionVi must not be blank", card.questionVi.isNotBlank())
      assertTrue("${card.id} must have distractors", card.distractors.isNotEmpty())
      assertTrue("${card.id} must have exactly one correct option", card.distractors.count { it.isCorrect } == 1)

      val introSpeech = LearningData.getFlashcardIntroSpeech(card)
      assertTrue("${card.id} intro speech must not be blank", introSpeech.isNotBlank())

      val soundSpeech = LearningData.getFlashcardSoundSpeech(card)
      assertTrue("${card.id} sound speech must not be blank", soundSpeech.isNotBlank())
    }
  }

  @Test
  fun testSubVehiclesSubCategory() {
    val subVehicles = LearningData.subCategories.find { it.id == "sub_vehicles" }
    assertNotNull("sub_vehicles sub-category must exist", subVehicles)
    assertEquals(CategoryType.VEHICLES, subVehicles!!.category)
    assertEquals("Xe Cộ", subVehicles.titleVi)
  }
}
