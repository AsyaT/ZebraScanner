package businesslogic;

public class ScannerStateHelper {

    private ScannerState CurrentState ;

    public ScannerStateHelper()
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
