package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.symbol.emdk.EMDKManager;

public class PackageListActivity extends MainActivity {

    @Override
    public void onOpened(EMDKManager emdkManager)
    {
        super.onOpened(emdkManager);

        DisableScanner();
        PullProductsInThread();

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
    }
}
