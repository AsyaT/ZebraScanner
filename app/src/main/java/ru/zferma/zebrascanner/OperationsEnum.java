package ru.zferma.zebrascanner;

public enum OperationsEnum {
    Rotation("Ротация", MainActivity.class),
    PackageList("Упаковочный лист",PackageListActivity.class),
    Realization("Реализация", RealizationActivity.class),
    Acceptance("Приемка", MainActivity.class),
    Inventory("Инвентаризация", MainActivity.class),
    ReturnTransfer("Передача возвратов", MainActivity.class);

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