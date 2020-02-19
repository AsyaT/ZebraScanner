package scanningcommand;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.BaseDocumentStructureModel;
import businesslogic.ScannerState;
import presentation.FragmentHelper;
import ru.zferma.zebrascanner.OrderInfoFragment;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanOrderFragment;
import ru.zferma.zebrascanner.ScannerApplication;
import serverDatabaseInteraction.BaseDocumentHelper;

public class DocumentBaseCommand implements Command {

    Activity Activity;
    ScannerApplication appState = null;
    FragmentHelper fragmentHelper;

    ScanOrderFragment orderInfoFragment;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;
        appState = ((ScannerApplication)this.Activity.getApplication());
        fragmentHelper = new FragmentHelper(Activity);

        orderInfoFragment = (ScanOrderFragment) ((AppCompatActivity)Activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);

    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {
        String OrderGuid = data.getData();

        try {

            appState.SetBaseDocument(GetBaseDocumentFromServer(OrderGuid));
            //appState.GetBaseDocument().SetOrderGuid(OrderGuid);

            CloseCurrentFragment();

            ShowBottomInfoFragment();

            appState.scannerState.Set(ScannerState.PRODUCT);
        }
        catch (ApplicationException e)
        {
            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    ShowErrorMessageOnFragment(e.getMessage());
                    return;
                }
            });
        }
        catch (ExecutionException e)
        {
            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    ShowErrorMessageOnFragment(e.getMessage());
                    return;
                }
            });
        }
        catch (InterruptedException e)
        {
            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    ShowErrorMessageOnFragment(e.getMessage());
                    return;
                }
            });
        }
    }

    protected BaseDocumentStructureModel GetBaseDocumentFromServer(String guid) throws ApplicationException, ExecutionException, InterruptedException {
        String userpass =  appState.serverConnection.GetUsernameAndPassword();
        String url= appState.serverConnection.GetOrderProductURL(appState.GetLocationContext().GetAccountingAreaGUID(), guid);

        BaseDocumentHelper baseDocumentHelper = new BaseDocumentHelper(url, userpass);

        BaseDocumentStructureModel resultModel =  (BaseDocumentStructureModel) baseDocumentHelper.GetData();

        resultModel.SetOrderGuid(guid);
        return resultModel;
    }

    protected void CloseCurrentFragment()
    {
        fragmentHelper.closeFragment(orderInfoFragment);
    }

    protected void ShowErrorMessageOnFragment(String message)
    {
        orderInfoFragment.UpdateText(message);
    }

    protected void ShowBottomInfoFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", appState.GetBaseDocument());

        Fragment orderNameInfoFragment = new OrderInfoFragment();
        orderNameInfoFragment.setArguments(bundle);
        fragmentHelper.replaceFragment(orderNameInfoFragment, R.id.frOrderInfo);

    }

}
