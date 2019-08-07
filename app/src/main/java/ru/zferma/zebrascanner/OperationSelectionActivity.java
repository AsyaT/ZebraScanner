package ru.zferma.zebrascanner;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.Map;

public class OperationSelectionActivity extends AppCompatActivity {

    ListView operationsListView;
    ArrayList<String> listItem;
    Map<Integer,AccountingAreaModel> AccountingAreaIncomeData;
    Map<String,String> OperationsList;

    String SelectedOperationType;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        operationsListView = (ListView)findViewById(R.id.OperationListView);
        okButton = (Button) findViewById(R.id.OKButton);

        AccountingAreaIncomeData = new HashMap<Integer,AccountingAreaModel>();
        AccountingAreaIncomeData.put(1, new AccountingAreaModel("Инвентаризация", "gr-bbg-erb-r","Инвентаризация склад 12-1","gv8df-bdfb8dfb-dfb8"));
        AccountingAreaIncomeData.put(2, new AccountingAreaModel("Перемещение", "6r-ne-6jme2-0lbd1r","Межцеховой учет УПК",",qpr7-bvnt0-3y5n-dit"));

        listItem = new ArrayList<>();
        OperationsList = new HashMap<String,String>();
        for (Map.Entry<Integer,AccountingAreaModel> accountingArea:AccountingAreaIncomeData.entrySet())
        {
            listItem.add( accountingArea.getValue().OperationType );
            OperationsList.put(accountingArea.getValue().OperationType, accountingArea.getValue().GuideOperationType);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem);

        operationsListView.setAdapter(adapter);

        operationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                  view.setBackgroundColor(Color.YELLOW);
                  SelectedOperationType = ((TextView)view).getText().toString();

              }
          });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAreaSelectionIntent = new Intent(getBaseContext(),AccountAreaSelection.class);
                goToAreaSelectionIntent.putExtra("operation_guid", OperationsList.get(SelectedOperationType));
                startActivity( goToAreaSelectionIntent );
            }
        });
    }

}
