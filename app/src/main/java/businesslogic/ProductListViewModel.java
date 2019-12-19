package businesslogic;

public class ProductListViewModel extends Object {
    private String ProductGuid;
    private String StringNumber;
    private String Nomenclature;
    private String Characteristic;
    private String BarCode;
    private Double Weight;
    private Integer Coefficient;

    public String getProductGuid() { return ProductGuid;}
    public String getNomenclature() {
        return Nomenclature;
    }
    public String getCharacteristic()
    {
        return Characteristic;
    }
    public String getStringNumber(){return StringNumber;}

    public String getBarCode(){
        return BarCode;
    }
    public Double getWeight () {return Weight;}
    public Integer getCoefficient(){
        return Coefficient;
    }

    public ProductListViewModel(String productGuid, String stringNumber, String characteristic, String nomenclature, String barCode, Integer coefficient, Double weight){
        this.ProductGuid = productGuid;
        this.StringNumber = stringNumber;
        this.Characteristic = characteristic;
        this.Nomenclature = nomenclature;
        this.BarCode = barCode;
        this.Coefficient = coefficient;
        this.Weight = weight;
    }

}