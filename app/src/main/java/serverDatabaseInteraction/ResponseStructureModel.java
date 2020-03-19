package serverDatabaseInteraction;

import java.util.ArrayList;

public class ResponseStructureModel {

    public ResponseStructureModel()
    {
        this.ProductList = new ArrayList<>();
    }

    public String AccountingAreaGUID;
    public String UserID;
    public String DocumentID;
    public ArrayList<ResponseProductStructureModel> ProductList;

    public static class ResponseProductStructureModel
    {
        public String Product;
        public String Charact;

        public String ManufactureDate;
        public String ExpirationDate;
        public String Manufacturer;
        public String Quantity;
        public String PackageCounter;

        public String PackageList; //Guid of package list
        public String BarCode;
    }

}
