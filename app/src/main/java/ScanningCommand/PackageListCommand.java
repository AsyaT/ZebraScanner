package ScanningCommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

import businesslogic.BarcodeStructureModel;
import businesslogic.FullDataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.PackageListStructureModel;
import businesslogic.ProductLogic;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.ScannerApplication;

public class PackageListCommand implements Command {

    Activity Activity;
    ScannerApplication appState;

    businesslogic.ProductLogic ProductLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;

        this.ProductLogic = new ProductLogic(appState.barcodeStructureModel,
                appState.nomenclatureStructureModel,
                appState.characterisiticStructureModel,
                appState.manufacturerStructureModel,
                appState.baseDocumentStructureModel,
                appState.LocationContext);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        // ALARM if this client need to compile order only by Package Lists!!! - will be passed with Base document

        // DO Not scan each list twice

        // CHECK order. Check each product or ALL products??? - order should include ALL products

        // GO to DB and get list of products

        PackageListStructureModel packageListStructureModel = new PackageListStructureModel(); //TODO : create helper

        // Add all products to tables

        for( PackageListStructureModel.ProductStructureModel product : packageListStructureModel.GetProducts())
        {
            SuccessSaveData(product);
        }
    }

    protected void SuccessSaveData( PackageListStructureModel.ProductStructureModel product)
    {
        ListViewPresentationModel viewUpdateModel = this.ProductLogic.CreateListView(product);

        if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            ((MainActivity)this.Activity).new BaseAsyncDataUpdate( viewUpdateModel).execute();
        }
        else if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == true)
        {
            String result = this.ProductLogic.CreateStringResponse(product);
            ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
        }

        FullDataTableControl.Details detailsModel = this.ProductLogic.CreateDetails(product);
        appState.ScannedProductsToSend.Add(detailsModel);
    }

    @Override
    public void PostAction() {

    }
}
