package seven.mesachinsingh.com;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Locale;

import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.ui.AIButton;

public class MainActivity extends AppCompatActivity implements AIButton.AIButtonListener {
    private ListView listView;
    private ArrayAdapter<String> chatAdapter;

    TextToSpeech tts;

    IntentIdentifier intentId = new IntentIdentifier();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(seven.mesachinsingh.com.R.layout.activity_main);


        AIButton aiButton = (AIButton) findViewById(seven.mesachinsingh.com.R.id.micButton);
        //TextView chatText = (TextView) findViewById(seven.mesachinsingh.com.R.id.message_text);
        listView = (ListView) findViewById(seven.mesachinsingh.com.R.id.listView);

        chatAdapter = new ArrayAdapter<>(this, seven.mesachinsingh.com.R.layout.chat_text);

        //Initialize Text To Speech
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                   if(status != TextToSpeech.ERROR) {
                       tts.setLanguage(Locale.ENGLISH);
                   }
            }
        });

        final AIConfiguration config = new AIConfiguration("b7293e4e08f144e5b2a304bcca5eaa70",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        //aiService = AIService.getService(this, config);
        //aiService.setListener();

        aiButton.initialize(config);
        aiButton.setResultsListener(this);
    }

   /* public void listenButtonClick(final View view) {
        aiService.startListening();
    }       */

    public void onResult(final AIResponse response) {
        //chatText = (TextView)context.findViewById(R.id.message_text);

        Result result = response.getResult();
        String parameterString = "";

        Log.d("ParameterString : ",parameterString);

        String Jiva_response = result.getFulfillment().getSpeech();
        /*
        resultTextView.append("\nQuery: " + result.getResolvedQuery() +                                                // Query sent to server
                "\nSpeech: " + result.getFulfillment().getSpeech());             // Uncomment to enable debugging of Action.
        */
        tts.speak(Jiva_response, TextToSpeech.QUEUE_FLUSH, null, null);

        //chatText.setGravity(Gravity.START);
        chatAdapter.add(result.getResolvedQuery());
        //chatText.setGravity(Gravity.END);
        chatAdapter.add(result.getFulfillment().getSpeech());

        listView.setAdapter(chatAdapter);
        /*
        resultTextView.setGravity(Gravity.LEFT|Gravity.BOTTOM);
        resultTextView.append("\n" + result.getResolvedQuery());
        //resultTextView.setGravity(Gravity.END|Gravity.BOTTOM);
        //resultTextView.append("\n" + result.getResolvedQuery());

        */
        intentId.onReceiveIntent(this, result);

    }

    @Override
    public void  onError(final AIError error) {
        //resultTextView.setText(error.toString());
    }

    @Override
    public void onCancelled() {

    }


}


