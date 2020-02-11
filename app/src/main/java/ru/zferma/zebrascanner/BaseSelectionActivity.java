package ru.zferma.zebrascanner;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import presentation.FragmentHelper;

public class BaseSelectionActivity extends AppCompatActivity {

    Button okButton;
    Button cancelButton;
    ListView listView;
    ProgressBar progressBar;

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

    protected void ShowFragmentNoConnection()
    {
        Fragment noConnectionFragment = new NoConnectionFragment();
        FragmentHelper fragmentHelper = new FragmentHelper(this);
        fragmentHelper.replaceFragment(noConnectionFragment,R.id.frConnectionInfo);
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
}
