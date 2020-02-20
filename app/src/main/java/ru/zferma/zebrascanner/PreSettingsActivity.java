package ru.zferma.zebrascanner;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PreSettingsActivity extends AppCompatActivity {

    public static final int RESULT_ENABLE = 11;
    public static final int INTENT_AUTHENTICATE = 12;

    private DevicePolicyManager devicePolicyManager;


    Button btnSelectOperationType;
    Button btnSettings;

    private Boolean AreSettingsFill()
    {
        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        if(appState.serverConnection == null)
        {
            return false;
        }

        if(appState.serverConnection.GetServerIP().isEmpty())
        {
            return false;
        }
        if(appState.serverConnection.GetUsername().isEmpty())
        {
            return false;
        }
        if(appState.serverConnection.GetPassword().isEmpty())
        {
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_settings);

        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        btnSelectOperationType = (Button) findViewById(R.id.btnSelectOperation);
         btnSettings = (Button) findViewById(R.id.btnSettings);

        btnSelectOperationType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AreSettingsFill() == false)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreSettingsActivity.this);
                    builder.setTitle("Настройки не заданы!").setMessage("Дабавьте детили соединения с сервером");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                    {
                    Intent goToOperationTypeSelection = new Intent(getBaseContext(), OperationSelectionActivity.class);
                    startActivity(goToOperationTypeSelection);
                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                    if (km.isKeyguardSecure()) {
                        Intent authIntent = km.createConfirmDeviceCredentialIntent("Пожалуйста, авторизуйтесь", "Введите пароль администратора");

                        startActivityForResult(authIntent, INTENT_AUTHENTICATE);
                    }
                    else
                    {
                        StartSettingsActivity();
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == INTENT_AUTHENTICATE)
        {
            if (resultCode == RESULT_OK)
            {
                StartSettingsActivity();
            }
        }
    }

    protected void StartSettingsActivity()
    {
        Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        startActivityForResult(settingsActivityIntent, RESULT_ENABLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(PreSettingsActivity.this,
                permissions,
                1);
    }
}
