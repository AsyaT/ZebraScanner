package ru.zferma.zebrascanner;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements EMDKListener, StatusListener, DataListener {

    // Declare a variable to store EMDKManager object
    private EMDKManager emdkManager = null;

    // Declare a variable to store Barcode Manager object
    private BarcodeManager barcodeManager = null;

    // Declare a variable to hold scanner device to scan
    private Scanner scanner = null;

    // Text view to display status of EMDK and Barcode Scanning Operations
    private TextView statusTextView = null;

    // Edit Text that is used to display scanned barcode data
    private EditText dataView = null;

    private ListView listView = null;
    private List<OrderModel> dataTable = null;
    CustomListAdapter whatever = null;

    Map<String, IncomeCollectionModel> orderCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Reference to UI elements
        statusTextView = (TextView) findViewById(R.id.textViewStatus);
        dataView = (EditText) findViewById(R.id.editText1);

// The EMDKManager object will be created and returned in the callback.
        EMDKResults results = EMDKManager.getEMDKManager(
                getApplicationContext(), this);
// Check the return status of getEMDKManager and update the status Text
// View accordingly
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            statusTextView.setText("EMDKManager Request Failed");
        }

        whatever = new CustomListAdapter(this,getModel() );
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(whatever);

        orderCollection = new HashMap<String,IncomeCollectionModel>();
        orderCollection.put("9785389076990",new IncomeCollectionModel("Cat-cat",1));
        orderCollection.put("9785431508530",new IncomeCollectionModel("Little car",1));
        orderCollection.put("4607097079818",new IncomeCollectionModel("Corn flacks",1));
        orderCollection.put("7322540387483",new IncomeCollectionModel("Libress Super",1));
        orderCollection.put("7322540581171",new IncomeCollectionModel("Libress Night",1));
    }

    private List<OrderModel> getModel() {
        dataTable = new ArrayList<OrderModel>();

        return dataTable;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
// Use the scanned data, process it on background thread using AsyncTask
// and update the UI thread with the scanned results
        new AsyncDataUpdate().execute(scanDataCollection);
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
            statusTextView.setText(result);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    int dataLength = 0;

    // AsyncTask that configures the scanned data on background
// thread and updated the result on UI thread with scanned data and type of
// label
    private class AsyncDataUpdate extends
            AsyncTask<ScanDataCollection, Void, String> {

        @Override
        protected String doInBackground(ScanDataCollection... params) {

            // Status string that contains both barcode data and type of barcode
            // that is being scanned
            String statusStr = "";

            try {

                // Starts an asynchronous Scan. The method will not turn ON the
                // scanner. It will, however, put the scanner in a state in
                // which
                // the scanner can be turned ON either by pressing a hardware
                // trigger or can be turned ON automatically.
                scanner.read();

                ScanDataCollection scanDataCollection = params[0];

                // The ScanDataCollection object gives scanning result and the
                // collection of ScanData. So check the data and its status
                if (scanDataCollection != null && scanDataCollection.getResult() == ScannerResults.SUCCESS) {

                    ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();

                    // Iterate through scanned data and prepare the statusStr
                    for (ScanDataCollection.ScanData data : scanData) {
                        // Get the scanned data
                        String barcodeData = data.getData();

                        // Concatenate barcode data and label type
                        statusStr = barcodeData;
                    }
                }

            } catch (ScannerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Return result to populate on UI thread
            return statusStr;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String result) {
            // Update the dataView EditText on UI thread with barcode data and
            // its label type
            if (dataLength++ > 50) {
                // Clear the cache after 50 scans
                dataView.getText().clear();
                dataLength = 0;
            }
            dataView.append(result + "\n");

            IncomeCollectionModel searchResult = (IncomeCollectionModel) orderCollection.get(result);
            try
            {
                if(searchResult!=null)
                {
                    OrderModel existingTableModel =  dataTable.stream().filter(x->result.equals(x.getBarCode())).findAny().orElse(null);

                    if(existingTableModel == null)
                    {
                        OrderModel tableModel = new OrderModel(searchResult.Nomenklature, result,searchResult.Coefficient.toString());
                        dataTable.add(tableModel);
                        whatever.notifyDataSetChanged();
                    }
                    else
                    {
                        dataTable.remove(existingTableModel);
                        
                        Integer newCoefficient = Integer.parseInt( existingTableModel.getCoefficient()) + searchResult.Coefficient;
                        OrderModel tableModel = new OrderModel(searchResult.Nomenklature, result, newCoefficient.toString() );
                        dataTable.add(tableModel);
                        whatever.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "Такой штрихкод не найден в коллекции",
                            Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception ex){
                dataView.setText(ex.getMessage());
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}