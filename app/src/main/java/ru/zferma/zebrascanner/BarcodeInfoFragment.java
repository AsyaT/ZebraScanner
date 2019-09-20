package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BarcodeInfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        View retView = inflater.inflate(R.layout.fragment_barcode_info, container, false);

        retView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.setVisibility(View.GONE);

                return false;
            }
        });

        return retView;
    }

    public void UpdateText(String data)
    {
        TextView textViewBarcodeInfo =  (TextView)  getView().findViewById(R.id.textViewBarcodeInfo);
        textViewBarcodeInfo.setText(data);
    }

}
