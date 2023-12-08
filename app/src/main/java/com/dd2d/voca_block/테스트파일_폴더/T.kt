
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.dd2d.voca_block.common.LabelSlider
import com.dd2d.voca_block.common.TT
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
    var value by remember { mutableStateOf(0F) }
    val valueRange = 100F..500000F

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        LabelSlider(
            value = value,
            valueRange = valueRange,
            onValueChange = { value = it },
        )
    }
}

