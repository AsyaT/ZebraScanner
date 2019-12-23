package ru.zferma.zebrascanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import businesslogic.OperationTypesStructureModel;
import businesslogic.OperationsTypesAccountingAreaStructureModel;
import presentation.AccountingAreasAdapter;
import presentation.AccountingAreasListViewModel;
import presentation.FragmentHelper;
import serverDatabaseInteraction.ApplicationException;
import serverDatabaseInteraction.OperationTypesHelper;

public class AccountAreaSelectionActivity extends BaseSelectionActivity {

    OperationTypesStructureModel OperationTypesStructureModel;
    AccountingAreasListViewModel SelectedAccountingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);

        OperationTypesStructureModel = (OperationTypesStructureModel) getIntent().getSerializableExtra("location_context");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(OperationTypesStructureModel.GetOperationName());

        okButton = (Button) findViewById(R.id.OKButtonAA);
        cancelButton = (Button) findViewById(R.id.CancelButtonAA);

        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        OperationTypesHelper operationTypesHelper = null;
        try {
            operationTypesHelper = new OperationTypesHelper(
                    appState.serverConnection.GetOperationTypesURL(),
                    appState.serverConnection.GetUsernameAndPassword());
        }
        catch (ApplicationException applicationException)
        {
            ShowFragmentNoConnection();
            new AsyncFragmentInfoUpdate().execute(applicationException.getMessage());
        } catch (ExecutionException e)
        {
            ShowFragmentNoConnection();
            new AsyncFragmentInfoUpdate().execute(e.getMessage());
        }
        catch (InterruptedException e)
        {
            ShowFragmentNoConnection();
            new AsyncFragmentInfoUpdate().execute(e.getMessage());
        }

        OperationsTypesAccountingAreaStructureModel data = operationTypesHelper.GetData();

        ArrayList<AccountingAreasListViewModel> listItem = new ArrayList<>();

        HashMap<String,OperationsTypesAccountingAreaStructureModel.AccountingArea> accountAreas = data.GetAccountingAreas(OperationTypesStructureModel.GetOperationGuid());

         for(String accountAreaGuid: accountAreas.keySet() )
         {
             AccountingAreasListViewModel result = new AccountingAreasListViewModel();
             result.AccountingAreaGuid = accountAreaGuid;
             result.AccountingAreaName = accountAreas.get(accountAreaGuid).GetName();
             listItem.add(result);
         }

        if(listItem == null)
        {
            Fragment noConnectionFragment = new NoConnectionFragment();
            FragmentHelper fragmentHelper = new FragmentHelper(this);
            fragmentHelper.replaceFragment(noConnectionFragment, R.id.frConnectionInfo);

            new AsyncFragmentInfoUpdate().execute("Соединение с сервером 1С отсутствуем.\n Обратитесь к Системному администратору");
        }

        listView = (ListView)findViewById(R.id.AccountAreaListView);
        final AccountingAreasAdapter adapter = new AccountingAreasAdapter(this, listItem); // WHAT Is IT "simple_list_item_1" ???

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                AccountingAreasListViewModel tap = (AccountingAreasListViewModel) adapter.getItem(position);

                if(SelectedAccountingArea == null || SelectedAccountingArea != tap)
                {
                    for (int i = 0; i < listView.getChildCount(); i++) {
                        View listItem = listView.getChildAt(i);
                        listItem.setBackgroundColor(Color.WHITE);
                    }

                    view.setBackgroundColor(Color.YELLOW);
                    SelectedAccountingArea = tap;
                }
                else{
                    view.setBackgroundColor(Color.WHITE);
                    SelectedAccountingArea = null;
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectedAccountingArea != null)
                {
                    Intent goToMainActivityIntent = new Intent(getBaseContext(), getOperationsEnum(OperationTypesStructureModel.GetOperationName()).getActivityClass());

                    OperationTypesStructureModel operationTypesStructureModel = new OperationTypesStructureModel(
                            OperationTypesStructureModel.GetOperationName(),
                            OperationTypesStructureModel.GetOperationGuid(),
                            SelectedAccountingArea.AccountingAreaName,
                            SelectedAccountingArea.AccountingAreaGuid,
                            accountAreas.get(SelectedAccountingArea.AccountingAreaGuid).GetScanningPermissions(),
                            accountAreas.get(SelectedAccountingArea.AccountingAreaGuid).IsPackageListAllowed());

                    goToMainActivityIntent.putExtra("location_context", (Serializable) operationTypesStructureModel);

                    startActivity(goToMainActivityIntent);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent operationSelection = new Intent(getBaseContext(), OperationSelectionActivity.class);
                startActivity(operationSelection);
            }
        });


    }
}
