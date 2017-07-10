package seven.mesachinsingh.com;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

import ai.api.model.Result;

/**
 * Created by root on 7/7/17.
 */

public class ControlSettings extends Activity {
    Context context;
    public void turnOnOff(Context context, Result result) {
        this.context = context;

        String pValue1 = result.getStringParameter("module");
        Log.d("Parameter 2nd value ", pValue1);

        String pValue2 = result.getStringParameter("device-action");
        Log.d("Parameters are ", pValue2);
        ;

        switch(pValue1) {
            case "gps":
                Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                if (pValue2.equals("on"))
                    context.startActivity(gps);

                else if (pValue2.equals("off"))
                    context.startActivity(gps);

                //context.sendBroadcast(gps);
                break;

            case "internet":
                try {
                    ConnectivityManager dataManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    Method dataMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
                    dataMethod.setAccessible(true);

                    if (pValue2.equals("on"))
                        dataMethod.invoke(dataManager, true);

                    else if (pValue2.equals("off"))
                        dataMethod.invoke(dataManager, false);
                } catch (Exception e) {
                    Toast.makeText(context, "Internet Connectivity Problem", Toast.LENGTH_SHORT).show();
                }

                break;


            case "wifi":
                WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(pValue2.equals("on"))
                    wifi.setWifiEnabled(true);
                else if(pValue2.equals("off"))
                    wifi.setWifiEnabled(false);
                break;

            case "bluetooth":
                BluetoothManager bluetooth = (BluetoothManager)context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
                if(bt == null) {
                    Toast.makeText(context, "No Bluetooth device found", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(pValue2.equals("on"))        //turn on bluetooth
                    bt.enable();

                else if(pValue2.equals("off"))      //turn off bluetooth
                    bt.disable();

                break;

            case "airplane":
                Intent Airplane_mode = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                if(pValue2.equals("on"))
                    Airplane_mode.putExtra("state", true);
                else if(pValue2.equals("off"))
                    Airplane_mode.putExtra("state", false);

                context.sendBroadcast(Airplane_mode);
                break;

        }

    }
}
