package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;

import com.symbol.emdk.EMDKManager;

public class PackageListActivity extends MainActivity {

    @Override
    public void onOpened(EMDKManager emdkManager)
    {
        super.onOpened(emdkManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(PackageListActivity.this);
        builder.setTitle(R.string.ChoseAction)
                .setItems(R.array.PackageListActions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0: break;
                            case 1:
                                EnableScanner();
                                ShowFragmentScanBaseDocument();
                                break;
                            default: break;
                        }
                        if(ProgressBarMainActivity.isShown() == false)
                        {
                            EnableScanner(); // enable only if finish product pull
                        }
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        DisableScanner();

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
    }
}
