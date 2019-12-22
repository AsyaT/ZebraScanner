package serverDatabaseInteraction;

import com.google.gson.Gson;

import businesslogic.OperationsTypesAccountingAreaStructureModel;

public class OperationTypesHelper {

    private OperationTypesAndAccountingAreasModel InputModel;
    private OperationsTypesAccountingAreaStructureModel ResultModel;

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

    public OperationsTypesAccountingAreaStructureModel GetData()
    {
        return ResultModel;
    }
}
