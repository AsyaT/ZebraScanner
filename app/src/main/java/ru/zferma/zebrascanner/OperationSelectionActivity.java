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

import java.io.Serializable;
import java.util.ArrayList;

import businesslogic.LocationContext;
import businesslogic.OperationTypesHelper;
import businesslogic.OperationTypesAndAccountingAreasModel;

public class OperationSelectionActivity extends BaseSelectionActivity{

    OperationTypesHelper AccountingAreaIncomeData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        listView = (ListView)findViewById(R.id.OperationListView);
        okButton = (Button) findViewById(R.id.OKButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);

        listItem = new ArrayList<>();

        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        AccountingAreaIncomeData = new OperationTypesHelper(
                appState.serverConnection.GetOperationTypesURL(),
                appState.serverConnection.GetUsernameAndPassword());

        OperationTypesAndAccountingAreasModel data= AccountingAreaIncomeData.GetData();

        if(data == null )
        {
            ShowFragmentNoConnection();

            new AsyncFragmentInfoUpdate().execute("Соединение с сервером 1С отсутствует.\n Обратитесь к Системному администратору");
        }
        else if(data.Error == true)
        {
            ShowFragmentNoConnection();

            new AsyncFragmentInfoUpdate().execute("Сервер ответил с ошибкой.\n Обратитесь к Системному администратору");
        }
        else if(data.Error == false)
        {
            for (OperationTypesAndAccountingAreasModel.OperationTypeModel operationType : data.AccountingAreasAndTypes) {
                listItem.add(operationType.getName());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(ClickAction);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SelectedType.isEmpty() == false)
                    {
                        Class NextActivityClass ;
                        LocationContext locationContext;
                        if (AccountingAreaIncomeData.HasSeveralAccountingAreas(SelectedType))
                        {
                            NextActivityClass =  AccountAreaSelectionActivity.class;
                            locationContext= new LocationContext(
                                    SelectedType,
                                    null,
                                    null,
                                    null);
                        }
                        else
                        {
                            NextActivityClass =  getOperationsEnum(SelectedType).getActivityClass();
                            locationContext= new LocationContext(
                                    SelectedType,
                                    AccountingAreaIncomeData.GetSingleAccountingArea(SelectedType).Name,
                                    AccountingAreaIncomeData.GetScanningPermissions(SelectedType),
                                    AccountingAreaIncomeData.IsPackageListScanningAllowed(SelectedType));
                        }

                        Intent goToMainActivityIntent = new Intent(getBaseContext(), NextActivityClass);

                        goToMainActivityIntent.putExtra("location_context", (Serializable) locationContext);

                        startActivity(goToMainActivityIntent);

                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent preSettings = new Intent(getBaseContext(),PreSettingsActivity.class);
                    startActivity(preSettings);
                }
            });

        }

    }

    public void RefreshActivity()
    {
        startActivity(new Intent(OperationSelectionActivity.this,OperationSelectionActivity.class)) ;
        finish();
    }

    private void ShowFragmentNoConnection()
    {
        Fragment noConnectionFragment = new NoConnectionFragment();
        FragmentHelper fragmentHelper = new FragmentHelper(this);
        fragmentHelper.replaceFragment(noConnectionFragment,R.id.frConnectionInfo);
    }
}
