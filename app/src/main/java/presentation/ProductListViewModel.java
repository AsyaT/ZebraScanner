package presentation;

public class ProductListViewModel extends Object {
    private String ProductGuid;
    private String StringNumber;
    private String Nomenclature;
    private String Characteristic;
    private String SummaryWeight;
    private String Quantity;

    public String getProductGuid() { return ProductGuid;}
    public String getNomenclature() {
        return Nomenclature;
    }
    public String getCharacteristic()
    {
        return Characteristic;
    }
    public String getStringNumber(){return StringNumber;}

    public void setStringNumber(String newStringNumber)
    {
        this.StringNumber = newStringNumber;
    }

    public String getSummaryWeight() {return SummaryWeight;}
    public String getCoefficient(){
        return Quantity;
    }

    public ProductListViewModel(String productGuid, String stringNumber, String characteristic, String nomenclature, String coefficient, String weight){
        this.ProductGuid = productGuid;
        this.StringNumber = stringNumber;
        this.Characteristic = characteristic;
        this.Nomenclature = nomenclature;
        this.Quantity = coefficient;
        this.SummaryWeight = weight;
    }

}