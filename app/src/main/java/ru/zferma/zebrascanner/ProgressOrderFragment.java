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

import businesslogic.OrderStructureModel;

public class ProgressOrderFragment extends Fragment {

   OrderStructureModel Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_progress_order, container, false);

        Order = (OrderStructureModel) getArguments().getSerializable("order");

        MainActivity activity = (MainActivity) getActivity();

        TextView txtOrderName = view.findViewById(R.id.txtProgressOrderName);
        txtOrderName.setText(Order.GetOrderName());

        TableLayout tblLayout = view.findViewById(R.id.tblProgrssOrder);

        ScannerApplication appState = ((ScannerApplication) getActivity().getApplication());

        for(OrderStructureModel.ProductOrderStructureModel product : Order.ProductList())
        {
            try {
                Double doneKilos = 0.0;
                Integer doneItems = 0;

                //TODO : set how many scanned to doneKilos and doneItems . Use FullDataTableControl to keep all scnned

                TextView txtView = new TextView(getActivity());
                txtView.setLayoutParams(new TableRow.LayoutParams(120, TableRow.LayoutParams.WRAP_CONTENT));
                txtView.setText(
                        appState.productStructureModel.FindProductByGuid(product.GetProductGuid()) +
                                "\n" +
                                appState.productStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGuid()));
                txtView.setBackgroundResource(R.drawable.textviewborder);

                LinearLayout linearLayoutKilos = new LinearLayout(getActivity());
                linearLayoutKilos.setOrientation(LinearLayout.HORIZONTAL);

                TextView txtOrderd = new TextView(getActivity());
                txtOrderd.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtOrderd.setText(product.OrderedInKilos().toString());
                txtOrderd.setBackgroundResource(R.drawable.textviewborder);

                TextView txtExecuted = new TextView(getActivity());
                txtExecuted.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                Double finalSum = product.DoneInKilos() + doneKilos;
                txtExecuted.setText(finalSum.toString());
                txtExecuted.setBackgroundResource(R.drawable.textviewborder);

                TextView txtLeft = new TextView(getActivity());
                txtLeft.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                Double finalDiff = product.LeftInKilos() - doneKilos;
                txtLeft.setText(finalDiff.toString());
                txtLeft.setBackgroundResource(R.drawable.textviewborder);

                linearLayoutKilos.addView(txtOrderd);
                linearLayoutKilos.addView(txtExecuted);
                linearLayoutKilos.addView(txtLeft);

                LinearLayout linearLayoutItems = new LinearLayout(getActivity());
                linearLayoutItems.setOrientation(LinearLayout.HORIZONTAL);

                TextView txtOrderedPieces = new TextView(getActivity());
                txtOrderedPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtOrderedPieces.setText(product.OrderedInItems().toString());
                txtOrderedPieces.setBackgroundResource(R.drawable.textviewborder);

                TextView txtExecutedPieces = new TextView(getActivity());
                txtExecutedPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                Integer finalSumItems = product.DoneInItems() + doneItems;
                txtExecutedPieces.setText(finalSumItems.toString());
                txtExecutedPieces.setBackgroundResource(R.drawable.textviewborder);

                TextView txtLeftPieces = new TextView(getActivity());
                txtLeftPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                Integer finalDiffItems = product.LeftInItems() - doneItems;
                txtLeftPieces.setText(finalDiffItems.toString());
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
            catch (Exception ex)
            {
                ex.getMessage();
            }
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
