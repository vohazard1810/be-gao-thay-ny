package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ColoringTemplate
import com.example.ui.components.BeGaoMascot
import com.example.ui.components.BeGaoMood
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.KidTopAppBar
import com.example.ui.components.PastelBackgroundWithDots
import com.example.ui.components.ThoBongMascot
import com.example.ui.theme.*

data class ColoredPath(
  val points: List<Offset>,
  val color: Color,
  val strokeWidth: Float = 28f
)

/**
 * Màn chơi "Bé Tập Tô Màu" (Preschool Coloring Book):
 * - Dành riêng cho trẻ mầm non 2-4 tuổi với nét vẽ lớn, trực quan.
 * - Cho bé tự do chấm màu pastel, vuốt ngón tay tô điểm cho Bé Gạo, Thỏ Bông, Sóc Nâu.
 * - Tuân thủ tuyệt đối Character Bible: Nét vẽ 2D mềm mại, ấm áp như sách tranh.
 */
@Composable
fun ColoringScreen(
  totalStars: Int,
  isSpeaking: Boolean,
  onBrushStroke: () -> Unit,
  onCompleteColoring: () -> Unit,
  onReplaySpeech: () -> Unit,
  onHomeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val templates = remember {
    listOf(
      ColoringTemplate("begao", "Bé Gạo", "Bé gái má phúng phính tóc ngắn cài hoa", "🌸", Color(0xFFFFB2C9)),
      ColoringTemplate("thobong", "Thỏ Bông", "Bạn thỏ bông trắng áo len tím", "🐰", Color(0xFFCE93D8)),
      ColoringTemplate("socnau", "Sóc Nâu", "Bạn sóc nâu thích ăn hạt dẻ", "🐿️", Color(0xFFFFCC80)),
      ColoringTemplate("cundom", "Cún Đốm", "Bạn cún đốm mắt trái mặc yếm xanh", "🐶", Color(0xFF90CAF9)),
      ColoringTemplate("meomay", "Mèo Mây", "Bạn mèo mây xám thắt nơ xinh", "🐱", Color(0xFFF48FB1)),
      ColoringTemplate("vitmo", "Vịt Mơ", "Bạn vịt lông vàng bơi lội dưới ao", "🐥", Color(0xFFFFF59D))
    )
  }

  var selectedTemplateIndex by remember { mutableIntStateOf(0) }
  val currentTemplate = templates[selectedTemplateIndex]

  val paletteColors = remember {
    listOf(
      Color(0xFFFF8DA1), // Pastel Pink
      Color(0xFF81C784), // Sage Green
      Color(0xFFFFD54F), // Warm Yellow
      Color(0xFFBA68C8), // Soft Lavender
      Color(0xFF4FC3F7), // Baby Sky Blue
      Color(0xFFA1887F)  // Warm Earth Brown
    )
  }

  var selectedColor by remember { mutableStateOf(paletteColors[0]) }
  val paths = remember { mutableStateListOf<ColoredPath>() }
  var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
  var showCelebration by remember { mutableStateOf(false) }

  PastelBackgroundWithDots(
    modifier = modifier
      .fillMaxSize()
      .testTag("coloring_screen")
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // 1. Top App Bar
        KidTopAppBar(
          title = "Bé Tập Tô Màu",
          onHomeClick = onHomeClick,
          onReplaySpeech = onReplaySpeech,
          isSpeaking = isSpeaking
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 2. Character Template Selector Tabs
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 14.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          templates.forEachIndexed { index, t ->
            val isSelected = index == selectedTemplateIndex
            Surface(
              shape = RoundedCornerShape(18.dp),
              color = if (isSelected) Color(0xFFFFE082) else Color.White,
              border = androidx.compose.foundation.BorderStroke(
                if (isSelected) 2.dp else 1.dp,
                if (isSelected) Color(0xFFFB8C00) else Color(0xFFE0E0E0)
              ),
              shadowElevation = if (isSelected) 3.dp else 1.dp,
              modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .clickable {
                  if (selectedTemplateIndex != index) {
                    selectedTemplateIndex = index
                    paths.clear()
                    currentPoints = emptyList()
                    showCelebration = false
                  }
                }
            ) {
              Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Text(text = t.iconEmoji, fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = t.titleVi,
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color(0xFFB45309) else TextDark,
                    fontSize = 12.sp
                  ),
                  maxLines = 1
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 3. Drawing Canvas with Outline
        Surface(
          shape = RoundedCornerShape(24.dp),
          color = Color(0xFFFFFDF9), // Warm picture-book paper white
          border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFE0B2)),
          shadowElevation = 4.dp,
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .clip(RoundedCornerShape(24.dp))
        ) {
          Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
              modifier = Modifier
                .fillMaxSize()
                .pointerInput(selectedColor) {
                  detectDragGestures(
                    onDragStart = { offset ->
                      currentPoints = listOf(offset)
                      onBrushStroke()
                    },
                    onDrag = { change, _ ->
                      change.consume()
                      currentPoints = currentPoints + change.position
                    },
                    onDragEnd = {
                      if (currentPoints.isNotEmpty()) {
                        paths.add(ColoredPath(currentPoints, selectedColor))
                        currentPoints = emptyList()
                      }
                    },
                    onDragCancel = {
                      currentPoints = emptyList()
                    }
                  )
                }
            ) {
              val w = size.width
              val h = size.height

              // 3A. Draw user's colored brush strokes
              for (path in paths) {
                if (path.points.size > 1) {
                  val composePath = Path().apply {
                    moveTo(path.points.first().x, path.points.first().y)
                    for (i in 1 until path.points.size) {
                      lineTo(path.points[i].x, path.points[i].y)
                    }
                  }
                  drawPath(
                    path = composePath,
                    color = path.color.copy(alpha = 0.78f),
                    style = Stroke(
                      width = path.strokeWidth,
                      cap = StrokeCap.Round,
                      join = StrokeJoin.Round
                    )
                  )
                } else if (path.points.size == 1) {
                  drawCircle(
                    color = path.color.copy(alpha = 0.78f),
                    radius = path.strokeWidth / 2,
                    center = path.points.first()
                  )
                }
              }

              // In-progress stroke
              if (currentPoints.size > 1) {
                val composePath = Path().apply {
                  moveTo(currentPoints.first().x, currentPoints.first().y)
                  for (i in 1 until currentPoints.size) {
                    lineTo(currentPoints[i].x, currentPoints[i].y)
                  }
                }
                drawPath(
                  path = composePath,
                  color = selectedColor.copy(alpha = 0.78f),
                  style = Stroke(
                    width = 28f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                  )
                )
              }

              // 3B. Hand-painted outline of selected character on top
              val strokeColor = Color(0xFF5D4037) // Warm pencil brown outline
              val strokeStyle = Stroke(width = 5.5f, cap = StrokeCap.Round, join = StrokeJoin.Round)

              when (currentTemplate.id) {
                "begao" -> {
                  // Bé Gạo toddler outline:
                  // Head circle
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.22f,
                    center = Offset(w * 0.5f, h * 0.38f),
                    style = strokeStyle
                  )
                  // Dark bob bangs arc
                  drawArc(
                    color = strokeColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(w * 0.28f, h * 0.16f),
                    size = androidx.compose.ui.geometry.Size(w * 0.44f, h * 0.26f),
                    style = strokeStyle
                  )
                  // Flower clip
                  drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = w * 0.045f,
                    center = Offset(w * 0.68f, h * 0.25f)
                  )
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.045f,
                    center = Offset(w * 0.68f, h * 0.25f),
                    style = Stroke(width = 4f)
                  )
                  // Cute eyes
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.022f,
                    center = Offset(w * 0.44f, h * 0.38f)
                  )
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.022f,
                    center = Offset(w * 0.56f, h * 0.38f)
                  )
                  // Rosy toddler cheeks
                  drawCircle(
                    color = Color(0xFFFFCDD2).copy(alpha = 0.7f),
                    radius = w * 0.038f,
                    center = Offset(w * 0.38f, h * 0.42f)
                  )
                  drawCircle(
                    color = Color(0xFFFFCDD2).copy(alpha = 0.7f),
                    radius = w * 0.038f,
                    center = Offset(w * 0.62f, h * 0.42f)
                  )
                  // Button smile
                  drawArc(
                    color = strokeColor,
                    startAngle = 20f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(w * 0.46f, h * 0.42f),
                    size = androidx.compose.ui.geometry.Size(w * 0.08f, h * 0.05f),
                    style = strokeStyle
                  )
                  // Sage-green Pinafore dress outline
                  val dressPath = Path().apply {
                    moveTo(w * 0.36f, h * 0.58f)
                    lineTo(w * 0.64f, h * 0.58f)
                    lineTo(w * 0.72f, h * 0.82f)
                    lineTo(w * 0.28f, h * 0.82f)
                    close()
                  }
                  drawPath(dressPath, color = strokeColor, style = strokeStyle)
                  // Two yellow buttons on pinafore
                  drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = w * 0.018f,
                    center = Offset(w * 0.42f, h * 0.63f)
                  )
                  drawCircle(
                    color = Color(0xFFFFD54F),
                    radius = w * 0.018f,
                    center = Offset(w * 0.58f, h * 0.63f)
                  )
                  // Star crossbody bag
                  drawCircle(
                    color = Color(0xFFFFEB3B),
                    radius = w * 0.055f,
                    center = Offset(w * 0.66f, h * 0.75f)
                  )
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.055f,
                    center = Offset(w * 0.66f, h * 0.75f),
                    style = strokeStyle
                  )
                }

                "thobong" -> {
                  // Thỏ Bông white bunny outline:
                  // Long Floppy Ears
                  drawOval(
                    color = strokeColor,
                    topLeft = Offset(w * 0.32f, h * 0.10f),
                    size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.28f),
                    style = strokeStyle
                  )
                  drawOval(
                    color = strokeColor,
                    topLeft = Offset(w * 0.56f, h * 0.10f),
                    size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.28f),
                    style = strokeStyle
                  )
                  // Head
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.22f,
                    center = Offset(w * 0.5f, h * 0.44f),
                    style = strokeStyle
                  )
                  // Eyes
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.43f, h * 0.42f))
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.57f, h * 0.42f))
                  // Pink nose
                  drawCircle(color = Color(0xFFFF8DA1), radius = w * 0.016f, center = Offset(w * 0.5f, h * 0.47f))
                  // Body with Lilac sweater vest outline
                  val vestPath = Path().apply {
                    moveTo(w * 0.35f, h * 0.64f)
                    lineTo(w * 0.65f, h * 0.64f)
                    lineTo(w * 0.70f, h * 0.85f)
                    lineTo(w * 0.30f, h * 0.85f)
                    close()
                  }
                  drawPath(vestPath, color = strokeColor, style = strokeStyle)
                }

                "socnau" -> {
                  // Sóc Nâu cute squirrel:
                  // Head
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.20f,
                    center = Offset(w * 0.45f, h * 0.42f),
                    style = strokeStyle
                  )
                  // Big fluffy tail arc
                  val tailPath = Path().apply {
                    moveTo(w * 0.62f, h * 0.78f)
                    cubicTo(w * 0.90f, h * 0.65f, w * 0.92f, h * 0.30f, w * 0.70f, h * 0.25f)
                    cubicTo(w * 0.58f, h * 0.22f, w * 0.65f, h * 0.45f, w * 0.60f, h * 0.60f)
                  }
                  drawPath(tailPath, color = strokeColor, style = strokeStyle)
                  // Acorn in hands
                  drawCircle(color = Color(0xFFD7CCC8), radius = w * 0.045f, center = Offset(w * 0.45f, h * 0.68f))
                  drawCircle(color = strokeColor, radius = w * 0.045f, center = Offset(w * 0.45f, h * 0.68f), style = strokeStyle)
                  // Eyes
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.40f, h * 0.40f))
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.52f, h * 0.40f))
                }

                "cundom" -> {
                  // Cún Đốm: Puppy with eye patch & floppy ears
                  // Floppy ears
                  drawOval(
                    color = Color(0xFFD7CCC8),
                    topLeft = Offset(w * 0.18f, h * 0.30f),
                    size = androidx.compose.ui.geometry.Size(w * 0.15f, h * 0.26f)
                  )
                  drawOval(
                    color = Color(0xFFD7CCC8),
                    topLeft = Offset(w * 0.67f, h * 0.30f),
                    size = androidx.compose.ui.geometry.Size(w * 0.15f, h * 0.26f)
                  )
                  // Head
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.22f,
                    center = Offset(w * 0.50f, h * 0.40f),
                    style = strokeStyle
                  )
                  // Distinct brown patch over left eye
                  drawCircle(
                    color = Color(0xFFD7CCC8),
                    radius = w * 0.065f,
                    center = Offset(w * 0.41f, h * 0.38f)
                  )
                  // Eyes
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.41f, h * 0.38f))
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.59f, h * 0.38f))
                  // Cute black button nose
                  drawCircle(color = strokeColor, radius = w * 0.020f, center = Offset(w * 0.50f, h * 0.46f))
                  // Blue overalls body
                  val bodyPath = Path().apply {
                    moveTo(w * 0.34f, h * 0.62f)
                    lineTo(w * 0.66f, h * 0.62f)
                    lineTo(w * 0.70f, h * 0.86f)
                    lineTo(w * 0.30f, h * 0.86f)
                    close()
                  }
                  drawPath(bodyPath, color = strokeColor, style = strokeStyle)
                }

                "meomay" -> {
                  // Mèo Mây: Kitten with cute ears and bow
                  // Triangle ears
                  val leftEar = Path().apply {
                    moveTo(w * 0.32f, h * 0.32f)
                    lineTo(w * 0.28f, h * 0.16f)
                    lineTo(w * 0.44f, h * 0.24f)
                    close()
                  }
                  val rightEar = Path().apply {
                    moveTo(w * 0.68f, h * 0.32f)
                    lineTo(w * 0.72f, h * 0.16f)
                    lineTo(w * 0.56f, h * 0.24f)
                    close()
                  }
                  drawPath(leftEar, color = strokeColor, style = strokeStyle)
                  drawPath(rightEar, color = strokeColor, style = strokeStyle)
                  // Head
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.21f,
                    center = Offset(w * 0.50f, h * 0.42f),
                    style = strokeStyle
                  )
                  // Pale green bow on right ear
                  drawCircle(color = Color(0xFFA5D6A7), radius = w * 0.038f, center = Offset(w * 0.68f, h * 0.24f))
                  // Eyes
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.42f, h * 0.40f))
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.58f, h * 0.40f))
                  // Tiny nose
                  drawCircle(color = Color(0xFFFF8DA1), radius = w * 0.015f, center = Offset(w * 0.50f, h * 0.46f))
                  // Whiskers
                  drawLine(strokeColor, Offset(w * 0.28f, h * 0.45f), Offset(w * 0.38f, h * 0.45f), strokeWidth = 3f)
                  drawLine(strokeColor, Offset(w * 0.62f, h * 0.45f), Offset(w * 0.72f, h * 0.45f), strokeWidth = 3f)
                }

                else -> {
                  // Vịt Mơ: Yellow duckling floating on pond
                  // Head
                  drawCircle(
                    color = strokeColor,
                    radius = w * 0.18f,
                    center = Offset(w * 0.46f, h * 0.36f),
                    style = strokeStyle
                  )
                  // Cute orange beak
                  drawOval(
                    color = Color(0xFFFFAB40),
                    topLeft = Offset(w * 0.24f, h * 0.35f),
                    size = androidx.compose.ui.geometry.Size(w * 0.14f, h * 0.08f)
                  )
                  // Eye
                  drawCircle(color = strokeColor, radius = w * 0.022f, center = Offset(w * 0.43f, h * 0.34f))
                  // Body
                  drawOval(
                    color = strokeColor,
                    topLeft = Offset(w * 0.30f, h * 0.52f),
                    size = androidx.compose.ui.geometry.Size(w * 0.42f, h * 0.28f),
                    style = strokeStyle
                  )
                  // Pond water ripples
                  val wavePath = Path().apply {
                    moveTo(w * 0.18f, h * 0.84f)
                    cubicTo(w * 0.35f, h * 0.88f, w * 0.50f, h * 0.80f, w * 0.82f, h * 0.84f)
                  }
                  drawPath(wavePath, color = Color(0xFF4FC3F7), style = strokeStyle)
                }
              }
            }

            // Cheerful hint for toddlers
            if (paths.isEmpty() && currentPoints.isEmpty()) {
              Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier
                  .align(Alignment.BottomCenter)
                  .padding(bottom = 12.dp)
              ) {
                Text(
                  text = "👆 Bé dùng ngón tay chấm hoặc vuốt để tô màu nhé!",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE65100),
                    fontSize = 11.5.sp
                  ),
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 4. Color Palette Picker Bar
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          paletteColors.forEach { color ->
            val isSelected = color == selectedColor
            val scale by animateFloatAsState(
              targetValue = if (isSelected) 1.25f else 1f,
              animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
              label = "color_scale"
            )

            Box(
              modifier = Modifier
                .size(46.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(color)
                .clickable {
                  selectedColor = color
                  onBrushStroke()
                }
                .testTag("color_picker_${color.value}")
            ) {
              if (isSelected) {
                Surface(
                  shape = CircleShape,
                  color = Color.White,
                  modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center)
                ) {}
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 5. Bottom Action Controls
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Clear / Reset Canvas
          Button(
            onClick = {
              paths.clear()
              currentPoints = emptyList()
              showCelebration = false
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFECB3)),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = "🔄 Tô Lại",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB45309),
                fontSize = 13.5.sp
              )
            )
          }

          // Complete & Celebrate Artwork
          Button(
            onClick = {
              showCelebration = true
              onCompleteColoring()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784)),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
            modifier = Modifier.weight(1.3f)
          ) {
            Text(
              text = "⭐ Khoe Tranh Đẹp!",
              style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 13.5.sp
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
      }

      // 6. Confetti & Buddy Celebration Overlay
      if (showCelebration) {
        ConfettiOverlay()

        Surface(
          shape = RoundedCornerShape(22.dp),
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier
            .align(Alignment.Center)
            .padding(24.dp)
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              verticalAlignment = Alignment.Bottom,
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              BeGaoMascot(
                size = 76.dp,
                mood = BeGaoMood.CHEERING,
                showNameBadge = false
              )
              ThoBongMascot(
                size = 62.dp,
                isCheering = true,
                showNameBadge = false
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Bé Vẽ Đẹp Tuyệt Vời! 🌟",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFE65100),
                fontSize = 16.sp
              ),
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Thầy Ny và Bé Gạo tặng con 1 ngôi sao sáng nhé!",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = TextDark,
                fontSize = 12.5.sp
              ),
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = { showCelebration = false },
              colors = ButtonDefaults.buttonColors(containerColor = PastelMint),
              shape = RoundedCornerShape(16.dp)
            ) {
              Text(text = "Tiếp tục chơi 🌸", color = Color.White, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}
