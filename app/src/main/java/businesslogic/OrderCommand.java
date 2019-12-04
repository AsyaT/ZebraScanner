package businesslogic;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import ru.zferma.zebrascanner.FragmentHelper;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanOrderFragment;

public class OrderCommand implements Command {
    @Override
    public void Action(Activity activity) {
        ScanOrderFragment orderInfoFragment = (ScanOrderFragment) ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);
        FragmentHelper fragmentHelper = new FragmentHelper(activity);
        fragmentHelper.closeFragment(orderInfoFragment);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {
        //TODO : parse data and save
    }

    @Override
    public void PostAction( ) {

    }
}
