package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import businesslogic.BarcodeHelper;
import businesslogic.ProductHelper;
import businesslogic.ServerConnection;

public class ScannerApplication extends Application {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";
    public static final String APP_1C_SERVER = "Server";

    private static Context context;

    public ServerConnection serverConnection = null;

    public BarcodeHelper barcodeHelper = null;
    public ProductHelper productHelper = null;

    public void onCreate() {
        super.onCreate();
        ScannerApplication.context = getApplicationContext();

        SharedPreferences spSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        serverConnection = new ServerConnection(
                spSettings.getString(APP_1C_SERVER,""),
                spSettings.getString(APP_1C_USERNAME,""),
                spSettings.getString(APP_1C_PASSWORD,""));
    }

    public static Context getAppContext() {
        return ScannerApplication.context;
    }

}
