package ScanningCommand;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.concurrent.ExecutionException;

import businesslogic.FullDataTableControl;
import businesslogic.ResponseStructureModel;
import presentation.FragmentHelper;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanBadgeFragment;
import ru.zferma.zebrascanner.ScannerApplication;
import serverDatabaseInteraction.WebServiceResponse;

public class BadgeCommand implements Command  {

    Activity Activity;

    @Override
    public void Action(Activity activity)
    {
        this.Activity = activity;

        ScanBadgeFragment scanBadgeFragment = (ScanBadgeFragment) ((AppCompatActivity)Activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);
        FragmentHelper fragmentHelper = new FragmentHelper(Activity);
        fragmentHelper.closeFragment(scanBadgeFragment);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        ScannerApplication appState = ((ScannerApplication) Activity.getApplication());
        appState.BadgeGuid = data.getData();

    }

    @Override
    public void PostAction()
    {
        // 3. Отправить POST

        ScannerApplication appState = ((ScannerApplication) Activity.getApplication());
        String url = appState.serverConnection.getResponseUrl();

        ResponseStructureModel responseStructureModel = new ResponseStructureModel();
        responseStructureModel.AccountingAreaGUID = appState.LocationContext.GetAccountingAreaGUID();
        responseStructureModel.UserID = appState.BadgeGuid;
        if(appState.orderStructureModel != null)
        {
            responseStructureModel.DocumentID = appState.orderStructureModel.GetOrderId();
        }

        for(FullDataTableControl.Details product : appState.ScannedProductsToSend.GetListOfProducts())
        {
            ResponseStructureModel.ResponseProductStructureModel rpsm = new ResponseStructureModel.ResponseProductStructureModel();
            rpsm.ProductGUID = product.getProductGuid();
            rpsm.ProductCharactGUID = product.getCharacteristicGuid();
            rpsm.Weigth = String.valueOf(product.getInfoFromScanner().getWeight() * product.getQuantity());
            rpsm.Pieces = String.valueOf(product.getQuantity());
            rpsm.DateOfProduction = String.valueOf(product.getInfoFromScanner().getProductionDate());
            rpsm.DataOfExpiration = String.valueOf(product.getInfoFromScanner().getExpirationDate());
            rpsm.ManufacturerGUID = product.getManufacturerGUID();
            responseStructureModel.ProductList.add(rpsm);
        }

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseStructureModel);

        try {
            Integer resultCode = (new WebServiceResponse()).execute(url,appState.serverConnection.GetUsernameAndPassword(), jsonResponse).get();

            ((MainActivity)this.Activity).new MessageDialog().execute(String.valueOf(resultCode));

            if(resultCode == 200)
            {
                appState.ScannedProductsToSend.CleanListOfProducts();

                // TODO: 5. очищать таблицы или переходить на выбор операции
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // TODO: 4. GET для печатной формы

    }
}
