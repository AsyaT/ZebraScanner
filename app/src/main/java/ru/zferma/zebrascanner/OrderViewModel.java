package ru.zferma.zebrascanner;

import java.util.List;

public class OrderViewModel {

    public String Name;
    public List<ProductsListModel> Products;

    public class ProductsListModel
    {
        public String Nomenclature;
        public String Characteristic;
    }
}
