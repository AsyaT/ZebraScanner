package ru.zferma.zebrascanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperationSelectionActivity extends AppCompatActivity {

    ListView operationsListView;
    ArrayList<String> listItem;
    Map<Integer,AccountingAreaModel> AccountingAreaIncomeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        operationsListView = (ListView)findViewById(R.id.OperationListView);

        AccountingAreaIncomeData = new HashMap<Integer,AccountingAreaModel>();
        AccountingAreaIncomeData.put(1, new AccountingAreaModel("Инвентаризация", "gr-bbg-erb-r","Инвентаризация склад 12-1","gv8df-bdfb8dfb-dfb8"));
        AccountingAreaIncomeData.put(2, new AccountingAreaModel("Перемещение", "6r-ne-6jme2-0lbd1r","Межцеховой учет УПК",",qpr7-bvnt0-3y5n-dit"));

        listItem = new ArrayList<>();
        for (Map.Entry<Integer,AccountingAreaModel> accountingArea:AccountingAreaIncomeData.entrySet())
        {
            listItem.add( accountingArea.getValue().OperationType );
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem);

        operationsListView.setAdapter(adapter);

    }

}
