package ru.zferma.zebrascanner;

public class ListViewPresentationModel {

    public String ProductGuid;
    public String UniqueCode;
    public String Nomenclature;
    public Double Weight;

    public ListViewPresentationModel(String uniqueCode, String nomenclature, Double weight, String productGuid){
        this.UniqueCode = uniqueCode;
        this.Nomenclature = nomenclature;
        this.Weight = weight;
        this.ProductGuid = productGuid;
    }
}
