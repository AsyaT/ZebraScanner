package ru.zferma.zebrascanner;

public class ProductListViewModel {
    private String ProductGuid;
    private String Nomenclature;
    private String BarCode;
    private String Weight;
    private String Coefficient;

    public String getProductGuid() { return ProductGuid;}
    public String getNomenclature() {
        return Nomenclature;
    }
    public String getBarCode(){
        return BarCode;
    }
    public String getWeight () {return Weight;}
    public String getCoefficient(){
        return Coefficient;
    }

    public ProductListViewModel(String productGuid, String nomenclature, String barCode, String coefficient, String weight){
        this.ProductGuid = productGuid;
        this.Nomenclature = nomenclature;
        this.BarCode = barCode;
        this.Coefficient = coefficient;
        this.Weight = weight;
    }
}