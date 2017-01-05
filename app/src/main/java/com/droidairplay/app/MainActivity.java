package com.droidairplay.app;

import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.raventech.airplayserver.AirPlayServer;
import com.raventech.airplayserver.network.NetworkUtils;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        startAirPlay();
    }

    public void startAirPlay()
    {
        //get Network details
        NetworkUtils networkUtils = NetworkUtils.getInstance();

        String msg = "bug occur!";
        String hardwareAddressString = networkUtils.getHardwareAddressString();
        NetworkUtils.getInstance().setHostName("Airplay "+ hardwareAddressString);
        final AirPlayServer airPlayServer = AirPlayServer.getIstance();
        airPlayServer.setRtspPort(8998);
        Thread airThread = new Thread(airPlayServer);
        try {
            airThread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        airThread.start();
        //airPlayServer.run();
        msg = "Starting Service...";

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
