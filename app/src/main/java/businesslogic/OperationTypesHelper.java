package businesslogic;

import com.google.gson.Gson;
import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.ArrayList;
import java.util.HashMap;

public class OperationTypesHelper {

    private OperationTypesAndAccountingAreasModel InputModel;

    public OperationTypesHelper(String url, String userpass)
    {
        String jsonString = PullJsonData(url,userpass);
        this.InputModel = ParseJson(jsonString);
    }

    protected String PullJsonData(String url, String userpass)
    {
        try {
            return (new WebService()).execute(url,userpass).get();
        }
        catch (Exception ex)
        {
            ex.getMessage();
        }
        return null;
    }

    private OperationTypesAndAccountingAreasModel ParseJson(String jsonString)
    {
        Gson g = new Gson();
        return g.fromJson(jsonString, OperationTypesAndAccountingAreasModel.class);
    }

    public OperationTypesAndAccountingAreasModel GetData()
    {
        return InputModel;
    }

    public boolean HasSeveralAccountingAreas(String operationTypeName)
    {
        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes)
        {
            if (otModel.getName() == operationTypeName && otModel.AccountingAreas.size()>1)
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> GetAccountingAreas(String operationTypeName)
    {
        if(InputModel == null)
        {
            return null;
        }
        ArrayList<String> result = new ArrayList<>();
        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes)
        {
            if(((String)otModel.getName()).equalsIgnoreCase((String)operationTypeName))
            {
                for(OperationTypesAndAccountingAreasModel.AccountingAreaModel accountingAreaModel: otModel.AccountingAreas)
                {
                    result.add(accountingAreaModel.Name);
                }
            }
        }
        return result;
    }

    public OperationTypesAndAccountingAreasModel.AccountingAreaModel GetSingleAccountingArea(String operationTypeName)
    {
        if(InputModel == null)
        {
            return null;
        }
        if(GetAccountingAreas(operationTypeName).size() > 1)
        {
            return null;
        }
        else
        {
            for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes)
            {
                if(((String)otModel.getName()).equalsIgnoreCase((String)operationTypeName))
                {
                    for(OperationTypesAndAccountingAreasModel.AccountingAreaModel accountingAreaModel: otModel.AccountingAreas)
                    {
                        return  accountingAreaModel;
                    }
                }
            }
        }

        return null;
    }

    public HashMap<ScanDataCollection.LabelType, Boolean> GetScanningPermissions(String accountingArea)
    {
        HashMap<ScanDataCollection.LabelType, Boolean> permissions = new HashMap<>();

        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes) {
            for (OperationTypesAndAccountingAreasModel.AccountingAreaModel accountingAreasModel : otModel.AccountingAreas)
            {
                if(accountingAreasModel.Name.equals(accountingArea))
                {
                    permissions.put(ScanDataCollection.LabelType.GS1_DATABAR_EXP, accountingAreasModel.DataBar_Denied);
                    permissions.put(ScanDataCollection.LabelType.EAN13, accountingAreasModel.EAN13_Denied);
                }
            }
        }

        return permissions;
    }

    public Boolean IsPackageListScanningAllowed(String accountingArea)
    {
        for(OperationTypesAndAccountingAreasModel.OperationTypeModel otModel: InputModel.AccountingAreasAndTypes) {
            for (OperationTypesAndAccountingAreasModel.AccountingAreaModel accountingAreasModel : otModel.AccountingAreas)
            {
                if(accountingAreasModel.Name.equalsIgnoreCase(accountingArea))
                {
                    return accountingAreasModel.PackageList_Denied;
                }
            }
        }

        return null;
    }
}
