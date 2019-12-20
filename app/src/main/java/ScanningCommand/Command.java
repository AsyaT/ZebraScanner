package ScanningCommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

public interface Command {
    public void Action(Activity activity);
    public void ParseData(ScanDataCollection.ScanData data);
    public void PostAction();
}
