package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NoConnectionFragment extends FragmentWithText {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_no_connection, container, false);
        Button btnRefreshConnection = (Button)view.findViewById(R.id.btnRefreshConnection);
        btnRefreshConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OperationSelectionActivity activity = (OperationSelectionActivity) getActivity();
                 activity.RefreshActivity();
                view.setVisibility(View.GONE);
            }
        });

        return view;
    }

    @Override
    public void UpdateText(String data) {
        TextView txtInfoNoConnection = (TextView) getView().findViewById(R.id.txtNoConnectionInfo);
        txtInfoNoConnection.setText(data);
    }
}
