package ru.zferma.zebrascanner;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PreSettingsActivity extends AppCompatActivity {

    public static final int RESULT_ENABLE = 11;
    public static final int INTENT_AUTHENTICATE = 12;

    private DevicePolicyManager devicePolicyManager;


    Button btnSelectOperationType;
    Button btnSettings;

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
                Intent goToOperationTypeSelection = new Intent(getBaseContext(), OperationSelectionActivity.class);
                startActivity(goToOperationTypeSelection);
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
                Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
                startActivityForResult(settingsActivityIntent, RESULT_ENABLE);
            }
        }
    }

}
