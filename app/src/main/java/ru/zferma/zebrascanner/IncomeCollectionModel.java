package ru.zferma.zebrascanner;

public class IncomeCollectionModel {

    public String UniqueCode;
    public String Nomenclature;
    public Double Weight;

    public IncomeCollectionModel(String uniqueCode, String nomenclature, Double weight){
        this.UniqueCode = uniqueCode;
        this.Nomenclature = nomenclature;
        this.Weight = weight;
    }
}
