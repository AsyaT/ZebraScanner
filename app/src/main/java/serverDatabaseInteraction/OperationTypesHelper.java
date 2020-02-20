package serverDatabaseInteraction;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import businesslogic.ApplicationException;
import businesslogic.BarcodeTypes;
import businesslogic.OperationsTypesAccountingAreaStructureModel;

public class OperationTypesHelper extends  PullDataHelperAbstractFactory
{

    public OperationTypesHelper(String url, String userpass) throws ApplicationException, ExecutionException, InterruptedException {
        super(url, userpass);
    }

    @Override
    protected void SetClassesToCast() {
        this.ClassToCastJson = OperationTypesAndAccountingAreasModel.class;
        this.ClassToCastResultModel = OperationsTypesAccountingAreaStructureModel.class;
    }

    public OperationTypesHelper(OperationTypesAndAccountingAreasModel model) throws ApplicationException {
        super(model);
        this.ClassToCastResultModel = OperationsTypesAccountingAreaStructureModel.class;
    }

    protected OperationsTypesAccountingAreaStructureModel ParseIncomeDataToResultModel(Object inputModel) throws ApplicationException {

        if(((OperationTypesAndAccountingAreasModel) inputModel).Error == false)
        {
            OperationsTypesAccountingAreaStructureModel ResultModel = new OperationsTypesAccountingAreaStructureModel();

            for(OperationTypesAndAccountingAreasModel.OperationTypeModel otm : ((OperationTypesAndAccountingAreasModel) inputModel).AccountingAreasAndTypes)
            {
                OperationsTypesAccountingAreaStructureModel.Operation data =
                        new OperationsTypesAccountingAreaStructureModel.Operation(otm.OperationTypeName);

                for(OperationTypesAndAccountingAreasModel.AccountingAreaModel aa:otm.AccountingAreas)
                {
                    OperationsTypesAccountingAreaStructureModel.AccountingArea accountingArea =
                            new OperationsTypesAccountingAreaStructureModel.AccountingArea();

                    HashMap<BarcodeTypes, Boolean> permissions = new HashMap<>();

                    permissions.put(BarcodeTypes.LocalEAN13, ! aa.EAN13_Denied);
                    permissions.put(BarcodeTypes.LocalGS1_EXP, ! aa.DataBar_Denied);

                    accountingArea.Add(aa.Name, permissions, !aa.PackageList_Denied);
                    data.AddAccountingArea(aa.GUID, accountingArea);
                }

                ResultModel.Add(otm.OperationTypeID,data);
            }
            return ResultModel;
        }
        else{
            throw new ApplicationException("Сервер ответил с ошибкой.");
        }
    }
}
