package ScanningCommand;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import presentation.FragmentHelper;
import ru.zferma.zebrascanner.OrderInfoFragment;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanOrderFragment;
import ru.zferma.zebrascanner.ScannerApplication;
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

        if(data.getData().length() != 39)
        {
            this.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    orderInfoFragment.UpdateText("Это не штрих-код заказа! Сканируйте другой штрих-код.");
                }
            });
        }
        else
            {
                OrderGuid = data.getData();

                FragmentHelper fragmentHelper = new FragmentHelper(Activity);
                fragmentHelper.closeFragment(orderInfoFragment);
        }
    }

    @Override
    public void PostAction( ) {

        ScannerApplication appState = ((ScannerApplication)this.Activity.getApplication());
        String userpass =  appState.serverConnection.GetUsernameAndPassword();
        String url= appState.serverConnection.GetOrderProductURL(OrderGuid);

        OrderHelper orderHelper = new OrderHelper(url, userpass);

        appState.orderStructureModel = orderHelper.GetModel();

        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderHelper.GetModel());

        Fragment orderInfoFragment = new OrderInfoFragment();
        orderInfoFragment.setArguments(bundle);
        FragmentHelper fragmentHelper = new FragmentHelper(this.Activity);
        fragmentHelper.replaceFragment(orderInfoFragment, R.id.frOrderInfo);

    }
}
