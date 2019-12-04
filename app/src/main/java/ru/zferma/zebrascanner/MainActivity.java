package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKManager.EMDKListener;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.Scanner.DataListener;
import com.symbol.emdk.barcode.Scanner.StatusListener;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerResults;
import com.symbol.emdk.barcode.StatusData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import businesslogic.BarcodeExecutor;
import businesslogic.DataTableControl;
import businesslogic.ListViewPresentationModel;
import businesslogic.ProductHelper;
import businesslogic.ScannerState;
import businesslogic.ScannerStateHelper;

public class MainActivity extends AppCompatActivity implements EMDKListener, StatusListener, DataListener {

    // Declare a variable to store EMDKManager object
    private EMDKManager emdkManager = null;

    // Declare a variable to store Barcode Manager object
    private BarcodeManager barcodeManager = null;

    // Declare a variable to hold scanner device to scan
    private Scanner scanner = null;

    public Scanner getScanner()
    {
        return scanner;
    }

    public ProductHelper productHelper = null;

    DataTableControl dataTableControl;
    private ListView listView = null;
    CustomListAdapter customListAdapter = null;

    ScannerStateHelper scannerState = new ScannerStateHelper();

    public Boolean IsBarcodeInfoFragmentShowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


// The EMDKManager object will be created and returned in the callback.
        EMDKResults results = EMDKManager.getEMDKManager(
                getApplicationContext(), this);
// Check the return status of getEMDKManager and update the status Text
// View accordingly

        ScannerApplication appState = ((ScannerApplication)this.getApplication());

        try {
            productHelper = new ProductHelper(appState.serverConnection.GetProductURL(),appState.serverConnection.GetUserPass());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dataTableControl = new DataTableControl();
        customListAdapter = new CustomListAdapter(this, dataTableControl.GetDataControl() );
        listView = (ListView) findViewById(R.id.listView);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.list_view_header,listView,false);
        listView.addHeaderView(header);

        listView.setAdapter(customListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                dataTableControl.ItemClicked(view,position);
            }
        });

        Button btnDel = (Button) findViewById(R.id.btnRemoveOne);
        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dataTableControl.RemoveSelected();
                customListAdapter.notifyDataSetChanged();
            }
        });

        Button btnDelAll = (Button) findViewById(R.id.btnRenoveAll);
        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTableControl.RemoveAll();
                customListAdapter.notifyDataSetChanged();
            }
        });


        Button btnBarcodeInfo = findViewById(R.id.btnBarcodeInfo);
        btnBarcodeInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                try{
                    Fragment barcodeInfoFragment = new BarcodeInfoFragment();
                    FragmentHelper fragmentHelper = new FragmentHelper(MainActivity.this);
                    fragmentHelper.replaceFragment(barcodeInfoFragment);
                    IsBarcodeInfoFragmentShowed = true;
                }
                catch (Exception ex){

                }
            }
        });

        Button btnBackToOperationsList = findViewById(R.id.btnBack);
        btnBackToOperationsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emdkManager != null) {

                    emdkManager.release();
                    emdkManager = null;
                }

                Intent operationSelectionIntent = new Intent(getBaseContext(), OperationSelectionActivity.class);
                startActivity(operationSelectionIntent);
            }
        });
    }

    protected void ShowFragmentScanOrder()
    {
        Fragment scanOrderFragment = new ScanOrderFragment();
        FragmentHelper fragmentHelper = new FragmentHelper(this);
        fragmentHelper.replaceFragment(scanOrderFragment);
        scannerState.Set(ScannerState.ORDER);
    }

    // Method to initialize and enable Scanner and its listeners
    private void initializeScanner() throws ScannerException {
        if (scanner == null) {
            // Get the Barcode Manager object
            barcodeManager = (BarcodeManager) this.emdkManager
                    .getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
            // Get default scanner defined on the device
            scanner = barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
            // Add data and status listeners
            scanner.addDataListener(this);
            scanner.addStatusListener(this);
            // Hard trigger. When this mode is set, the user has to manually
            // press the trigger on the device after issuing the read call.
            scanner.triggerType = Scanner.TriggerType.HARD;
            // Enable the scanner
            scanner.enable();
            // Starts an asynchronous Scan. The method will not turn ON the
            // scanner. It will, however, put the scanner in a state in which
            // the scanner can be turned ON either by pressing a hardware
            // trigger or can be turned ON automatically.
            scanner.read();
        }
    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        this.emdkManager = emdkManager;

        try {
            // Call this method to enable Scanner and its listeners
            initializeScanner();
        } catch (ScannerException e) {
            e.printStackTrace();
        }

// Toast to indicate that the user can now start scanning
        Toast.makeText(MainActivity.this,
                "Нажмите жёлтую кнопку на рукоятке для начала сканирования...",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart()
    {
        super.onRestart();

        try {
            // Call this method to enable Scanner and its listeners
            initializeScanner();
        } catch (ScannerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {

        if (emdkManager != null) {

            emdkManager.release();
            emdkManager = null;
        }

        super.onPause();
    }


    @Override
    public void onClosed() {
// The EMDK closed abruptly. // Clean up the objects created by EMDK
// manager
        if (this.emdkManager != null) {

            this.emdkManager.release();
            this.emdkManager = null;
        }
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {

        try {
            // The ScanDataCollection object gives scanning result and the
            // collection of ScanData. So check the data and its status
            if (scanDataCollection != null && scanDataCollection.getResult() == ScannerResults.SUCCESS)
                {
                    ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();

                    // Iterate through scanned data and prepare the statusStr
                    for (ScanDataCollection.ScanData data : scanData)
                    {

                        BarcodeExecutor executor = new BarcodeExecutor();
                        executor.Execute(scannerState.GetCurrent(), data,this);
                        scannerState.Set(ScannerState.PRODUCT);
                    }
                }
        }
        catch(Exception ex)
        {

            try {
                scanner.disable();
            } catch (ScannerException e) {
                e.printStackTrace();
            }

            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.beep01);
            mediaPlayer.start();
            new MessageDialog().execute(ex.getMessage());

        }
    }

    @Override
    public void onStatus(StatusData statusData) {
// process the scan status event on the background thread using
// AsyncTask and update the UI thread with current scanner state
        new AsyncStatusUpdate().execute(statusData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (emdkManager != null) {

// Clean up the objects created by EMDK manager
            emdkManager.release();
            emdkManager = null;


        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        try {
            if (scanner != null) {
                // releases the scanner hardware resources for other application
                // to use. You must call this as soon as you're done with the
                // scanning.
                scanner.removeDataListener(this);
                scanner.removeStatusListener(this);
                scanner.disable();
                scanner = null;
            }
        } catch (ScannerException e) {
            e.printStackTrace();
        }
    }

    public class AsyncBarcodeInfoUpdate extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String result) {
            FragmentWithText barcodeInfoFragment = (FragmentWithText) getSupportFragmentManager().findFragmentById(R.id.frBarcodeInfo);
            barcodeInfoFragment.UpdateText(result);
        }
    }

    class AsyncStatusUpdate extends AsyncTask<StatusData, Void, String> {

        @Override
        protected String doInBackground(StatusData... params) {
            String statusStr = "";
            // Get the current state of scanner in background
            StatusData statusData = params[0];
            StatusData.ScannerStates state = statusData.getState();
            // Different states of Scanner
            switch (state) {
                // Scanner is IDLE
                case IDLE:
                    statusStr = "Сканер включен и находится в режиме ожидания";
                    try {
                    scanner.read();
                    } catch (ScannerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                // Scanner is SCANNING
                case SCANNING:
                    statusStr = "Сканирование..";
                    break;
                // Scanner is waiting for trigger press
                case WAITING:
                    statusStr = "В ожидании нажатия на жёлтую кнопку на рукоятке..";
                    break;
                // Scanner is not enabled
                case DISABLED:
                    statusStr = "Сканер не включен";
                    break;
                default:
                    break;
            }

            // Return result to populate on UI thread
            return statusStr;
        }

        @Override
        protected void onPostExecute(String result) {
            // Update the status text view on UI thread with current scanner
            // state

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    // AsyncTask that configures the scanned data on background
// thread and updated the result on UI thread with scanned data and type of
// label
    public class BaseAsyncDataUpdate extends AsyncTask<Object, Void, Void> {

        ListViewPresentationModel Model = null;

        public BaseAsyncDataUpdate(ListViewPresentationModel model)
        {
            this.Model = model;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Object... params) {

            try {
                scanner.read();
            } catch (ScannerException e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid)
        {
            dataTableControl.AddOne(Model);
            customListAdapter.notifyDataSetChanged();
        }


    }

    public class MessageDialog extends AsyncTask<String, Void, String>
    {

        AlertDialog.Builder alertDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(MainActivity.this);

        }

        @Override
        protected String doInBackground(String... params)
        {
            return params[0];
        }

        @Override
        protected void onPostExecute(String message) {

            alertDialog.setTitle("Ошибка при сканировании штрих-кода!");
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        scanner.enable();
                        scanner.read();
                    } catch (ScannerException e) {
                        e.printStackTrace();
                    }
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            super.onPostExecute(message);
        }
    }
/*
    private class DataBaseCaller extends AsyncTask<Void, Void, Void>
    {

        AlertDialog.Builder alertDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            alertDialog.setTitle("Info from DB");
            SQLiteDBHelper dbHandler = new SQLiteDBHelper(MainActivity.this);
            alertDialog.setMessage(dbHandler.getData(1));
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            super.onPostExecute(aVoid);
        }
    }

 */
}