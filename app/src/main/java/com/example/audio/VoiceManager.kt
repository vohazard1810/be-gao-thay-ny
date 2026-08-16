package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class VoiceManager(private val context: Context) {

  private var tts: TextToSpeech? = null
  private var isTtsInitialized = false
  private val mainHandler = Handler(Looper.getMainLooper())

  private val _isSpeaking = MutableStateFlow(false)
  val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

  private val _spokenText = MutableStateFlow("")
  val spokenText: StateFlow<String> = _spokenText.asStateFlow()

  private var onSpeechDoneCallback: (() -> Unit)? = null
  private var toneGen: ToneGenerator? = null

  init {
    initTts()
    initToneGenerator()
  }

  private fun initToneGenerator() {
    try {
      toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
    } catch (e: Exception) {
      Log.w("VoiceManager", "ToneGenerator init exception: ${e.message}")
    }
  }

  private fun initTts() {
    tts = TextToSpeech(context.applicationContext) { status ->
      if (status == TextToSpeech.SUCCESS) {
        val viLocale = Locale("vi", "VN")
        val result = tts?.setLanguage(viLocale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
          Log.w("VoiceManager", "Vietnamese TTS not fully supported, falling back to default locale")
          tts?.language = Locale("vi")
        }
        tts?.setPitch(0.95f)
        tts?.setSpeechRate(0.88f)
        isTtsInitialized = true

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
          override fun onStart(utteranceId: String?) {
            mainHandler.post {
              _isSpeaking.value = true
            }
          }

          override fun onDone(utteranceId: String?) {
            mainHandler.post {
              _isSpeaking.value = false
              onSpeechDoneCallback?.invoke()
              onSpeechDoneCallback = null
            }
          }

          override fun onError(utteranceId: String?) {
            mainHandler.post {
              _isSpeaking.value = false
              onSpeechDoneCallback?.invoke()
              onSpeechDoneCallback = null
            }
          }
        })
      } else {
        Log.e("VoiceManager", "Failed to initialize TTS engine")
      }
    }
  }

  fun speak(
    text: String,
    pitch: Float = 0.95f,
    rate: Float = 0.88f,
    onDone: (() -> Unit)? = null
  ) {
    _spokenText.value = text
    onSpeechDoneCallback = onDone

    if (tts != null && isTtsInitialized) {
      try {
        tts?.setPitch(pitch.coerceIn(0.8f, 1.25f))
        tts?.setSpeechRate(rate.coerceIn(0.75f, 1.05f))
        val params = Bundle()
        val utteranceId = "utterance_${System.currentTimeMillis()}"
        val res = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
        if (res == TextToSpeech.ERROR) {
          Log.w("VoiceManager", "TTS returned ERROR, falling back to timer")
          _isSpeaking.value = true
          mainHandler.postDelayed({
            _isSpeaking.value = false
            onSpeechDoneCallback?.invoke()
            onSpeechDoneCallback = null
          }, 1200)
        }
      } catch (e: Exception) {
        Log.e("VoiceManager", "TTS speak exception: ${e.message}")
        _isSpeaking.value = false
        onSpeechDoneCallback?.invoke()
        onSpeechDoneCallback = null
      }
    } else {
      _isSpeaking.value = true
      mainHandler.postDelayed({
        _isSpeaking.value = false
        onDone?.invoke()
      }, 1200)
    }
  }

  fun stop() {
    tts?.stop()
    _isSpeaking.value = false
    onSpeechDoneCallback = null
  }

  fun playSuccessChime() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_ACK, 220)
    } catch (_: Exception) {}
  }

  fun playEncourageTone() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_PROMPT, 180)
    } catch (_: Exception) {}
  }

  fun playPopTone() {
    try {
      toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 70)
    } catch (_: Exception) {}
  }

  fun shutdown() {
    try {
      tts?.stop()
      tts?.shutdown()
      tts = null
      toneGen?.release()
      toneGen = null
    } catch (_: Exception) {}
  }
}
