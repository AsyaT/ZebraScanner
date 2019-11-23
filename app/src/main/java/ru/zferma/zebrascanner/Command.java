package ru.zferma.zebrascanner;

import android.app.Activity;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;

public interface Command {
    public void Action(Activity activity);
    public void ParseData(ScanDataCollection.ScanData data);
    public void PostAction(Scanner scanner);
}
