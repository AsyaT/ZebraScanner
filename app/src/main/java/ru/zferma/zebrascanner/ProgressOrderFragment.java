package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        ScannerApplication appState = ((ScannerApplication) getActivity().getApplication());

        for(OrderModel.ProductListModel product : Order.ProductList) {

            TextView txtView = new TextView(getActivity());
            txtView.setLayoutParams(new TableRow.LayoutParams(120,TableRow.LayoutParams.WRAP_CONTENT));
            txtView.setText(appState.productHelper.FindProductByGuid(product.Product) + "\n" + appState.productHelper.FindCharacteristicByGuid(product.Charact) );
            txtView.setBackgroundResource(R.drawable.textviewborder);

            LinearLayout linearLayoutKilos = new LinearLayout(getActivity());
            linearLayoutKilos.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtOrderd = new TextView(getActivity());
            txtOrderd.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtOrderd.setText(product.Ordered);
            txtOrderd.setBackgroundResource(R.drawable.textviewborder);

            TextView txtExecuted = new TextView(getActivity());
            txtExecuted.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtExecuted.setText(product.Done);
            txtExecuted.setBackgroundResource(R.drawable.textviewborder);

            TextView txtLeft = new TextView(getActivity());
            txtLeft.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtLeft.setText(product.Left);
            txtLeft.setBackgroundResource(R.drawable.textviewborder);

            linearLayoutKilos.addView(txtOrderd);
            linearLayoutKilos.addView(txtExecuted);
            linearLayoutKilos.addView(txtLeft);

            LinearLayout linearLayoutItems = new LinearLayout(getActivity());
            linearLayoutItems.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtOrderedPieces = new TextView(getActivity());
            txtOrderedPieces.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtOrderedPieces.setText(product.PiecesOrdered.toString());
            txtOrderedPieces.setBackgroundResource(R.drawable.textviewborder);

            TextView txtExecutedPieces = new TextView(getActivity());
            txtExecutedPieces.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtExecutedPieces.setText(product.PiecesDone.toString());
            txtExecutedPieces.setBackgroundResource(R.drawable.textviewborder);

            TextView txtLeftPieces = new TextView(getActivity());
            txtLeftPieces.setLayoutParams(new TableRow.LayoutParams(60,TableRow.LayoutParams.MATCH_PARENT));
            txtLeftPieces.setText(product.PiecesLeft.toString());
            txtLeftPieces.setBackgroundResource(R.drawable.textviewborder);

            linearLayoutItems.addView(txtOrderedPieces);
            linearLayoutItems.addView(txtExecutedPieces);
            linearLayoutItems.addView(txtLeftPieces);

            TableRow tr = new TableRow(getActivity());
            tr.setBackgroundResource(R.drawable.textviewborder);

            tr.addView(txtView);
            tr.addView(linearLayoutKilos);
            tr.addView(linearLayoutItems);

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
