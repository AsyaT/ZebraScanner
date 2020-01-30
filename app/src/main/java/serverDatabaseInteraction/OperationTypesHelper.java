package serverDatabaseInteraction;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import businesslogic.OperationsTypesAccountingAreaStructureModel;

public class OperationTypesHelper {

    private OperationTypesAndAccountingAreasModel InputModel;
    private OperationsTypesAccountingAreaStructureModel ResultModel;

    public OperationTypesHelper(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {

        String jsonString = PullJsonData(url,userpass);

        this.InputModel = ParseJson(jsonString);

        if(this.InputModel != null) {
            ParseIncomeData();
        }
    }

    protected void ParseIncomeData() throws ApplicationException {
        if(InputModel.Error == false)
        {
            ResultModel = new OperationsTypesAccountingAreaStructureModel();

            for(OperationTypesAndAccountingAreasModel.OperationTypeModel otm : InputModel.AccountingAreasAndTypes)
            {
                OperationsTypesAccountingAreaStructureModel.Operation data = new OperationsTypesAccountingAreaStructureModel.Operation();
                data.SetName(otm.OperationTypeName);

                for(OperationTypesAndAccountingAreasModel.AccountingAreaModel aa:otm.AccountingAreas)
                {
                    OperationsTypesAccountingAreaStructureModel.AccountingArea accountingArea = new OperationsTypesAccountingAreaStructureModel.AccountingArea();
                    HashMap<BarcodeTypes, Boolean> permissions = new HashMap<>();

                    permissions.put(BarcodeTypes.LocalEAN13, ! aa.EAN13_Denied);
                    permissions.put(BarcodeTypes.LocalGS1_EXP, ! aa.DataBar_Denied);

                    accountingArea.Add(aa.Name, permissions, !aa.PackageList_Denied);
                    data.AddAccountingArea(aa.GUID, accountingArea);
                }

                ResultModel.Add(otm.OperationTypeID,data);
            }
        }
        else{
            throw new ApplicationException("Сервер ответил с ошибкой.");
        }
    }

    protected String PullJsonData(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {

        if(url.isEmpty())
        {
            return null;
        }

        String result = null;
        result =  (new WebService()).execute(url,userpass).get();
        if(result == null || result.isEmpty())
        {
            throw new ApplicationException("Сервер не отвечает.");
        }
        else {
            return result;
        }
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
