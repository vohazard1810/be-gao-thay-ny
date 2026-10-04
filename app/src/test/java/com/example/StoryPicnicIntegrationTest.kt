package com.example

import com.example.data.StoryAssetManifest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class StoryPicnicIntegrationTest {

  private fun projectFile(path: String): File =
    File("src/main/$path").takeIf { it.exists() } ?: File("app/src/main/$path")

  @Test
  fun testPicnicStoryManifestConfiguration() {
    val picnicStory = StoryAssetManifest.storyBooks.find { it.id == "picnic_story" }
    assertNotNull("picnic_story must exist in manifest", picnicStory)

    assertEquals("Bé Gạo và Sóc Nâu đi dã ngoại", picnicStory!!.titleVi)
    assertEquals(4, picnicStory.totalScenes)
    assertEquals(4, picnicStory.scenes.size)
    assertTrue("picnic_story must be published", picnicStory.isPublished)

    // Scene 1 verification
    val s1 = picnicStory.scenes[0]
    assertEquals(1, s1.sceneNumber)
    assertEquals("Chuẩn Bị Giỏ Bánh", s1.titleVi)
    assertEquals("Bé Gạo cùng Sóc Nâu chuẩn bị giỏ bánh ngon.", s1.narrationVi)
    assertEquals("“Chúng mình cùng xếp bánh vào giỏ nhé!”", s1.dialogueVi)
    assertEquals("file:///android_asset/stories/picnic_story/scene_01.png", s1.imageAsset)
    assertEquals("hotspot_gio_banh", s1.interaction)
    assertEquals("be_gao", s1.speakerId)

    // Scene 2 verification
    val s2 = picnicStory.scenes[1]
    assertEquals(2, s2.sceneNumber)
    assertEquals("Con Đường Rợp Bóng", s2.titleVi)
    assertEquals("Hai bạn nhỏ tung tăng đi dưới tán cây xanh mát.", s2.narrationVi)
    assertEquals("“Gió thổi lá cây xào xạc vui tai quá!”", s2.dialogueVi)
    assertEquals("file:///android_asset/stories/picnic_story/scene_02.png", s2.imageAsset)
    assertEquals("hotspot_la_cay", s2.interaction)
    assertEquals("soc_nau", s2.speakerId)

    // Scene 3 verification
    val s3 = picnicStory.scenes[2]
    assertEquals(3, s3.sceneNumber)
    assertEquals("Món Quà Rừng Xanh", s3.titleVi)
    assertEquals("Sóc Nâu nhặt một quả thông đẹp tặng Bé Gạo.", s3.narrationVi)
    assertEquals("“Tặng Bé Gạo quả thông xinh xắn này!”", s3.dialogueVi)
    assertEquals("file:///android_asset/stories/picnic_story/scene_03.png", s3.imageAsset)
    assertEquals("hotspot_qua_thong", s3.interaction)
    assertEquals("soc_nau", s3.speakerId)

    // Scene 4 verification
    val s4 = picnicStory.scenes[3]
    assertEquals(4, s4.sceneNumber)
    assertEquals("Bữa Trưa Vui Vẻ", s4.titleVi)
    assertEquals("Hai bạn trải thảm cỏ và cùng nhau ăn trưa.", s4.narrationVi)
    assertEquals("“Bánh mì và nho ngọt ngon tuyệt cú mèo!”", s4.dialogueVi)
    assertEquals("file:///android_asset/stories/picnic_story/scene_04.png", s4.imageAsset)
    assertEquals("hotspot_bua_trua", s4.interaction)
    assertEquals("soc_nau", s4.speakerId)

    // Verify all 4 images exist on filesystem
    for (i in 1..4) {
      val file = projectFile("assets/stories/picnic_story/scene_0$i.png")
      assertTrue("Picnic story scene $i asset must exist", file.exists() && file.length() > 0)
    }
  }
}
