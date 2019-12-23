package serverDatabaseInteraction;

import com.google.gson.Gson;
import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import businesslogic.OperationsTypesAccountingAreaStructureModel;

public class OperationTypesHelper {

    private OperationTypesAndAccountingAreasModel InputModel;
    private OperationsTypesAccountingAreaStructureModel ResultModel;

    public OperationTypesHelper(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {

        String jsonString ="";
        jsonString= PullJsonData(url,userpass);

        if(jsonString == null || jsonString.isEmpty())
        {
            throw new ApplicationException("Сервер не отвечает.");
        }

        this.InputModel = ParseJson(jsonString);

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
                    HashMap<ScanDataCollection.LabelType, Boolean> permissions = new HashMap<>();

                    permissions.put(ScanDataCollection.LabelType.EAN13, ! aa.EAN13_Denied);
                    permissions.put(ScanDataCollection.LabelType.GS1_DATABAR_EXP, ! aa.DataBar_Denied);

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

    protected String PullJsonData(String url, String userpass) throws ExecutionException, InterruptedException {
            return (new WebService()).execute(url,userpass).get();
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
