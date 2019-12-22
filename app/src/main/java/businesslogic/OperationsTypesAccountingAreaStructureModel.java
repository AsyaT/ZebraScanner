package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.HashMap;
import java.util.Set;

public class OperationsTypesAccountingAreaStructureModel {
    private HashMap<String,Operation > Operations;

    public class Operation {
        private String OperationName;
        private HashMap<String,AccountingArea> AccountingAreas;
    }

    public class AccountingArea {
        private String AccountingAreaName;
        private HashMap<ScanDataCollection.LabelType, Boolean> Rules = new HashMap<>();
        private Boolean PackedListScanningAllowed;

        public String GetName()
        {
            return AccountingAreaName;
        }

        public  HashMap<ScanDataCollection.LabelType, Boolean> GetScanningPermissions()
        {
            return Rules;
        }

        public Boolean IsPackageListAllowed()
        {
            return PackedListScanningAllowed;
        }
    }

    public Set<String> GetOperationKeys()
    {
        return  this.Operations.keySet();
    }

    public String GetOperationName(String operationGuid)
    {
        return this.Operations.get(operationGuid).OperationName;
    }

    public Boolean HasSeveralAccountingAreas(String operationGuid)
    {
       return this.Operations.get(operationGuid).AccountingAreas.size() > 1;
    }


    public HashMap<String,AccountingArea> GetAccountingAreas(String operationGuid)
    {
        return this.Operations.get(operationGuid).AccountingAreas;
    }




}
