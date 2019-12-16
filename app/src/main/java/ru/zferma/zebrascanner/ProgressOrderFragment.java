package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import businesslogic.OrderModel;

public class ProgressOrderFragment extends Fragment {

   OrderModel Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_progress_order, container, false);

        Order = (OrderModel) getArguments().getSerializable("order");

        TextView txtOrderName = view.findViewById(R.id.txtProgressOrderName);
        txtOrderName.setText(Order.OrderName);

        TableLayout tblLayout = view.findViewById(R.id.tblProgrssOrder);

        for(OrderModel.ProductListModel product : Order.ProductList) {

            TextView txtView = new TextView(getActivity());
            txtView.setLayoutParams(new TableRow.LayoutParams(120,TableRow.LayoutParams.WRAP_CONTENT));
            txtView.setText(product.Product + "\n" + product.Charact);

            TextView txtOrderd = new TextView(getActivity());
            txtOrderd.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.WRAP_CONTENT));
            txtOrderd.setText(product.Ordered+ " | "+product.PiecesOrdered);

            TextView txtExecuted = new TextView(getActivity());
            txtExecuted.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.WRAP_CONTENT));
            txtExecuted.setText(product.Done+ " | "+product.PiecesDone);

            TextView txtLeft = new TextView(getActivity());
            txtLeft.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.WRAP_CONTENT));
            txtLeft.setText(product.Left+ " | "+product.PiecesLeft);

            TableRow tr = new TableRow(getActivity());
            tr.addView(txtView);
            tr.addView(txtOrderd);
            tr.addView(txtExecuted);
            tr.addView(txtLeft);

            tblLayout.addView(tr);
        }

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setVisibility(View.GONE);
                return false;
            }
        });

        return view;
    }


}
