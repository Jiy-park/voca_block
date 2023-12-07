package com.dd2d.voca_block

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TTS{
    /** TTS, 단어를 주면 해당 단어를 읽어줌.
     * @param[meanTTS] 단어의 뜻을 읽어줌.
     * @param[wordTTS] 단어를 읽어줌.*/
    data class Instance(
        val wordTTS: TextToSpeech,
        val meanTTS: TextToSpeech,
    ){
        fun speakWord(word: String, endSpeak: ()->Unit){
            wordTTS.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
            endSpeak()
        }

        fun speakMean(mean: String, endSpeak: ()->Unit){
            meanTTS.speak(mean, TextToSpeech.QUEUE_FLUSH, null, null)
            endSpeak()
        }
    }

    companion object {
        private var wordInstance: TextToSpeech? = null
        private var meanInstance: TextToSpeech? = null

        fun getInstance(context: Context, wordType: Values.Common.WordType): Instance{
            if(wordInstance != null && meanInstance != null) return Instance(wordInstance!!, meanInstance!!)
            else{
                val (typeWord, typeMean) = wordType.type
                wordInstance = TextToSpeech(context){
                    wordInstance?.language = when(typeWord){
                        Values.Common.DetailType.En -> { Locale.US }
                        Values.Common.DetailType.Kr -> { Locale.KOREA }
                    }
                }

                meanInstance = TextToSpeech(context){
                    meanInstance?.language = when(typeMean){
                        Values.Common.DetailType.En -> { Locale.US }
                        Values.Common.DetailType.Kr -> { Locale.KOREA }
                    }
                }

                return Instance(wordInstance!!, meanInstance!!)
            }
        }
    }
}