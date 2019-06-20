package ru.zferma.zebrascanner;

public class OrderModel {
    private String Nomenclature;
    private String BarCode;
    private String Coefficient;

    public String getNomenclature() {
        return Nomenclature;
    }
    public String getBarCode(){
        return BarCode;
    }
    public String getCoefficient(){
        return Coefficient;
    }

    public OrderModel(String nomenclature, String barCode, String coefficient){
        this.Nomenclature = nomenclature;
        this.BarCode = barCode;
        this.Coefficient = coefficient;

    }

}