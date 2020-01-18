package ScanningCommand;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;

import businesslogic.ScannerState;

public class BarcodeExecutor {
    public void Execute(ScannerState state, ScanDataCollection.ScanData data, Activity activity) throws InstantiationException, IllegalAccessException {

        //TODO : set proper LabelType
        if(state == ScannerState.PRODUCT && data.getLabelType() == ScanDataCollection.LabelType.GS1_DATABAR) // TODO: also might be scanned GUID of PAckegeList
        {
            state = ScannerState.PACKAGELIST;
        }

        Class classCommand = state.GetClass();
        Command command = (Command) classCommand.newInstance();
        command.Action(activity);
        command.ParseData(data);
        command.PostAction();

    }
}
