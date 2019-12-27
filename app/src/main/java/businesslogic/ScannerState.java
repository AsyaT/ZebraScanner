package businesslogic;

import ScanningCommand.BadgeCommand;
import ScanningCommand.OrderCommand;
import ScanningCommand.ProductCommand;

public enum ScannerState {
    ORDER(OrderCommand.class),
    BADGE(BadgeCommand.class),
    DOCUMENT(ProductCommand.class), //TODO: create class
    PRODUCT(ProductCommand.class);

    private Class CurrentClass;

    ScannerState(Class aClass) {
        CurrentClass = aClass;
    }

    public Class GetClass()
    {
        return this.CurrentClass;
    }
}
