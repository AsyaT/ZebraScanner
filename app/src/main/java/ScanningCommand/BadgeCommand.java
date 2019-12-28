package ScanningCommand;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;

import presentation.FragmentHelper;
import ru.zferma.zebrascanner.MainActivity;
import ru.zferma.zebrascanner.R;
import ru.zferma.zebrascanner.ScanBadgeFragment;
import ru.zferma.zebrascanner.ScannerApplication;

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
    public void PostAction() {
        ((MainActivity) Activity).SendServerPOST();
    }
}
