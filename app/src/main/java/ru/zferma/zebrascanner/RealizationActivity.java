package ru.zferma.zebrascanner;

import android.os.Handler;
import android.view.View;

import com.symbol.emdk.EMDKManager;

public class RealizationActivity extends MainActivity {
    @Override
    public void onOpened(EMDKManager emdkManager)
    {
        super.onOpened(emdkManager);

        Handler hdlr = new Handler();
        ProgressBarMainActivity = findViewById(R.id.progressBarMainActivity);
        new Thread(new Runnable() {
            public void run() {

                hdlr.post(new Runnable() {
                    public void run() {
                        ProgressBarMainActivity.setVisibility(View.VISIBLE);
                        //DisableScanner();
                    }
                });

                UpdateProductsFromServer();

                hdlr.post(new Runnable() {
                    public void run() {
                        ProgressBarMainActivity.setVisibility(View.GONE);
                        EnableScanner();
                    }
                });

            }
        }).start();

        // TODO: enable scanner
        EnableScanner();
        ShowFragmentScanBaseDocument();
    }

}
