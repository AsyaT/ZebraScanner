package ru.zferma.zebrascanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import businesslogic.OperationTypesStructureModel;
import businesslogic.OperationsTypesAccountingAreaStructureModel;
import presentation.FragmentHelper;
import presentation.OperationTypesListViewModel;
import presentation.OperationsListAdapter;
import serverDatabaseInteraction.OperationTypesHelper;

public class OperationSelectionActivity extends BaseSelectionActivity{

    OperationTypesListViewModel SelectedOperation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_selection);

        listView = (ListView)findViewById(R.id.OperationListView);
        okButton = (Button) findViewById(R.id.OKButton);
        cancelButton = (Button) findViewById(R.id.CancelButton);

        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        OperationTypesHelper operationTypesHelper = new OperationTypesHelper(
                appState.serverConnection.GetOperationTypesURL(),
                appState.serverConnection.GetUsernameAndPassword());

        OperationsTypesAccountingAreaStructureModel data = operationTypesHelper.GetData();

        if(data == null )
        {
            ShowFragmentNoConnection();

            new AsyncFragmentInfoUpdate().execute("Соединение с сервером 1С отсутствует.\n Обратитесь к Системному администратору");
        }
        else
        {
            List<OperationTypesListViewModel> listItem = new ArrayList<>();

            for (String operationTypeId : data.GetOperationKeys()) {
                OperationTypesListViewModel model = new OperationTypesListViewModel();
                model.OperationGuid = operationTypeId;
                model.OperationName = data.GetOperationName(operationTypeId);
                listItem.add(model);
            }

            final OperationsListAdapter adapter = new OperationsListAdapter(this, listItem);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    OperationTypesListViewModel tap = (OperationTypesListViewModel) adapter.getItem(position);

                    if(SelectedOperation == null || SelectedOperation != tap)
                    {
                        for (int i = 0; i < listView.getChildCount(); i++) {
                            View listItem = listView.getChildAt(i);
                            listItem.setBackgroundColor(Color.WHITE);
                        }

                        view.setBackgroundColor(Color.YELLOW);
                        SelectedOperation = tap;
                    }
                    else{
                        view.setBackgroundColor(Color.WHITE);
                        SelectedOperation = null;
                    }
                }
            });

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SelectedOperation != null)
                    {
                        Class NextActivityClass ;
                        OperationTypesStructureModel operationTypesStructureModel;
                        if (data.HasSeveralAccountingAreas(SelectedOperation.OperationGuid))
                        {
                            NextActivityClass =  AccountAreaSelectionActivity.class;
                            operationTypesStructureModel = new OperationTypesStructureModel(
                                    SelectedOperation.OperationName,
                                    SelectedOperation.OperationGuid,
                                    null,
                                    null,
                                    null,
                                    null);
                        }
                        else
                        {
                            OperationsTypesAccountingAreaStructureModel.AccountingArea accountingArea = data.GetAccountingAreas(SelectedOperation.OperationGuid).get(0);
                            String accountingAreaGuid = (String) data.GetAccountingAreas(SelectedOperation.OperationGuid).keySet().toArray()[0];

                            NextActivityClass =  getOperationsEnum(SelectedOperation.OperationName).getActivityClass();
                            operationTypesStructureModel = new OperationTypesStructureModel(
                                    SelectedOperation.OperationName,
                                    SelectedOperation.OperationGuid,
                                    accountingArea.GetName(),
                                    accountingAreaGuid,
                                    accountingArea.GetScanningPermissions(),
                                    accountingArea.IsPackageListAllowed());
                        }

                        Intent nextActivityIntent = new Intent(getBaseContext(), NextActivityClass);

                        nextActivityIntent.putExtra("location_context", (Serializable) operationTypesStructureModel);

                        startActivity(nextActivityIntent);

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
