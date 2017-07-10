package seven.mesachinsingh.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

/**
 * Created by gs on 4/4/2017.
 */

public class AIAppManagement extends Activity {
    Context context;
    public void aiAppManage(Context context, String app_name, String toDo) {
        Log.d("app-name : ", app_name);
        Log.d("todo : ", toDo);
        switch(toDo) {
            case "open":
                // Call function to start an Activity
                openApp(context, app_name);
                break;

            case "close":

                break;
        }
    }

    public void openApp(Context context, String appName) {
        this.context = context;
        final PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for(ApplicationInfo packageInfo : packages) {
            String resolvePackage = packageManager.getApplicationLabel(packageInfo).toString();
            Log.d("resolved package : ", resolvePackage);
            if(appName.equals(resolvePackage)) {
                Intent launchApp = packageManager.getLaunchIntentForPackage(packageInfo.packageName);
                context.startActivity(launchApp);
            }
        }
    }

    public void closeApp() {
        // Write code to close an running application

    }
}
