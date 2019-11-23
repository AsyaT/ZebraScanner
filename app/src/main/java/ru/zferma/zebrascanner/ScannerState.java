package ru.zferma.zebrascanner;

public enum ScannerState {
    ORDER(OrderCommand.class),
    BADGE(ProductCommand.class), //TODO: create class
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
