package presentation;

public class ProductListViewModel extends Object {
    private String ProductGuid;
    private String CharacteristicGuid;
    private Integer StringNumber;
    private String Nomenclature;
    private String Characteristic;
    private Double SummaryWeight;
    private Integer Quantity;

    public String getProductGuid() { return ProductGuid;}
    public String getCharacteristicGuid() { return CharacteristicGuid;}
    public String getNomenclature() {
        return Nomenclature;
    }
    public String getCharacteristic()
    {
        return Characteristic;
    }
    public Integer getStringNumber(){return StringNumber;}
    public Double getSummaryWeight() {return SummaryWeight;}
    public Integer getQuantity(){
        return Quantity;
    }

    public ProductListViewModel(String productGuid, String characteristicGuid, Integer stringNumber,  String nomenclature, String characteristic, Double weight, Integer coefficient){
        this.ProductGuid = productGuid;
        this.CharacteristicGuid = characteristicGuid;
        this.StringNumber = stringNumber;
        this.Characteristic = characteristic;
        this.Nomenclature = nomenclature;
        this.Quantity = coefficient;
        this.SummaryWeight = weight;
    }
}