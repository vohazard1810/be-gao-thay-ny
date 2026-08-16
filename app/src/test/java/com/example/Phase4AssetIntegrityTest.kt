package com.example

import com.example.data.FlashcardAssetManifest
import com.example.data.LearningData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class Phase4AssetIntegrityTest {

  private fun projectFile(path: String): File =
    File("src/main/$path").takeIf { it.exists() } ?: File("app/src/main/$path")

  @Test
  fun `approved Thay Ny raster states are bundled`() {
    val states = listOf("idle", "speaking", "pointing", "encouraging", "celebrating", "storytelling")
    states.forEach { state ->
      val file = projectFile("assets/characters/thay_ny/thay_ny_$state.png")
      assertTrue("Missing Thay Ny state: $state", file.exists() && file.length() > 0)
    }
  }

  @Test
  fun `panda has a local real photo fallback`() {
    val file = projectFile("assets/flashcards/wild/wild_panda.jpg")
    assertTrue("Local panda photo must exist", file.exists() && file.length() > 0)
  }

  @Test
  fun `duck and rooster never share one photo mapping`() {
    val rooster = LearningData.flashcards.first { it.id == "farm_chicken" }
    val duck = LearningData.flashcards.first { it.id == "farm_duck" }
    assertNotEquals(rooster.photoUrl, duck.photoUrl)
    assertEquals("Gà Trống", rooster.nameVi)
    assertEquals("Chú Vịt", duck.nameVi)

    val roosterManifest = FlashcardAssetManifest.getAsset("farm_chicken")!!
    val duckManifest = FlashcardAssetManifest.getAsset("farm_duck")!!
    assertNotEquals(roosterManifest.photoUrl, duckManifest.photoUrl)
  }
}
