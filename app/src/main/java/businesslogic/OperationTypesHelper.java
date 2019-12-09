package businesslogic;

import com.google.gson.Gson;

import java.util.ArrayList;

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

}
