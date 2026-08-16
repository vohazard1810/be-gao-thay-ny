package com.example

import com.example.data.StoryAssetManifest
import org.junit.Assert.*
import org.junit.Test

class StoryTowelIntegrationTest {

  @Test
  fun testTowelStoryManifestConfiguration() {
    val towelStory = StoryAssetManifest.storyBooks.find { it.id == "towel_story" }
    assertNotNull("towel_story must exist in manifest", towelStory)
    
    assertEquals("Bé Gạo giúp Thỏ Bông tìm chiếc khăn", towelStory!!.titleVi)
    assertEquals(4, towelStory.totalScenes)
    assertEquals(4, towelStory.scenes.size)

    // Scene 1 verification
    val s1 = towelStory.scenes[0]
    assertEquals(1, s1.sceneNumber)
    assertEquals("Bé Gạo thấy Thỏ Bông đang lo lắng.", s1.narrationVi)
    assertEquals("“Bạn đang tìm gì thế?”", s1.dialogueVi)
    assertEquals("file:///android_asset/stories/towel_story/scene_01.png", s1.imageAsset)
    assertEquals("hotspot_tho_bong", s1.interaction)

    // Scene 2 verification
    val s2 = towelStory.scenes[1]
    assertEquals(2, s2.sceneNumber)
    assertEquals("Thỏ Bông làm mất chiếc khăn xanh.", s2.narrationVi)
    assertEquals("“Chiếc khăn của mình đâu rồi nhỉ?”", s2.dialogueVi)
    assertEquals("file:///android_asset/stories/towel_story/scene_02.png", s2.imageAsset)

    // Scene 3 verification
    val s3 = towelStory.scenes[2]
    assertEquals(3, s3.sceneNumber)
    assertEquals("Bé Gạo cùng bạn tìm quanh khu vườn.", s3.narrationVi)
    assertEquals("“Chúng mình nhìn bên bụi hoa nhé!”", s3.dialogueVi)
    assertEquals("file:///android_asset/stories/towel_story/scene_03.png", s3.imageAsset)

    // Scene 4 verification
    val s4 = towelStory.scenes[3]
    assertEquals(4, s4.sceneNumber)
    assertEquals("Bé Gạo nhìn thấy chiếc khăn bên bụi hoa.", s4.narrationVi)
    assertEquals("“Khăn của bạn đây rồi!”", s4.dialogueVi)
    assertEquals("file:///android_asset/stories/towel_story/scene_04.png", s4.imageAsset)
    assertEquals("hotspot_chiec_khan", s4.interaction)
  }
}
