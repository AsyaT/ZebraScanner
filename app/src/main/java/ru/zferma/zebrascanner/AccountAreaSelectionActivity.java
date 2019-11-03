package ru.zferma.zebrascanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_PASSWORD;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_SERVER;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_USERNAME;
import static ru.zferma.zebrascanner.SettingsActivity.APP_PREFERENCES;

public class AccountAreaSelectionActivity extends AppCompatActivity {

    ListView accountAreasListView;
    ArrayList<String> listItem;
    String SelectedAccountingArea="";

    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);


        String operationName = getIntent().getStringExtra("operation_name");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(operationName);

        okButton = (Button) findViewById(R.id.OKButtonAA);
        cancelButton = (Button) findViewById(R.id.CancelButtonAA);

        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        OperationTypes AccountingAreaIncomeData = new OperationTypes(spSettings.getString(APP_1C_SERVER,""),spSettings.getString(APP_1C_USERNAME,""), spSettings.getString(APP_1C_PASSWORD,""));
        listItem = AccountingAreaIncomeData.GetAccountingAreas(operationName);

        accountAreasListView = (ListView)findViewById(R.id.AccountAreaListView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

        accountAreasListView.setAdapter(adapter);

        accountAreasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String tap = ((TextView)view).getText().toString();

                if(SelectedAccountingArea.isEmpty() || SelectedAccountingArea != tap)
                {
                    for (int i = 0; i < accountAreasListView.getChildCount(); i++) {
                        View listItem = accountAreasListView.getChildAt(i);
                        listItem.setBackgroundColor(Color.WHITE);
                    }

                    view.setBackgroundColor(Color.YELLOW);
                    SelectedAccountingArea = tap;
                }
                else{
                    view.setBackgroundColor(Color.WHITE);
                    SelectedAccountingArea = "";
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectedAccountingArea.isEmpty() == false)
                {
                    Intent goToMainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                    goToMainActivityIntent.putExtra("accounting_area_name", SelectedAccountingArea);
                    startActivity(goToMainActivityIntent);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < accountAreasListView.getChildCount(); i++) {
                    View listItem = accountAreasListView.getChildAt(i);
                    listItem.setBackgroundColor(Color.WHITE);
                }
                SelectedAccountingArea = "";
            }
        });


    }
}
