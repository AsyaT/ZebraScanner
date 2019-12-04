package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;

import businesslogic.ServerConnection;

public class ScannerApplication extends Application {

    private static Context context;

    public ServerConnection serverConnection = null;

    public void onCreate() {
        super.onCreate();
        ScannerApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ScannerApplication.context;
    }

}
