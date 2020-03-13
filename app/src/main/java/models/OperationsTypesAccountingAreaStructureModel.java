package models;

import java.util.HashMap;
import java.util.Set;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;

public class OperationsTypesAccountingAreaStructureModel {

    private HashMap<String,Operation> Operations;

    public OperationsTypesAccountingAreaStructureModel()
    {
        this.Operations = new HashMap<>();
    }


    public static class Operation {

        private String OperationName;
        private HashMap<String,AccountingArea> AccountingAreas;

        public Operation(String operationName)
        {
            this.AccountingAreas = new HashMap<>();
            this.OperationName = operationName;
        }

        public void AddAccountingArea(String accountingAreaGuid, AccountingArea accountingAreaDetails)
        {
            this.AccountingAreas.put(accountingAreaGuid, accountingAreaDetails);
        }
    }

    public static class AccountingArea {
        private String AccountingAreaName;
        private HashMap<BarcodeTypes, Boolean> Rules = new HashMap<>();
        private Boolean PackedListScanningAllowed;

        public String GetName()
        {
            return AccountingAreaName;
        }

        public  HashMap<BarcodeTypes, Boolean> GetScanningPermissions()
        {
            return Rules;
        }

        public Boolean IsPackageListAllowed()
        {
            return PackedListScanningAllowed;
        }

        public void Add(String accountingAreaString, HashMap<BarcodeTypes, Boolean> permissions, Boolean packedListScanningAllowed)
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

    public String GetOperationName(String operationGuid) throws ApplicationException {
        if(this.Operations.containsKey(operationGuid))
        {
            return this.Operations.get(operationGuid).OperationName;
        }
        else
            {
                throw new ApplicationException("Операции "+operationGuid+" не найдено");
            }
    }

    public Boolean HasSeveralAccountingAreas(String operationGuid) throws ApplicationException {
        if(this.Operations.containsKey(operationGuid))
        {
            return this.Operations.get(operationGuid).AccountingAreas.size() > 1;
        }
        else
        {
            throw new ApplicationException("Операции "+operationGuid+" не найдено");
        }
    }


    public HashMap<String,AccountingArea> GetAccountingAreas(String operationGuid) throws ApplicationException {
        if(this.Operations.containsKey(operationGuid))
        {
            return this.Operations.get(operationGuid).AccountingAreas;
        }
        else
        {
            throw new ApplicationException("Операции "+operationGuid+" не найдено");
        }
    }

    public void Add(String operationGuid, OperationsTypesAccountingAreaStructureModel.Operation operationData)
    {
        this.Operations.put(operationGuid,operationData);
    }


}
