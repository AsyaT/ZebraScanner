package ru.zferma.zebrascanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class OperationSelectionActivity extends AppCompatActivity {

    ListView operationsListView;
    ArrayList<String> listItem;
    OperationTypes AccountingAreaIncomeData;
    Map<String,String> OperationsList;

    String SelectedOperationType = "";
    Button okButton;
    Button cancelButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        operationsListView = (ListView)findViewById(R.id.OperationListView);
        okButton = (Button) findViewById(R.id.OKButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);

        listItem = new ArrayList<>();

        AccountingAreaIncomeData = new OperationTypes();
        OperationTypesAndAccountingAreasModel data= AccountingAreaIncomeData.GetData();

        if(data.Error == false)
        {
            for (OperationTypesAndAccountingAreasModel.OperationTypeModel operationType : data.AccountingAreasAndTypes )
            {
                listItem.add(operationType.OperationType);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

        operationsListView.setAdapter(adapter);

        operationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                  String tap = ((TextView)view).getText().toString();

                  if(SelectedOperationType.isEmpty() || SelectedOperationType != tap)
                  {
                      for (int i = 0; i < operationsListView.getChildCount(); i++) {
                          View listItem = operationsListView.getChildAt(i);
                          listItem.setBackgroundColor(Color.WHITE);
                      }

                    view.setBackgroundColor(Color.YELLOW);
                    SelectedOperationType = tap;
                  }
                  else{
                      view.setBackgroundColor(Color.WHITE);
                      SelectedOperationType = "";
                  }

              }
          });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAreaSelectionIntent = new Intent(getBaseContext(), AccountAreaSelectionActivity.class);
                goToAreaSelectionIntent.putExtra("operation_guid", OperationsList.get(SelectedOperationType));
                goToAreaSelectionIntent.putExtra("operation_name", SelectedOperationType);
                startActivity( goToAreaSelectionIntent );
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < operationsListView.getChildCount(); i++) {
                    View listItem = operationsListView.getChildAt(i);
                    listItem.setBackgroundColor(Color.WHITE);
                }
                SelectedOperationType = "";
            }
        });

    }

}
