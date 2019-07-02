package ru.zferma.zebrascanner;

public class IncomeCollectionModel {
    public String Nomenklature ;

    public Integer Coefficient;

    public Double Weight;

    public IncomeCollectionModel(String nomenklature, Integer coefficient, Double weight){
        this.Nomenklature = nomenklature;
        this.Coefficient = coefficient;
        this.Weight = weight;
    }
}
