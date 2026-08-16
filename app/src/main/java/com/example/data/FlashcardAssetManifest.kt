package com.example.data

/**
 * Manifest Asset Cố Định Cho Thẻ Học Vui:
 * Mỗi mục đã được kiểm tra:
 * 1. Đúng đối tượng thực tế
 * 2. Đúng tên tiếng Việt
 * 3. Không bị crop mất bộ phận nhận diện
 * 4. Không có watermark
 * 5. Không có chữ trên ảnh
 * 6. Đối tượng chiếm 65-80% khung hình
 * 7. Độ phân giải cao
 */
data class FlashcardAssetEntry(
  val id: String,
  val displayName: String,
  val category: String, // "animals", "fruits", "colors", "letters_numbers"
  val subCategory: String? = null, // "farm", "wild", "water", "insects", "alphabet", "numbers"
  val assetType: String, // "real_photo", "color_swatch", "educational_letter", "educational_number"
  val photoUrl: String?,
  val altText: String,
  val soundEffectVi: String,
  val funFactVi: String
)

object FlashcardAssetManifest {
  val manifest: Map<String, FlashcardAssetEntry> = listOf(
    // === CÔN TRÙNG & CHIM (INSECTS & BIRDS) ===
    FlashcardAssetEntry(
      id = "insect_butterfly",
      displayName = "Bướm Xinh",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1526336024174-e58f5cdd8e13?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con bướm nhiều màu đang xòe rộng hai cánh",
      soundEffectVi = "Dập dờn cánh bay!",
      funFactVi = "Bướm có đôi cánh rực rỡ sắc màu bay lượn bên những bông hoa thơm!"
    ),
    FlashcardAssetEntry(
      id = "insect_bee",
      displayName = "Chú Ong",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1558642452-9d2a7deb7f62?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một chú ong vàng sọc đen đậu trên hoa hút mật",
      soundEffectVi = "Vo ve vo ve!",
      funFactVi = "Chú ong chăm chỉ hút mật hoa làm nên những giọt mật ngọt lịm!"
    ),
    FlashcardAssetEntry(
      id = "insect_ladybug",
      displayName = "Bọ Rùa",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con bọ rùa đỏ chấm đen trên chiếc lá xanh",
      soundEffectVi = "Bò ngoan trên lá!",
      funFactVi = "Bọ rùa có chiếc áo choàng màu đỏ chấm bi đen tròn rất xinh!"
    ),
    FlashcardAssetEntry(
      id = "insect_dragonfly",
      displayName = "Chuồn Chuồn",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1512438248247-f0f2a5a8b7f0?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con chuồn chuồn thấy rõ thân và bốn cánh mỏng",
      soundEffectVi = "Vút bay lượn lờ!",
      funFactVi = "Chuồn chuồn có bốn chiếc cánh mỏng trong suốt bay lượn báo hiệu thời tiết!"
    ),
    FlashcardAssetEntry(
      id = "insect_parrot",
      displayName = "Chú Vẹt",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1552728089-57bdde30beb3?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một chú vẹt có bộ lông sặc sỡ",
      soundEffectVi = "Hót líu lo bắt chước!",
      funFactVi = "Chú vẹt có bộ lông nhiều màu và bắt chước giọng nói rất giỏi!"
    ),
    FlashcardAssetEntry(
      id = "insect_dove",
      displayName = "Bồ Câu",
      category = "animals",
      subCategory = "insects",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1522926197415-e55c22c8880c?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chim bồ câu trắng đậu yên tĩnh",
      soundEffectVi = "Gù gù thân thương!",
      funFactVi = "Chim bồ câu trắng hiền lành là biểu tượng của tình bạn và hòa bình!"
    ),

    // === ĐỘNG VẬT DƯỚI NƯỚC (WATER ANIMALS) ===
    FlashcardAssetEntry(
      id = "water_starfish",
      displayName = "Sao Biển",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1518837695005-2083093ee35b?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con sao biển màu cam 5 cánh trên bờ cát",
      soundEffectVi = "Lấp lánh bờ cát!",
      funFactVi = "Sao biển có hình dáng như một ngôi sao 5 cánh xinh đẹp dưới đáy đại dương!"
    ),
    FlashcardAssetEntry(
      id = "water_crab",
      displayName = "Chú Cua",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con cua với hai chiếc càng to rõ ràng",
      soundEffectVi = "Lách cách hai càng!",
      funFactVi = "Chú cua có hai chiếc càng to và thích bò ngang trên bãi biển!"
    ),
    FlashcardAssetEntry(
      id = "water_dolphin",
      displayName = "Cá Heo",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1607153333879-c174d265f1d2?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật cá heo thấy rõ toàn bộ cơ thể đang bơi trong nước biển trong xanh",
      soundEffectVi = "Lách chách vui tai!",
      funFactVi = "Cá heo nhảy nhót lộn nhào và rất thông minh, thân thiện!"
    ),
    FlashcardAssetEntry(
      id = "water_whale",
      displayName = "Cá Voi",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1568430462989-44163eb1752f?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật toàn thân cá voi bơi trên biển xanh",
      soundEffectVi = "Phun nước lên cao!",
      funFactVi = "Cá voi to lớn nhất đại dương, biết phun cột nước cao vút!"
    ),
    FlashcardAssetEntry(
      id = "water_turtle",
      displayName = "Rùa Biển",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1518467166778-b88f373ffec7?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con rùa biển bơi dưới làn nước",
      soundEffectVi = "Bơi chậm rãi êm đềm!",
      funFactVi = "Rùa biển có chiếc mai cứng cáp và bơi lội khắp đại dương!"
    ),
    FlashcardAssetEntry(
      id = "water_octopus",
      displayName = "Bạch Tuộc",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1545671913-b89ac1b4ac10?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật con bạch tuộc với các xúc tu cuộn tròn",
      soundEffectVi = "Uốn lượn xúc tu!",
      funFactVi = "Bạch tuộc có tám chiếc xúc tu mềm mại và có thể đổi màu ngụy trang!"
    ),
    FlashcardAssetEntry(
      id = "water_clownfish",
      displayName = "Cá Hề Nemo",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1535591273668-578e31182c4f?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú cá hề sọc cam trắng bơi cạnh hải quỳ",
      soundEffectVi = "Bơi lội tung tăng!",
      funFactVi = "Cá hề có sọc cam trắng rực rỡ và thích sống quây quần trong rạn san hô!"
    ),
    FlashcardAssetEntry(
      id = "water_penguin",
      displayName = "Chim Cánh Cụt",
      category = "animals",
      subCategory = "water",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1598439210625-5067c578f3f6?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chim cánh cụt đứng trên băng tuyết",
      soundEffectVi = "Lạch bạch trên băng!",
      funFactVi = "Chim cánh cụt mặc áo lông đen trắng đi lạch bạch và bơi lặn cực cừ!"
    ),

    // === NÔNG TRẠI (FARM ANIMALS) ===
    FlashcardAssetEntry(
      id = "farm_dog",
      displayName = "Chú Chó",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú chó cưng nhìn thẳng ống kính",
      soundEffectVi = "Gâu gâu gâu!",
      funFactVi = "Chú chó trung thành, hay vẫy đuôi mừng rỡ mỗi khi bé đi học về!"
    ),
    FlashcardAssetEntry(
      id = "farm_cat",
      displayName = "Mèo Con",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật mèo con lông mềm mắt to tròn",
      soundEffectVi = "Meo meo meo!",
      funFactVi = "Mèo con thích cuộn tròn ngủ trưa và rửa mặt bằng chân trước rất sạch!"
    ),
    FlashcardAssetEntry(
      id = "farm_chicken",
      displayName = "Gà Trống",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1548550023-2bdb3c5beed7?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật gà trống mào đỏ lông óng ả",
      soundEffectVi = "Ò ó o o!",
      funFactVi = "Chú gà trống gáy vang đánh thức ông mặt trời và các bạn nhỏ dậy sớm!"
    ),
    FlashcardAssetEntry(
      id = "farm_duck",
      displayName = "Chú Vịt",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1555852095-64e7428df0fa?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú vịt bơi dưới nước",
      soundEffectVi = "Quạc quạc quạc!",
      funFactVi = "Chú vịt có bộ lông không thấm nước và đôi chân màng bơi lội dưới ao!"
    ),
    FlashcardAssetEntry(
      id = "farm_pig",
      displayName = "Bé Heo",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú heo con màu hồng sạch sẽ",
      soundEffectVi = "Ủn ỉn ủn ỉn!",
      funFactVi = "Bé heo hồng hào mập mạp, ăn ngoan và ngủ ngon giấc!"
    ),
    FlashcardAssetEntry(
      id = "farm_cow",
      displayName = "Bò Sữa",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1546445317-29f4545e9d53?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật cô bò sữa đốm đen trắng trên đồng cỏ",
      soundEffectVi = "Ùm bò bò!",
      funFactVi = "Cô bò sữa cho bé những ly sữa tươi thơm ngon bổ dưỡng mỗi ngày!"
    ),
    FlashcardAssetEntry(
      id = "farm_goat",
      displayName = "Chú Dê",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1524024973431-2ad916746881?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú dê núi có sừng nhỏ",
      soundEffectVi = "Be be be!",
      funFactVi = "Chú dê thích gặm cỏ non xanh mướt trên sườn đồi!"
    ),
    FlashcardAssetEntry(
      id = "farm_horse",
      displayName = "Chú Ngựa",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1553284965-83fd3e82fa5a?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một chú ngựa phi oai phong",
      soundEffectVi = "Hí hí hí!",
      funFactVi = "Chú ngựa chạy phi nhanh lóc cóc lóc cóc với chiếc bờm tuyệt đẹp!"
    ),
    FlashcardAssetEntry(
      id = "farm_sheep",
      displayName = "Bé Cừu",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1500595046743-cd271d694d30?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật bé cừu lông xoăn bồng bềnh",
      soundEffectVi = "Bê bê bê!",
      funFactVi = "Bé cừu có bộ lông dày xốp ấm áp như đám mây trắng!"
    ),
    FlashcardAssetEntry(
      id = "farm_rabbit",
      displayName = "Thỏ Trắng",
      category = "animals",
      subCategory = "farm",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú thỏ trắng tai dài mắt hồng",
      soundEffectVi = "Nhún nhảy thoăn thoắt!",
      funFactVi = "Bạn thỏ có đôi tai dài lắng nghe rất thính và thích ăn cà rốt giòn ngọt!"
    ),

    // === ĐỘNG VẬT HOANG DÃ (WILD ANIMALS) ===
    FlashcardAssetEntry(
      id = "wild_lion",
      displayName = "Sư Tử",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chúa sơn lâm sư tử có bờm oai vệ",
      soundEffectVi = "Gầm vang gừ gừ!",
      funFactVi = "Sư tử là vua của muôn loài với chiếc bờm vàng óng ả oai vệ!"
    ),
    FlashcardAssetEntry(
      id = "wild_elephant",
      displayName = "Chú Voi",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú voi có vòi dài và đôi tai to như quạt",
      soundEffectVi = "Rống to vung vòi!",
      funFactVi = "Chú voi có chiếc vòi dài khéo léo dùng để hút nước và hái lá cây!"
    ),
    FlashcardAssetEntry(
      id = "wild_tiger",
      displayName = "Chú Hổ",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1534188753412-3e26d0d618d6?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật hổ vằn oai phong",
      soundEffectVi = "Gầm vang rừng xanh!",
      funFactVi = "Chú hổ có bộ lông vàng sọc đen dũng mãnh và bước đi rất êm ái!"
    ),
    FlashcardAssetEntry(
      id = "wild_giraffe",
      displayName = "Hươu Cao Cổ",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1547721064-da6cfb341d50?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật hươu cao cổ đang vươn cổ ăn lá trên cây cao",
      soundEffectVi = "Vươn cổ cao tít!",
      funFactVi = "Hươu cao cổ là bạn động vật cao nhất thế giới, dễ dàng ăn lá trên ngọn cây!"
    ),
    FlashcardAssetEntry(
      id = "wild_zebra",
      displayName = "Ngựa Vằn",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1526095179574-86e545346ae6?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một con ngựa vằn sọc trắng đen rõ nét",
      soundEffectVi = "Chạy nhanh như gió!",
      funFactVi = "Ngựa vằn khoác chiếc áo sọc trắng đen độc đáo không bạn nào trùng bạn nào!"
    ),
    FlashcardAssetEntry(
      id = "wild_panda",
      displayName = "Gấu Trúc",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1564349683136-77e08dba1ef7?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú gấu trúc tròn xoe đang ăn cành trúc",
      soundEffectVi = "Nhai trúc rộp rộp!",
      funFactVi = "Gấu trúc tròn xoe có hai quầng mắt đen nhánh và cực kỳ thích ăn lá trúc!"
    ),
    FlashcardAssetEntry(
      id = "wild_monkey",
      displayName = "Khỉ Con",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1540573133985-87b6da6d54a9?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật chú khỉ con chuyền cành",
      soundEffectVi = "Khẹc khẹc vui nhộn!",
      funFactVi = "Khỉ con nhanh nhẹn, thích đu cành cây và ăn chuối chín vàng!"
    ),
    FlashcardAssetEntry(
      id = "wild_bear",
      displayName = "Gấu Nâu",
      category = "animals",
      subCategory = "wild",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1530595467537-0b5996c41f2d?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật gấu nâu trong rừng cây",
      soundEffectVi = "Gừ gừ ấm áp!",
      funFactVi = "Gấu nâu thích bắt cá suối và ăn mật ong ngọt ngào trong rừng!"
    ),

    // === TRÁI CÂY (FRUITS) ===
    FlashcardAssetEntry(
      id = "fruit_apple",
      displayName = "Quả Táo",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một quả táo đỏ giòn tươi",
      soundEffectVi = "Cắn rộp rộp ngọt lành!",
      funFactVi = "Quả táo đỏ giòn ngọt, giàu vitamin giúp bé có đôi má hồng hào khỏe mạnh!"
    ),
    FlashcardAssetEntry(
      id = "fruit_banana",
      displayName = "Quả Chuối",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật nải chuối chín vàng ươm",
      soundEffectVi = "Bóc vỏ thơm lừng!",
      funFactVi = "Quả chuối cong cong màu vàng, ăn thơm mềm và rất tốt cho bụng của bé!"
    ),
    FlashcardAssetEntry(
      id = "fruit_orange",
      displayName = "Quả Cam",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1611080626919-7cf5a9dbab5b?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật quả cam mọng nước",
      soundEffectVi = "Vắt nước ngọt lành!",
      funFactVi = "Quả cam tròn xoe chứa nhiều vitamin C giúp bé tăng cường sức đề kháng!"
    ),
    FlashcardAssetEntry(
      id = "fruit_watermelon",
      displayName = "Dưa Hấu",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật miếng dưa hấu đỏ ruột mọng nước",
      soundEffectVi = "Mát lạnh ngọt lịm!",
      funFactVi = "Dưa hấu có vỏ xanh ruột đỏ mọng nước giải nhiệt ngày hè cực đã!"
    ),
    FlashcardAssetEntry(
      id = "fruit_mango",
      displayName = "Quả Xoài",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1553279768-865429fa0078?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật quả xoài cát chín vàng óng",
      soundEffectVi = "Thơm nức mũi!",
      funFactVi = "Quả xoài chín vàng ươm, mềm ngọt và thơm nức gian phòng!"
    ),
    FlashcardAssetEntry(
      id = "fruit_strawberry",
      displayName = "Dâu Tây",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1464965911861-746a04b4bca6?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật quả dâu tây đỏ mọng có cuống xanh",
      soundEffectVi = "Chua ngọt thơm dịu!",
      funFactVi = "Quả dâu tây đỏ mọng xinh xắn có những hạt nhỏ li ti đáng yêu!"
    ),
    FlashcardAssetEntry(
      id = "fruit_grapes",
      displayName = "Chùm Nho",
      category = "fruits",
      assetType = "real_photo",
      photoUrl = "https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=600&auto=format&fit=crop&q=80",
      altText = "Ảnh thật một chùm nho mọng quả",
      soundEffectVi = "Từng quả mọng nước!",
      funFactVi = "Chùm nho có nhiều quả tròn xoe mọng nước quây quần bên nhau thật đẹp!"
    )
  ).associateBy { it.id }

  fun getAsset(id: String): FlashcardAssetEntry? = manifest[id]
}
