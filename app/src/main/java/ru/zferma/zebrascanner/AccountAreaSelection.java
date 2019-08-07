package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AccountAreaSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);

        String operationGuid = getIntent().getStringExtra("operation_guid");
        String operationName = getIntent().getStringExtra("operation_name");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(operationName);
    }
}
