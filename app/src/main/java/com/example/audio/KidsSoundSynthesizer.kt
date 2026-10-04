package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * KidsSoundSynthesizer:
 * Bộ tạo âm thanh trò chơi mầm non thuần túy bằng toán học (PCM Audio Synthesis).
 * Không phụ thuộc file mạng, không phụ thuộc ToneGenerator viễn thông khô cứng.
 * Âm sắc trong trẻo, êm dịu, chuẩn nhạc điệu ngũ cung và bong bóng dành cho trẻ 2-4 tuổi.
 */
class KidsSoundSynthesizer {

  private val sampleRate = 44100
  private val scope = CoroutineScope(Dispatchers.Default)

  /**
   * Phát âm thanh chuông ngân thành công (Melodic Chime):
   * Hợp âm Đô - Mi - Son - Đố (C5 -> E5 -> G5 -> C6) với âm sắc chuông gió/xylophone ấm áp.
   */
  fun playSuccessChime() {
    scope.launch {
      try {
        // C5 (523Hz), E5 (659Hz), G5 (784Hz), C6 (1046Hz)
        val notes = listOf(523.25, 659.25, 783.99, 1046.50)
        val noteDuration = 0.11 // 110ms per note
        val totalDuration = noteDuration * notes.size + 0.35 // tail decay
        val totalSamples = (sampleRate * totalDuration).toInt()
        val buffer = ShortArray(totalSamples)

        notes.forEachIndexed { index, freq ->
          val startSample = (index * noteDuration * sampleRate).toInt()
          val noteLength = ((noteDuration + 0.30) * sampleRate).toInt()

          for (i in 0 until noteLength) {
            val targetIndex = startSample + i
            if (targetIndex >= totalSamples) break
            val t = i.toDouble() / sampleRate
            // Fundamental + 2nd overtone for xylophone chime warmth
            val envelope = exp(-8.5 * t)
            val sampleVal = (sin(2.0 * PI * freq * t) * 0.75 +
                sin(4.0 * PI * freq * t) * 0.25) * envelope
            val shortVal = (sampleVal * 18000).toInt().coerceIn(-32768, 32767).toShort()
            buffer[targetIndex] = (buffer[targetIndex] + shortVal).coerceIn(-32768, 32767).toShort()
          }
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in success chime: ${e.message}")
      }
    }
  }

  /**
   * Phát âm thanh bong bóng pop (Cute Bubble Pop):
   * Quét tần số nhanh từ 420Hz lên 960Hz tạo tiếng "Bụp" ngọt ngào khi bé chạm thẻ.
   */
  fun playPopBubble() {
    scope.launch {
      try {
        val duration = 0.08 // 80ms
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
          val t = i.toDouble() / sampleRate
          val progress = i.toDouble() / totalSamples
          // Frequency sweeps upward rapidly
          val freq = 420.0 + progress * 580.0
          val envelope = sin(PI * progress) * exp(-12.0 * t)
          val sampleVal = sin(2.0 * PI * freq * t) * envelope
          buffer[i] = (sampleVal * 22000).toInt().coerceIn(-32768, 32767).toShort()
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in pop sound: ${e.message}")
      }
    }
  }

  /**
   * Phát âm thanh khích lệ bé (Encouraging Gentle Boing):
   * 2 nốt ấm áp (F4 -> A4) vỗ về, không gây sợ hãi khi bé bấm nhầm.
   */
  fun playEncourageTone() {
    scope.launch {
      try {
        val notes = listOf(349.23, 440.00) // F4 -> A4
        val noteDuration = 0.14
        val totalDuration = noteDuration * 2 + 0.25
        val totalSamples = (sampleRate * totalDuration).toInt()
        val buffer = ShortArray(totalSamples)

        notes.forEachIndexed { index, freq ->
          val startSample = (index * noteDuration * sampleRate).toInt()
          val noteLength = ((noteDuration + 0.20) * sampleRate).toInt()

          for (i in 0 until noteLength) {
            val targetIndex = startSample + i
            if (targetIndex >= totalSamples) break
            val t = i.toDouble() / sampleRate
            val envelope = exp(-6.0 * t)
            val sampleVal = sin(2.0 * PI * freq * t) * envelope
            val shortVal = (sampleVal * 16000).toInt().coerceIn(-32768, 32767).toShort()
            buffer[targetIndex] = (buffer[targetIndex] + shortVal).coerceIn(-32768, 32767).toShort()
          }
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in encourage tone: ${e.message}")
      }
    }
  }

  /**
   * Phát âm thanh lật thẻ bài (Card Flip / Whoosh):
   * Âm thanh sột soạt lật mặt thẻ mềm mại.
   */
  fun playFlipSound() {
    scope.launch {
      try {
        val duration = 0.09
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)

        for (i in 0 until totalSamples) {
          val progress = i.toDouble() / totalSamples
          val freq = 280.0 + (1.0 - progress) * 220.0
          val envelope = sin(PI * progress)
          val sampleVal = sin(2.0 * PI * freq * (i.toDouble() / sampleRate)) * envelope
          buffer[i] = (sampleVal * 14000).toInt().coerceIn(-32768, 32767).toShort()
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in flip sound: ${e.message}")
      }
    }
  }

  /**
   * Phát âm thanh ăn mừng chiến thắng (Mini Fanfare / Triumphant Joy):
   * Chuỗi 5 nốt vui nhộn khi bé hoàn thành màn chơi hoặc tìm đủ cặp thẻ.
   */
  fun playCelebrationFanfare() {
    scope.launch {
      try {
        // G4, C5, E5, G5, C6
        val notes = listOf(392.00, 523.25, 659.25, 783.99, 1046.50)
        val noteDuration = 0.10
        val totalDuration = noteDuration * notes.size + 0.40
        val totalSamples = (sampleRate * totalDuration).toInt()
        val buffer = ShortArray(totalSamples)

        notes.forEachIndexed { index, freq ->
          val startSample = (index * noteDuration * sampleRate).toInt()
          val isLast = index == notes.lastIndex
          val durationSec = if (isLast) 0.38 else 0.15
          val noteLength = (durationSec * sampleRate).toInt()

          for (i in 0 until noteLength) {
            val targetIndex = startSample + i
            if (targetIndex >= totalSamples) break
            val t = i.toDouble() / sampleRate
            val envelope = exp(-(if (isLast) 4.5 else 9.0) * t)
            val sampleVal = (sin(2.0 * PI * freq * t) * 0.70 +
                sin(4.0 * PI * freq * t) * 0.30) * envelope
            val shortVal = (sampleVal * 20000).toInt().coerceIn(-32768, 32767).toShort()
            buffer[targetIndex] = (buffer[targetIndex] + shortVal).coerceIn(-32768, 32767).toShort()
          }
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in fanfare: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh nước mát róc rách (Water Splash / Stream):
   * Dành cho hoạt động rửa tay sạch, vòi nước mát róc rách trong truyện.
   */
  fun playWaterSplash() {
    scope.launch {
      try {
        val duration = 0.32
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)
        var randomSeed = 12345
        for (i in 0 until totalSamples) {
          val t = i.toDouble() / sampleRate
          val envelope = sin(PI * (t / duration))
          randomSeed = (randomSeed * 1103515245 + 12345) and 0x7fffffff
          val noise = ((randomSeed % 2000) - 1000) / 1000.0
          val wave = sin(2.0 * PI * (520.0 + 160.0 * sin(35.0 * t)) * t) * 0.45 + noise * 0.55
          val sampleVal = wave * envelope
          buffer[i] = (sampleVal * 12000).toInt().coerceIn(-32768, 32767).toShort()
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in water splash: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh bọt xà phòng xốp mềm (Effervescent Soap Foam):
   * 4 tiếng nổ tí tách siêu nhỏ, vui tai khi bé xoa xà phòng.
   */
  fun playBubbleFoam() {
    scope.launch {
      try {
        val freqs = listOf(680.0, 920.0, 1150.0, 840.0)
        val noteDuration = 0.05
        val totalDuration = 0.24
        val totalSamples = (sampleRate * totalDuration).toInt()
        val buffer = ShortArray(totalSamples)

        freqs.forEachIndexed { idx, freq ->
          val start = (idx * noteDuration * sampleRate).toInt()
          val popLength = (0.07 * sampleRate).toInt()
          for (i in 0 until popLength) {
            val target = start + i
            if (target >= totalSamples) break
            val t = i.toDouble() / sampleRate
            val env = exp(-40.0 * t)
            val currentFreq = freq + (t / 0.07) * 300.0
            val sampleVal = sin(2.0 * PI * currentFreq * t) * env
            val shortVal = (sampleVal * 14000).toInt().coerceIn(-32768, 32767).toShort()
            buffer[target] = (buffer[target] + shortVal).coerceIn(-32768, 32767).toShort()
          }
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in bubble foam: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh còi xe đồ chơi dễ thương (Cute Toy Car Horn "Pim pim!"):
   */
  fun playCarHorn() {
    scope.launch {
      try {
        val duration = 0.24
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)
        val beepDuration = 0.09
        val gap = 0.04
        val freq = 659.25
        val freq2 = 783.99

        val len1 = (beepDuration * sampleRate).toInt()
        for (i in 0 until len1) {
          val t = i.toDouble() / sampleRate
          val env = if (t < 0.01) t / 0.01 else if (t > 0.07) (beepDuration - t) / 0.02 else 1.0
          val sampleVal = (sin(2.0 * PI * freq * t) * 0.7 + sin(2.0 * PI * freq2 * t) * 0.3) * env
          buffer[i] = (sampleVal * 14000).toInt().coerceIn(-32768, 32767).toShort()
        }

        val start2 = ((beepDuration + gap) * sampleRate).toInt()
        for (i in 0 until len1) {
          val target = start2 + i
          if (target >= totalSamples) break
          val t = i.toDouble() / sampleRate
          val env = if (t < 0.01) t / 0.01 else if (t > 0.07) (beepDuration - t) / 0.02 else 1.0
          val sampleVal = (sin(2.0 * PI * freq * t) * 0.7 + sin(2.0 * PI * freq2 * t) * 0.3) * env
          buffer[target] = (sampleVal * 14000).toInt().coerceIn(-32768, 32767).toShort()
        }

        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in car horn: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh chim hót líu lo / hoa lá (Gentle Bird Chirp):
   */
  fun playNatureChirp() {
    scope.launch {
      try {
        val duration = 0.18
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
          val t = i.toDouble() / sampleRate
          val envelope = sin(PI * (t / duration))
          val f = 2200.0 + 800.0 * sin(PI * (t / duration))
          val sampleVal = sin(2.0 * PI * f * t) * envelope
          buffer[i] = (sampleVal * 10000).toInt().coerceIn(-32768, 32767).toShort()
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in nature chirp: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh lấp lánh kỳ diệu (Magic Twinkle / Fairy Glissando):
   */
  fun playMagicTwinkle() {
    scope.launch {
      try {
        val notes = listOf(1318.51, 1567.98, 1975.53, 2637.02)
        val noteDur = 0.07
        val totalDur = noteDur * notes.size + 0.25
        val totalSamples = (sampleRate * totalDur).toInt()
        val buffer = ShortArray(totalSamples)

        notes.forEachIndexed { idx, freq ->
          val start = (idx * noteDur * sampleRate).toInt()
          val noteLen = (0.22 * sampleRate).toInt()
          for (i in 0 until noteLen) {
            val target = start + i
            if (target >= totalSamples) break
            val t = i.toDouble() / sampleRate
            val env = exp(-12.0 * t)
            val sampleVal = sin(2.0 * PI * freq * t) * env
            val shortVal = (sampleVal * 12000).toInt().coerceIn(-32768, 32767).toShort()
            buffer[target] = (buffer[target] + shortVal).coerceIn(-32768, 32767).toShort()
          }
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in magic twinkle: ${e.message}")
      }
    }
  }

  /**
   * Âm thanh quẹt cọ màu sắc (Color Brush Swish):
   */
  fun playColorBrushSound() {
    scope.launch {
      try {
        val duration = 0.12
        val totalSamples = (sampleRate * duration).toInt()
        val buffer = ShortArray(totalSamples)
        for (i in 0 until totalSamples) {
          val t = i.toDouble() / sampleRate
          val env = sin(PI * (t / duration))
          val freq = 360.0 + 180.0 * (t / duration)
          val sampleVal = sin(2.0 * PI * freq * t) * env
          buffer[i] = (sampleVal * 11000).toInt().coerceIn(-32768, 32767).toShort()
        }
        playPcm(buffer)
      } catch (e: Exception) {
        Log.e("SoundSynth", "Error in brush sound: ${e.message}")
      }
    }
  }

  // ==================== BGM (BACKGROUND MUSIC LULLABY) ====================
  private var bgmTrack: AudioTrack? = null
  private var isBgmRunning = false

  @Synchronized
  fun startBgm() {
    if (isBgmRunning) return
    isBgmRunning = true
    scope.launch(Dispatchers.IO) {
      try {
        // Pentatonic C major lullaby (4.0s peaceful loop)
        val melody = listOf(261.63, 293.66, 329.63, 392.00, 440.00, 392.00, 329.63, 261.63)
        val noteDur = 0.50
        val loopSamples = (sampleRate * (melody.size * noteDur)).toInt()
        val bgmBuffer = ShortArray(loopSamples)

        melody.forEachIndexed { idx, freq ->
          val start = (idx * noteDur * sampleRate).toInt()
          val noteLen = (noteDur * sampleRate).toInt()
          for (i in 0 until noteLen) {
            val target = start + i
            if (target >= loopSamples) break
            val t = i.toDouble() / sampleRate
            val env = exp(-3.2 * t)
            val sampleVal = (sin(2.0 * PI * freq * t) * 0.8 + sin(4.0 * PI * freq * t) * 0.2) * env
            bgmBuffer[target] = (sampleVal * 4500).toInt().coerceIn(-32768, 32767).toShort()
          }
        }

        val attributes = AudioAttributes.Builder()
          .setUsage(AudioAttributes.USAGE_GAME)
          .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
          .build()

        val format = AudioFormat.Builder()
          .setSampleRate(sampleRate)
          .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
          .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
          .build()

        val track = AudioTrack.Builder()
          .setAudioAttributes(attributes)
          .setAudioFormat(format)
          .setBufferSizeInBytes(bgmBuffer.size * 2)
          .setTransferMode(AudioTrack.MODE_STATIC)
          .build()

        track.write(bgmBuffer, 0, bgmBuffer.size)
        track.setLoopPoints(0, bgmBuffer.size, -1)
        track.setVolume(0.10f)
        track.play()
        bgmTrack = track
      } catch (e: Exception) {
        Log.e("SoundSynth", "BGM start error: ${e.message}")
        isBgmRunning = false
      }
    }
  }

  @Synchronized
  fun stopBgm() {
    isBgmRunning = false
    try {
      bgmTrack?.stop()
      bgmTrack?.release()
      bgmTrack = null
    } catch (_: Exception) {}
  }

  fun setBgmDucking(isDucking: Boolean) {
    try {
      bgmTrack?.setVolume(if (isDucking) 0.025f else 0.10f)
    } catch (_: Exception) {}
  }

  fun shutdown() {
    stopBgm()
  }

  private fun playPcm(buffer: ShortArray) {
    var audioTrack: AudioTrack? = null
    try {
      val minBuf = AudioTrack.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT
      )
      val bufferSize = maxOf(buffer.size * 2, minBuf)

      val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

      val format = AudioFormat.Builder()
        .setSampleRate(sampleRate)
        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
        .build()

      audioTrack = AudioTrack.Builder()
        .setAudioAttributes(attributes)
        .setAudioFormat(format)
        .setBufferSizeInBytes(bufferSize)
        .setTransferMode(AudioTrack.MODE_STREAM)
        .build()

      audioTrack.play()
      audioTrack.write(buffer, 0, buffer.size)

      // Short delay to allow playback before releasing
      val playTimeMs = ((buffer.size.toDouble() / sampleRate) * 1000).toLong() + 50
      Thread.sleep(playTimeMs)
    } catch (e: Exception) {
      Log.e("SoundSynth", "AudioTrack play exception: ${e.message}")
    } finally {
      try {
        audioTrack?.stop()
        audioTrack?.release()
      } catch (_: Exception) {}
    }
  }
}
