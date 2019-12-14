package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import businesslogic.OrderModel;


public class OrderInfoFragment extends Fragment {

    private OrderModel Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Order = (OrderModel) getArguments().getSerializable("order");

        View view = inflater.inflate(R.layout.fragment_order_info, container, false);

        TextView txtOrderName = (TextView)  view.findViewById(R.id.txtOrderName);
        txtOrderName.setText(Order.OrderName);

        return view;
    }

}
