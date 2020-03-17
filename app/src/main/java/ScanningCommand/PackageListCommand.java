package scanningcommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.text.ParseException;
import java.util.Date;

import businesslogic.ApplicationException;
import businesslogic.BarcodeScanningLogic;
import businesslogic.BarcodeTypes;
import businesslogic.BaseDocumentLogic;
import businesslogic.DoesNotExistsInOrderException;
import businesslogic.FullDataTableControl;
import businesslogic.ResponseModelGenerator;
import businesslogic.ScannerState;
import models.ObjectForSaving;
import models.PackageListStructureModel;
import models.Product_PackageListStructureModel;
import models.ScanningBarcodeStructureModel;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.ScannerApplication;

public class PackageListCommand extends ResponseFormat implements Command {

    Activity Activity;
    ScannerApplication appState;

    ResponseModelGenerator responseModelGenerator;
    BaseDocumentLogic baseDocumentLogic;
    BarcodeScanningLogic barcodeScanningLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;

        this.responseModelGenerator = new ResponseModelGenerator(
                appState.nomenclatureStructureModel,
                appState.characteristicStructureModel,
                appState.manufacturerStructureModel);

        try {
            this.baseDocumentLogic = new BaseDocumentLogic(appState.GetBaseDocument());
            this.barcodeScanningLogic = new BarcodeScanningLogic(appState.GetLocationContext(), appState.GetBaseDocument());
        }
        catch (ApplicationException ex)
        {
            ((MainActivity) Activity).AlarmAndNotify(ex.getMessage());
        }
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        try
        {
            this.barcodeScanningLogic.IsBarcodeAllowedToScan(ScannerState.PACKAGELIST);
            appState.packageListDataTable.IsActionAllowedWithPackageList(data.getData());

            ScanningBarcodeStructureModel barcode = new ScanningBarcodeStructureModel(data.getData(), BarcodeTypes.GetType(data.getLabelType()));

            // GO to DB and get list of products

            PackageListStructureModel packageListStructureModel = new PackageListStructureModel("", new Date(),"",""); //TODO : create helper

            // Add all products to tables

            for( Product_PackageListStructureModel product : packageListStructureModel.GetProducts())
            {
                try
                {
                    this.baseDocumentLogic.IsExistsInOrder(product);
                }
                catch (DoesNotExistsInOrderException ex)
                {
                    ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
                }
            }

            SuccessSaveData(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed,packageListStructureModel , barcode);

        }
        catch (ApplicationException ex)
        {
            ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
        }
        catch (ParseException ex)
        {
            ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
        }
    }


    @Override
    protected void SaveInfoForProductList(ScanningBarcodeStructureModel barcode, ObjectForSaving packageList)
    {
        PackageListStructureModel input = (PackageListStructureModel) packageList;

        appState.packageListDataTable.Add(input);

        try {
            for (Product_PackageListStructureModel product : input.GetProducts()) {

                for (int i = 1; i <= product.GetItems(); i++) {

                    FullDataTableControl.Details detailsModel = this.responseModelGenerator.CreateFullDataTableResponse(product);
                    appState.ScannedProductsToSend.Add(detailsModel);
                }
            }
        }
        catch (ApplicationException e)
        {
            ((MainActivity) Activity).AlarmAndNotify(e.getMessage());
        }
    }

    @Override
    protected void ShowInfoForFragment(ScanningBarcodeStructureModel barcode, ObjectForSaving packageList)
    {
        String result = "";
        PackageListStructureModel input = (PackageListStructureModel) packageList;
        for (Product_PackageListStructureModel product : input.GetProducts()) {

            result = result + "\n--------------\n" + (this.responseModelGenerator.CreateStringResponse(product));
        }

        ((MainActivity) this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
    }

}

