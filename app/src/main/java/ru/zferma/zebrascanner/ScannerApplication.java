package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import businesslogic.CharacterisiticStructureModel;
import businesslogic.OperationTypesStructureModel;
import businesslogic.OperationsTypesAccountingAreaStructureModel;
import businesslogic.OrderStructureModel;
import businesslogic.ScannerStateHelper;
import businesslogic.ServerConnection;
import businesslogic.BarcodeStructureModel;
import businesslogic.ProductStructureModel;

public class ScannerApplication extends Application {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";
    public static final String APP_1C_SERVER = "Server";

    private static Context context;

    public ServerConnection serverConnection = null;

    public BarcodeStructureModel barcodeStructureModel = null;
    public ProductStructureModel productStructureModel = null;
    public CharacterisiticStructureModel characterisiticStructureModel = null;
    public OperationsTypesAccountingAreaStructureModel operationsTypesAccountingAreaStructureModel = null;

    //Context entities
    public ScannerStateHelper scannerState = new ScannerStateHelper();
    public OperationTypesStructureModel LocationContext;
    public OrderStructureModel orderStructureModel = null;
    public String BadgeGuid = null;

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
