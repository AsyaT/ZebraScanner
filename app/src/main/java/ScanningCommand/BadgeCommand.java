package scanningcommand;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import presentation.FragmentHelper;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanBadgeFragment;
import ru.zferma.zebrascanner.ScannerApplication;
import serverDatabaseInteraction.AsyncWebServiceResponse;
import serverDatabaseInteraction.ResponseModelMaker;

public class BadgeCommand implements Command {

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
        appState.SetBadge(data.getData());

        // 3. Отправить POST

        try {
            String url = appState.serverConnection.getResponseUrl();

            String jsonResponse = ResponseModelMaker.MakeResponseJson(
                    appState.GetLocationContext().GetAccountingAreaGUID(),
                    appState.GetBadgeGuid(),
                    appState.GetBaseDocument().GetOrderId(),
                    appState.ScannedProductsToSend.GetListOfProducts());

            Integer resultCode = (new AsyncWebServiceResponse())
                    .execute(
                        url,
                        appState.serverConnection.GetUsernameAndPassword(),
                        jsonResponse)
                    .get();

            //TODO: notification for user if Success or not
            ((MainActivity)this.Activity).new AsyncMessageDialog().execute(String.valueOf(resultCode));

            if(resultCode == 200)
            {
                // TODO: 5. переходить на выбор операции
               appState.CleanContextEntities();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApplicationException e) {
            ((MainActivity)this.Activity).AlarmAndNotify(e.getMessage());
        }


        // TODO: 4. GET для печатной формы

    }
}
