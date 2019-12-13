package businesslogic;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import ru.zferma.zebrascanner.FragmentHelper;
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

        //Show small row fragment with Order Name and products list
    }
}
