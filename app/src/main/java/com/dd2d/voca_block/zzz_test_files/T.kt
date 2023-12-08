package com.dd2d.voca_block.zzz_test_files

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.dd2d.voca_block.common.TT
import com.dd2d.voca_block.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
@Preview
fun TTS(
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    var text by remember { mutableStateOf("banana") }
    var tts: TextToSpeech? = null

    tts = TextToSpeech(context){ init->
        if(init == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(context, "language not support", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "success to init tts", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context, "fail to init tts", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ){

        TextField(
            value = text,
            onValueChange = { text = it }
        )

        Button(onClick = { tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null) }) {
            TT(text = "speak")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun T(
    modifier: Modifier = Modifier
){
}

class Q{
    var a by mutableStateOf(false)
    var b by mutableStateOf(false)

    fun d(){
        a = true
        CoroutineScope(Dispatchers.Main).launch{
            if(!b && !a){
                log("1")
            }
            delay(1000L)
            a = false


        }
    }
}