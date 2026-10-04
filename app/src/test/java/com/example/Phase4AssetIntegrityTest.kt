package com.example

import com.example.data.FlashcardAssetManifest
import com.example.data.LearningData
import com.example.data.StoryAssetManifest
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
  fun `baby chick has a local real photo fallback`() {
    val file = projectFile("assets/flashcards/farm/farm_chick.jpg")
    assertTrue("Local baby chick photo must exist", file.exists() && file.length() > 0)
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

  @Test
  fun `published story dialogue has an explicit speaker`() {
    val publishedScenes = StoryAssetManifest.storyBooks
      .filter { it.isPublished }
      .flatMap { it.scenes }
    assertEquals(20, publishedScenes.size)
    assertTrue(publishedScenes.all { it.speakerId.isNotBlank() })

    val towelSpeakers = StoryAssetManifest.getStoryBook("towel_story").scenes.map { it.speakerId }
    assertEquals(listOf("be_gao", "tho_bong", "be_gao", "be_gao"), towelSpeakers)

    val picnicSpeakers = StoryAssetManifest.getStoryBook("picnic_story").scenes.map { it.speakerId }
    assertEquals(listOf("be_gao", "soc_nau", "soc_nau", "soc_nau"), picnicSpeakers)
  }

  @Test
  fun `approved Be Gao raster states are bundled`() {
    val states = listOf("happy", "cheering", "listening", "idle", "waving")
    states.forEach { state ->
      val file = projectFile("assets/characters/be_gao/be_gao_$state.png")
      assertTrue("Missing Be Gao state: $state", file.exists() && file.length() > 0)
    }
  }

  @Test
  fun `approved Tho Bong raster states are bundled`() {
    val states = listOf("happy", "cheering", "curious", "idle")
    states.forEach { state ->
      val file = projectFile("assets/characters/tho_bong/tho_bong_$state.png")
      assertTrue("Missing Tho Bong state: $state", file.exists() && file.length() > 0)
    }
  }

  @Test
  fun `universe character and prop item badges are bundled`() {
    val items = listOf("cun_dom", "meo_may", "soc_nau", "xe_o_to", "chiec_khan", "tho_bong", "be_gao", "thay_ny")
    items.forEach { item ->
      val file = projectFile("assets/items/$item.png")
      assertTrue("Missing item badge: $item", file.exists() && file.length() > 0)
    }
  }

  @Test
  fun `all published story scenes are valid binary PNG files`() {
    val publishedScenes = StoryAssetManifest.storyBooks
      .filter { it.isPublished }
      .flatMap { it.scenes }
    assertEquals(20, publishedScenes.size)
    publishedScenes.forEach { scene ->
      val relativePath = scene.imageAsset!!.removePrefix("file:///android_asset/")
      val file = projectFile("assets/$relativePath")
      assertTrue("Scene file must exist: ${file.path}", file.exists() && file.length() > 1000)
      // Check PNG magic bytes
      val header = file.inputStream().use { it.readNBytes(8) }
      val pngMagic = byteArrayOf(0x89.toByte(), 'P'.code.toByte(), 'N'.code.toByte(), 'G'.code.toByte(), 0x0D, 0x0A, 0x1A, 0x0A)
      assertTrue("Scene file must have valid PNG magic bytes: ${file.path}", header.contentEquals(pngMagic))
    }
  }
}
