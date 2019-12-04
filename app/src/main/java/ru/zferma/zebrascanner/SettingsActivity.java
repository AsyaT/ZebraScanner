package ru.zferma.zebrascanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import businesslogic.ServerConnection;

import static ru.zferma.zebrascanner.ScannerApplication.APP_1C_PASSWORD;
import static ru.zferma.zebrascanner.ScannerApplication.APP_1C_SERVER;
import static ru.zferma.zebrascanner.ScannerApplication.APP_1C_USERNAME;
import static ru.zferma.zebrascanner.ScannerApplication.APP_PREFERENCES;

public class SettingsActivity extends AppCompatActivity {

    public static final int RESULT_ENABLE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ScannerApplication appState = ((ScannerApplication)this.getApplication());
        EditText etServer = (EditText) findViewById(R.id.editTxtServer);
        EditText et1CUsername = (EditText) findViewById(R.id.editTxt1CUserName);
        EditText  et1CPassword =  (EditText)findViewById(R.id.editTxt1CPassword);

        if(appState.serverConnection != null)
        {
            etServer.setText(appState.serverConnection.GetServerIP());

            et1CUsername.setText(appState.serverConnection.GetUsername());

            et1CPassword.setText(appState.serverConnection.GetPassword());
        }

        Button btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverIP = etServer.getText().toString();
                String username = et1CUsername.getText().toString();
                String password = et1CPassword.getText().toString();

                SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor e = spSettings.edit();
                e.putString(APP_1C_USERNAME, et1CUsername.getText().toString());
                e.putString(APP_1C_PASSWORD, et1CPassword.getText().toString());
                e.putString(APP_1C_SERVER, etServer.getText().toString());
                e.commit();

                appState.serverConnection = new ServerConnection(serverIP,username,password);
                Toast.makeText(SettingsActivity.this, "Настройки сохранены", Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case RESULT_ENABLE :
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(SettingsActivity.this, "You have enabled the Admin Device features", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Problem to enable the Admin Device features. Result code "+ resultCode, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
