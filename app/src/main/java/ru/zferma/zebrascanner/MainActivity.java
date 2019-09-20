package ru.zferma.zebrascanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements EMDKListener, StatusListener, DataListener {

    // Declare a variable to store EMDKManager object
    private EMDKManager emdkManager = null;

    // Declare a variable to store Barcode Manager object
    private BarcodeManager barcodeManager = null;

    // Declare a variable to hold scanner device to scan
    private Scanner scanner = null;

    // Text view to display status of EMDK and Barcode Scanning Operations
    private TextView statusTextView = null;

    MediaPlayer mediaPlayer = null;

    private ListView listView = null;
    private List<OrderModel> dataTable = null;
    CustomListAdapter whatever = null;

    Map<String, IncomeCollectionModel> orderCollection;

    ArrayList<Integer> ItemsToDelete= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Reference to UI elements
        statusTextView = (TextView) findViewById(R.id.textViewStatus);


// The EMDKManager object will be created and returned in the callback.
        EMDKResults results = EMDKManager.getEMDKManager(
                getApplicationContext(), this);
// Check the return status of getEMDKManager and update the status Text
// View accordingly
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            statusTextView.setText("EMDKManager Request Failed");
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.beep01);

        whatever = new CustomListAdapter(this,getModel() );
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(whatever);

        orderCollection = new HashMap<String,IncomeCollectionModel>();
        orderCollection.put("9785389076990",new IncomeCollectionModel("Cat-cat",1, 0.2));
        orderCollection.put("9785431508530",new IncomeCollectionModel("Little car",1, 0.01));
        orderCollection.put("4607097079818",new IncomeCollectionModel("Corn flacks",1, 0.5));
        orderCollection.put("7322540387483",new IncomeCollectionModel("Libress Super",1, 0.01));
        orderCollection.put("7322540581171",new IncomeCollectionModel("Libress Night",1, 0.01));
        orderCollection.put("2203383",new IncomeCollectionModel("Пимидоры весовые на веточках",1, 0.0));
        orderCollection.put("2203233",new IncomeCollectionModel("Кабачки",1, 0.0));
        orderCollection.put("04630037036817",new IncomeCollectionModel("Филе цыпленка",1, 0.0));

        ItemsToDelete = new ArrayList<Integer>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(ItemsToDelete.contains(position))
                    {
                        view.setBackgroundColor(Color.WHITE);
                        ItemsToDelete.remove((Integer) position);
                    }
                else
                    {
                        view.setBackgroundColor(Color.RED);
                        ItemsToDelete.add(position);
                    }
            }
        });

        Button btnDel = (Button) findViewById(R.id.btnRemoveOne);
        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for (Integer x : ItemsToDelete) {
                    dataTable.remove((int) x);
                };
                ItemsToDelete.clear();
                whatever.notifyDataSetChanged();
            }
        });

        Button btnDelAll = (Button) findViewById(R.id.btnRenoveAll);
        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataTable.clear();
                whatever.notifyDataSetChanged();
            }
        });

        Button showRow = findViewById(R.id.ShowRowBtn);
        showRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataBaseCaller().execute();
            }
        });

        Button btnBarcodeInfo = findViewById(R.id.btnBarcodeInfo);
        btnBarcodeInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Go to activity BarcodeInfo

                try{
                    Fragment infoFragemnt = new BarcodeInfoFragment();
                    replaceFragment(infoFragemnt);
                }
                catch (Exception ex){
                    statusTextView.setText(ex.getMessage());
                }
            }
        });
    }

    public void replaceFragment(Fragment destFragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frBarcodeInfo, destFragment);
        fragmentTransaction.commit();
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

            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);

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

        BarcodeStructure barCode = null;

        try {
            // The ScanDataCollection object gives scanning result and the
            // collection of ScanData. So check the data and its status
            if (scanDataCollection != null && scanDataCollection.getResult() == ScannerResults.SUCCESS)
                {
                    ArrayList<ScanDataCollection.ScanData> scanData = scanDataCollection.getScanData();

                    // Iterate through scanned data and prepare the statusStr
                    for (ScanDataCollection.ScanData data : scanData) {
                        // Get the scanned data
                        barCode = new BarcodeStructure( data.getData(), data.getLabelType());
                    }
                }

                statusTextView.setText("Type: "+barCode.getLabelType()+"\nBarcode: "+barCode.getUniqueIdentifier()+"\n"+"Weight: "+barCode.getWeight() );
            }
        catch(Exception ex)
        {}

        IncomeCollectionModel searchResult = orderCollection.get(barCode.getUniqueIdentifier());

        if (searchResult == null)
        {
            try {
                scanner.disable();
            } catch (ScannerException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            new AsyncCaller().execute();

            Toast.makeText(MainActivity.this, "Такой штрихкод не найден в коллекции", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (barCode.getLabelType() == ScanDataCollection.LabelType.EAN13 && barCode.getWeight() != null) {
                new WeightEan13AsyncDataUpdate().execute(searchResult, barCode);
            }
            else if(barCode.getLabelType() == ScanDataCollection.LabelType.EAN13 && barCode.getWeight()== null) {

                new Ean13AsyncDataUpdate().execute(searchResult, barCode);
            }
            else if(barCode.getLabelType() == ScanDataCollection.LabelType.GS1_DATABAR_EXP){
                String productionDate = barCode.GetFullBarcode().substring(34, 40);
                String expirationDate = barCode.GetFullBarcode().substring(42,48);
                String serialnumber = barCode.GetFullBarcode().substring(50,55);
                String internalProducer = barCode.GetFullBarcode().substring(57,58);
                String internalEquipment = barCode.GetFullBarcode().substring(58,61);

                SQLiteDBHelper dbHandler = new SQLiteDBHelper(this);
                dbHandler.insertDatabar(
                        barCode.getUniqueIdentifier(),
                        barCode.getWeight().toString(),
                        barCode.getLotNumber(),
                        productionDate,
                        expirationDate,
                        serialnumber,
                        internalProducer,
                        internalEquipment );

                new DatabarAsyncDataUpdate().execute(searchResult, barCode);
            }
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


    private class DatabarAsyncDataUpdate extends BaseAsyncDataUpdate
    {
        @Override
        protected  Double WeightCalculator()
        {
            return WeightBarCode;
        }
    }

    private class WeightEan13AsyncDataUpdate extends BaseAsyncDataUpdate
    {
        @Override
        protected  Double WeightCalculator()
        {
            return WeightBarCode;
        }
    }

    private class Ean13AsyncDataUpdate extends BaseAsyncDataUpdate
    {
        @Override
        protected  Double WeightCalculator()
        {
            return CollectionSearchResult.Weight;
        }
    }

    // AsyncTask that configures the scanned data on background
// thread and updated the result on UI thread with scanned data and type of
// label
    private abstract class BaseAsyncDataUpdate extends AsyncTask<Object, Void, Void> {

        IncomeCollectionModel CollectionSearchResult = null;
        String UniqueCode ="";
        Double WeightBarCode;
        String Nomenclature;
        Integer Quantity;

        @Override
        protected Void doInBackground(Object... params) {

            try {
                scanner.read();
            } catch (ScannerException e) {
                e.printStackTrace();
            }

            this.CollectionSearchResult = (IncomeCollectionModel) params[0];
            this.UniqueCode = ((BarcodeStructure) params[1]).getUniqueIdentifier();
            this.WeightBarCode = ((BarcodeStructure) params[1]).getWeight();
            this.Nomenclature = CollectionSearchResult.Nomenklature;
            this.Quantity = CollectionSearchResult.Coefficient;

            return null;
        }

        protected abstract Double WeightCalculator();

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Void aVoid)
        {
            Double currentWeight = WeightCalculator();

            OrderModel existingTableModel =  dataTable.stream().filter(x-> UniqueCode.equals(x.getBarCode())).findAny().orElse(null);

            if(existingTableModel == null)
            {
                CreateNewLineInListView(Nomenclature, UniqueCode, Quantity.toString(), currentWeight.toString());
            }
            else
            {
                dataTable.remove(existingTableModel);
                whatever.notifyDataSetChanged();

                Integer newCoefficient = Integer.parseInt( existingTableModel.getCoefficient()) + Quantity;
                Double newWeight = Double.parseDouble(existingTableModel.getWeight()) + currentWeight;

                CreateNewLineInListView(Nomenclature, UniqueCode, newCoefficient.toString(), newWeight.toString() );
            }
        }

        void CreateNewLineInListView(String nomenclature, String barcode, String coefficient, String weight)
        {
            OrderModel tableModel = new OrderModel(nomenclature, barcode, coefficient, weight );
            dataTable.add(tableModel);
            whatever.notifyDataSetChanged();
        }
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
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

            alertDialog.setTitle("Неверный штрих-код!");
            alertDialog.setMessage("Этот штрих-код не найден в коллекции");
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
            super.onPostExecute(aVoid);
        }
    }

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
}