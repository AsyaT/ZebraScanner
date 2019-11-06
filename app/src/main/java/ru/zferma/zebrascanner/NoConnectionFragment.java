package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NoConnectionFragment extends FragmentWithText {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_no_connection, container, false);
    }

    @Override
    public void UpdateText(String data) {
        TextView txtInfoNoConnection = (TextView) getView().findViewById(R.id.txtNoConnectionInfo);
        txtInfoNoConnection.setText(data);
    }
}
