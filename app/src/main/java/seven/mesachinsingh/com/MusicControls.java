package seven.mesachinsingh.com;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by root on 7/7/17.
 */

public class MusicControls {
    Context context;
    public void musicControls(Context context, String musicAction) {
        this.context = context;

        Log.d("Music Action : ", musicAction);

        Intent todoPlayer = new Intent("com.android.music.musicservicecommand");

        switch(musicAction) {
            case "play":
                Intent openPlayer = new Intent("android.intent.action.MUSIC_PLAYER");
                //openPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(openPlayer);
                todoPlayer.putExtra("command", "play");
                context.sendBroadcast(todoPlayer);
                break;

            case "pause":
                todoPlayer.putExtra("command", "pause");
                context.sendBroadcast(todoPlayer);
                break;

            case "resume":
                todoPlayer.putExtra("command", "togglepause");
                context.sendBroadcast(todoPlayer);
                break;

            case "stop":
                todoPlayer.putExtra("command", "stop");
                context.sendBroadcast(todoPlayer);
                break;

            case "next":
                todoPlayer.putExtra("command", "next");
                context.sendBroadcast(todoPlayer);
                break;

            case "previous":
                todoPlayer.putExtra("command", "previous");
                context.sendBroadcast(todoPlayer);
                break;

        }
    }

}
