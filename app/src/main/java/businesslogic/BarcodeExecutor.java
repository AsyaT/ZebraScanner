package businesslogic;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;

public class BarcodeExecutor {
    public void Execute(ScannerState state, ScanDataCollection.ScanData data, Activity activity, Scanner scanner) throws InstantiationException, IllegalAccessException {
        Class classCommand = state.GetClass();
        Command command = (Command) classCommand.newInstance();
        command.Action(activity, scanner);
        command.ParseData(data);
        command.PostAction(scanner);

    }
}
