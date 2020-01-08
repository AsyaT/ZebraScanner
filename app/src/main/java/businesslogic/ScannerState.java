package businesslogic;

import ScanningCommand.BadgeCommand;
import ScanningCommand.DocumentBaseCommand;
import ScanningCommand.PackageListCommand;
import ScanningCommand.ProductCommand;

public enum ScannerState {
    DOCUMENTBASE(DocumentBaseCommand.class),
    BADGE(BadgeCommand.class),
    PACKAGELIST(PackageListCommand.class),
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
