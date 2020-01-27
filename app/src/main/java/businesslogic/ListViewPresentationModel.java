package businesslogic;

public class ListViewPresentationModel {

    public String ProductGuid;
    public String Nomenclature;
    public String Characteristic;
    public Double Weight;

    public ListViewPresentationModel( String nomenclature, String characteristic, Double weight, String productGuid){
        this.Nomenclature = nomenclature;
        this.Characteristic= characteristic;
        this.Weight = weight;
        this.ProductGuid = productGuid;
    }
}
