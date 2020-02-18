package scanningcommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

import businesslogic.ScannerState;

public class BarcodeExecutor {
    public void Execute(ScannerState state, ScanDataCollection.ScanData data, Activity activity) throws InstantiationException, IllegalAccessException {

        Class classCommand = state.GetClass();
        Command command = (Command) classCommand.newInstance();
        command.Action(activity);
        command.ParseData(data);
    }
}
