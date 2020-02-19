package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import businesslogic.*;

public class ScannerApplication extends Application {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";
    public static final String APP_1C_SERVER = "Server";

    private static Context context;

    public ServerConnection serverConnection = null;

    public BarcodeStructureModel barcodeStructureModel = null;
    public NomenclatureStructureModel nomenclatureStructureModel = null;
    public CharacteristicStructureModel characteristicStructureModel = null;
    public OperationsTypesAccountingAreaStructureModel operationsTypesAccountingAreaStructureModel = null;
    public ManufacturerStructureModel manufacturerStructureModel = null;

    //Context entities
    public ScannerStateHelper scannerState = new ScannerStateHelper();

    protected OperationTypesStructureModel LocationContext = null;
    public void SetCurrentLocationContext(OperationTypesStructureModel model)
    {
        LocationContext = model;
    }
    public OperationTypesStructureModel GetLocationContext() throws ApplicationException {
        if(LocationContext != null)
        {
            return LocationContext;
        }
        else
            {
                throw new ApplicationException("Не выбран участок учёта!");
            }
    }

    protected BaseDocumentStructureModel baseDocumentStructureModel = null;

    public void SetBaseDocument(BaseDocumentStructureModel model)
    {
        baseDocumentStructureModel = model;
    }
    public BaseDocumentStructureModel GetBaseDocument()
    {
            return baseDocumentStructureModel;
    }

    protected String BadgeGuid = null;
    public void SetBadge(String guid)
    {
        BadgeGuid = guid;
    }

    public String GetBadgeGuid() throws ApplicationException {
        if(BadgeGuid != null) {
            return BadgeGuid;
        }
        else {
            throw new ApplicationException("Бейдж не был сканирован");
        }
    }

    public FullDataTableControl ScannedProductsToSend = new FullDataTableControl();
    public PackageListDataTable packageListDataTable = new PackageListDataTable();

    public void CleanContextEntities()
    {
        LocationContext = null;
        baseDocumentStructureModel = null;
        BadgeGuid = null;
        ScannedProductsToSend = new FullDataTableControl();
        packageListDataTable = new PackageListDataTable();
    }

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
