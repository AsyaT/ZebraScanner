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

import java.util.ArrayList;
import java.util.HashMap;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import models.OperationTypesStructureModel;
import models.OperationsTypesAccountingAreaStructureModel;
import presentation.AccountingAreasAdapter;
import presentation.AccountingAreasListViewModel;
import presentation.FragmentHelper;

public class AccountAreaSelectionActivity extends BaseSelectionActivity {

    OperationTypesStructureModel OperationTypesStructureModel;
    AccountingAreasListViewModel SelectedAccountingArea;
    HashMap<String, OperationsTypesAccountingAreaStructureModel.AccountingArea> AccountingAreas;

    ScannerApplication appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);

        appState = ((ScannerApplication)this.getApplication());
        OperationTypesStructureModel = appState.LocationContext;
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(OperationTypesStructureModel.GetOperationName());

        okButton = (Button) findViewById(R.id.OKButtonAA);
        cancelButton = (Button) findViewById(R.id.CancelButtonAA);


        AccountingAreas = GetAccountingAreasList();
        ArrayList<AccountingAreasListViewModel> listItem = PrepareModel(AccountingAreas);

        listView = (ListView)findViewById(R.id.AccountAreaListView);
        final AccountingAreasAdapter adapter = new AccountingAreasAdapter(this, listItem);

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
                    Intent goToMainActivityIntent =
                            new Intent(getBaseContext(), getOperationsEnum(OperationTypesStructureModel.GetOperationName()).getActivityClass());

                    try {
                        appState.LocationContext = new OperationTypesStructureModel(
                                OperationTypesStructureModel.GetOperationName(),
                                OperationTypesStructureModel.GetOperationGuid(),
                                SelectedAccountingArea.AccountingAreaName,
                                SelectedAccountingArea.AccountingAreaGuid,
                                GetScanningPermissions(SelectedAccountingArea.AccountingAreaGuid),
                                GetPackageListAllowance(SelectedAccountingArea.AccountingAreaGuid));
                    }
                    catch (ApplicationException e) {

                        ShowErrorFragment(e.getMessage());
                    }

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

    protected HashMap<BarcodeTypes, Boolean> GetScanningPermissions(String guid) throws ApplicationException {
        if(AccountingAreas.containsKey(guid))
        {
            return AccountingAreas.get(guid).GetScanningPermissions();
        }
        else
        {
            throw new ApplicationException("Такого участка учёта не существует");
        }
    }

    protected Boolean GetPackageListAllowance(String guid) throws ApplicationException {
        if(AccountingAreas.containsKey(guid))
        {
            return AccountingAreas.get(guid).IsPackageListAllowed();
        }
        else
        {
            throw new ApplicationException("Такого участка учёта не существует");
        }
    }

    protected HashMap<String, OperationsTypesAccountingAreaStructureModel.AccountingArea> GetAccountingAreasList()
    {
        HashMap<String, OperationsTypesAccountingAreaStructureModel.AccountingArea> accountAreas = null;

        try {
            accountAreas = appState.operationsTypesAccountingAreaStructureModel.GetAccountingAreas(OperationTypesStructureModel.GetOperationGuid());
        }
        catch (ApplicationException e)
        {
            ShowErrorFragment(e.getMessage());
        }

        return accountAreas;
    }

    protected ArrayList<AccountingAreasListViewModel> PrepareModel(HashMap<String, OperationsTypesAccountingAreaStructureModel.AccountingArea> accountingAreas)
    {
        ArrayList<AccountingAreasListViewModel> listItem = new ArrayList<>();

        for (String accountAreaGuid : accountingAreas.keySet()) {
            AccountingAreasListViewModel result = new AccountingAreasListViewModel();
            result.AccountingAreaGuid = accountAreaGuid;
            result.AccountingAreaName = accountingAreas.get(accountAreaGuid).GetName();
            listItem.add(result);
        }
        if(listItem == null)
        {
            ShowErrorFragment("Соединение с сервером 1С отсутствуем.\n Обратитесь к Системному администратору");
        }

        return listItem;
    }

    protected void ShowErrorFragment(String message)
    {
        Fragment noConnectionFragment = new NoConnectionFragment();
        FragmentHelper fragmentHelper = new FragmentHelper(this);
        fragmentHelper.replaceFragment(noConnectionFragment, R.id.frConnectionInfo);

        new AsyncFragmentInfoUpdate().execute(message);
    }
}
