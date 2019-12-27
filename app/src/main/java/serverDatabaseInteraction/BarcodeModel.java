package serverDatabaseInteraction;

import java.util.List;

public class BarcodeModel {
    public List<ProductListModel> BarCodeList;

    public static class ProductListModel {
        public String Barcode;
        public List<PropertiesListModel> PropertiesList;
    }

    public static class PropertiesListModel{
        public String ProductName;
        public String ProductGUID;
        public String ProductCharactName;
        public String ProductCharactGUID;
        public String Quant;
    }
}
