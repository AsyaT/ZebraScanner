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

    public boolean equals(ListViewPresentationModel obj) {
        return this.ProductGuid.equals(obj.ProductGuid)
                && this.Nomenclature.equals(obj.Nomenclature)
                && this.Characteristic.equals(obj.Characteristic)
                && this.Weight.equals(obj.Weight)
                && this.Coefficient.equals(obj.Coefficient);
    }
}
