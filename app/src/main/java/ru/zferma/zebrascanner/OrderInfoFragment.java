package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import models.BaseDocumentStructureModel;
import presentation.FragmentHelper;


public class OrderInfoFragment extends Fragment {

    private BaseDocumentStructureModel Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Order = (BaseDocumentStructureModel) getArguments().getSerializable("order");

        View view = inflater.inflate(R.layout.fragment_order_info, container, false);

        TextView txtOrderName = (TextView)  view.findViewById(R.id.txtOrderName);
        txtOrderName.setText(Order.GetOrderName());

        if(Order.IsShowOrderProgressAllowed()) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order", Order);

                    Fragment progressOrderFragment = new ProgressOrderFragment();
                    progressOrderFragment.setArguments(bundle);
                    FragmentHelper fragmentHelper = new FragmentHelper(getActivity());
                    fragmentHelper.replaceFragment(progressOrderFragment, R.id.frBarcodeInfo, "OrderProgress");

                    return false;
                }
            });
        }

        return view;
    }

}
