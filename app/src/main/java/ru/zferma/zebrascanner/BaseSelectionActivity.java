package ru.zferma.zebrascanner;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseSelectionActivity extends AppCompatActivity {

    Button okButton;
    Button cancelButton;
    ArrayList<String> listItem;
    String SelectedType = "";
    ListView listView;

    OperationsEnum getOperationsEnum(String operationName)
    {
        if (operationName.isEmpty() == false)
        {
            OperationsEnum operationsEnum = null;

            for(OperationsEnum item : OperationsEnum.values())
            {
                if(item.getRussianName().equalsIgnoreCase(operationName))
                {
                    operationsEnum = item;
                };
            }
            return operationsEnum;}
        else {
            return null;
        }
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

}
