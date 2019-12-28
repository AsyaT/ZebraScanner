package ScanningCommand;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.concurrent.ExecutionException;

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

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseStructureModel);

        try {
            String result = (new WebServiceResponse()).execute(url,appState.serverConnection.GetUsernameAndPassword(), jsonResponse).get();

            ((MainActivity)this.Activity).new MessageDialog().execute(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO: очищать таблицы или переходить на выбор операции
        // TODO: 4. GET для печатной формы
    }
}
