package scanningcommand;

import businesslogic.ObjectForSaving;
import businesslogic.ScanningBarcodeStructureModel;

public abstract class ResponseFormat
{
    protected void SuccessSaveData(Boolean isFragmentShowed, ObjectForSaving product, ScanningBarcodeStructureModel barcode)
    {
        if(isFragmentShowed)
        {
            ShowInfoForFragment(product, barcode);
        }
        else
        {
            SaveInfoForProductList(product);
        }
    }

    protected abstract void SaveInfoForProductList(ObjectForSaving product);
    protected abstract void ShowInfoForFragment(ObjectForSaving product, ScanningBarcodeStructureModel barcode);
}
