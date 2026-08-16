package com.example

import com.example.data.StoryAssetManifest
import org.junit.Assert.*
import org.junit.Test
import java.io.File

class StoryHandwashingIntegrationTest {

  @Test
  fun testHandwashingStoryManifestConfiguration() {
    val handwashingStory = StoryAssetManifest.storyBooks.find { it.id == "handwashing_story" }
    assertNotNull("handwashing_story must exist in manifest", handwashingStory)
    
    assertEquals("Mèo Mây học rửa tay sạch sẽ", handwashingStory!!.titleVi)
    assertEquals(4, handwashingStory.totalScenes)
    assertEquals(4, handwashingStory.scenes.size)

    // Scene 1 verification
    val s1 = handwashingStory.scenes[0]
    assertEquals(1, s1.sceneNumber)
    assertEquals(4, s1.totalScenes)
    assertEquals("Tay Mèo Mây Dính Đất", s1.titleVi)
    assertEquals("Sau giờ chơi, tay Mèo Mây dính đất.", s1.narrationVi)
    assertEquals("“Ôi, tay mình bẩn rồi!”", s1.dialogueVi)
    assertEquals("file:///android_asset/stories/handwashing_story/scene_01.png", s1.imageAsset)
    assertEquals("hotspot_tay_dinh_dat", s1.interaction)

    // Scene 2 verification
    val s2 = handwashingStory.scenes[1]
    assertEquals(2, s2.sceneNumber)
    assertEquals(4, s2.totalScenes)
    assertEquals("Thầy Ny Hướng Dẫn", s2.titleVi)
    assertEquals("Thầy Ny chỉ Mèo Mây cách rửa tay.", s2.narrationVi)
    assertEquals("“Trước tiên, mình làm ướt tay nhé!”", s2.dialogueVi)
    assertEquals("file:///android_asset/stories/handwashing_story/scene_02.png", s2.imageAsset)
    assertEquals("hotspot_voi_nuoc", s2.interaction)

    // Scene 3 verification
    val s3 = handwashingStory.scenes[2]
    assertEquals(3, s3.sceneNumber)
    assertEquals(4, s3.totalScenes)
    assertEquals("Xoa Xà Phòng Thật Kỹ", s3.titleVi)
    assertEquals("Mèo Mây xoa xà phòng thật kỹ.", s3.narrationVi)
    assertEquals("“Xoa lòng bàn tay và từng ngón tay.”", s3.dialogueVi)
    assertEquals("file:///android_asset/stories/handwashing_story/scene_03.png", s3.imageAsset)
    assertEquals("hotspot_bot_xa_phong", s3.interaction)

    // Scene 4 verification
    val s4 = handwashingStory.scenes[3]
    assertEquals(4, s4.sceneNumber)
    assertEquals(4, s4.totalScenes)
    assertEquals("Hai Bàn Tay Sạch Sẽ", s4.titleVi)
    assertEquals("Hai bàn tay đã sạch sẽ rồi!", s4.narrationVi)
    assertEquals("“Tay mình sạch rồi!”", s4.dialogueVi)
    assertEquals("file:///android_asset/stories/handwashing_story/scene_04.png", s4.imageAsset)
    assertEquals("hotspot_ban_tay_sach", s4.interaction)
  }

  @Test
  fun testTowelStoryPreserved() {
    val towelStory = StoryAssetManifest.storyBooks.find { it.id == "towel_story" }
    assertNotNull("towel_story must be preserved", towelStory)
    assertEquals("Bé Gạo giúp Thỏ Bông tìm chiếc khăn", towelStory!!.titleVi)
    assertEquals(4, towelStory.totalScenes)
    assertEquals(4, towelStory.scenes.size)
  }

  @Test
  fun testAssetFilesPhysicalExistence() {
    for (i in 1..4) {
      val fTowel = File("src/main/assets/stories/towel_story/scene_0$i.png")
        .takeIf { it.exists() } ?: File("app/src/main/assets/stories/towel_story/scene_0$i.png")
      assertTrue("towel scene $i must exist physically", fTowel.exists())
      assertTrue("towel scene $i size > 0", fTowel.length() > 0)

      val fHandwashing = File("src/main/assets/stories/handwashing_story/scene_0$i.png")
        .takeIf { it.exists() } ?: File("app/src/main/assets/stories/handwashing_story/scene_0$i.png")
      assertTrue("handwashing scene $i must exist physically", fHandwashing.exists())
      assertTrue("handwashing scene $i size > 0", fHandwashing.length() > 0)
    }
  }
}
