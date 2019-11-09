package ru.zferma.zebrascanner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OperationSelectionActivity extends BaseSelectionActivity{

    OperationTypes AccountingAreaIncomeData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        listView = (ListView)findViewById(R.id.OperationListView);
        okButton = (Button) findViewById(R.id.OKButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);

        listItem = new ArrayList<>();

        AccountingAreaIncomeData = new OperationTypes(GetConnectionUrl(), GetUserPass() );
        OperationTypesAndAccountingAreasModel data= AccountingAreaIncomeData.GetData();

        if(data == null )
        {
            Fragment noConnectionFragment = new NoConnectionFragment();
            replaceFragment(noConnectionFragment);

            new AsyncFragmentInfoUpdate().execute("Соединение с сервером 1С отсутствует.\n Обратитесь к Системному администратору");
        }
        else if(data.Error == true)
        {
            Fragment noConnectionFragment = new NoConnectionFragment();
            replaceFragment(noConnectionFragment);

            new AsyncFragmentInfoUpdate().execute("Сервер ответил с ошибкой.\n Обратитесь к Системному администратору");
        }
        else if(data.Error == false)
        {
            for (OperationTypesAndAccountingAreasModel.OperationTypeModel operationType : data.AccountingAreasAndTypes) {
                listItem.add(operationType.OperationType);
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(ClickAction);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SelectedType.isEmpty() == false) {
                        if (AccountingAreaIncomeData.HasSeveralAccountingAreas(SelectedType)) {
                            Intent goToAreaSelectionIntent = new Intent(getBaseContext(), AccountAreaSelectionActivity.class);
                            goToAreaSelectionIntent.putExtra("operation_name", SelectedType);
                            startActivity(goToAreaSelectionIntent);
                        } else {
                            Intent goToMainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                            goToMainActivityIntent.putExtra("operation_name", SelectedType);
                            startActivity(goToMainActivityIntent);
                        }
                    }
                }
            });

            cancelButton.setOnClickListener(clickListener);

        }

    }

    public void RefreshActivity()
    {
        startActivity(new Intent(OperationSelectionActivity.this,OperationSelectionActivity.class)) ;
        finish();
    }
}
