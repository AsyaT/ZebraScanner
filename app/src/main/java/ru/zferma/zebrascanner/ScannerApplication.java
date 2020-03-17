package ru.zferma.zebrascanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import businesslogic.ApplicationException;
import businesslogic.FullDataTableControl;
import businesslogic.PackageListDataTable;
import businesslogic.ScannerStateHelper;
import businesslogic.ServerConnection;
import businesslogic.VersionUpdateServerConnection;
import models.BarcodeStructureModel;
import models.BaseDocumentStructureModel;
import models.CharacteristicStructureModel;
import models.ManufacturerStructureModel;
import models.NomenclatureStructureModel;
import models.OperationTypesStructureModel;
import models.OperationsTypesAccountingAreaStructureModel;
import models.ProductStructureModel;
import upgrading.UpgradeHelper;

public class ScannerApplication extends Application {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";
    public static final String APP_1C_SERVER = "Server";
    public static final String APP_1C_DATABASE = "Database";
    public static final String APP_UPDATE_SERVER = "UpdateServer";

    private static Context context;

    public ServerConnection serverConnection = null;

    public VersionUpdateServerConnection versionUpdateServerConnection = null;

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
    public HashMap<String, ProductStructureModel> SelectedDialogNomenclatures = new HashMap<>();

    public void CleanContextEntities()
    {
        LocationContext = null;
        baseDocumentStructureModel = null;
        BadgeGuid = null;
        ScannedProductsToSend = new FullDataTableControl();
        packageListDataTable = new PackageListDataTable();
        SelectedDialogNomenclatures = new HashMap<>();
    }

    public void onCreate() {
        super.onCreate();
        ScannerApplication.context = getApplicationContext();

        UpgradeHelper.StartService(context);

        SharedPreferences spSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        serverConnection = new ServerConnection(
                spSettings.getString(APP_1C_SERVER,""),
                spSettings.getString(APP_1C_DATABASE,""),
                spSettings.getString(APP_1C_USERNAME,""),
                spSettings.getString(APP_1C_PASSWORD,""));

        versionUpdateServerConnection = new VersionUpdateServerConnection(spSettings.getString(APP_UPDATE_SERVER, ""));
    }

    public static Context getAppContext() {
        return ScannerApplication.context;
    }

}
