package serverDatabaseInteraction;

import com.google.gson.Gson;
import com.symbol.emdk.barcode.ScanDataCollection;

import java.util.HashMap;

import businesslogic.OperationsTypesAccountingAreaStructureModel;

public class OperationTypesHelper {

    private OperationTypesAndAccountingAreasModel InputModel;
    private OperationsTypesAccountingAreaStructureModel ResultModel;

    public OperationTypesHelper(String url, String userpass)
    {
        String jsonString = PullJsonData(url,userpass);
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
            // TODO
        }
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
