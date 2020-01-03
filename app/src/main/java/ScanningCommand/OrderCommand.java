package ScanningCommand;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import businesslogic.OrderStructureModel;
import businesslogic.ScannerState;
import presentation.FragmentHelper;
import ru.zferma.zebrascanner.OrderInfoFragment;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanOrderFragment;
import ru.zferma.zebrascanner.ScannerApplication;
import serverDatabaseInteraction.ApplicationException;
import serverDatabaseInteraction.OrderHelper;

public class OrderCommand implements Command {

    String OrderGuid;
    Activity Activity;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;


    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data)
    {

        ScanOrderFragment orderInfoFragment = (ScanOrderFragment) ((AppCompatActivity)Activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);

        OrderGuid = data.getData();

        ScannerApplication appState = ((ScannerApplication)this.Activity.getApplication());
        String userpass =  appState.serverConnection.GetUsernameAndPassword();
        String url= appState.serverConnection.GetOrderProductURL(OrderGuid);

        OrderHelper orderHelper = null;
        try {
            orderHelper = new OrderHelper(url, userpass);

            OrderStructureModel serverResult = orderHelper.GetModel();

            appState.orderStructureModel = serverResult;
            appState.orderStructureModel.SetOrderGuid(data.getData());

            FragmentHelper fragmentHelper = new FragmentHelper(Activity);
            fragmentHelper.closeFragment(orderInfoFragment);

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", appState.orderStructureModel);

            Fragment orderNameInfoFragment = new OrderInfoFragment();
            orderNameInfoFragment.setArguments(bundle);
           // FragmentHelper fragmentHelper = new FragmentHelper(this.Activity);
            fragmentHelper.replaceFragment(orderNameInfoFragment, R.id.frOrderInfo);

            appState.scannerState.Set(ScannerState.PRODUCT);
        }
        catch (ApplicationException e)
        {
            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    orderInfoFragment.UpdateText(e.getMessage());
                    return;
                }
            });
        }
    }

    @Override
    public void PostAction( ) {



    }
}
