package ScanningCommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

import businesslogic.ApplicationException;
import businesslogic.BaseDocumentLogic;
import businesslogic.FullDataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.PackageListStructureModel;
import businesslogic.ProductLogic;
import businesslogic.ProductModel;
import businesslogic.Product_PackageListStructureModel;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.ScannerApplication;

public class PackageListCommand implements Command {

    Activity Activity;
    ScannerApplication appState;

    businesslogic.ProductLogic ProductLogic;
    BaseDocumentLogic baseDocumentLogic;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;

        this.ProductLogic = new ProductLogic(
                appState.nomenclatureStructureModel,
                appState.characterisiticStructureModel,
                appState.manufacturerStructureModel);

        this.baseDocumentLogic = new BaseDocumentLogic(appState.baseDocumentStructureModel);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        // TODO: ALARM if this client need to compile order only by Package Lists!!! - will be passed with Base document
        
        if(appState.packageListDataTable.IsContains(data.getData()))
        {
            ((MainActivity)Activity).AlarmAndNotify("Этот Упаковочный лист уже сканировался");
        }
        else
        {
            // GO to DB and get list of products

            PackageListStructureModel packageListStructureModel = new PackageListStructureModel(); //TODO : create helper

            // Add all products to tables

            Boolean areAllProductsContainsInOrder = null;

            for( Product_PackageListStructureModel product : packageListStructureModel.GetProducts())
            {
                try {
                    this.baseDocumentLogic.IsExistsInOrder(product);
                    areAllProductsContainsInOrder = true;
                }
                catch (ApplicationException ex)
                {
                    ((MainActivity)Activity).AlarmAndNotify(ex.getMessage());
                    areAllProductsContainsInOrder = false;
                }
            }

            //TODO: show full info about Package List
        /*
        if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == true)
        {
            String result = this.ProductLogic.CreatePcageListInfo(packageList); // TODO: implement method
            ((MainActivity)this.Activity).new AsyncBarcodeInfoUpdate().execute(result);
        }

         */

            if(areAllProductsContainsInOrder == true)
            {
                appState.packageListDataTable.Add(packageListStructureModel);

                for( Product_PackageListStructureModel product : packageListStructureModel.GetProducts()) {

                    for(int i=1;  i<= product.GetItems() ; i++) {
                        SuccessSaveData(product);
                    }
                }
            }
        }

    }

    protected void SuccessSaveData( ProductModel product)
    {
        if(((MainActivity)this.Activity).IsBarcodeInfoFragmentShowed == false)
        {
            ListViewPresentationModel viewUpdateModel = this.ProductLogic.CreateListView(product);
            ((MainActivity)this.Activity).new BaseAsyncDataUpdate( viewUpdateModel).execute();

            FullDataTableControl.Details detailsModel = this.ProductLogic.CreateDetails(product);
            appState.ScannedProductsToSend.Add(detailsModel);
        }
    }

    @Override
    public void PostAction() {

    }
}
