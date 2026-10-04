package com.example

import androidx.compose.ui.graphics.Color
import com.example.audio.KidsSoundSynthesizer
import com.example.model.MemoryCard
import com.example.model.ScreenDestination
import com.example.model.StickerReward
import org.junit.Assert.*
import org.junit.Test

class NewMinigamesAndAudioTest {

  @Test
  fun testMemoryCardModelAndMatching() {
    val cardA = MemoryCard(
      id = "cat_1",
      matchKey = "cat",
      nameVi = "Mèo Con",
      emoji = "🐱",
      cardColor = Color(0xFFFFD1DC),
      isFaceUp = false,
      isMatched = false
    )

    val cardB = MemoryCard(
      id = "cat_2",
      matchKey = "cat",
      nameVi = "Mèo Con",
      emoji = "🐱",
      cardColor = Color(0xFFFFD1DC),
      isFaceUp = false,
      isMatched = false
    )

    val cardC = MemoryCard(
      id = "dog_1",
      matchKey = "dog",
      nameVi = "Cún Con",
      emoji = "🐶",
      cardColor = Color(0xFFB5EAD7),
      isFaceUp = false,
      isMatched = false
    )

    // Test match logic
    assertEquals(cardA.matchKey, cardB.matchKey)
    assertNotEquals(cardA.id, cardB.id)
    assertNotEquals(cardA.matchKey, cardC.matchKey)

    // Flipped state
    val flippedA = cardA.copy(isFaceUp = true)
    assertTrue(flippedA.isFaceUp)
    assertFalse(flippedA.isMatched)

    // Matched state
    val matchedA = flippedA.copy(isMatched = true)
    assertTrue(matchedA.isMatched)
    assertTrue(matchedA.isFaceUp)
  }

  @Test
  fun testStickerRewardProgression() {
    val stickers = listOf(
      StickerReward("stk_begao", "Bé Gạo Chăm Ngoan", "🌸", 0, "Bé Gạo tóc ngắn cài hoa vàng đáng yêu!", true),
      StickerReward("stk_thobong", "Thỏ Bông Áo Tím", "🐰", 1, "Bạn thỏ bông trắng mặc áo len tím!", false),
      StickerReward("stk_star", "Ngôi Sao Lấp Lánh", "⭐", 2, "Ngôi sao may mắn thưởng cho bé thông minh!", false),
      StickerReward("stk_meomay", "Mèo Mây Ngọt Ngào", "🐱", 3, "Bạn mèo mây xám sọc thích chơi đùa!", false),
      StickerReward("stk_cundom", "Cún Đốm Vui Vẻ", "🐶", 5, "Bạn cún đốm tai nâu rất trung thành!", false)
    )

    // Stars = 0: only first sticker unlocked
    var currentStars = 0
    var updated = stickers.map { it.copy(isUnlocked = it.isUnlocked || currentStars >= it.requiredStars) }
    assertEquals(1, updated.count { it.isUnlocked })

    // Stars = 2: 3 stickers unlocked (0, 1, 2)
    currentStars = 2
    updated = stickers.map { it.copy(isUnlocked = it.isUnlocked || currentStars >= it.requiredStars) }
    assertEquals(3, updated.count { it.isUnlocked })

    // Stars = 5: all 5 stickers unlocked
    currentStars = 5
    updated = stickers.map { it.copy(isUnlocked = it.isUnlocked || currentStars >= it.requiredStars) }
    assertEquals(5, updated.count { it.isUnlocked })
  }

  @Test
  fun testScreenDestinationTypes() {
    val destinations: List<ScreenDestination> = listOf(
      ScreenDestination.Home,
      ScreenDestination.GameHub,
      ScreenDestination.MemoryMatch,
      ScreenDestination.StickerBook,
      ScreenDestination.StoryMenu,
      ScreenDestination.QuizPlay
    )
    assertEquals(6, destinations.size)
  }

  @Test
  fun testKidsSoundSynthesizerInstantiation() {
    // Verify synthesizer can be created without crashing
    val synth = KidsSoundSynthesizer()
    assertNotNull(synth)
  }
}
