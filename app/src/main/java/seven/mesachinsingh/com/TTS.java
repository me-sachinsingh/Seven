package seven.mesachinsingh.com;

import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Sachin on 4/2/2017.
 */

public class TTS {
    private static TextToSpeech textToSpeech;

    public static void speak(final String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
