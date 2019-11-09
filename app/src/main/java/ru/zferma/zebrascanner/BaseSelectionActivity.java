package ru.zferma.zebrascanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_PASSWORD;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_SERVER;
import static ru.zferma.zebrascanner.SettingsActivity.APP_1C_USERNAME;
import static ru.zferma.zebrascanner.SettingsActivity.APP_PREFERENCES;

public class BaseSelectionActivity extends AppCompatActivity {

    Button okButton;
    Button cancelButton;
    ArrayList<String> listItem;
    String SelectedType = "";
    ListView listView;


    public void replaceFragment(Fragment destFragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frConnectionInfo, destFragment);
        fragmentTransaction.commit();
    }

    class AsyncFragmentInfoUpdate extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String finalText = params[0];

            return finalText;
        }

        @Override
        protected void onPostExecute(String result) {
            FragmentWithText barcodeInfoFragment = (FragmentWithText) getSupportFragmentManager().findFragmentById(R.id.frConnectionInfo);
            barcodeInfoFragment.UpdateText(result);
        }
    }

    protected String GetConnectionUrl()
    {
        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return "http://"+ spSettings.getString(APP_1C_SERVER,"")+"/erp_troyan/hs/TSD_Feed/AccountingArea/v1/GetList?UserName="+ spSettings.getString(APP_1C_USERNAME,"");
    }

    protected String GetUserPass()
    {
        SharedPreferences spSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        return spSettings.getString(APP_1C_USERNAME,"") + ":" + spSettings.getString(APP_1C_PASSWORD,"");
    }

    AdapterView.OnItemClickListener ClickAction = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            String tap = ((TextView)view).getText().toString();

            if(SelectedType.isEmpty() || SelectedType != tap)
            {
                for (int i = 0; i < listView.getChildCount(); i++) {
                    View listItem = listView.getChildAt(i);
                    listItem.setBackgroundColor(Color.WHITE);
                }

                view.setBackgroundColor(Color.YELLOW);
                SelectedType = tap;
            }
            else{
                view.setBackgroundColor(Color.WHITE);
                SelectedType = "";
            }
        }
    };

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < listView.getChildCount(); i++) {
                View listItem = listView.getChildAt(i);
                listItem.setBackgroundColor(Color.WHITE);
            }
            SelectedType = "";
        }
    };
}
