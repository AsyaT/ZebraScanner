package ru.zferma.zebrascanner;

public class ScannerStateHelper {

    private  ScannerState CurrentState ;

    ScannerStateHelper()
    {
        CurrentState = ScannerState.PRODUCT;
    }

    public  void Set(ScannerState state)
    {
        CurrentState = state;
    }

    public  ScannerState GetCurrent()
    {
        return CurrentState;
    }
}
