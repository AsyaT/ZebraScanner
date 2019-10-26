package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountAreaSelectionActivity extends AppCompatActivity {

    ListView accountAreasListView;
    ArrayList<String> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);


        String operationName = getIntent().getStringExtra("operation_name");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(operationName);


        OperationTypes AccountingAreaIncomeData = new OperationTypes();
        listItem = AccountingAreaIncomeData.GetAccountingAreas(operationName);

        accountAreasListView = (ListView)findViewById(R.id.AccountAreaListView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

        accountAreasListView.setAdapter(adapter);

    }
}
