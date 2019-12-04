package businesslogic;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;

public interface Command {
    public void Action(Activity activity, Scanner scanner);
    public void ParseData(ScanDataCollection.ScanData data);
    public void PostAction(Scanner scanner);
}
