package ru.zferma.zebrascanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import businesslogic.LocationContext;
import businesslogic.OperationTypesHelper;

public class AccountAreaSelectionActivity extends BaseSelectionActivity {

    LocationContext LocationContext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_area_selection);

        LocationContext = (LocationContext) getIntent().getSerializableExtra("location_context");
        TextView operationTypeTextView = (TextView) findViewById(R.id.OperationTypeTextView);
        operationTypeTextView.setText(LocationContext.GetOperationName());

        okButton = (Button) findViewById(R.id.OKButtonAA);
        cancelButton = (Button) findViewById(R.id.CancelButtonAA);

        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        OperationTypesHelper AccountingAreaIncomeData = new OperationTypesHelper(
                appState.serverConnection.GetOperationTypesURL(),
                appState.serverConnection.GetUsernameAndPassword());
        listItem = AccountingAreaIncomeData.GetAccountingAreas(LocationContext.GetOperationName());

        if(listItem == null)
        {
            Fragment noConnectionFragment = new NoConnectionFragment();
            FragmentHelper fragmentHelper = new FragmentHelper(this);
            fragmentHelper.replaceFragment(noConnectionFragment, R.id.frConnectionInfo);

            new AsyncFragmentInfoUpdate().execute("Соединение с сервером 1С отсутствуем.\n Обратитесь к Системному администратору");
        }

        listView = (ListView)findViewById(R.id.AccountAreaListView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItem); // WHAT Is IT "simple_list_item_1" ???

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(ClickAction);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectedType.isEmpty() == false)
                {
                    Intent goToMainActivityIntent = new Intent(getBaseContext(), getOperationsEnum(LocationContext.GetOperationName()).getActivityClass());

                    LocationContext locationContext = new LocationContext(
                            LocationContext.GetOperationName(),
                            SelectedType,
                            AccountingAreaIncomeData.GetScanningPermissions(SelectedType),
                            AccountingAreaIncomeData.IsPackageListScanningAllowed(SelectedType));

                    goToMainActivityIntent.putExtra("location_context", (Serializable) locationContext);

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
