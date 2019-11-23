package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class PackageListActivity extends MainActivity {

    @Override
    protected void onStart() {
        super.onStart();

        AlertDialog.Builder builder = new AlertDialog.Builder(PackageListActivity.this);
        builder.setTitle(R.string.ChoseAction)
                .setItems(R.array.PackageListActions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0: break;
                            case 1: ShowFragmentScanOrder(); break;
                            default: break;
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
