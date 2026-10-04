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
