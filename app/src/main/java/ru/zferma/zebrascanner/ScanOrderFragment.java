package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScanOrderFragment extends FragmentWithText {


    public ScanOrderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_order, container, false);
    }

    @Override
    public void UpdateText(String orderInfo)
    {
        TextView txtOrderError = getActivity().findViewById(R.id.txtFragmentOrderScanError);
        txtOrderError.setText(orderInfo);
    }
}
