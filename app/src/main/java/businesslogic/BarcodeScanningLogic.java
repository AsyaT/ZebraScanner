package businesslogic;

import com.symbol.emdk.barcode.ScanDataCollection;

public class BarcodeScanningLogic
{
    OperationTypesStructureModel OperationTypesStructureModel = null;

    public BarcodeScanningLogic(OperationTypesStructureModel operationTypesStructureModel)
    {
        this.OperationTypesStructureModel = operationTypesStructureModel;
    }

    public Boolean IsAllowedToScan(ScanDataCollection.LabelType type) throws ApplicationException {
        if(this.OperationTypesStructureModel.IsAllowed(type))
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Тип "+type.name()+" запрещен к сканирванию");
        }
    }
}
