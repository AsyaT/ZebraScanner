package scanningcommand;

import models.ObjectForSaving;
import models.ScanningBarcodeStructureModel;

public abstract class ResponseFormat
{
    protected void SuccessSaveData(Boolean isFragmentShowed, ObjectForSaving product, ScanningBarcodeStructureModel barcode)
    {
        if(isFragmentShowed)
        {
            ShowInfoForFragment(barcode, product);
        }
        else
        {
            SaveInfoForProductList(barcode, product);
        }
    }

    protected abstract void SaveInfoForProductList(ScanningBarcodeStructureModel barcode, ObjectForSaving product);
    protected abstract void ShowInfoForFragment(ScanningBarcodeStructureModel barcode, ObjectForSaving product);
}
