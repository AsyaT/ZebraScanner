package businesslogic;

import java.io.Serializable;
import java.util.HashMap;

public class OperationTypesStructureModel implements Serializable {

    protected  HashMap<BarcodeTypes, Boolean> Rules = new HashMap<>();

    protected Boolean PackedListScanning;

    protected String Operation;
    protected String OperationGUID;
    protected String AccountingArea;
    protected String AccountingAreaGUID;

    public OperationTypesStructureModel(
            String operationName,
            String operationGuid,
            String accountingAreaName,
            String accountingAreaGuid,
            HashMap<BarcodeTypes, Boolean> scanningRules,
            Boolean packageListRule )
    {
        this.Operation = operationName;
        this.OperationGUID = operationGuid;
        this.AccountingArea = accountingAreaName;
        this.AccountingAreaGUID = accountingAreaGuid;
        this.Rules = scanningRules;
        this.PackedListScanning = packageListRule;
    }

    public Boolean IsAllowed(BarcodeTypes labelType)
    {
        return Rules.get(labelType);
    }

    public String GetOperationName()
    {
        return this.Operation;
    }

    public String GetOperationGuid() { return this.OperationGUID; }

    public String GetAccountingAreaGUID()
    {
        return this.AccountingAreaGUID;
    }

    public Boolean IsPackageListAllowed() { return  this.PackedListScanning;}
}
