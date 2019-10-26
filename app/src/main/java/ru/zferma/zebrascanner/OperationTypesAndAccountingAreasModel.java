package ru.zferma.zebrascanner;


import java.util.List;

public class OperationTypesAndAccountingAreasModel {
    Boolean Error;
    List<OperationTypeModel> AccountingAreasAndTypes;

    public class  OperationTypeModel
    {
        String OperationType;
        List<AccountingAreaModel> AccountingAreas;

        public String getName()
        {
            return this.OperationType;
        }
    }

    public class AccountingAreaModel{
        String Name;
        String GUID;
    }
}
