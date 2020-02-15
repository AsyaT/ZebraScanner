package ScanningCommand;

import businesslogic.ObjectForSaving;

public abstract class ResponseFormat
{
    protected void SuccessSaveData(Boolean isFragmentShowed, ObjectForSaving product)
    {
        if(isFragmentShowed)
        {
            ShowInfoForFragment(product);
        }
        else
        {
            SaveInfoForProductList(product);
        }
    }

    protected abstract void SaveInfoForProductList(ObjectForSaving product);
    protected abstract void ShowInfoForFragment(ObjectForSaving product);

}
