package businesslogic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import ru.zferma.zebrascanner.FragmentHelper;
import ru.zferma.zebrascanner.OrderInfoFragment;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanOrderFragment;
import ru.zferma.zebrascanner.ScannerApplication;

public class OrderCommand implements Command {

    String OrderGuid;
    Activity Activity;

    @Override
    public void Action(Activity activity) {
        this.Activity = activity;

        ScanOrderFragment orderInfoFragment = (ScanOrderFragment) ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);
        FragmentHelper fragmentHelper = new FragmentHelper(activity);
        fragmentHelper.closeFragment(orderInfoFragment);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {

        OrderGuid = data.getData();
    }

    @Override
    public void PostAction( ) {

        ScannerApplication appState = ((ScannerApplication)this.Activity.getApplication());
        String userpass =  appState.serverConnection.GetUsernameAndPassword();
        String url= appState.serverConnection.GetOrderProductURL(OrderGuid);

        OrderHelper orderHelper = new OrderHelper(url, userpass);

        Bundle bundle = new Bundle();
        bundle.putSerializable("order", orderHelper.GetData());

        Fragment orderInfoFragment = new OrderInfoFragment();
        orderInfoFragment.setArguments(bundle);
        FragmentHelper fragmentHelper = new FragmentHelper(this.Activity);
        fragmentHelper.replaceFragment(orderInfoFragment, R.id.frOrderInfo);

    }
}
