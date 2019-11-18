package ru.zferma.zebrascanner;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;

public class BarcodeExecutor {
    public void Execute(ScannerState state, ScanDataCollection.ScanData data, Activity activity, ScannerStateHelper stateHelper, Scanner scanner) throws InstantiationException, IllegalAccessException {
        Class classCommand = state.GetClass();
        Command command = (Command) classCommand.newInstance();
        command.Action(activity);
        command.ParseData(data);
        command.PostAction(stateHelper, scanner);

    }
}
