package ru.zferma.zebrascanner;

import com.symbol.emdk.EMDKManager;

public class RealizationActivity extends MainActivity {
    @Override
    public void onOpened(EMDKManager emdkManager)
    {
        super.onOpened(emdkManager);

        PullProductsInThread();

        ShowFragmentScanBaseDocument();
    }

}
