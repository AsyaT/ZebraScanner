package businesslogic;


import java.util.List;

public class OperationTypesAndAccountingAreasModel {
    public Boolean Error;
    public List<OperationTypeModel> AccountingAreasAndTypes;

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
