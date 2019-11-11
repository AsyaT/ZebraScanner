package ru.zferma.zebrascanner;

import java.util.List;

public class ProductModel {
    public List<ProductListModel> BarCodeList;



    public class ProductListModel {
        public String Barcode;
        public List<PropertiesListModel> PropertiesList;
    }

    public class PropertiesListModel{
        public String ProductName;
        public String ProductGUID;
        public String ProductCharactName;
        public String ProductCharactGUID;
        public String Quant; //TODO: parse to Double
    }
}
