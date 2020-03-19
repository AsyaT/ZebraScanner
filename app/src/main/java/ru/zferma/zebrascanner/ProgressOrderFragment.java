package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import models.BaseDocumentStructureModel;

public class ProgressOrderFragment extends Fragment {

   BaseDocumentStructureModel Order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_progress_order, container, false);

        Order = (BaseDocumentStructureModel) getArguments().getSerializable("order");

        MainActivity activity = (MainActivity) getActivity();

        TextView txtOrderName = view.findViewById(R.id.txtProgressOrderName);
        txtOrderName.setText(Order.GetOrderName());

        TableLayout tblLayout = view.findViewById(R.id.tblProgrssOrder);

        ScannerApplication appState = ((ScannerApplication) getActivity().getApplication());

        DecimalFormat formatter = new DecimalFormat("#.###", DecimalFormatSymbols.getInstance( Locale.GERMAN ));
        formatter.setRoundingMode( RoundingMode.DOWN );

        for(BaseDocumentStructureModel.ProductOrderStructureModel product : Order.ProductList())
        {
            try {
                Double doneKilos = 0.0;
                Integer doneItems = 0;
/*
                if(activity.dataTableControl.IsProductExists(product.GetProductGuid()))
                {
                    doneKilos = Double.parseDouble(activity.dataTableControl.FindProduct(product.GetProductGuid()).getSummaryWeight());
                    doneItems = Integer.parseInt(activity.dataTableControl.FindProduct(product.GetProductGuid()).getCoefficient());
                }
                
 */

                TextView txtView = new TextView(getActivity());
                txtView.setLayoutParams(new TableRow.LayoutParams(120, TableRow.LayoutParams.WRAP_CONTENT));
                txtView.setTextSize(10);
                txtView.setText(
                        appState.nomenclatureStructureModel.FindProductByGuid(product.GetProductGuid()) +
                                "\n" +
                                appState.characteristicStructureModel.FindCharacteristicByGuid(product.GetCharacteristicGuid()));
                txtView.setBackgroundResource(R.drawable.textviewborder);

                LinearLayout linearLayoutKilos = new LinearLayout(getActivity());
                linearLayoutKilos.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutKilos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));

                TextView txtOrderd = new TextView(getActivity());
                txtOrderd.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtOrderd.setGravity(Gravity.CENTER);
                txtOrderd.setText(formatter.format(product.OrderedInKilos()));
                txtOrderd.setBackgroundResource(R.drawable.textviewborder);

                TextView txtExecuted = new TextView(getActivity());
                txtExecuted.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtExecuted.setGravity(Gravity.CENTER);
                Double finalSum = product.DoneInKilos() + doneKilos;
                txtExecuted.setText(formatter.format(finalSum));
                txtExecuted.setBackgroundResource(R.drawable.textviewborder);

                TextView txtLeft = new TextView(getActivity());
                txtLeft.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtLeft.setGravity(Gravity.CENTER);
                Double finalDiff = product.LeftInKilos() - doneKilos;
                txtLeft.setText(formatter.format(finalDiff));
                txtLeft.setBackgroundResource(R.drawable.textviewborder);

                linearLayoutKilos.addView(txtOrderd);
                linearLayoutKilos.addView(txtExecuted);
                linearLayoutKilos.addView(txtLeft);

                LinearLayout linearLayoutItems = new LinearLayout(getActivity());
                linearLayoutItems.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutItems.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));

                TextView txtOrderedPieces = new TextView(getActivity());
                txtOrderedPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtOrderedPieces.setGravity(Gravity.CENTER);
                txtOrderedPieces.setText(product.OrderedInItems().toString());
                txtOrderedPieces.setBackgroundResource(R.drawable.textviewborder);

                TextView txtExecutedPieces = new TextView(getActivity());
                txtExecutedPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtExecutedPieces.setGravity(Gravity.CENTER);
                Integer finalSumItems = product.DoneInItems() + doneItems;
                txtExecutedPieces.setText(finalSumItems.toString());
                txtExecutedPieces.setBackgroundResource(R.drawable.textviewborder);

                TextView txtLeftPieces = new TextView(getActivity());
                txtLeftPieces.setLayoutParams(new TableRow.LayoutParams(60, TableRow.LayoutParams.MATCH_PARENT));
                txtLeftPieces.setGravity(Gravity.CENTER);
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

        return view;
    }


}
