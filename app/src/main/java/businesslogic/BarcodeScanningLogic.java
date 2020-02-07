package businesslogic;

public class BarcodeScanningLogic
{
    //TODO: move here logic for PackageListScanning

    OperationTypesStructureModel OperationTypesStructureModel = null;
    BaseDocumentStructureModel baseDocumentStructureModel = null;

    public BarcodeScanningLogic(OperationTypesStructureModel operationTypesStructureModel, BaseDocumentStructureModel baseDocument)
    {
        this.OperationTypesStructureModel = operationTypesStructureModel;
        this.baseDocumentStructureModel = baseDocument;
    }

    public Boolean IsBarcodeTypeAllowedToScan(BarcodeTypes type) throws ApplicationException {
        if(this.OperationTypesStructureModel.IsAllowed(type))
        {
            return true;
        }
        else
        {
            throw new ApplicationException("Тип "+type.name()+" запрещен к сканирванию");
        }
    }

    public Boolean IsBarcodeAllowedToScan(ScannerState currentScannerState) throws ApplicationException
    {
        return IsPackageListAllowedToScan(currentScannerState) && IsProductAllowedToScan(currentScannerState);
    }

    protected Boolean IsPackageListAllowedToScan(ScannerState currentScannerState) throws ApplicationException
    {
        if(currentScannerState.equals(ScannerState.PACKAGELIST) && OperationTypesStructureModel.IsPackageListAllowed() == false)
        {
            throw new ApplicationException("На этом участке учета упаковочный лист запрещен к сканированию!");
        }

        return true;
    }

    protected Boolean IsProductAllowedToScan(ScannerState currentScannerState) throws ApplicationException
    {
        if(
                currentScannerState.equals(ScannerState.PRODUCT) &&
                        baseDocumentStructureModel != null &&
                        baseDocumentStructureModel.IsCompileByPackageListOnly() == true)
        {
            throw new ApplicationException("Этот заказ собирается только по упаковочным листам!");
        }
        return true;
    }
}
