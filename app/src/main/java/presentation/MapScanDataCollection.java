package presentation;

import android.util.Log;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.ScannerResults;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public  class MapScanDataCollection
{
    private ArrayList<ScanDataStub> bufferedScans = new ArrayList<>();
    private static final String TAG = "Barcode Stub";
    private static final String FIELD_FRIENDLY_NAME = "friendlyName";
    private static final String FIELD_SCANNER_STATE = "scannerState";
    private static final String FIELD_RESULT = "result";
    private static final String FIELD_RAW_DATA = "rawData";
    private static final String FIELD_CHARSET_NAME = "charsetName";
    private static final String FIELD_TIMESTAMP = "timeStamp";
    private static final String FIELD_LABEL_TYPE = "labelType";
    private static final String FIELD_SCANNER_INDEX = "scannerIndex";
    private static final String FIELD_MODEL_NUMBER = "modelNumber";
    private static final String FIELD_DEVICE_TYPE = "deviceType";
    private static final String FIELD_CONNECTION_TYPE = "connectionType";
    private static final String FIELD_DEVICE_IDENTIFIER = "deviceIdentifier";
    private static final String FIELD_DECODER_TYPE = "decoderType";
    private static final String FIELD_IS_DEFAULT_SCANNER = "isDefaultScanner";
    private static final String FIELD_IS_CONNECTED = "isConnected";

    public  ScanDataCollection CreateCollection(String input)
    {
        byte[] test_scan_barcode = input.getBytes();

        //2017-02-27 12:58:51.238
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        String test_scan_timestamp = df.format(new Date());

        String test_scan_charsetName = "UTF-8";

        ScanDataCollection.LabelType test_scan_label_type = null;
        if(input.length() == 14)
        {
            test_scan_label_type = ScanDataCollection.LabelType.EAN13;
        }
        else if(input.length() > 50)
        {
            test_scan_label_type = ScanDataCollection.LabelType.GS1_DATABAR_EXP;
        }

        ScanDataStub temp = new ScanDataStub(test_scan_barcode,test_scan_label_type,test_scan_charsetName,test_scan_timestamp);
        bufferedScans.add(temp);

        try {
            return ReportScan(ScannerResults.SUCCESS);
        } catch (InstantiationException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public ScanDataCollection ReportScan(ScannerResults scannerResult) throws InstantiationException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        ScanDataCollection scanDataCollection = null;
        if (bufferedScans.size() == 0)
        {
            Log.e(TAG, "No scans available to report");
            throw new NoSuchFieldException("No scans available to report");
        }
        try {
            Constructor<ScanDataCollection> scanDataCollectionConstructor = ScanDataCollection.class.getDeclaredConstructor(new Class[0]);
            scanDataCollectionConstructor.setAccessible(true);
            scanDataCollection = scanDataCollectionConstructor.newInstance(new Object[0]);
            Field fieldFriendlyName = ScanDataCollection.class.getDeclaredField(FIELD_FRIENDLY_NAME);
            fieldFriendlyName.setAccessible(true);
            fieldFriendlyName.set(scanDataCollection, "Test scanning");
            Field fieldScannerResult = ScanDataCollection.class.getDeclaredField(FIELD_RESULT);
            fieldScannerResult.setAccessible(true);
            fieldScannerResult.set(scanDataCollection, scannerResult);

            ArrayList<ScanDataCollection.ScanData> scannedData = new ArrayList<>();

            for (int i = 0; i < bufferedScans.size(); i++) {
                Constructor[] scanDataConstructors = ScanDataCollection.ScanData.class.getDeclaredConstructors();
                Constructor scanDataConstructor = scanDataConstructors[0];
                scanDataConstructor.setAccessible(true);
                ScanDataCollection.ScanData data = (ScanDataCollection.ScanData) scanDataConstructor.newInstance(scanDataCollection);
                Field fieldScanDataRawData = ScanDataCollection.ScanData.class.getDeclaredField(FIELD_RAW_DATA);
                fieldScanDataRawData.setAccessible(true);
                fieldScanDataRawData.set(data, bufferedScans.get(i).getScanDataRawBytes());
                Field fieldScanDataCharsetName = ScanDataCollection.ScanData.class.getDeclaredField(FIELD_CHARSET_NAME);
                fieldScanDataCharsetName.setAccessible(true);
                fieldScanDataCharsetName.set(data, bufferedScans.get(i).getCharsetName());
                Field fieldScanDataTimestamp = ScanDataCollection.ScanData.class.getDeclaredField(FIELD_TIMESTAMP);
                fieldScanDataTimestamp.setAccessible(true);
                fieldScanDataTimestamp.set(data, bufferedScans.get(i).getTimestamp());
                Field fieldScanDataLabelType = ScanDataCollection.ScanData.class.getDeclaredField(FIELD_LABEL_TYPE);
                fieldScanDataLabelType.setAccessible(true);
                fieldScanDataLabelType.set(data, bufferedScans.get(i).getScanLabelType());
                scannedData.add(data);
            }

            Field fieldScannedData = ScanDataCollection.class.getDeclaredField("scanData");
            fieldScannedData.setAccessible(true);
            fieldScannedData.set(scanDataCollection, scannedData);
        } catch (InstantiationException e) {
            Log.e(TAG, "Problem with reflecting on Zebra EMDK API" + e.getMessage());
            throw e;
        } catch (InvocationTargetException e) {
            Log.e(TAG, "Problem with reflecting on Zebra EMDK API" + e.getMessage());
            throw e;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "Problem with reflecting on Zebra EMDK API" + e.getMessage());
            throw e;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Problem with reflecting on Zebra EMDK API" + e.getMessage());
            throw e;
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "Problem with reflecting on Zebra EMDK API" + e.getMessage());
            throw e;
        }
        bufferedScans.clear();
        return scanDataCollection;
    }

    private class ScanDataStub
    {
        private byte[] scanDataRawBytes;
        private ScanDataCollection.LabelType scanLabelType;
        private String charsetName;
        private String timestamp;
        ScanDataStub(byte[] scanDataRawBytes, ScanDataCollection.LabelType scanLabelType,
                     String charsetName, String timestamp)
        {
            this.scanDataRawBytes = scanDataRawBytes;
            this.scanLabelType = scanLabelType;
            this.charsetName = charsetName;
            this.timestamp = timestamp;
        }
        public byte[] getScanDataRawBytes() {return scanDataRawBytes;}
        public ScanDataCollection.LabelType getScanLabelType() {return scanLabelType;}
        public String getCharsetName() {return charsetName;}
        public String getTimestamp() {return timestamp;}
    }
}
