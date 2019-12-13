package businesslogic;

import android.os.Parcel;
import android.os.Parcelable;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.io.Serializable;
import java.util.HashMap;

public class LocationContext implements Serializable {

    HashMap<ScanDataCollection.LabelType, Boolean> Rules = new HashMap<>();

    Boolean PackedListScanning;

    String Operation;
    String AccountingArea;

    public LocationContext(String operationName, String accountingAreaName, HashMap<ScanDataCollection.LabelType, Boolean> scanningRules, Boolean packageListRule )
    {
        this.Operation = operationName;
        this.AccountingArea = accountingAreaName;
        this.Rules = scanningRules;
        this.PackedListScanning = packageListRule;
    }

    public Boolean isAllowed(ScanDataCollection.LabelType labelType)
    {
        return Rules.get(labelType);
    }

    public String GetOperationName()
    {
        return this.Operation;
    }

}
