package com.example.data

import androidx.compose.ui.graphics.Color

/**
 * Cấu trúc dữ liệu cho từng cảnh truyện theo chuẩn Phase 1 & 2
 */
data class SimpleStoryScene(
  val sceneId: String = "",
  val sceneNumber: Int,
  val totalScenes: Int = 8,
  val titleVi: String = "",
  val narrationVi: String,
  val dialogueVi: String = "",
  val imageAsset: String? = null,
  val audioAsset: String? = null,
  val interaction: String? = null,
  val illustrationKey: String = "scene_$sceneNumber"
)

/**
 * Cấu trúc dữ liệu cho một cuốn truyện
 */
data class StoryBook(
  val id: String,
  val titleVi: String,
  val subtitleVi: String,
  val coverEmoji: String,
  val themeColor: Color,
  val accentColor: Color,
  val moralLessonVi: String,
  val isPublished: Boolean = false,
  val totalScenes: Int = 8,
  val scenes: List<SimpleStoryScene>
)

object StoryAssetManifest {

  val storyBooks: List<StoryBook> = listOf(
    // =========================================================================
    // TRUYỆN 1: BÉ GẠO GIÚP THỎ BÔNG TÌM CHIẾC KHĂN
    // Nhân vật: Bé Gạo, Thỏ Bông (đạo cụ: đúng 1 chiếc khăn len xanh dương ở Cảnh 4)
    // =========================================================================
    StoryBook(
      id = "towel_story",
      titleVi = "Bé Gạo giúp Thỏ Bông tìm chiếc khăn",
      subtitleVi = "Bài học về sự quan tâm và giúp đỡ bạn bè",
      coverEmoji = "🐰🧣",
      themeColor = Color(0xFFFFF3E0),
      accentColor = Color(0xFFFB8C00),
      moralLessonVi = "Quan tâm và giúp đỡ bạn bè",
      isPublished = true,
      totalScenes = 4,
      scenes = listOf(
        SimpleStoryScene(
          sceneId = "towel_story_scene_01",
          sceneNumber = 1,
          totalScenes = 4,
          titleVi = "Thỏ Bông Lo Lắng",
          narrationVi = "Bé Gạo thấy Thỏ Bông đang lo lắng.",
          dialogueVi = "“Bạn đang tìm gì thế?”",
          imageAsset = "file:///android_asset/stories/towel_story/scene_01.png",
          audioAsset = null,
          interaction = "hotspot_tho_bong"
        ),
        SimpleStoryScene(
          sceneId = "towel_story_scene_02",
          sceneNumber = 2,
          totalScenes = 4,
          titleVi = "Chiếc Khăn Bị Mất",
          narrationVi = "Thỏ Bông làm mất chiếc khăn xanh.",
          dialogueVi = "“Chiếc khăn của mình đâu rồi nhỉ?”",
          imageAsset = "file:///android_asset/stories/towel_story/scene_02.png",
          audioAsset = null,
          interaction = "hotspot_co_tho_bong"
        ),
        SimpleStoryScene(
          sceneId = "towel_story_scene_03",
          sceneNumber = 3,
          totalScenes = 4,
          titleVi = "Cùng Nhau Đi Tìm",
          narrationVi = "Bé Gạo cùng bạn tìm quanh khu vườn.",
          dialogueVi = "“Chúng mình nhìn bên bụi hoa nhé!”",
          imageAsset = "file:///android_asset/stories/towel_story/scene_03.png",
          audioAsset = null,
          interaction = "hotspot_bui_hoa"
        ),
        SimpleStoryScene(
          sceneId = "towel_story_scene_04",
          sceneNumber = 4,
          totalScenes = 4,
          titleVi = "Tìm Thấy Chiếc Khăn",
          narrationVi = "Bé Gạo nhìn thấy chiếc khăn bên bụi hoa.",
          dialogueVi = "“Khăn của bạn đây rồi!”",
          imageAsset = "file:///android_asset/stories/towel_story/scene_04.png",
          audioAsset = null,
          interaction = "hotspot_chiec_khan"
        )
      )
    ),

    // =========================================================================
    // TRUYỆN 2: MÈO MÂY HỌC RỬA TAY SẠCH SẼ
    // Nhân vật: Mèo Mây, Thầy Ny
    // =========================================================================
    StoryBook(
      id = "handwashing_story",
      titleVi = "Mèo Mây học rửa tay sạch sẽ",
      subtitleVi = "Bài học về vệ sinh cá nhân và rửa tay sạch sẽ",
      coverEmoji = "🐱🧼",
      themeColor = Color(0xFFE1F5FE),
      accentColor = Color(0xFF039BE5),
      moralLessonVi = "Rửa tay sạch bằng xà phòng và nước sạch",
      isPublished = true,
      totalScenes = 4,
      scenes = listOf(
        SimpleStoryScene(
          sceneId = "handwashing_story_scene_01",
          sceneNumber = 1,
          totalScenes = 4,
          titleVi = "Tay Mèo Mây Dính Đất",
          narrationVi = "Sau giờ chơi, tay Mèo Mây dính đất.",
          dialogueVi = "“Ôi, tay mình bẩn rồi!”",
          imageAsset = "file:///android_asset/stories/handwashing_story/scene_01.png",
          audioAsset = null,
          interaction = "hotspot_tay_dinh_dat"
        ),
        SimpleStoryScene(
          sceneId = "handwashing_story_scene_02",
          sceneNumber = 2,
          totalScenes = 4,
          titleVi = "Thầy Ny Hướng Dẫn",
          narrationVi = "Thầy Ny chỉ Mèo Mây cách rửa tay.",
          dialogueVi = "“Trước tiên, mình làm ướt tay nhé!”",
          imageAsset = "file:///android_asset/stories/handwashing_story/scene_02.png",
          audioAsset = null,
          interaction = "hotspot_voi_nuoc"
        ),
        SimpleStoryScene(
          sceneId = "handwashing_story_scene_03",
          sceneNumber = 3,
          totalScenes = 4,
          titleVi = "Xoa Xà Phòng Thật Kỹ",
          narrationVi = "Mèo Mây xoa xà phòng thật kỹ.",
          dialogueVi = "“Xoa lòng bàn tay và từng ngón tay.”",
          imageAsset = "file:///android_asset/stories/handwashing_story/scene_03.png",
          audioAsset = null,
          interaction = "hotspot_bot_xa_phong"
        ),
        SimpleStoryScene(
          sceneId = "handwashing_story_scene_04",
          sceneNumber = 4,
          totalScenes = 4,
          titleVi = "Hai Bàn Tay Sạch Sẽ",
          narrationVi = "Hai bàn tay đã sạch sẽ rồi!",
          dialogueVi = "“Tay mình sạch rồi!”",
          imageAsset = "file:///android_asset/stories/handwashing_story/scene_04.png",
          audioAsset = null,
          interaction = "hotspot_ban_tay_sach"
        )
      )
    ),

    // =========================================================================
    // TRUYỆN 3: CÚN ĐỐM VUI VẺ CHIA SẺ ĐỒ CHƠI
    // Nhân vật: Cún Đốm, Bé Gạo
    // =========================================================================
    StoryBook(
      id = "story_sharing",
      titleVi = "Cún Đốm Vui Vẻ Chia Sẻ Đồ Chơi",
      subtitleVi = "Bài học về sự sẻ chia và niềm vui khi cùng chơi",
      coverEmoji = "🐶🚗",
      themeColor = Color(0xFFFFFDE7),
      accentColor = Color(0xFFFBC02D),
      moralLessonVi = "Cùng chia sẻ đồ chơi với bạn bè",
      totalScenes = 8,
      scenes = listOf(
        SimpleStoryScene(
          sceneId = "story_sharing_scene_01",
          sceneNumber = 1,
          titleVi = "Chiếc Ô Tô Đỏ Mới",
          narrationVi = "Cún Đốm được bố tặng một chiếc xe ô tô đồ chơi màu đỏ tươi bóng loáng, còi kêu pim pim rất vui tai.",
          dialogueVi = "“Gâu gâu, xe ô tô của mình đẹp nhất quả đất!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_02",
          sceneNumber = 2,
          titleVi = "Chơi Một Mình Buồn Quá",
          narrationVi = "Cún Đốm ôm chặt chiếc xe ngồi chơi một mình trong góc, chỉ lát sau đã cảm thấy buồn tẻ và chán ngắt.",
          dialogueVi = "“Chơi xe một mình mãi chẳng có ai thi cùng, chán quá đi...”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_03",
          sceneNumber = 3,
          titleVi = "Bộ Đường Ray Của Bé Gạo",
          narrationVi = "Bé Gạo mang theo bộ đường ray xe lửa bằng gỗ xinh xắn bước vào phòng và tươi cười chào Cún Đốm.",
          dialogueVi = "“Cún Đốm ơi, mình có đường ray uốn lượn đẹp lắm này!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_04",
          sceneNumber = 4,
          titleVi = "Lời Đề Nghị Dễ Thương",
          narrationVi = "Cún Đốm ngập ngừng một chút rồi quyết định đưa chiếc ô tô đỏ sang đường ray của Bé Gạo.",
          dialogueVi = "“Chúng mình cho xe chạy chung trên đường ray của bạn nhé!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_05",
          sceneNumber = 5,
          titleVi = "Xây Cây Cầu Vượt",
          narrationVi = "Hai bạn nhỏ cùng nhau lắp ghép những khối gỗ màu sắc tạo thành cây cầu vượt cao vút và đường hầm kỳ thú.",
          dialogueVi = "“Bé Gạo giữ mố cầu, Cún Đốm đặt đường ray lên trên nha!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_06",
          sceneNumber = 6,
          titleVi = "Chiếc Xe Vút Bay",
          narrationVi = "Chiếc xe ô tô đỏ lao vun vút qua cầu vượt, chui qua đường hầm gỗ trong tiếng reo hò thích thú.",
          dialogueVi = "“Vèo vèo... chiếc xe lướt êm ru qua cầu rồi kìa!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_07",
          sceneNumber = 7,
          titleVi = "Tiếng Cười Rộn Rã",
          narrationVi = "Căn phòng ngập tràn tiếng cười giòn giã của hai bạn nhỏ, niềm vui lan tỏa khắp không gian.",
          dialogueVi = "“Cùng chơi với bạn vui gấp trăm lần chơi một mình!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_sharing_scene_08",
          sceneNumber = 8,
          titleVi = "Đôi Bạn Thân Thiết",
          narrationVi = "Cún Đốm và Bé Gạo cùng cất đồ chơi vào giỏ gọn gàng và khoác vai nhau thật hạnh phúc.",
          dialogueVi = "“Cún Đốm hiểu rồi: Chia sẻ đồ chơi là cách giữ trọn niềm vui!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        )
      )
    ),

    // =========================================================================
    // TRUYỆN 4: SÓC NÂU CÙNG BÉ GẠO ĂN RAU XANH
    // Nhân vật: Sóc Nâu, Bé Gạo, Cô Hươu
    // =========================================================================
    StoryBook(
      id = "story_eating_veggies",
      titleVi = "Sóc Nâu Cùng Bé Gạo Ăn Rau Xanh",
      subtitleVi = "Bài học dinh dưỡng và tình yêu với rau củ quả",
      coverEmoji = "🐿️🥦",
      themeColor = Color(0xFFE8F5E9),
      accentColor = Color(0xFF43A047),
      moralLessonVi = "Ăn nhiều rau củ giúp bé lớn nhanh",
      totalScenes = 8,
      scenes = listOf(
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_01",
          sceneNumber = 1,
          titleVi = "Bữa Trưa Của Sóc Nâu",
          narrationVi = "Đến giờ ăn trưa, bạn Sóc Nâu chỉ thích ăn hạt dẻ và bánh ngọt, còn rau xanh thì để nguyên trong bát.",
          dialogueVi = "“Mình không thích ăn rau đâu, rau có màu xanh lá kì lắm!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_02",
          sceneNumber = 2,
          titleVi = "Bí Mật Của Siêu Nhân Rau",
          narrationVi = "Bé Gạo ghé tai Sóc Nâu bật mí bí mật kỳ diệu về sức mạnh siêu nhiên của những loài rau củ.",
          dialogueVi = "“Sóc Nâu có biết rau củ là thức ăn yêu thích của các siêu nhân không?”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_03",
          sceneNumber = 3,
          titleVi = "Sức Mạnh Diệu Kỳ",
          narrationVi = "Bé Gạo chỉ vào súp lơ xanh giống như những tán cây tí hon giúp tăng cường sức đề kháng và cơ bắp.",
          dialogueVi = "“Cà rốt cam giúp mắt sáng tinh anh, súp lơ xanh giúp xương chắc khỏe!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_04",
          sceneNumber = 4,
          titleVi = "Đĩa Thức Ăn Cầu Vồng",
          narrationVi = "Cô Hươu khéo léo tỉa cà rốt thành hình ngôi sao, súp lơ thành chú gấu nhỏ và cà chua thành bông hoa.",
          dialogueVi = "“Đĩa thức ăn biến thành một bức tranh vườn hoa cầu vồng rồi!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_05",
          sceneNumber = 5,
          titleVi = "Miếng Cắn Đầu Tiên",
          narrationVi = "Sóc Nâu tò mò cầm nĩa gắp một miếng súp lơ hình bông hoa đưa vào miệng nhai thử chậm rãi.",
          dialogueVi = "“Ngoàm... Ồ, súp lơ giòn giòn và ngọt mát quá Bé Gạo ơi!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_06",
          sceneNumber = 6,
          titleVi = "Thử Cà Rốt Ngọt Lành",
          narrationVi = "Tiếp đó, Sóc Nâu ăn thêm miếng cà rốt cam giòn ngọt và miếng bí đỏ bùi bùi béo ngậy.",
          dialogueVi = "“Rau củ quả ngon tuyệt cú mèo, ngọt tự nhiên như mật ong!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_07",
          sceneNumber = 7,
          titleVi = "Hết Veo Cả Đĩa",
          narrationVi = "Chẳng mấy chốc, Sóc Nâu đã ăn sạch bong cả đĩa rau xanh nhiều màu sắc, bụng no căng tròn.",
          dialogueVi = "“Hoan hô Sóc Nâu, bạn đã ăn hết cả đĩa rau rồi nè!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_eating_veggies_scene_08",
          sceneNumber = 8,
          titleVi = "Khỏe Mạnh Vươn Cao",
          narrationVi = "Sóc Nâu vẫy chiếc đuôi bông xù khỏe khoắn, cùng Bé Gạo nhảy chân sáo đầy tràn năng lượng dưới nắng.",
          dialogueVi = "“Từ nay Sóc Nâu là chú sóc siêu nhân thích ăn rau xanh!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        )
      )
    ),

    // =========================================================================
    // TRUYỆN 5: CHÚC BÉ GẠO NGỦ NGOAN CÙNG VÌ SAO
    // Nhân vật: Bé Gạo, Thỏ Bông, Thầy Ny
    // =========================================================================
    StoryBook(
      id = "story_bedtime",
      titleVi = "Chúc Bé Gạo Ngủ Ngoan Cùng Vì Sao",
      subtitleVi = "Bài học giữ thói quen đi ngủ đúng giờ",
      coverEmoji = "🌙⭐",
      themeColor = Color(0xFFEDE7F6),
      accentColor = Color(0xFF7E57C2),
      moralLessonVi = "Vệ sinh cá nhân & ngủ đúng giờ",
      totalScenes = 8,
      scenes = listOf(
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_01",
          sceneNumber = 1,
          titleVi = "Ánh Trăng Đầu Cửa Sổ",
          narrationVi = "Đồng hồ điểm 9 giờ tối, trăng tròn vành vạnh tỏa ánh sáng dịu êm qua khung cửa sổ phòng bé.",
          dialogueVi = "“Chị Hằng và ngàn vì sao đã thức dậy chào Bé Gạo rồi!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_02",
          sceneNumber = 2,
          titleVi = "Cất Gọn Đồ Chơi",
          narrationVi = "Bé Gạo cùng mẹ nhẹ nhàng cất các khối gỗ, sách truyện và đồ chơi vào giỏ ngăn nắp.",
          dialogueVi = "“Đồ chơi cũng buồn ngủ rồi, vào giỏ ngủ ngon nhé các bạn!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_03",
          sceneNumber = 3,
          titleVi = "Đánh Răng Sạch Bóng",
          narrationVi = "Bé Gạo đứng trước gương đánh răng kỹ càng với bàn chải nhỏ, súc miệng nước ấm sạch sẽ.",
          dialogueVi = "“Hàm răng trắng muốt, thơm mát hương dâu để đi ngủ thôi nào!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_04",
          sceneNumber = 4,
          titleVi = "Bộ Pijama Ấm Áp",
          narrationVi = "Bé Gạo mặc bộ pijama màu vàng in hình ngôi sao êm ái, trèo lên chiếc giường đệm bồng bềnh.",
          dialogueVi = "“Bộ quần áo ngủ mềm mại và thơm tho quá mẹ ơi!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_05",
          sceneNumber = 5,
          titleVi = "Đọc Truyện Cùng Mẹ",
          narrationVi = "Mẹ ngồi bên cạnh, giọng đọc ấm áp kể cho Bé Gạo nghe câu chuyện về xứ sở thần tiên diệu kỳ.",
          dialogueVi = "“Mẹ ơi, con thích nghe mẹ đọc truyện ru ngủ nhất trên đời.”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_06",
          sceneNumber = 6,
          titleVi = "Ôm Bạn Thỏ Bông",
          narrationVi = "Bé Gạo ôm bạn Thỏ Bông nhỏ vào lòng, kéo chăn ấm lên ngang ngực, mắt dần khép lại nhẹ nhàng.",
          dialogueVi = "“Thỏ Bông ơi, ngủ ngoan cùng Bé Gạo nhé.”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_07",
          sceneNumber = 7,
          titleVi = "Nụ Hôn Chúc Ngủ Ngon",
          narrationVi = "Mẹ đặt nụ hôn âu yếm lên trán Bé Gạo, chỉnh đèn ngủ màu vàng dịu ấm áp khắp căn phòng.",
          dialogueVi = "“Chúc Bé Gạo yêu quý của mẹ ngủ thật ngon và mơ giấc mơ đẹp!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        ),
        SimpleStoryScene(
          sceneId = "story_bedtime_scene_08",
          sceneNumber = 8,
          titleVi = "Giấc Mơ Diệu Kỳ",
          narrationVi = "Trong giấc ngủ say nồng, Bé Gạo mỉm cười bay lượn cùng ngàn vì sao lấp lánh trên bầu trời đêm.",
          dialogueVi = "“Chúc tất cả các bé ngoan ngủ ngon giấc, ngày mai thức dậy thật vui!”",
          imageAsset = null,
          audioAsset = null,
          interaction = null
        )
      )
    ),

    // Bản 4 cảnh đã có tranh raster, câu ngắn phù hợp bé 3 tuổi.
    StoryBook(
      id = "sharing_story",
      titleVi = "Cún Đốm học chia sẻ đồ chơi",
      subtitleVi = "Chơi cùng bạn vui hơn chơi một mình",
      coverEmoji = "🐶🚗",
      themeColor = Color(0xFFFFFDE7),
      accentColor = Color(0xFFFBC02D),
      moralLessonVi = "Biết chia sẻ và cùng chơi với bạn",
      isPublished = true,
      totalScenes = 4,
      scenes = listOf(
        SimpleStoryScene("sharing_story_scene_01", 1, 4, "Chiếc Xe Đỏ", "Cún Đốm ôm chiếc xe đỏ và chơi một mình.", "“Mình chơi một mình thôi!”", "file:///android_asset/stories/story_sharing/scene_01.png", interaction = "hotspot_xe_do"),
        SimpleStoryScene("sharing_story_scene_02", 2, 4, "Bé Gạo Rủ Bạn", "Bé Gạo mang đường ray đến rủ bạn cùng chơi.", "“Cún Đốm ơi, mình cùng chơi nhé?”", "file:///android_asset/stories/story_sharing/scene_02.png", interaction = "hotspot_be_gao"),
        SimpleStoryScene("sharing_story_scene_03", 3, 4, "Cùng Xây Cây Cầu", "Hai bạn cùng lắp cây cầu và đường ray.", "“Bạn đặt xe lên cầu nhé!”", "file:///android_asset/stories/story_sharing/scene_03.png", interaction = "hotspot_cay_cau"),
        SimpleStoryScene("sharing_story_scene_04", 4, 4, "Chơi Cùng Thật Vui", "Chiếc xe chạy vòng quanh trong tiếng cười.", "“Chơi cùng nhau vui quá!”", "file:///android_asset/stories/story_sharing/scene_04.png", interaction = "hotspot_hai_ban")
      )
    ),

    StoryBook(
      id = "bedtime_story",
      titleVi = "Bé Gạo đi ngủ đúng giờ",
      subtitleVi = "Một buổi tối êm đềm của Bé Gạo",
      coverEmoji = "👧🌙",
      themeColor = Color(0xFFEDE7F6),
      accentColor = Color(0xFF7E57C2),
      moralLessonVi = "Cất đồ chơi, đánh răng và đi ngủ đúng giờ",
      isPublished = true,
      totalScenes = 4,
      scenes = listOf(
        SimpleStoryScene("bedtime_story_scene_01", 1, 4, "Cất Đồ Chơi", "Đã chín giờ, Bé Gạo cất đồ chơi vào giỏ.", "“Đồ chơi ngủ ngoan nhé!”", "file:///android_asset/stories/story_bedtime/scene_01.png", interaction = "hotspot_gio_do_choi"),
        SimpleStoryScene("bedtime_story_scene_02", 2, 4, "Chải Răng Sạch", "Bé Gạo chải răng thật sạch trước khi ngủ.", "“Chải đều từng chiếc răng nào!”", "file:///android_asset/stories/story_bedtime/scene_02.png", interaction = "hotspot_ban_chai"),
        SimpleStoryScene("bedtime_story_scene_03", 3, 4, "Mẹ Kể Chuyện", "Mẹ ngồi bên giường đọc truyện cho Bé Gạo.", "“Con thích nghe mẹ kể chuyện.”", "file:///android_asset/stories/story_bedtime/scene_03.png", interaction = "hotspot_quyen_sach"),
        SimpleStoryScene("bedtime_story_scene_04", 4, 4, "Ngủ Thật Ngon", "Bé Gạo ôm Thỏ Bông và ngủ thật ngon.", "“Chúc các bé ngủ ngon!”", "file:///android_asset/stories/story_bedtime/scene_04.png", interaction = "hotspot_be_gao")
      )
    )
  )

  fun getStoryBook(storyId: String): StoryBook {
    return storyBooks.firstOrNull { it.id == storyId } ?: storyBooks.first()
  }
}
