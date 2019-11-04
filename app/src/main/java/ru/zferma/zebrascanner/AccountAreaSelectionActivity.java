package ru.zferma.zebrascanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AccountAreaSelectionActivity extends BaseSelectionActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);


        String operationName = getIntent().getStringExtra("operation_name");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(operationName);

        okButton = (Button) findViewById(R.id.OKButtonAA);
        cancelButton = (Button) findViewById(R.id.CancelButtonAA);

        OperationTypes AccountingAreaIncomeData = new OperationTypes(GetConnectionUrl(), GetUserPass());
        listItem = AccountingAreaIncomeData.GetAccountingAreas(operationName);

        listView = (ListView)findViewById(R.id.AccountAreaListView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(ClickAction);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectedType.isEmpty() == false)
                {
                    Intent goToMainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                    goToMainActivityIntent.putExtra("accounting_area_name", SelectedType);
                    startActivity(goToMainActivityIntent);
                }
            }
        });

        cancelButton.setOnClickListener(clickListener);


    }
}
