package ru.zferma.zebrascanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_PASSWORD;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_SERVER;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_USERNAME;
import static ru.zferma.zebrascanner.SettingsActivity.APP_PREFERENCES;

public class OperationSelectionActivity extends AppCompatActivity {

    ListView operationsListView;
    ArrayList<String> listItem;
    OperationTypes AccountingAreaIncomeData;

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

        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String jsonString="";
        String userpass = spSettings.getString(APP_1C_USERNAME,"") + ":" + spSettings.getString(APP_1C_PASSWORD,"");
        String url = "http://"+ spSettings.getString(APP_1C_SERVER,"")+"/erp_troyan/hs/TSD_Feed/AccountingArea/v1/GetList?UserName="+ spSettings.getString(APP_1C_USERNAME,"");
        try {
            jsonString = (new WebService()).execute(url,userpass).get();
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

        AccountingAreaIncomeData = new OperationTypes(jsonString );
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
                if (SelectedOperationType.isEmpty() == false)
                {
                    if (AccountingAreaIncomeData.HasSeveralAccountingAreas(SelectedOperationType)) {
                        Intent goToAreaSelectionIntent = new Intent(getBaseContext(), AccountAreaSelectionActivity.class);
                        goToAreaSelectionIntent.putExtra("operation_name", SelectedOperationType);
                        startActivity(goToAreaSelectionIntent);
                    } else {
                        Intent goToMainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                        goToMainActivityIntent.putExtra("operation_name", SelectedOperationType);
                        startActivity(goToMainActivityIntent);
                    }
                }
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
