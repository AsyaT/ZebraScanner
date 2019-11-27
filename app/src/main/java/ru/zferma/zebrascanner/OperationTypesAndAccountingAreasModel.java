package ru.zferma.zebrascanner;


import java.util.List;

public class OperationTypesAndAccountingAreasModel {
    Boolean Error;
    List<OperationTypeModel> AccountingAreasAndTypes;

    public class  OperationTypeModel
    {
        private String OperationTypeName;
        private String OperationTypeID;
        List<AccountingAreaModel> AccountingAreas;

        public String getName()
        {
            return this.OperationTypeName;
        }
        public String getId()
        {
            return this.OperationTypeID;
        }
    }

    public class AccountingAreaModel{
        String Name;
        String GUID;
    }
}
