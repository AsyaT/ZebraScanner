package businesslogic;

public class ListViewPresentationModel {

    public String ProductGuid;
    public String Nomenclature;
    public String Characteristic;
    public Double Weight;
    public Integer Coefficient;

    public ListViewPresentationModel( String nomenclature, String characteristic, Double weight, String productGuid, Integer coefficient){
        this.Nomenclature = nomenclature;
        this.Characteristic= characteristic;
        this.Weight = weight;
        this.ProductGuid = productGuid;
        this.Coefficient = coefficient;
    }
}
