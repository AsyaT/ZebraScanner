package ru.zferma.zebrascanner;

import com.symbol.emdk.EMDKManager;

public class InventoryActivity extends MainActivity {

    @Override
    public void onOpened(EMDKManager emdkManager)
    {
        super.onOpened(emdkManager);

        DisableScanner();

        PullProductsInThread();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
