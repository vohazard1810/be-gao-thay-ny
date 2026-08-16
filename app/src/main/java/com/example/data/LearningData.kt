package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.model.*

object LearningData {

  // Praise Variations
  val praiseVariations: List<String> = listOf(
    "Hoan hô bé Gạo! Con giỏi quá, trả lời đúng rồi!",
    "Chính xác rồi bé Gạo ơi! Con làm thầy Ny rất vui!",
    "Bé Gạo của thầy siêu thông minh và nhanh nhẹn!",
    "Đúng rồi con yêu! Thầy thưởng cho bé một ngôi sao sáng nhé!",
    "Xuất sắc lắm bé Gạo! Con học giỏi tuyệt vời!",
    "Tuyệt vời ông mặt trời! Bé Gạo thông minh quá!",
    "Bé Gạo ngoan và giỏi nhất lớp của thầy Ny luôn!",
    "Đúng boong luôn rồi bé Gạo ơi! Con thật là cừ khôi!"
  )

  fun getRandomPraise(itemName: String = ""): String {
    val base = praiseVariations.random()
    return if (itemName.isNotBlank() && base.contains("trả lời đúng rồi")) {
      "Hoan hô bé Gạo! Con giỏi quá, chính là $itemName!"
    } else {
      base
    }
  }

  // Sub-categories list
  val subCategories: List<SubCategory> = listOf(
    // Animals
    SubCategory("sub_farm", CategoryType.ANIMALS, "Nông Trại", "🏡", Color(0xFFFFE082)),
    SubCategory("sub_wild", CategoryType.ANIMALS, "Hoang Dã", "🦁", Color(0xFFFFCC80)),
    SubCategory("sub_water", CategoryType.ANIMALS, "Dưới Nước", "🐬", Color(0xFF81D4FA)),
    SubCategory("sub_insects", CategoryType.ANIMALS, "Côn Trùng", "🦋", Color(0xFFA5D6A7)),
    // Letters and numbers
    SubCategory("sub_alphabet", CategoryType.LETTERS_NUMBERS, "Bảng Chữ Cái", "🔤", Color(0xFFFFAB91)),
    SubCategory("sub_numbers", CategoryType.LETTERS_NUMBERS, "Bảng Số Đếm", "🔢", Color(0xFFCE93D8))
  )

  // ==================== ALL FLASHCARD ITEMS ====================
  val flashcards: List<FlashcardItem> = listOf(
    // ----------------------------------------------------
    // 1. ANIMALS - NÔNG TRẠI (FARM)
    // ----------------------------------------------------
    FlashcardItem(
      id = "farm_chicken",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Gà Trống",
      pronunciationVi = "Chú gà trống",
      emoji = "🐔",
      photoUrl = "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ò ó o o!",
      funFactVi = "Chú gà gáy gọi ông mặt trời và bé dậy đi học!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Bạn nào có mào đỏ gáy ò ó o?",
      distractors = listOf(
        FlashcardOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("cat", "Con Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_duck",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Chú Vịt",
      pronunciationVi = "Chú vịt vàng",
      emoji = "🦆",
      photoUrl = "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Quạc quạc quạc!",
      funFactVi = "Chú vịt có bộ lông vàng óng, bơi lội dưới ao!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Bạn nào bơi dưới ao kêu quạc quạc hả bé?",
      distractors = listOf(
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("pig", "Bé Heo", "🐷", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_pig",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Bé Heo",
      pronunciationVi = "Bé heo hồng",
      emoji = "🐷",
      photoUrl = "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ủn ỉn ủn ỉn!",
      funFactVi = "Bé heo hồng hào mập mạp, ăn ngoan và ngủ ngon!",
      cardColor = Color(0xFFF8BBD0),
      questionVi = "Bé heo hồng kêu ủn ỉn ở đâu nhỉ?",
      distractors = listOf(
        FlashcardOption("pig", "Bé Heo", "🐷", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_cow",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Bò Sữa",
      pronunciationVi = "Cô bò sữa",
      emoji = "🐮",
      photoUrl = "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ùm bò bò!",
      funFactVi = "Cô bò sữa cho bé những ly sữa thơm ngon bổ dưỡng!",
      cardColor = Color(0xFFE0F2F1),
      questionVi = "Bạn nào cho bé những ly sữa thơm ngọt?",
      distractors = listOf(
        FlashcardOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("goat", "Chú Dê", "🐐", "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("horse", "Chú Ngựa", "🐴", "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_goat",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Chú Dê",
      pronunciationVi = "Chú dê núi",
      emoji = "🐐",
      photoUrl = "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Be be be!",
      funFactVi = "Chú dê thích gặm cỏ non xanh mướt trên sườn đồi!",
      cardColor = Color(0xFFDCEDC8),
      questionVi = "Bạn dê nào kêu be be vui tai?",
      distractors = listOf(
        FlashcardOption("goat", "Chú Dê", "🐐", "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("sheep", "Bé Cừu", "🐑", "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_horse",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Chú Ngựa",
      pronunciationVi = "Chú ngựa phi",
      emoji = "🐴",
      photoUrl = "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Hí hí hí!",
      funFactVi = "Chú ngựa chạy phi nhanh lóc cóc lóc cóc!",
      cardColor = Color(0xFFD7CCC8),
      questionVi = "Bạn ngựa phi nhanh lóc cóc ở đâu nào?",
      distractors = listOf(
        FlashcardOption("horse", "Chú Ngựa", "🐴", "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_sheep",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Bé Cừu",
      pronunciationVi = "Bé cừu bông",
      emoji = "🐑",
      photoUrl = "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Mê mê mê!",
      funFactVi = "Bé cừu có bộ lông trắng xù mềm mại như kẹo bông!",
      cardColor = Color(0xFFF5F5F5),
      questionVi = "Bạn nào có bộ lông trắng xù mềm êm?",
      distractors = listOf(
        FlashcardOption("sheep", "Bé Cừu", "🐑", "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("goat", "Chú Dê", "🐐", "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("pig", "Bé Heo", "🐷", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_dog",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Chú Chó",
      pronunciationVi = "Chú cún cưng",
      emoji = "🐶",
      photoUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gâu gâu gâu!",
      funFactVi = "Chú cún vẫy đuôi mừng khi bé đi học về!",
      cardColor = Color(0xFFFFECB3),
      questionVi = "Bạn cún ngoan vẫy đuôi kêu gâu gâu ở đâu?",
      distractors = listOf(
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_cat",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Bé Mèo",
      pronunciationVi = "Bé mèo con",
      emoji = "🐱",
      photoUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Meo meo meo!",
      funFactVi = "Bé mèo thích phơi nắng và cuộn tròn ngủ ngoan!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Bạn mèo đáng yêu kêu meo meo ở đâu?",
      distractors = listOf(
        FlashcardOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_rabbit",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Thỏ Trắng",
      pronunciationVi = "Bé thỏ trắng",
      emoji = "🐰",
      photoUrl = "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Nhảy nhót tung tăng!",
      funFactVi = "Bé thỏ có đôi tai dài xinh xắn và rất thích ăn cà rốt tươi ngon!",
      cardColor = Color(0xFFFFEBEE),
      questionVi = "Bạn nào có đôi tai dài thích ăn củ cà rốt đỏ?",
      distractors = listOf(
        FlashcardOption("rabbit", "Thỏ Trắng", "🐰", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_chick",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Gà Con",
      pronunciationVi = "Chú gà con vàng",
      emoji = "🐥",
      photoUrl = "https://images.unsplash.com/photo-1563281577-a7be47e20db9?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Chíp chíp chíp!",
      funFactVi = "Chú gà con có bộ lông vàng tơ mềm mịn như kẹo bông!",
      cardColor = Color(0xFFFFFDE7),
      questionVi = "Bạn nhỏ nào lông vàng tơ kêu chíp chíp?",
      distractors = listOf(
        FlashcardOption("chick", "Gà Con", "🐥", "https://images.unsplash.com/photo-1563281577-a7be47e20db9?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "farm_buffalo",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_farm",
      nameVi = "Chú Trâu",
      pronunciationVi = "Chú trâu nước",
      emoji = "🐃",
      photoUrl = "https://images.unsplash.com/photo-1570042225831-d98fa7577f1e?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Nghé ọ nghé ọ!",
      funFactVi = "Chú trâu có đôi sừng cong cong và thích tắm bùn mát mẻ!",
      cardColor = Color(0xFFCFD8DC),
      questionVi = "Bạn nào có đôi sừng cong cong gặm cỏ trên đồng?",
      distractors = listOf(
        FlashcardOption("buffalo", "Chú Trâu", "🐃", "https://images.unsplash.com/photo-1570042225831-d98fa7577f1e?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("horse", "Chú Ngựa", "🐴", "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80", false)
      )
    ),

    // ----------------------------------------------------
    // 2. ANIMALS - HOANG DÃ (WILD)
    // ----------------------------------------------------
    FlashcardItem(
      id = "wild_lion",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Sư Tử",
      pronunciationVi = "Vua sư tử",
      emoji = "🦁",
      photoUrl = "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gầm gừ oai phong!",
      funFactVi = "Sư tử có chiếc bờm to lớn oai phong lẫm liệt!",
      cardColor = Color(0xFFFFE082),
      questionVi = "Chúa sơn lâm có bờm to oai phong là bạn nào?",
      distractors = listOf(
        FlashcardOption("lion", "Sư Tử", "🦁", "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("tiger", "Chú Hổ", "🐯", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_elephant",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Voi",
      pronunciationVi = "Chú voi to lớn",
      emoji = "🐘",
      photoUrl = "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Huơ vòi phun nước!",
      funFactVi = "Chú voi có chiếc vòi dài hút nước tắm mát lành!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Bạn nào to lớn có vòi dài hút nước?",
      distractors = listOf(
        FlashcardOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("giraffe", "Hươu Cao Cổ", "🦒", "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("bear", "Chú Gấu", "🐻", "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_tiger",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Hổ",
      pronunciationVi = "Chú hổ vằn",
      emoji = "🐯",
      photoUrl = "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gầm vang núi rừng!",
      funFactVi = "Chú hổ có bộ lông sọc vằn cam đen nổi bật!",
      cardColor = Color(0xFFFFCC80),
      questionVi = "Bạn nào có bộ lông sọc vằn cam đen dũng mãnh?",
      distractors = listOf(
        FlashcardOption("tiger", "Chú Hổ", "🐯", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("lion", "Sư Tử", "🦁", "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("zebra", "Ngựa Vằn", "🦓", "https://images.unsplash.com/photo-1526095179574-86e545346ae6?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_giraffe",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Hươu Cao Cổ",
      pronunciationVi = "Hươu cao cổ",
      emoji = "🦒",
      photoUrl = "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gặm lá trên cao!",
      funFactVi = "Hươu có chiếc cổ dài nhất thế giới để ăn lá non trên cao!",
      cardColor = Color(0xFFFFF59D),
      questionVi = "Bạn nào có chiếc cổ dài ngoằng ăn lá cây?",
      distractors = listOf(
        FlashcardOption("giraffe", "Hươu Cao Cổ", "🦒", "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("monkey", "Chú Khỉ", "🐒", "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_monkey",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Khỉ",
      pronunciationVi = "Chú khỉ nâu",
      emoji = "🐒",
      photoUrl = "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Khẹc khẹc vui nhộn!",
      funFactVi = "Chú khỉ leo trèo cây thoăn thoắt và rất thích ăn chuối chín!",
      cardColor = Color(0xFFD7CCC8),
      questionVi = "Bạn nào thích leo cây thoăn thoắt và ăn chuối?",
      distractors = listOf(
        FlashcardOption("monkey", "Chú Khỉ", "🐒", "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("bear", "Chú Gấu", "🐻", "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("tiger", "Chú Hổ", "🐯", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_bear",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Gấu",
      pronunciationVi = "Chú gấu nâu",
      emoji = "🐻",
      photoUrl = "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gừ gừ ấm áp!",
      funFactVi = "Chú gấu tròn xoe thích ăn mật ong ngọt ngào!",
      cardColor = Color(0xFFBCAAA4),
      questionVi = "Bạn gấu tròn xoe thích ăn mật ong ở đâu?",
      distractors = listOf(
        FlashcardOption("bear", "Chú Gấu", "🐻", "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("lion", "Sư Tử", "🦁", "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_zebra",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Ngựa Vằn",
      pronunciationVi = "Ngựa vằn sọc",
      emoji = "🦓",
      photoUrl = "https://images.unsplash.com/photo-1526095179574-86e545346ae6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Hí vang đồng cỏ!",
      funFactVi = "Ngựa vằn có những sọc đen trắng sành điệu khắp cơ thể!",
      cardColor = Color(0xFFECEFF1),
      questionVi = "Bạn nào có sọc đen trắng sành điệu trên mình?",
      distractors = listOf(
        FlashcardOption("zebra", "Ngựa Vằn", "🦓", "https://images.unsplash.com/photo-1526095179574-86e545346ae6?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("horse", "Chú Ngựa", "🐴", "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("tiger", "Chú Hổ", "🐯", "https://images.unsplash.com/photo-1561731216-c3a4d99437d5?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_panda",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Gấu Trúc",
      pronunciationVi = "Gấu trúc Panda",
      emoji = "🐼",
      photoUrl = "https://images.unsplash.com/photo-1564349683136-77e08dba1ef6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Nhai trúc rộp rộp!",
      funFactVi = "Gấu trúc tròn xoe thích ăn lá trúc non và ngủ nướng cả ngày!",
      cardColor = Color(0xFFE0E0E0),
      questionVi = "Bạn gấu nào có quầng mắt đen thích ăn cành trúc?",
      distractors = listOf(
        FlashcardOption("panda", "Gấu Trúc", "🐼", "https://images.unsplash.com/photo-1564349683136-77e08dba1ef6?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("bear", "Chú Gấu", "🐻", "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_fox",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Cáo",
      pronunciationVi = "Chú cáo đỏ",
      emoji = "🦊",
      photoUrl = "https://images.unsplash.com/photo-1516934024742-b461fba47600?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Khẹc khẹc lanh lợi!",
      funFactVi = "Chú cáo có bộ lông màu cam rực rỡ và chiếc đuôi xù to đẹp!",
      cardColor = Color(0xFFFFCCBC),
      questionVi = "Bạn nào có chiếc đuôi xù to và bộ lông cam nổi bật?",
      distractors = listOf(
        FlashcardOption("fox", "Chú Cáo", "🦊", "https://images.unsplash.com/photo-1516934024742-b461fba47600?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "wild_squirrel",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_wild",
      nameVi = "Chú Sóc",
      pronunciationVi = "Chú sóc con",
      emoji = "🐿️",
      photoUrl = "https://images.unsplash.com/photo-1507667522163-37fb33c7d813?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Chít chít nhanh nhẹn!",
      funFactVi = "Chú sóc con có chiếc đuôi xù to và thích nhặt hạt dẻ dự trữ!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Bạn nào đuôi xù chuyền cành thoăn thoắt nhặt hạt dẻ?",
      distractors = listOf(
        FlashcardOption("squirrel", "Chú Sóc", "🐿️", "https://images.unsplash.com/photo-1507667522163-37fb33c7d813?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("monkey", "Chú Khỉ", "🐒", "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("rabbit", "Thỏ Trắng", "🐰", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=500&auto=format&fit=crop&q=80", false)
      )
    ),

    // ----------------------------------------------------
    // 3. ANIMALS - DƯỚI NƯỚC (WATER)
    // ----------------------------------------------------
    FlashcardItem(
      id = "water_dolphin",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Cá Heo",
      pronunciationVi = "Bạn cá heo",
      emoji = "🐬",
      photoUrl = "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Lách chách vui tai!",
      funFactVi = "Cá heo nhảy nhót lộn nhào và rất thân thiện với con người!",
      cardColor = Color(0xFF81D4FA),
      questionVi = "Bạn nào thông minh nhảy múa trên mặt sóng biển?",
      distractors = listOf(
        FlashcardOption("dolphin", "Cá Heo", "🐬", "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("whale", "Cá Voi", "🐋", "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("crab", "Chú Cua", "🦀", "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_whale",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Cá Voi",
      pronunciationVi = "Cá voi khổng lồ",
      emoji = "🐋",
      photoUrl = "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Phun nước lên cao!",
      funFactVi = "Cá voi to lớn nhất đại dương, biết phun cột nước cao vút!",
      cardColor = Color(0xFF90CAF9),
      questionVi = "Bạn nào khổng lồ biết phun cột nước cao vút?",
      distractors = listOf(
        FlashcardOption("whale", "Cá Voi", "🐋", "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("dolphin", "Cá Heo", "🐬", "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("octopus", "Bạch Tuộc", "🐙", "https://images.unsplash.com/photo-1545671913-b89ac1b4ac10?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_crab",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Chú Cua",
      pronunciationVi = "Chú cua biển",
      emoji = "🦀",
      photoUrl = "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Lách cách hai càng!",
      funFactVi = "Chú cua có hai chiếc càng to và thích bò ngang trên cát!",
      cardColor = Color(0xFFFFAB91),
      questionVi = "Bạn nào có hai chiếc càng to bò ngang trên cát?",
      distractors = listOf(
        FlashcardOption("crab", "Chú Cua", "🦀", "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("shrimp", "Chú Tôm", "🦐", "https://images.unsplash.com/photo-1565680018434-b513d5e5fd47?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("starfish", "Sao Biển", "⭐", "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_turtle",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Rùa Biển",
      pronunciationVi = "Cụ rùa biển",
      emoji = "🐢",
      photoUrl = "https://images.unsplash.com/photo-1518467166778-b88f373ffec7?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Bơi chậm rãi êm đềm!",
      funFactVi = "Rùa biển có chiếc mai cứng cáp và sống rất thọ!",
      cardColor = Color(0xFFA5D6A7),
      questionVi = "Bạn nào có chiếc mai tròn cứng bơi chậm rãi?",
      distractors = listOf(
        FlashcardOption("turtle", "Rùa Biển", "🐢", "https://images.unsplash.com/photo-1518467166778-b88f373ffec7?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("crab", "Chú Cua", "🦀", "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("dolphin", "Cá Heo", "🐬", "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_octopus",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Bạch Tuộc",
      pronunciationVi = "Bạch tuộc tám xúc tu",
      emoji = "🐙",
      photoUrl = "https://images.unsplash.com/photo-1545671913-b89ac1b4ac10?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Uốn lượn xúc tu!",
      funFactVi = "Bạch tuộc có tám chiếc xúc tu mềm mại và có thể đổi màu ngụy trang!",
      cardColor = Color(0xFFCE93D8),
      questionVi = "Bạn nào dưới biển có tám chiếc xúc tu mềm mại?",
      distractors = listOf(
        FlashcardOption("octopus", "Bạch Tuộc", "🐙", "https://images.unsplash.com/photo-1545671913-b89ac1b4ac10?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("crab", "Chú Cua", "🦀", "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("whale", "Cá Voi", "🐋", "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_starfish",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Sao Biển",
      pronunciationVi = "Sao biển năm cánh",
      emoji = "⭐",
      photoUrl = "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Lấp lánh bờ cát!",
      funFactVi = "Sao biển có hình dáng như một ngôi sao 5 cánh xinh đẹp dưới đáy đại dương!",
      cardColor = Color(0xFFFFE082),
      questionVi = "Bạn nào hình ngôi sao 5 cánh nằm trên bờ cát?",
      distractors = listOf(
        FlashcardOption("starfish", "Sao Biển", "⭐", "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("crab", "Chú Cua", "🦀", "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("turtle", "Rùa Biển", "🐢", "https://images.unsplash.com/photo-1518467166778-b88f373ffec7?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_clownfish",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Cá Hề Nemo",
      pronunciationVi = "Chú cá hề",
      emoji = "🐠",
      photoUrl = "https://images.unsplash.com/photo-1535591273668-578e31182c4f?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Bơi lội tung tăng!",
      funFactVi = "Cá hề có sọc cam trắng rực rỡ và thích sống trong rạn san hô!",
      cardColor = Color(0xFFFFCC80),
      questionVi = "Bạn cá nhỏ xinh sọc cam trắng bơi trong san hô?",
      distractors = listOf(
        FlashcardOption("clownfish", "Cá Hề Nemo", "🐠", "https://images.unsplash.com/photo-1535591273668-578e31182c4f?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("dolphin", "Cá Heo", "🐬", "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("whale", "Cá Voi", "🐋", "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "water_penguin",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_water",
      nameVi = "Chim Cánh Cụt",
      pronunciationVi = "Chim cánh cụt",
      emoji = "🐧",
      photoUrl = "https://images.unsplash.com/photo-1598439210625-5067c578f3f6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Lạch bạch trên băng!",
      funFactVi = "Chim cánh cụt mặc áo vest đen trắng đi lạch bạch trên băng tuyết!",
      cardColor = Color(0xFFB0BEC5),
      questionVi = "Bạn nào đi lạch bạch trên băng và bơi lặn cực giỏi?",
      distractors = listOf(
        FlashcardOption("penguin", "Chim Cánh Cụt", "🐧", "https://images.unsplash.com/photo-1598439210625-5067c578f3f6?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("dolphin", "Cá Heo", "🐬", "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80", false)
      )
    ),

    // ----------------------------------------------------
    // 4. ANIMALS - CÔN TRÙNG & CHIM (INSECTS & BIRDS)
    // ----------------------------------------------------
    FlashcardItem(
      id = "insect_butterfly",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Bướm Xinh",
      pronunciationVi = "Bướm hoa rực rỡ",
      emoji = "🦋",
      photoUrl = "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Dập dờn cánh bay!",
      funFactVi = "Bướm xinh có đôi cánh rực rỡ sắc màu bay lượn bên hoa!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Bạn nào có đôi cánh sắc màu rực rỡ bay quanh hoa?",
      distractors = listOf(
        FlashcardOption("butterfly", "Bướm Xinh", "🦋", "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("bee", "Chú Ong", "🐝", "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("ladybug", "Bọ Rùa", "🐞", "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "insect_bee",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Chú Ong",
      pronunciationVi = "Chú ong chăm chỉ",
      emoji = "🐝",
      photoUrl = "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Vo ve vo ve!",
      funFactVi = "Chú ong chăm chỉ hút mật hoa làm nên những giọt mật ngọt!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Bạn nào chăm chỉ hút mật hoa thơm ngon?",
      distractors = listOf(
        FlashcardOption("bee", "Chú Ong", "🐝", "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("butterfly", "Bướm Xinh", "🦋", "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("ladybug", "Bọ Rùa", "🐞", "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "insect_ladybug",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Bọ Rùa",
      pronunciationVi = "Bọ rùa đỏ chấm bi",
      emoji = "🐞",
      photoUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Bò ngoan trên lá!",
      funFactVi = "Bọ rùa có chiếc áo choàng màu đỏ chấm bi đen rất xinh xắn!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Bạn nào có áo đỏ chấm bi đen nhỏ nhắn?",
      distractors = listOf(
        FlashcardOption("ladybug", "Bọ Rùa", "🐞", "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("bee", "Chú Ong", "🐝", "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("butterfly", "Bướm Xinh", "🦋", "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "insect_dragonfly",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Chuồn Chuồn",
      pronunciationVi = "Chuồn chuồn ớt",
      emoji = "🛸",
      photoUrl = "https://images.unsplash.com/photo-1512438248247-f0f2a5a8b7f0?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Vút bay lượn lờ!",
      funFactVi = "Chuồn chuồn có bốn cánh mỏng trong suốt bay lượn báo hiệu trời nắng mưa!",
      cardColor = Color(0xFFB2DFDB),
      questionVi = "Bạn nào có cánh mỏng trong suốt bay lượn báo hiệu trời mưa?",
      distractors = listOf(
        FlashcardOption("dragonfly", "Chuồn Chuồn", "🛸", "https://images.unsplash.com/photo-1512438248247-f0f2a5a8b7f0?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("butterfly", "Bướm Xinh", "🦋", "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("bee", "Chú Ong", "🐝", "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "insect_parrot",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Chú Vẹt",
      pronunciationVi = "Chú vẹt thông minh",
      emoji = "🦜",
      photoUrl = "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Hót líu lo bắt chước tiếng người!",
      funFactVi = "Chú vẹt có bộ lông rực rỡ sắc màu và bắt chước giọng nói rất giỏi!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Bạn chim nào có lông sặc sỡ biết bắt chước tiếng người?",
      distractors = listOf(
        FlashcardOption("parrot", "Chú Vẹt", "🦜", "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "insect_dove",
      category = CategoryType.ANIMALS,
      subCategoryId = "sub_insects",
      nameVi = "Bồ Câu",
      pronunciationVi = "Chim bồ câu trắng",
      emoji = "🕊️",
      photoUrl = "https://images.unsplash.com/photo-1522926197415-e55c22c8880c?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Gù gù thân thương!",
      funFactVi = "Chim bồ câu trắng hiền lành là biểu tượng của tình bạn và hòa bình!",
      cardColor = Color(0xFFEDE7F6),
      questionVi = "Bạn chim màu trắng hiền lành mang thông điệp hòa bình?",
      distractors = listOf(
        FlashcardOption("dove", "Bồ Câu", "🕊️", "https://images.unsplash.com/photo-1522926197415-e55c22c8880c?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("parrot", "Chú Vẹt", "🦜", "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=500&auto=format&fit=crop&q=80", false)
      )
    ),

    // ----------------------------------------------------
    // 5. FRUITS - TRÁI CÂY (Chụp thật quả tự nhiên studio)
    // ----------------------------------------------------
    FlashcardItem(
      id = "fruit_apple",
      category = CategoryType.FRUITS,
      nameVi = "Quả Táo",
      pronunciationVi = "Quả táo đỏ giòn",
      emoji = "🍎",
      photoUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Cắn rộp rộp ngọt lành!",
      funFactVi = "Quả táo đỏ giòn ngọt, giàu vitamin giúp bé khỏe mạnh!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Quả nào màu đỏ tròn xoe giòn ngọt?",
      distractors = listOf(
        FlashcardOption("apple", "Quả Táo", "🍎", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("banana", "Quả Chuối", "🍌", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("orange", "Quả Cam", "🍊", "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_banana",
      category = CategoryType.FRUITS,
      nameVi = "Quả Chuối",
      pronunciationVi = "Quả chuối vàng",
      emoji = "🍌",
      photoUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Bóc vỏ thơm lừng!",
      funFactVi = "Quả chuối cong cong màu vàng, ăn ngọt thơm dễ tiêu hóa!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Quả nào cong cong màu vàng chín thơm lừng?",
      distractors = listOf(
        FlashcardOption("banana", "Quả Chuối", "🍌", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("apple", "Quả Táo", "🍎", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("watermelon", "Dưa Hấu", "🍉", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_orange",
      category = CategoryType.FRUITS,
      nameVi = "Quả Cam",
      pronunciationVi = "Quả cam mọng nước",
      emoji = "🍊",
      photoUrl = "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Vắt nước ngọt lành!",
      funFactVi = "Quả cam tròn xoe, vắt nước uống nhiều vitamin C mát mẻ!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Quả nào vỏ màu cam mọng nước bổ dưỡng?",
      distractors = listOf(
        FlashcardOption("orange", "Quả Cam", "🍊", "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("strawberry", "Dâu Tây", "🍓", "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("mango", "Quả Xoài", "🥭", "https://images.unsplash.com/photo-1553279768-865429fa0078?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_watermelon",
      category = CategoryType.FRUITS,
      nameVi = "Dưa Hấu",
      pronunciationVi = "Dưa hấu mát lạnh",
      emoji = "🍉",
      photoUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ăn ngọt lịm mát rượi!",
      funFactVi = "Dưa hấu vỏ xanh ruột đỏ thắm, ăn vào ngày hè siêu đã!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Quả nào vỏ xanh sọc ruột đỏ ngọt mát?",
      distractors = listOf(
        FlashcardOption("watermelon", "Dưa Hấu", "🍉", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("apple", "Quả Táo", "🍎", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("banana", "Quả Chuối", "🍌", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_mango",
      category = CategoryType.FRUITS,
      nameVi = "Quả Xoài",
      pronunciationVi = "Quả xoài cát ngọt",
      emoji = "🥭",
      photoUrl = "https://images.unsplash.com/photo-1553279768-865429fa0078?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ngọt ngào thơm phức!",
      funFactVi = "Quả xoài chín vàng ươm thơm phức ngọt ngào!",
      cardColor = Color(0xFFFFECB3),
      questionVi = "Quả nào chín vàng thơm phức ngọt lịm?",
      distractors = listOf(
        FlashcardOption("mango", "Quả Xoài", "🥭", "https://images.unsplash.com/photo-1553279768-865429fa0078?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("orange", "Quả Cam", "🍊", "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("grapes", "Chùm Nho", "🍇", "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_strawberry",
      category = CategoryType.FRUITS,
      nameVi = "Dâu Tây",
      pronunciationVi = "Quả dâu tây đỏ",
      emoji = "🍓",
      photoUrl = "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Chua ngọt thơm dịu!",
      funFactVi = "Quả dâu tây đỏ mọng có những hạt chấm bi li ti đáng yêu!",
      cardColor = Color(0xFFFF80AB).copy(alpha = 0.3f),
      questionVi = "Quả nào đỏ mọng có nhiều chấm li ti nhỏ xíu?",
      distractors = listOf(
        FlashcardOption("strawberry", "Dâu Tây", "🍓", "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("watermelon", "Dưa Hấu", "🍉", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("apple", "Quả Táo", "🍎", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80", false)
      )
    ),
    FlashcardItem(
      id = "fruit_grapes",
      category = CategoryType.FRUITS,
      nameVi = "Chùm Nho",
      pronunciationVi = "Chùm nho tím",
      emoji = "🍇",
      photoUrl = "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Từng quả mọng nước!",
      funFactVi = "Chùm nho có nhiều quả tròn xoe mọng nước quây quần bên nhau!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Trái nào mọc thành từng chùm nhiều quả tròn mọng?",
      distractors = listOf(
        FlashcardOption("grapes", "Chùm Nho", "🍇", "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=500&auto=format&fit=crop&q=80", true),
        FlashcardOption("banana", "Quả Chuối", "🍌", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80", false),
        FlashcardOption("orange", "Quả Cam", "🍊", "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80", false)
      )
    ),

    // ----------------------------------------------------
    // 6. COLORS - MÀU SẮC
    // ----------------------------------------------------
    FlashcardItem(
      id = "col_red",
      category = CategoryType.COLORS,
      nameVi = "Màu Đỏ",
      pronunciationVi = "Màu đỏ tươi",
      emoji = "🔴",
      photoUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Rực rỡ như hoa hồng!",
      funFactVi = "Màu đỏ tươi thắm của quả táo chín và lá cờ Tổ quốc!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là màu đỏ rực rỡ như trái tim và quả táo chín?",
      distractors = listOf(
        FlashcardOption("col_red", "Màu Đỏ", "🔴", null, true),
        FlashcardOption("col_yellow", "Màu Vàng", "🟡", null, false),
        FlashcardOption("col_blue", "Màu Xanh Dương", "🔵", null, false)
      )
    ),
    FlashcardItem(
      id = "col_yellow",
      category = CategoryType.COLORS,
      nameVi = "Màu Vàng",
      pronunciationVi = "Màu vàng tươi",
      emoji = "🟡",
      photoUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Ấm áp ánh mặt trời!",
      funFactVi = "Màu vàng ấm áp của ông mặt trời và quả chuối chín ngon!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Đâu là màu vàng ấm áp của ông mặt trời?",
      distractors = listOf(
        FlashcardOption("col_yellow", "Màu Vàng", "🟡", null, true),
        FlashcardOption("col_green", "Màu Xanh Lá", "🟢", null, false),
        FlashcardOption("col_red", "Màu Đỏ", "🔴", null, false)
      )
    ),
    FlashcardItem(
      id = "col_green",
      category = CategoryType.COLORS,
      nameVi = "Màu Xanh Lá",
      pronunciationVi = "Màu xanh lá cây",
      emoji = "🟢",
      photoUrl = "https://images.unsplash.com/photo-1518467166778-b88f373ffec7?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Tươi mát chồi non!",
      funFactVi = "Màu xanh lá tươi mát của cây cối thiên nhiên trong lành!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Đâu là màu xanh lá tươi mát của những chiếc lá non?",
      distractors = listOf(
        FlashcardOption("col_green", "Màu Xanh Lá", "🟢", null, true),
        FlashcardOption("col_blue", "Màu Xanh Dương", "🔵", null, false),
        FlashcardOption("col_yellow", "Màu Vàng", "🟡", null, false)
      )
    ),
    FlashcardItem(
      id = "col_blue",
      category = CategoryType.COLORS,
      nameVi = "Màu Xanh Dương",
      pronunciationVi = "Màu xanh da trời",
      emoji = "🔵",
      photoUrl = "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=500&auto=format&fit=crop&q=80",
      soundEffectVi = "Mênh mông biển cả!",
      funFactVi = "Màu xanh da trời và đại dương mênh mông hiền hòa!",
      cardColor = Color(0xFFBBDEFB),
      questionVi = "Đâu là màu xanh của bầu trời và biển cả bao la?",
      distractors = listOf(
        FlashcardOption("col_blue", "Màu Xanh Dương", "🔵", null, true),
        FlashcardOption("col_purple", "Màu Tím", "🟣", null, false),
        FlashcardOption("col_orange", "Màu Cam", "🟧", null, false)
      )
    ),

    // ----------------------------------------------------
    // 7. LETTERS & NUMBERS (Đồ họa chữ to rõ, chuẩn giáo dục mầm non Việt Nam)
    // ----------------------------------------------------
    // 29 Chữ Cái Tiếng Việt Chuẩn Bộ Giáo Dục
    FlashcardItem(
      id = "alpha_a",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ A",
      pronunciationVi = "Chữ A",
      emoji = "A",
      soundEffectVi = "A - Quả táo đỏ 🍎",
      funFactVi = "Chữ A mở đầu bảng chữ cái tiếng Việt!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là Chữ A trong quả táo đỏ?",
      exampleWord = "Quả Táo 🍎",
      distractors = listOf(
        FlashcardOption("a", "Chữ A", "A", null, true),
        FlashcardOption("b", "Chữ B", "B", null, false),
        FlashcardOption("c", "Chữ C", "C", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_aw",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Ă",
      pronunciationVi = "Chữ Ă",
      emoji = "Ă",
      soundEffectVi = "Ă - Mặt trăng lưỡi liềm 🌙",
      funFactVi = "Chữ Ă có chiếc mũ cong như mặt trăng khuyết!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Đâu là Chữ Ă có chiếc mũ cong?",
      exampleWord = "Mặt Trăng 🌙",
      distractors = listOf(
        FlashcardOption("aw", "Chữ Ă", "Ă", null, true),
        FlashcardOption("a", "Chữ A", "A", null, false),
        FlashcardOption("aa", "Chữ Â", "Â", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_aa",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Â",
      pronunciationVi = "Chữ Â",
      emoji = "Â",
      soundEffectVi = "Â - Cây nấm xinh 🍄",
      funFactVi = "Chữ Â đội chiếc mũ nấm chóp nhọn xinh xắn!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Đâu là Chữ Â đội chiếc nón nấm?",
      exampleWord = "Cây Nấm 🍄",
      distractors = listOf(
        FlashcardOption("aa", "Chữ Â", "Â", null, true),
        FlashcardOption("aw", "Chữ Ă", "Ă", null, false),
        FlashcardOption("a", "Chữ A", "A", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_b",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ B",
      pronunciationVi = "Chữ B",
      emoji = "B",
      soundEffectVi = "B - Búp bê ngoan 🎎",
      funFactVi = "Chữ B có một nét thẳng và hai nét cong tròn!",
      cardColor = Color(0xFFF8BBD0),
      questionVi = "Đâu là Chữ B trong búp bê ngoan?",
      exampleWord = "Búp Bê 🎎",
      distractors = listOf(
        FlashcardOption("b", "Chữ B", "B", null, true),
        FlashcardOption("d", "Chữ D", "D", null, false),
        FlashcardOption("a", "Chữ A", "A", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_c",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ C",
      pronunciationVi = "Chữ C",
      emoji = "C",
      soundEffectVi = "C - Con cá bơi 🐟",
      funFactVi = "Chữ C cong tròn như vầng trăng khuyết bên trời!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Đâu là Chữ C trong con cá bơi lội?",
      exampleWord = "Con Cá 🐟",
      distractors = listOf(
        FlashcardOption("c", "Chữ C", "C", null, true),
        FlashcardOption("o", "Chữ O", "O", null, false),
        FlashcardOption("b", "Chữ B", "B", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_d",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ D",
      pronunciationVi = "Chữ D",
      emoji = "D",
      soundEffectVi = "D - Quả dưa hấu ngọt 🍉",
      funFactVi = "Chữ D có nét cong bên trái và nét thẳng bên phải!",
      cardColor = Color(0xFFD1C4E9),
      questionVi = "Đâu là Chữ D trong quả dưa hấu ngọt?",
      exampleWord = "Dưa Hấu 🍉",
      distractors = listOf(
        FlashcardOption("d", "Chữ D", "D", null, true),
        FlashcardOption("dd", "Chữ Đ", "Đ", null, false),
        FlashcardOption("b", "Chữ B", "B", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_dd",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Đ",
      pronunciationVi = "Chữ Đ",
      emoji = "Đ",
      soundEffectVi = "Đ - Đồng hồ tích tắc ⏰",
      funFactVi = "Chữ Đ giống chữ D nhưng có thêm nét gạch ngang trên đầu!",
      cardColor = Color(0xFFC5CAE9),
      questionVi = "Chữ Đ có gạch ngang trên đầu ở đâu nhỉ?",
      exampleWord = "Đồng Hồ ⏰",
      distractors = listOf(
        FlashcardOption("dd", "Chữ Đ", "Đ", null, true),
        FlashcardOption("d", "Chữ D", "D", null, false),
        FlashcardOption("t", "Chữ T", "T", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_e",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ E",
      pronunciationVi = "Chữ E",
      emoji = "E",
      soundEffectVi = "E - Em bé cười tươi 👶",
      funFactVi = "Chữ E cong tròn với chiếc miệng cười mở rộng!",
      cardColor = Color(0xFFBBDEFB),
      questionVi = "Đâu là Chữ E trong em bé cười tươi?",
      exampleWord = "Em Bé 👶",
      distractors = listOf(
        FlashcardOption("e", "Chữ E", "E", null, true),
        FlashcardOption("ee", "Chữ Ê", "Ê", null, false),
        FlashcardOption("c", "Chữ C", "C", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_ee",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Ê",
      pronunciationVi = "Chữ Ê",
      emoji = "Ê",
      soundEffectVi = "Ê - Chiếc ghế gỗ xinh 🪑",
      funFactVi = "Chữ Ê giống chữ E nhưng đội chiếc nón xinh trên đầu!",
      cardColor = Color(0xFFB2EBF2),
      questionVi = "Đâu là Chữ Ê có nón xinh?",
      exampleWord = "Chiếc Ghế 🪑",
      distractors = listOf(
        FlashcardOption("ee", "Chữ Ê", "Ê", null, true),
        FlashcardOption("e", "Chữ E", "E", null, false),
        FlashcardOption("a", "Chữ A", "A", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_g",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ G",
      pronunciationVi = "Chữ G",
      emoji = "G",
      soundEffectVi = "G - Gà trống gáy o o 🐔",
      funFactVi = "Chữ G có một nét cong tròn và nét móc dưới xinh xắn!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Đâu là Chữ G trong chú gà trống gáy vang?",
      exampleWord = "Gà Trống 🐔",
      distractors = listOf(
        FlashcardOption("g", "Chữ G", "G", null, true),
        FlashcardOption("c", "Chữ C", "C", null, false),
        FlashcardOption("q", "Chữ Q", "Q", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_h",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ H",
      pronunciationVi = "Chữ H",
      emoji = "H",
      soundEffectVi = "H - Bông hoa khoe sắc 🌸",
      funFactVi = "Chữ H có hai chân thẳng đứng và nét cầu nối giữa!",
      cardColor = Color(0xFFDCEDC8),
      questionVi = "Đâu là Chữ H trong bông hoa khoe sắc?",
      exampleWord = "Bông Hoa 🌸",
      distractors = listOf(
        FlashcardOption("h", "Chữ H", "H", null, true),
        FlashcardOption("k", "Chữ K", "K", null, false),
        FlashcardOption("n", "Chữ N", "N", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_i",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ I",
      pronunciationVi = "Chữ I",
      emoji = "I",
      soundEffectVi = "I - Hòn bi ve tròn 🔮",
      funFactVi = "Chữ I ngắn có một chấm tròn nhỏ như viên bi trên đầu!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Đâu là Chữ I có chấm nhỏ trên đầu?",
      exampleWord = "Hòn Bi 🔮",
      distractors = listOf(
        FlashcardOption("i", "Chữ I", "I", null, true),
        FlashcardOption("l", "Chữ L", "L", null, false),
        FlashcardOption("t", "Chữ T", "T", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_k",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ K",
      pronunciationVi = "Chữ K",
      emoji = "K",
      soundEffectVi = "K - Chiếc kéo cắt giấy ✂️",
      funFactVi = "Chữ K có nét thẳng đứng và hai nét chéo mở rộng!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Đâu là Chữ K trong chiếc kéo nhỏ xinh?",
      exampleWord = "Cái Kéo ✂️",
      distractors = listOf(
        FlashcardOption("k", "Chữ K", "K", null, true),
        FlashcardOption("h", "Chữ H", "H", null, false),
        FlashcardOption("x", "Chữ X", "X", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_l",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ L",
      pronunciationVi = "Chữ L",
      emoji = "L",
      soundEffectVi = "L - Chiếc lá non xanh 🍃",
      funFactVi = "Chữ L cao ráo và thẳng tắp như thân cây tre!",
      cardColor = Color(0xFFD7CCC8),
      questionVi = "Đâu là Chữ L trong chiếc lá non xanh?",
      exampleWord = "Lá Cây 🍃",
      distractors = listOf(
        FlashcardOption("l", "Chữ L", "L", null, true),
        FlashcardOption("i", "Chữ I", "I", null, false),
        FlashcardOption("t", "Chữ T", "T", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_m",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ M",
      pronunciationVi = "Chữ M",
      emoji = "M",
      soundEffectVi = "M - Bé mèo con đáng yêu 🐱",
      funFactVi = "Chữ M có hai vòm cong nối liền như đôi tai mèo!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là Chữ M trong bé mèo đáng yêu?",
      exampleWord = "Bé Mèo 🐱",
      distractors = listOf(
        FlashcardOption("m", "Chữ M", "M", null, true),
        FlashcardOption("n", "Chữ N", "N", null, false),
        FlashcardOption("u", "Chữ U", "U", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_n",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ N",
      pronunciationVi = "Chữ N",
      emoji = "N",
      soundEffectVi = "N - Chiếc nơ xinh xắn 🎀",
      funFactVi = "Chữ N có một vòm cong tròn trịa vững vàng!",
      cardColor = Color(0xFFF8BBD0),
      questionVi = "Đâu là Chữ N trong chiếc nơ cài áo?",
      exampleWord = "Cái Nơ 🎀",
      distractors = listOf(
        FlashcardOption("n", "Chữ N", "N", null, true),
        FlashcardOption("m", "Chữ M", "M", null, false),
        FlashcardOption("h", "Chữ H", "H", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_o",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ O",
      pronunciationVi = "Chữ O",
      emoji = "O",
      soundEffectVi = "O - Tròn như quả trứng gà 🥚",
      funFactVi = "Chữ O tròn xoe như quả trứng gà quả bóng!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Đâu là Chữ O tròn như quả trứng gà?",
      exampleWord = "Quả Trứng 🥚",
      distractors = listOf(
        FlashcardOption("o", "Chữ O", "O", null, true),
        FlashcardOption("oo", "Chữ Ô", "Ô", null, false),
        FlashcardOption("c", "Chữ C", "C", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_oo",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Ô",
      pronunciationVi = "Chữ Ô",
      emoji = "Ô",
      soundEffectVi = "Ô - Chiếc ô che mưa ☂️",
      funFactVi = "Chữ Ô tròn xoe và đội chiếc mũ che mưa nắng!",
      cardColor = Color(0xFFD1C4E9),
      questionVi = "Đâu là Chữ Ô đội chiếc mũ che mưa?",
      exampleWord = "Cái Ô ☂️",
      distractors = listOf(
        FlashcardOption("oo", "Chữ Ô", "Ô", null, true),
        FlashcardOption("o", "Chữ O", "O", null, false),
        FlashcardOption("ow", "Chữ Ơ", "Ơ", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_ow",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Ơ",
      pronunciationVi = "Chữ Ơ",
      emoji = "Ơ",
      soundEffectVi = "Ơ - Quả mơ có râu 🍑",
      funFactVi = "Chữ Ơ tròn tròn có thêm chiếc râu nhỏ bên phải!",
      cardColor = Color(0xFFC5CAE9),
      questionVi = "Đâu là Chữ Ơ có chiếc râu nhỏ xinh?",
      exampleWord = "Quả Mơ 🍑",
      distractors = listOf(
        FlashcardOption("ow", "Chữ Ơ", "Ơ", null, true),
        FlashcardOption("o", "Chữ O", "O", null, false),
        FlashcardOption("oo", "Chữ Ô", "Ô", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_p",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ P",
      pronunciationVi = "Chữ P",
      emoji = "P",
      soundEffectVi = "P - Xe cảnh sát tuần tra 🚓",
      funFactVi = "Chữ P có nét thẳng đứng và nét cong trên đầu!",
      cardColor = Color(0xFFBBDEFB),
      questionVi = "Đâu là Chữ P trong xe cảnh sát tuần tra?",
      exampleWord = "Xe Cảnh Sát 🚓",
      distractors = listOf(
        FlashcardOption("p", "Chữ P", "P", null, true),
        FlashcardOption("q", "Chữ Q", "Q", null, false),
        FlashcardOption("b", "Chữ B", "B", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_q",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Q",
      pronunciationVi = "Chữ Q",
      emoji = "Q",
      soundEffectVi = "Q - Quả cam ngọt lịm 🍊",
      funFactVi = "Chữ Q có nét cong tròn bên trái và nét thẳng bên phải!",
      cardColor = Color(0xFFB2EBF2),
      questionVi = "Đâu là Chữ Q trong quả cam ngọt lịm?",
      exampleWord = "Quả Cam 🍊",
      distractors = listOf(
        FlashcardOption("q", "Chữ Q", "Q", null, true),
        FlashcardOption("p", "Chữ P", "P", null, false),
        FlashcardOption("d", "Chữ D", "D", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_r",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ R",
      pronunciationVi = "Chữ R",
      emoji = "R",
      soundEffectVi = "R - Chú rùa chăm chỉ 🐢",
      funFactVi = "Chữ R rung đầu lưỡi phát âm rất vui tai!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Đâu là Chữ R trong chú rùa chăm chỉ?",
      exampleWord = "Chú Rùa 🐢",
      distractors = listOf(
        FlashcardOption("r", "Chữ R", "R", null, true),
        FlashcardOption("s", "Chữ S", "S", null, false),
        FlashcardOption("v", "Chữ V", "V", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_s",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ S",
      pronunciationVi = "Chữ S",
      emoji = "S",
      soundEffectVi = "S - Ngôi sao lấp lánh ⭐",
      funFactVi = "Chữ S uốn lượn mềm mại như dòng suối nhỏ!",
      cardColor = Color(0xFFDCEDC8),
      questionVi = "Đâu là Chữ S trong ngôi sao lấp lánh?",
      exampleWord = "Ngôi Sao ⭐",
      distractors = listOf(
        FlashcardOption("s", "Chữ S", "S", null, true),
        FlashcardOption("x", "Chữ X", "X", null, false),
        FlashcardOption("r", "Chữ R", "R", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_t",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ T",
      pronunciationVi = "Chữ T",
      emoji = "T",
      soundEffectVi = "T - Tàu hỏa xình xịch 🚂",
      funFactVi = "Chữ T có nét thẳng đứng và nét ngang như chiếc cột đèn!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Đâu là Chữ T trong đoàn tàu hỏa chạy dài?",
      exampleWord = "Tàu Hỏa 🚂",
      distractors = listOf(
        FlashcardOption("t", "Chữ T", "T", null, true),
        FlashcardOption("l", "Chữ L", "L", null, false),
        FlashcardOption("i", "Chữ I", "I", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_u",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ U",
      pronunciationVi = "Chữ U",
      emoji = "U",
      soundEffectVi = "U - Chiếc mũ đi nắng 👒",
      funFactVi = "Chữ U uốn cong như chiếc bát đựng đồ ăn!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Đâu là Chữ U trong chiếc mũ đi học?",
      exampleWord = "Cái Mũ 👒",
      distractors = listOf(
        FlashcardOption("u", "Chữ U", "U", null, true),
        FlashcardOption("uw", "Chữ Ư", "Ư", null, false),
        FlashcardOption("n", "Chữ N", "N", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_uw",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Ư",
      pronunciationVi = "Chữ Ư",
      emoji = "Ư",
      soundEffectVi = "Ư - Chú hươu cao cổ 🦒",
      funFactVi = "Chữ Ư giống chữ U nhưng có chiếc móc xinh trên đầu!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là Chữ Ư có móc nhỏ trên đầu?",
      exampleWord = "Con Hươu 🦒",
      distractors = listOf(
        FlashcardOption("uw", "Chữ Ư", "Ư", null, true),
        FlashcardOption("u", "Chữ U", "U", null, false),
        FlashcardOption("y", "Chữ Y", "Y", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_v",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ V",
      pronunciationVi = "Chữ V",
      emoji = "V",
      soundEffectVi = "V - Chú vịt bơi lội 🦆",
      funFactVi = "Chữ V mở rộng như hai cánh chim bay lượn!",
      cardColor = Color(0xFFF8BBD0),
      questionVi = "Đâu là Chữ V trong chú vịt bơi lội?",
      exampleWord = "Chú Vịt 🦆",
      distractors = listOf(
        FlashcardOption("v", "Chữ V", "V", null, true),
        FlashcardOption("u", "Chữ U", "U", null, false),
        FlashcardOption("x", "Chữ X", "X", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_x",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ X",
      pronunciationVi = "Chữ X",
      emoji = "X",
      soundEffectVi = "X - Chiếc xe buýt chở bé 🚌",
      funFactVi = "Chữ X có hai nét chéo bắt chéo qua nhau!",
      cardColor = Color(0xFFE1BEE7),
      questionVi = "Đâu là Chữ X trong chiếc xe buýt đến trường?",
      exampleWord = "Xe Buýt 🚌",
      distractors = listOf(
        FlashcardOption("x", "Chữ X", "X", null, true),
        FlashcardOption("s", "Chữ S", "S", null, false),
        FlashcardOption("k", "Chữ K", "K", null, false)
      )
    ),
    FlashcardItem(
      id = "alpha_y",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_alphabet",
      nameVi = "Chữ Y",
      pronunciationVi = "Chữ Y",
      emoji = "Y",
      soundEffectVi = "Y - Bác sĩ y tế chăm sóc bé 🩺",
      funFactVi = "Chữ Y dài kết thúc 29 chữ cái tiếng Việt thân yêu!",
      cardColor = Color(0xFFD1C4E9),
      questionVi = "Đâu là Chữ Y trong bác sĩ y tế?",
      exampleWord = "Y Tế 🩺",
      distractors = listOf(
        FlashcardOption("y", "Chữ Y", "Y", null, true),
        FlashcardOption("v", "Chữ V", "V", null, false),
        FlashcardOption("i", "Chữ I", "I", null, false)
      )
    ),

    // ====================================================
    // BẢNG SỐ ĐẾM (1 ĐẾN 10 ĐẦY ĐỦ CÓ CHẤM ĐẾM TRỰC QUAN)
    // ====================================================
    FlashcardItem(
      id = "num_1",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 1",
      pronunciationVi = "Số một",
      emoji = "1",
      soundEffectVi = "Một chú gấu bông 🧸",
      funFactVi = "Số 1 thẳng đứng như cây nến thắp sáng!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là Số 1 thẳng đứng hả bé?",
      dotCount = 1,
      exampleWord = "1 chú gấu 🧸",
      distractors = listOf(
        FlashcardOption("1", "Số 1", "1", null, true),
        FlashcardOption("2", "Số 2", "2", null, false),
        FlashcardOption("3", "Số 3", "3", null, false)
      )
    ),
    FlashcardItem(
      id = "num_2",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 2",
      pronunciationVi = "Số hai",
      emoji = "2",
      soundEffectVi = "Hai bạn vịt bơi 🦆🦆",
      funFactVi = "Số 2 cong cong như chú thiên nga bơi lội!",
      cardColor = Color(0xFFFFE0B2),
      questionVi = "Đâu là Số 2 cong cong như chú vịt?",
      dotCount = 2,
      exampleWord = "2 bạn vịt 🦆🦆",
      distractors = listOf(
        FlashcardOption("2", "Số 2", "2", null, true),
        FlashcardOption("1", "Số 1", "1", null, false),
        FlashcardOption("3", "Số 3", "3", null, false)
      )
    ),
    FlashcardItem(
      id = "num_3",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 3",
      pronunciationVi = "Số ba",
      emoji = "3",
      soundEffectVi = "Ba ngôi sao sáng ⭐⭐⭐",
      funFactVi = "Số 3 có hai nét cong uốn lượn đáng yêu!",
      cardColor = Color(0xFFFFF9C4),
      questionVi = "Đâu là Số 3 có hai nét cong?",
      dotCount = 3,
      exampleWord = "3 ngôi sao ⭐⭐⭐",
      distractors = listOf(
        FlashcardOption("3", "Số 3", "3", null, true),
        FlashcardOption("4", "Số 4", "4", null, false),
        FlashcardOption("2", "Số 2", "2", null, false)
      )
    ),
    FlashcardItem(
      id = "num_4",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 4",
      pronunciationVi = "Số bốn",
      emoji = "4",
      soundEffectVi = "Bốn quả táo đỏ 🍎🍎🍎🍎",
      funFactVi = "Số 4 giống như chiếc ghế gỗ nhỏ úp ngược!",
      cardColor = Color(0xFFC8E6C9),
      questionVi = "Đâu là Số 4 như chiếc ghế nhỏ?",
      dotCount = 4,
      exampleWord = "4 quả táo 🍎🍎🍎🍎",
      distractors = listOf(
        FlashcardOption("4", "Số 4", "4", null, true),
        FlashcardOption("3", "Số 3", "3", null, false),
        FlashcardOption("5", "Số 5", "5", null, false)
      )
    ),
    FlashcardItem(
      id = "num_5",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 5",
      pronunciationVi = "Số năm",
      emoji = "5",
      soundEffectVi = "Năm bông hoa xinh 🌸🌸🌸🌸🌸",
      funFactVi = "Số 5 có nét thẳng ngang và chiếc bụng tròn xoe!",
      cardColor = Color(0xFFB2EBF2),
      questionVi = "Đâu là Số 5 có chiếc bụng tròn?",
      dotCount = 5,
      exampleWord = "5 bông hoa 🌸🌸🌸🌸🌸",
      distractors = listOf(
        FlashcardOption("5", "Số 5", "5", null, true),
        FlashcardOption("4", "Số 4", "4", null, false),
        FlashcardOption("6", "Số 6", "6", null, false)
      )
    ),
    FlashcardItem(
      id = "num_6",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 6",
      pronunciationVi = "Số sáu",
      emoji = "6",
      soundEffectVi = "Sáu cây kẹo mút 🍭🍭🍭🍭🍭🍭",
      funFactVi = "Số 6 uốn cong từ trên xuống với vòng tròn dưới bụng!",
      cardColor = Color(0xFFBBDEFB),
      questionVi = "Đâu là Số 6 với vòng tròn dưới bụng?",
      dotCount = 6,
      exampleWord = "6 cây kẹo 🍭",
      distractors = listOf(
        FlashcardOption("6", "Số 6", "6", null, true),
        FlashcardOption("9", "Số 9", "9", null, false),
        FlashcardOption("5", "Số 5", "5", null, false)
      )
    ),
    FlashcardItem(
      id = "num_7",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 7",
      pronunciationVi = "Số bảy",
      emoji = "7",
      soundEffectVi = "Bảy sắc cầu vồng 🌈",
      funFactVi = "Số 7 có nét ngang trên và nét chéo đứng hiên ngang!",
      cardColor = Color(0xFFD1C4E9),
      questionVi = "Đâu là Số 7 sắc cầu vồng rực rỡ?",
      dotCount = 7,
      exampleWord = "7 sắc cầu vồng 🌈",
      distractors = listOf(
        FlashcardOption("7", "Số 7", "7", null, true),
        FlashcardOption("1", "Số 1", "1", null, false),
        FlashcardOption("8", "Số 8", "8", null, false)
      )
    ),
    FlashcardItem(
      id = "num_8",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 8",
      pronunciationVi = "Số tám",
      emoji = "8",
      soundEffectVi = "Tám quả bóng bay 🎈",
      funFactVi = "Số 8 giống như chú người tuyết có hai vòng tròn chồng lên nhau!",
      cardColor = Color(0xFFF8BBD0),
      questionVi = "Đâu là Số 8 như chú người tuyết tròn?",
      dotCount = 8,
      exampleWord = "8 quả bóng 🎈",
      distractors = listOf(
        FlashcardOption("8", "Số 8", "8", null, true),
        FlashcardOption("3", "Số 3", "3", null, false),
        FlashcardOption("9", "Số 9", "9", null, false)
      )
    ),
    FlashcardItem(
      id = "num_9",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 9",
      pronunciationVi = "Số chín",
      emoji = "9",
      soundEffectVi = "Chín chú cá vàng 🐟",
      funFactVi = "Số 9 có vòng tròn trên đầu và nét móc uốn lượn xuống dưới!",
      cardColor = Color(0xFFFFCDD2),
      questionVi = "Đâu là Số 9 có vòng tròn trên đầu?",
      dotCount = 9,
      exampleWord = "9 chú cá 🐟",
      distractors = listOf(
        FlashcardOption("9", "Số 9", "9", null, true),
        FlashcardOption("6", "Số 6", "6", null, false),
        FlashcardOption("10", "Số 10", "10", null, false)
      )
    ),
    FlashcardItem(
      id = "num_10",
      category = CategoryType.LETTERS_NUMBERS,
      subCategoryId = "sub_numbers",
      nameVi = "Số 10",
      pronunciationVi = "Số mười",
      emoji = "10",
      soundEffectVi = "Mười điểm tròn trĩnh 🔟",
      funFactVi = "Số 10 gồm số 1 thẳng đứng ghép cùng số 0 tròn xoe!",
      cardColor = Color(0xFFFFE082),
      questionVi = "Đâu là Số 10 mười điểm tròn trĩnh bé ơi?",
      dotCount = 10,
      exampleWord = "10 điểm mười 🔟",
      distractors = listOf(
        FlashcardOption("10", "Số 10", "10", null, true),
        FlashcardOption("1", "Số 1", "1", null, false),
        FlashcardOption("0", "Số 0", "0", null, false)
      )
    )
  )

  // ==================== STORY CHARACTERS (CHARACTER BIBLE) ====================
  // 2D Soft Modern Cartoon Characters with fixed consistent traits and visual bible
  val storyCharacters: List<StoryCharacter> = listOf(
    StoryCharacter(
      id = "char_gao",
      nameVi = "Bé Gạo",
      speciesVi = "Chú gấu nhỏ màu kem",
      emoji = "🐻",
      traitVi = "Hiền lành, tò mò và luôn sẵn lòng giúp đỡ bạn bè",
      outfitVi = "Áo yếm màu xanh lá nhạt và đeo túi nhỏ hình ngôi sao màu vàng",
      facialFeaturesVi = "Khuôn mặt tròn, tai tròn, má hồng nhẹ, mắt to biểu cảm",
      color = Color(0xFFFFF8E1)
    ),
    StoryCharacter(
      id = "char_tho_bong",
      nameVi = "Bé Thỏ Bông",
      speciesVi = "Bé thỏ trắng tai dài",
      emoji = "🐰",
      traitVi = "Nhanh nhẹn, tốt bụng và thích chia sẻ kẹo bánh",
      outfitVi = "Váy hồng pastel có thắt nơ xinh xắn và đi giày hoa cúc",
      facialFeaturesVi = "Đôi tai dài mềm, má phấn hồng, mắt long lanh vui tươi",
      color = Color(0xFFFFEBEE)
    ),
    StoryCharacter(
      id = "char_cun_vang",
      nameVi = "Bé Cún Vàng",
      speciesVi = "Chú cún nhỏ tai cụp",
      emoji = "🐶",
      traitVi = "Năng động, trung thực và dũng cảm bảo vệ bạn nhỏ",
      outfitVi = "Áo phông kẻ sọc xanh dương và chiếc mũ lưỡi trai vàng tươi",
      facialFeaturesVi = "Tai cụp mềm mại, mũi nâu xinh xắn, nụ cười rạng rỡ",
      color = Color(0xFFFFF9C4)
    ),
    StoryCharacter(
      id = "char_meo_miu",
      nameVi = "Bé Mèo Miu",
      speciesVi = "Mèo con lông trắng kem",
      emoji = "🐱",
      traitVi = "Lễ phép, điệu đà và biết nói lời cảm ơn",
      outfitVi = "Váy vàng hoa cúc nhẹ nhàng và túi xách hình trái tim",
      facialFeaturesVi = "Mắt tròn biếc xoe, ria mép nhỏ xíu xinh xắn",
      color = Color(0xFFFFF3E0)
    ),
    StoryCharacter(
      id = "char_soc_nhanh",
      nameVi = "Bé Sóc Nhanh",
      speciesVi = "Sóc nhỏ đuôi xù",
      emoji = "🐿️",
      traitVi = "Khéo léo, chăm chỉ và luôn mang quà tặng bạn bè",
      outfitVi = "Áo yếm màu cam đất và chiếc ba lô hạt dẻ nhỏ xinh",
      facialFeaturesVi = "Chiếc đuôi xù uốn lượn, hai má bầu bĩnh dễ thương",
      color = Color(0xFFFFE0B2)
    ),
    StoryCharacter(
      id = "char_voi_nho",
      nameVi = "Bé Voi Nhỏ",
      speciesVi = "Voi con hiền hậu",
      emoji = "🐘",
      traitVi = "Khỏe mạnh, ấm áp và luôn che chở bạn yếu hơn",
      outfitVi = "Quần yếm xanh ngọc bích và chiếc khăn quàng cổ đỏ thắm",
      facialFeaturesVi = "Vòi nhỏ uốn cong vui vẻ, đôi tai quạt mát dịu dàng",
      color = Color(0xFFEDE7F6)
    )
  )

  // ==================== STORY SETTINGS ====================
  val storySettings: List<StorySetting> = listOf(
    StorySetting("set_school", "Trường Mầm Non", "🏫", "Lớp học rực rỡ đồ chơi, cầu trượt và bạn bè", Color(0xFFFFE082)),
    StorySetting("set_supermarket", "Siêu Thị Tiện Lợi", "🛒", "Gian hàng trái cây tươi mát và bánh kẹo thơm ngon", Color(0xFFFFAB91)),
    StorySetting("set_park", "Công Viên Xanh", "🌳", "Cầu trượt, xích đu và thảm cỏ hoa rực rỡ", Color(0xFFA5D6A7)),
    StorySetting("set_zoo", "Sở Thú Muôn Loài", "🦁", "Thế giới các loài muông thú đáng yêu thân thiện", Color(0xFF81D4FA)),
    StorySetting("set_beach", "Bãi Biển Đầy Nắng", "🏖️", "Sóng biển rì rào vỗ nhẹ và lâu đài cát trắng", Color(0xFFFFCC80)),
    StorySetting("set_birthday", "Sinh Nhật Bạn Thân", "🎂", "Bánh kem ngọt ngào, nến lung linh và bóng bay rực rỡ", Color(0xFFCE93D8))
  )

  // Generator for dynamic child-friendly moral stories (8 scenes per story, 1-3 short sentences, under 15 words)
  fun generateStory(character: StoryCharacter, setting: StorySetting): GeneratedStory {
    val sentences = listOf(
      StorySentence(
        sceneId = "scene_0",
        sentenceIndex = 0,
        narration = "Sáng sớm mai thức giấc, bạn ${character.nameVi} vươn vai mỉm cười.",
        characterDialogue = "Chào buổi sáng! Hôm nay thật đẹp trời!",
        imagePrompt = "Cute 2D cartoon, pastel colors, ${character.nameVi} waking up in cozy bedroom wearing ${character.outfitVi}, morning sunlight streaming through window, 9:16 vertical ratio.",
        backgroundDescription = "Căn phòng ngủ nhỏ xinh ấm áp với ánh nắng ban mai rọi qua khung cửa sổ tròn.",
        characterPosition = "center",
        characterExpression = "happy",
        interactiveObject = "Túi Ngôi Sao Vàng ⭐",
        interactiveEmoji = "⭐",
        interactionInstruction = "Chạm vào Túi Ngôi Sao để mở túi thần kỳ!",
        soundEffect = "Ting ting lấp lánh!",
        animationSuggestion = "Bé Gạo chớp mắt mỉm cười và chiếc túi ngôi sao phát sáng",
        visualSceneEmoji = "🌅 ✨",
        characterActionVi = "${character.nameVi} vươn vai thức dậy và đeo chiếc túi ngôi sao."
      ),
      StorySentence(
        sceneId = "scene_1",
        sentenceIndex = 1,
        narration = "${character.nameVi} mang túi ngôi sao, lễ phép chào mẹ trước khi đi.",
        characterDialogue = "Con chào mẹ con đi chơi ngoan ạ!",
        imagePrompt = "Cute 2D cartoon, soft pastel, ${character.nameVi} waving hand goodbye at the doorstep with small smiling mother, warm flowers, 9:16 vertical ratio.",
        backgroundDescription = "Bậc thềm hiên nhà ấm cúng với giàn hoa giấy nở rực rỡ.",
        characterPosition = "right",
        characterExpression = "polite",
        interactiveObject = "Cánh Tay Vẫy 👋",
        interactiveEmoji = "👋",
        interactionInstruction = "Chạm vào bạn để vẫy tay chào mẹ nhé!",
        soundEffect = "Vẫy tay ríu rít!",
        animationSuggestion = "Bé Gạo cúi đầu chào và vẫy bàn tay nhỏ",
        visualSceneEmoji = "🏡 👋",
        characterActionVi = "Khoanh tay lễ phép chào tạm biệt mẹ."
      ),
      StorySentence(
        sceneId = "scene_2",
        sentenceIndex = 2,
        narration = "Trên con đường hoa nở, ${character.nameVi} tung tăng ngắm bướm xinh.",
        characterDialogue = "Ôi, những bông hoa và chú bướm đẹp quá!",
        imagePrompt = "Cute 2D storybook style, soft pastels, ${character.nameVi} skipping on green meadow path with yellow flowers and floating butterflies, clean focus, 9:16 vertical ratio.",
        backgroundDescription = "Đường mòn thảm cỏ xanh êm ái với hoa cúc vàng và bướm rập rờn.",
        characterPosition = "center",
        characterExpression = "curious",
        interactiveObject = "Bướm Xinh Rực Rỡ 🦋",
        interactiveEmoji = "🦋",
        interactionInstruction = "Chạm vào chú bướm để xem bạn bay lượn!",
        soundEffect = "Cánh bướm bay dập dờn!",
        animationSuggestion = "Đàn bướm bay xoay tròn và tỏa bụi sao lấp lánh",
        visualSceneEmoji = "🌻 🦋",
        characterActionVi = "Tung tăng nhảy chân sáo trên thảm cỏ hoa."
      ),
      StorySentence(
        sceneId = "scene_3",
        sentenceIndex = 3,
        narration = "${character.nameVi} gặp bạn thân, hai bạn cùng nắm tay vui vẻ.",
        characterDialogue = "Chào bạn nhỏ! Chúng mình cùng đi nào!",
        imagePrompt = "Cute 2D story illustration, pastel shades, ${character.nameVi} holding hands with friendly animal friend, big smiles, clear background, 9:16 vertical ratio.",
        backgroundDescription = "Cây cầu gỗ nhỏ bắc qua con suối trong vắt róc rách.",
        characterPosition = "center",
        characterExpression = "friendly",
        interactiveObject = "Bàn Tay Nắm Chặt 🤝",
        interactiveEmoji = "🤝",
        interactionInstruction = "Chạm vào hai bạn nhỏ để trao cái ôm ấm áp!",
        soundEffect = "Tiếng cười khúc khích!",
        animationSuggestion = "Hai bạn nhỏ nhảy nhót vui vẻ và trái tim hồng bay lên",
        visualSceneEmoji = "🤝 💖",
        characterActionVi = "Nắm tay bạn nhỏ cùng đồng hành."
      ),
      StorySentence(
        sceneId = "scene_4",
        sentenceIndex = 4,
        narration = "Trước mắt đã hiện ra cổng ${setting.titleVi} rực rỡ cờ hoa.",
        characterDialogue = "Đến nơi rồi, đẹp tuyệt vời luôn!",
        imagePrompt = "Cute 2D children book illustration, soft pastel colors, entrance of ${setting.titleVi} with balloons and flags, ${character.nameVi} looking up happily, 9:16 vertical ratio.",
        backgroundDescription = "Cổng chào trang trí bóng bay đủ sắc màu và cờ hoa rực rỡ.",
        characterPosition = "left",
        characterExpression = "excited",
        interactiveObject = "Chùm Bóng Bay 🎈",
        interactiveEmoji = "🎈",
        interactionInstruction = "Chạm vào chùm bóng bay để bóng bay lên trời!",
        soundEffect = "Bóng bay bồng bềnh!",
        animationSuggestion = "Những quả bóng bay lơ lửng bồng bềnh đung đưa",
        visualSceneEmoji = "🎈 🏫",
        characterActionVi = "Reo vang vui sướng khi nhìn thấy cổng địa điểm."
      ),
      StorySentence(
        sceneId = "scene_5",
        sentenceIndex = 5,
        narration = "Gặp Thầy Ny, ${character.nameVi} khoanh tay cúi đầu chào to.",
        characterDialogue = "Em chào Thầy Ny ạ!",
        imagePrompt = "Cute 2D cartoon, soft lighting, kind teacher wearing warm yellow shirt with round glasses greeting ${character.nameVi} who bows politely, 9:16 vertical ratio.",
        backgroundDescription = "Căn phòng rực rỡ kệ sách tranh và thảm cỏ xanh mầm non.",
        characterPosition = "center",
        characterExpression = "respectful",
        interactiveObject = "Nụ Cười Thầy Ny 👨‍🏫",
        interactiveEmoji = "👨‍🏫",
        interactionInstruction = "Chạm vào Thầy Ny để nghe lời khen ấm áp!",
        soundEffect = "Giọng nói hiền hậu của Thầy Ny!",
        animationSuggestion = "Thầy Ny mỉm cười xoa đầu khen ngợi bạn nhỏ",
        visualSceneEmoji = "👨‍🏫 🙇",
        characterActionVi = "Khoanh tay cúi đầu 90 độ chào Thầy Ny lễ phép."
      ),
      StorySentence(
        sceneId = "scene_6",
        sentenceIndex = 6,
        narration = "${character.nameVi} lấy món quà trong túi ngôi sao chia sẻ cùng bạn bè.",
        characterDialogue = "Tớ chia sẻ cho bạn này, cùng chơi nhé!",
        imagePrompt = "Cute 2D cartoon children illustration, ${character.nameVi} handing a toy from star bag to friend with warm smile, hearts floating, 9:16 vertical ratio.",
        backgroundDescription = "Khu vực vui chơi thảm xốp với nhiều khối xếp hình và đồ chơi.",
        characterPosition = "center",
        characterExpression = "generous",
        interactiveObject = "Hộp Quà Thần Kỳ 🎁",
        interactiveEmoji = "🎁",
        interactionInstruction = "Chạm vào hộp quà để mở món đồ chơi bất ngờ!",
        soundEffect = "Ting! Mở quà bất ngờ!",
        animationSuggestion = "Hộp quà bung mở phát ra ánh sao vàng và đồ chơi xinh",
        visualSceneEmoji = "🎁 🧸",
        characterActionVi = "Chủ động chia sẻ đồ chơi từ chiếc túi ngôi sao."
      ),
      StorySentence(
        sceneId = "scene_7",
        sentenceIndex = 7,
        narration = "Thầy Ny và cả lớp vỗ tay khen ${character.nameVi} ngoan ngoãn.",
        characterDialogue = "Cảm ơn Thầy và các bạn nhiều lắm!",
        imagePrompt = "Cute 2D cartoon, bright joyful pastel scene, all animal friends clapping hands around happy ${character.nameVi}, golden star glowing above, 9:16 vertical ratio.",
        backgroundDescription = "Khung cảnh lớp học tràn ngập nụ cười, cờ thưởng và huy hiệu bé ngoan.",
        characterPosition = "center",
        characterExpression = "proud",
        interactiveObject = "Ngôi Sao Bé Ngoan 🌟",
        interactiveEmoji = "🌟",
        interactionInstruction = "Chạm vào Ngôi Sao Bé Ngoan để tặng cúp khen thưởng!",
        soundEffect = "Tiếng vỗ tay hoan hô rộn rã! 👏",
        animationSuggestion = "Pháo hoa giấy sắc màu bung tỏa và bé Gạo nhảy múa ăn mừng",
        visualSceneEmoji = "👏 🌟",
        characterActionVi = "Nhận huy hiệu bé ngoan trong tiếng vỗ tay của cả lớp."
      )
    )

    val moralLesson = "Bé Gạo hãy luôn tự giác, lễ phép chào hỏi mọi người và biết yêu thương, chia sẻ cùng bạn bè như bạn ${character.nameVi} nhé!"

    return GeneratedStory(
      id = "story_${character.id}_${setting.id}",
      titleVi = "Chuyến phiêu lưu của ${character.nameVi}",
      character = character,
      setting = setting,
      moralLessonVi = moralLesson,
      sentences = sentences
    )
  }

  // ==================== QUIZ QUESTIONS (Real photo style matching flashcards) ====================
  val quizQuestions: List<QuizQuestion> = listOf(
    QuizQuestion(
      id = "quiz_chicken_sound",
      questionTextVi = "Con nào gáy Ò ó o o gọi bé dậy?",
      spokenTextVi = "Đố bé Gạo, bạn nào có mào đỏ gáy ò ó o gọi ông mặt trời thức giấc?",
      visualClueEmoji = "🐔",
      cluePhotoUrl = "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("chicken", "Gà Trống", "🐔", "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=500&auto=format&fit=crop&q=80", Color(0xFFFFE0B2)),
        QuizOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", Color(0xFFFFF9C4)),
        QuizOption("pig", "Bé Heo", "🐷", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80", Color(0xFFF8BBD0))
      ),
      correctId = "chicken",
      praiseSpeechVi = "Hoan hô bé Gạo! Chính là chú gà trống mào đỏ gáy ò ó o!",
      encourageSpeechVi = "Chưa đúng rồi con ơi, chú gà trống mào đỏ gáy ò ó o cơ mà!"
    ),
    QuizQuestion(
      id = "quiz_duck_quack",
      questionTextVi = "Con nào bơi dưới nước kêu Quạc quạc?",
      spokenTextVi = "Bạn nào có bộ lông vàng, bơi lội tung tăng dưới ao kêu quạc quạc?",
      visualClueEmoji = "🦆",
      cluePhotoUrl = "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("duck", "Chú Vịt", "🦆", "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=500&auto=format&fit=crop&q=80", Color(0xFFFFF9C4)),
        QuizOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", Color(0xFFFFE0B2)),
        QuizOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", Color(0xFFFFECB3))
      ),
      correctId = "duck",
      praiseSpeechVi = "Đúng rồi bé yêu! Chú vịt vàng bơi dưới ao kêu quạc quạc!",
      encourageSpeechVi = "Chưa đúng rồi bé ơi, chú vịt vàng kêu quạc quạc cơ, con chọn lại nhé!"
    ),
    QuizQuestion(
      id = "quiz_dog_bark",
      questionTextVi = "Con nào vẫy đuôi kêu Gâu gâu?",
      spokenTextVi = "Bạn nào rất yêu bé, mỗi khi bé đi học về vẫy đuôi kêu gâu gâu?",
      visualClueEmoji = "🐶",
      cluePhotoUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("dog", "Chú Chó", "🐶", "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=500&auto=format&fit=crop&q=80", Color(0xFFFFECB3)),
        QuizOption("goat", "Chú Dê", "🐐", "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=500&auto=format&fit=crop&q=80", Color(0xFFDCEDC8)),
        QuizOption("pig", "Bé Heo", "🐷", "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500&auto=format&fit=crop&q=80", Color(0xFFF8BBD0))
      ),
      correctId = "dog",
      praiseSpeechVi = "Chính xác rồi bé Gạo! Chú cún con kêu gâu gâu mừng bé!",
      encourageSpeechVi = "Chưa đúng rồi con, chú cún kêu gâu gâu cơ mà, bé nhìn kỹ lại nha!"
    ),
    QuizQuestion(
      id = "quiz_cat_meow",
      questionTextVi = "Con nào thích bắt chuột kêu Meo meo?",
      spokenTextVi = "Bạn nào thích cuộn tròn sưởi nắng và kêu meo meo nũng nịu?",
      visualClueEmoji = "🐱",
      cluePhotoUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("cat", "Bé Mèo", "🐱", "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=500&auto=format&fit=crop&q=80", Color(0xFFFFE0B2)),
        QuizOption("horse", "Chú Ngựa", "🐴", "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=500&auto=format&fit=crop&q=80", Color(0xFFD7CCC8)),
        QuizOption("cow", "Bò Sữa", "🐮", "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=500&auto=format&fit=crop&q=80", Color(0xFFE0F2F1))
      ),
      correctId = "cat",
      praiseSpeechVi = "Bé Gạo thông minh quá! Bé mèo mướp kêu meo meo rất dễ thương!",
      encourageSpeechVi = "Chưa đúng rồi con ơi, bé mèo kêu meo meo cơ mà!"
    ),
    QuizQuestion(
      id = "quiz_giraffe_neck",
      questionTextVi = "Con nào có chiếc cổ dài nhất?",
      spokenTextVi = "Bạn nào có chiếc cổ dài ngoằng để ăn lá cây trên ngọn cao hả bé?",
      visualClueEmoji = "🦒",
      cluePhotoUrl = "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("giraffe", "Hươu Cao Cổ", "🦒", "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=500&auto=format&fit=crop&q=80", Color(0xFFFFF59D)),
        QuizOption("lion", "Sư Tử", "🦁", "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=500&auto=format&fit=crop&q=80", Color(0xFFFFE082)),
        QuizOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", Color(0xFFE1BEE7))
      ),
      correctId = "giraffe",
      praiseSpeechVi = "Chính xác! Bạn hươu cao cổ có chiếc cổ dài nhất thế giới!",
      encourageSpeechVi = "Chưa đúng rồi con, bạn hươu cao cổ có cổ dài ăn lá trên cây cơ!"
    ),
    QuizQuestion(
      id = "quiz_elephant_trunk",
      questionTextVi = "Con nào có vòi dài và tai to?",
      spokenTextVi = "Bạn nào to lớn có chiếc vòi dài và đôi tai to như cái quạt hả con?",
      visualClueEmoji = "🐘",
      cluePhotoUrl = "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("elephant", "Chú Voi", "🐘", "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=500&auto=format&fit=crop&q=80", Color(0xFFE1BEE7)),
        QuizOption("bear", "Chú Gấu", "🐻", "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=500&auto=format&fit=crop&q=80", Color(0xFFBCAAA4)),
        QuizOption("monkey", "Chú Khỉ", "🐒", "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=500&auto=format&fit=crop&q=80", Color(0xFFD7CCC8))
      ),
      correctId = "elephant",
      praiseSpeechVi = "Tuyệt vời bé Gạo! Chú voi to lớn có chiếc vòi dài phun nước!",
      encourageSpeechVi = "Chưa đúng rồi, chú voi có chiếc vòi dài cơ, bé chọn lại nhé!"
    ),
    QuizQuestion(
      id = "quiz_watermelon_inside",
      questionTextVi = "Quả nào vỏ xanh ruột đỏ mát lạnh?",
      spokenTextVi = "Đố bé Gạo, quả nào bên ngoài vỏ xanh bên trong ruột đỏ ngọt mát?",
      visualClueEmoji = "🍉",
      cluePhotoUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("watermelon", "Dưa Hấu", "🍉", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=500&auto=format&fit=crop&q=80", Color(0xFFC8E6C9)),
        QuizOption("banana", "Quả Chuối", "🍌", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=500&auto=format&fit=crop&q=80", Color(0xFFFFF9C4)),
        QuizOption("orange", "Quả Cam", "🍊", "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=500&auto=format&fit=crop&q=80", Color(0xFFFFE0B2))
      ),
      correctId = "watermelon",
      praiseSpeechVi = "Hoan hô bé Gạo! Quả dưa hấu ruột đỏ ăn vào ngọt mát lịm!",
      encourageSpeechVi = "Chưa đúng rồi con, quả dưa hấu ruột đỏ vỏ xanh cơ mà!"
    ),
    QuizQuestion(
      id = "quiz_apple_red",
      questionTextVi = "Đâu là quả táo đỏ giòn ngọt?",
      spokenTextVi = "Bé hãy tìm cho thầy quả táo màu đỏ tròn xoe thơm ngon ở đâu nào?",
      visualClueEmoji = "🍎",
      cluePhotoUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80",
      options = listOf(
        QuizOption("apple", "Quả Táo", "🍎", "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=500&auto=format&fit=crop&q=80", Color(0xFFFFCDD2)),
        QuizOption("mango", "Quả Xoài", "🥭", "https://images.unsplash.com/photo-1553279768-865429fa0078?w=500&auto=format&fit=crop&q=80", Color(0xFFFFECB3)),
        QuizOption("grapes", "Chùm Nho", "🍇", "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=500&auto=format&fit=crop&q=80", Color(0xFFE1BEE7))
      ),
      correctId = "apple",
      praiseSpeechVi = "Xuất sắc lắm bé Gạo! Quả táo đỏ giòn ngọt thơm ngon!",
      encourageSpeechVi = "Chưa đúng rồi con ơi, quả táo đỏ tròn xoe cơ, con chọn lại nhé!"
    )
  )
}
