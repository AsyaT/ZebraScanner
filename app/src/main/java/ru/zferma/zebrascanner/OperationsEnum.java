package ru.zferma.zebrascanner;

public enum OperationsEnum {
    Rotation("Ротация", StubActionActivity.class),
    PackageList("Упаковочный лист",PackageListActivity.class),
    Realization("Реализация", RealizationActivity.class),
    Acceptance("Приемка", AcceptanceActivity.class),
    Inventory("Инвентаризация", InventoryActivity.class),
    ReturnTransfer("Передача возвратов", StubActionActivity.class);

    private String RussianName;
    private Class ActivityClass;

    OperationsEnum(String operation, Class activityClass) {
        this.RussianName = operation;
        this.ActivityClass = activityClass;
    }

    public Class getActivityClass()
    {
        return this.ActivityClass;
    }

    public String getRussianName()
    {
        return this.RussianName;
    }
}