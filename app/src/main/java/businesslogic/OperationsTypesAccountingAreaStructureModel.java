package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.HashMap;
import java.util.Set;

public class OperationsTypesAccountingAreaStructureModel {

    public OperationsTypesAccountingAreaStructureModel()
    {
        this.Operations = new HashMap<>();
    }

    private HashMap<String,Operation> Operations;

    public static class Operation {

        public Operation()
        {
            this.AccountingAreas = new HashMap<>();
        }

        private String OperationName;
        private HashMap<String,AccountingArea> AccountingAreas;

        public void SetName(String operationName)
        {
            this.OperationName = operationName;
        }

        public void AddAccountingArea(String accountingAreaGuid, AccountingArea accountingAreaDetails)
        {
            this.AccountingAreas.put(accountingAreaGuid, accountingAreaDetails);
        }
    }

    public static class AccountingArea {
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

        public void Add(String accountingAreaString, HashMap<ScanDataCollection.LabelType, Boolean> permissions, Boolean packedListScanningAllowed)
        {
            this.AccountingAreaName = accountingAreaString;
            this.Rules = permissions;
            this.PackedListScanningAllowed = packedListScanningAllowed;
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

    public void Add(String operationGuid, OperationsTypesAccountingAreaStructureModel.Operation operationData)
    {
        this.Operations.put(operationGuid,operationData);
    }


}
