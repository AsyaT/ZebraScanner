package ru.zferma.zebrascanner;

public class ListViewPresentationModel {

    public String UniqueCode;
    public String Nomenclature;
    public Double Weight;

    public ListViewPresentationModel(String uniqueCode, String nomenclature, Double weight){
        this.UniqueCode = uniqueCode;
        this.Nomenclature = nomenclature;
        this.Weight = weight;
    }
}
