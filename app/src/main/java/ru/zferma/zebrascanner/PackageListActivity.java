package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class PackageListActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(PackageListActivity.this);
        builder.setTitle(R.string.ChoseAction)
                .setItems(R.array.PackageListActions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0: break;
                            case 1: ShowFragmentScanBaseDocument(); break;
                            default: break;
                        }
                        EnableScanner();
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        DisableScanner();
    }
}
