package ru.zferma.zebrascanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_ACCOUNT_AREA_NAME = "Account area name";
    public static final String APP_ACCOUNT_AREA_INDEX = "Account area index";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";


    String[] AccountAreas = new String[]{"Ишалино", "Свинокомплекс", "Турбослинский бройлер"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Spinner spinner = (Spinner) findViewById(R.id.spnrSelectionAccountArea);
        ArrayAdapter<String> accountAreaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AccountAreas);
        spinner.setAdapter(accountAreaAdapter);

        long selectedSpinner = spSettings.getLong(APP_ACCOUNT_AREA_INDEX, (long) 0.0);
        spinner.setSelection((int) selectedSpinner, true);

        EditText et1CUsername = (EditText) findViewById(R.id.editTxt1CUserName);
        if(spSettings.contains(APP_1C_USERNAME))
        {
            et1CUsername.setText(spSettings.getString(APP_1C_USERNAME,""));
        }

        EditText  et1CPassword =  (EditText)findViewById(R.id.editTxt1CPassword);
        if(spSettings.contains(APP_1C_PASSWORD))
        {
            et1CPassword.setText(spSettings.getString(APP_1C_PASSWORD,""));
        }

        Button btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor e = spSettings.edit();
                e.putString(APP_ACCOUNT_AREA_NAME, spinner.getSelectedItem().toString());
                e.putLong(APP_ACCOUNT_AREA_INDEX, spinner.getSelectedItemId());
                e.putString(APP_1C_USERNAME, et1CUsername.getText().toString());
                e.putString(APP_1C_PASSWORD, et1CPassword.getText().toString());
                e.commit();
                
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
