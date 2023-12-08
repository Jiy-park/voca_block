package com.dd2d.voca_block

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.dd2d.voca_block.TTSState.OnReady
import com.dd2d.voca_block.TTSState.OnSpeakMean
import com.dd2d.voca_block.TTSState.OnSpeakWord
import com.dd2d.voca_block.view.word_book_view.DetailType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** TTS 상태.
 *@property[OnReady] 준비 완료 상태. 단어나 뜻을 읽을 수 있음.
 *@property[OnSpeakWord] 단어를 읽는 상태. 읽은 후 [OnReady] 상태로 전환
 *@property[OnSpeakMean] 뜻을 읽는 상태. 읽은 후 [OnReady] 상태로 전환*/
sealed class TTSState{
    object OnReady: TTSState()
    object OnSpeakWord: TTSState()
    object OnSpeakMean: TTSState()
}

/** TTS 가 읽을 대상.*/
enum class SpeakTarget{
    Word, Mean
}

/** @property[ttsState] TTS 상태 흐름. 흐름에 따라 특정 값을 가짐.
 * @see[TTSState]*/
class TTS(context: Context): TextToSpeech.OnInitListener {
    private var instance: TextToSpeech? = null
    private val _ttsState = MutableStateFlow<TTSState>(OnReady)
    val ttsState: StateFlow<TTSState>
        get() = _ttsState.asStateFlow()

    init {
        _ttsState
        instance = TextToSpeech(context, this).apply {
            setOnUtteranceProgressListener(object: UtteranceProgressListener(){
                override fun onStart(utteranceId: String?) {
                    when(utteranceId){
                        SpeakTarget.Word.name -> { _ttsState.value = OnSpeakWord }
                        SpeakTarget.Mean.name -> { _ttsState.value = OnSpeakMean }
                    }
                }

                override fun onDone(utteranceId: String?) {
                    _ttsState.value = OnReady
                }
                @Deprecated("")
                override fun onError(utteranceId: String?) {}
            })
        }
    }

    fun speak(text: String, detailType: DetailType, speakTarget: SpeakTarget){
        instance?.language = detailType.locale
        instance?.speak(text, TextToSpeech.QUEUE_FLUSH, null, speakTarget.name)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d("LOG_CHECK", "TTS :: onInit() -> complete init TTS")
        }
        else {
            Log.d("LOG_CHECK", "TTS :: onInit() -> fail init TTS")
        }
    }
}