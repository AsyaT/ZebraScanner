package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import businesslogic.BarcodeStructureModel;
import businesslogic.CharacterisiticStructureModel;
import businesslogic.FullDataTableControl;
import businesslogic.ManufacturerStructureModel;
import businesslogic.OperationTypesStructureModel;
import businesslogic.OperationsTypesAccountingAreaStructureModel;
import businesslogic.OrderStructureModel;
import businesslogic.ProductStructureModel;
import businesslogic.ScannerStateHelper;
import businesslogic.ServerConnection;

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
    public ManufacturerStructureModel manufacturerStructureModel = null;

    //Context entities
    public ScannerStateHelper scannerState = new ScannerStateHelper();
    public OperationTypesStructureModel LocationContext;
    public OrderStructureModel orderStructureModel = null;
    public String BadgeGuid = null;

    public FullDataTableControl ScannedProductsToSend = new FullDataTableControl();

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
