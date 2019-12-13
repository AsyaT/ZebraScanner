package businesslogic;


import java.util.List;

public class OperationTypesAndAccountingAreasModel {
    public Boolean Error;
    public List<OperationTypeModel> AccountingAreasAndTypes;

    public static class  OperationTypeModel
    {
        public String OperationTypeName;
        public String OperationTypeID;
        public List<AccountingAreaModel> AccountingAreas;

        public String getName()
        {
            return this.OperationTypeName;
        }
        public String getId()
        {
            return this.OperationTypeID;
        }
    }

    public static class AccountingAreaModel{
        public String Name;
        public String GUID;
        public Boolean EAN13_Denied;
        public Boolean DataBar_Denied;
        public Boolean PackageList_Denied;
    }
}
