package seven.mesachinsingh.com;

/**
 * Created by gs on 4/4/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import ai.api.model.Result;

public class IntentIdentifier extends Activity {
    String resolvedQuery, speech, intent_name, action, action_next, parameter, value;
    String toManage = "null";            // Things to control
    String serviceControl = "null";     // Services to be controlled

    AIAppManagement aiAppManagement = new AIAppManagement();

    ControlSettings controlSettings = new ControlSettings();

    MusicControls mControls = new MusicControls();

    public void onReceiveIntent(Context context, Result result)  {
        // Get all the parameters from the JSON element.
        resolvedQuery = result.getResolvedQuery();
        speech = result.getFulfillment().getSpeech();
        intent_name = result.getMetadata().getIntentName();
        action = result.getAction();
        parameter = result.getParameters().toString();
        value = result.getParameters().values().toString();

        //String param1 = result.getStringParameter("module");

        Set<String> intentName = new HashSet<>();

        Log.d("Parameters ", parameter);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("intents.txt")));
            String getIntentName = reader.readLine();
            while (getIntentName != null) {
                intentName.add(getIntentName);
                getIntentName = reader.readLine();
            }
        } catch (IOException e) {
            Log.d("Intent", "Unsuccessful");
        }


        if(intentName.contains(intent_name)) {
            Log.d("Intent Success : ", "Successfully loaded");
            // Add a dot(.) in all the intents name
            toManage = intent_name.substring(0, intent_name.indexOf("."));
            serviceControl = intent_name.substring(intent_name.indexOf(".")+1);
            Log.d("toManage", toManage);
        }

        switch(toManage) {
            case "app":
                // App management class.
                aiAppManagement.aiAppManage(context, result.getStringParameter("app-name"), result.getStringParameter("open_close"));
                break;

            case "device":
                switch(serviceControl) {
                    case "settings.on/off":
                        // All the settings are controlled through this class
                        controlSettings.turnOnOff(context, result);
                        break;

                }
                break;

            case "music":
                String musicAction = result.getStringParameter("music-action");
                mControls.musicControls(context, musicAction);
                break;

            default :
                break;
        }
    }
}
