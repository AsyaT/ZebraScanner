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

public class SettingsActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "spSettings";
    public static final String APP_1C_USERNAME = "Username";
    public static final String APP_1C_PASSWORD = "Password";
    public static final String APP_1C_SERVER = "Server";

    public static final int RESULT_ENABLE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        EditText etServer = (EditText) findViewById(R.id.editTxtServer);
        if(spSettings.contains(APP_1C_SERVER))
        {
            etServer.setText(spSettings.getString(APP_1C_SERVER,""));
        }

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
                e.putString(APP_1C_USERNAME, et1CUsername.getText().toString());
                e.putString(APP_1C_PASSWORD, et1CPassword.getText().toString());
                e.putString(APP_1C_SERVER, etServer.getText().toString());
                e.commit();
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
