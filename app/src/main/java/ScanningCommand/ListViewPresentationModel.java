package ScanningCommand;

public class ListViewPresentationModel {

    public String ProductGuid;
    public String UniqueCode;
    public String Nomenclature;
    public String Characteristic;
    public Double Weight;

    public ListViewPresentationModel(String uniqueCode, String nomenclature, String characteristic, Double weight, String productGuid){
        this.UniqueCode = uniqueCode;
        this.Nomenclature = nomenclature;
        this.Characteristic= characteristic;
        this.Weight = weight;
        this.ProductGuid = productGuid;
    }
}
