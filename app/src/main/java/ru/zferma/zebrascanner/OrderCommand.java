package ru.zferma.zebrascanner;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;

public class OrderCommand implements Command {

    String OrderGuid;

    @Override
    public void Action(Activity activity, Scanner scanner) {
        ScanOrderFragment orderInfoFragment = (ScanOrderFragment) ((AppCompatActivity)activity).getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);
        FragmentHelper fragmentHelper = new FragmentHelper(activity);
        fragmentHelper.closeFragment(orderInfoFragment);
    }

    @Override
    public void ParseData(ScanDataCollection.ScanData data) {
        OrderGuid = data.getData();
    }

    @Override
    public void PostAction( Scanner scanner) {
        OrderViewStructure orderViewStructure = new OrderViewStructure(OrderGuid);

        //Show fragment with Order Name and products list
    }
}
