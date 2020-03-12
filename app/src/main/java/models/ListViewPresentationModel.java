package models;

public class ListViewPresentationModel {

    public String ProductGuid;
    public String Nomenclature;
    public String Characteristic;
    public Double Weight;
    public Integer Quantity;

    public ListViewPresentationModel( String nomenclature, String characteristic, Double weight, String productGuid, Integer quantity){
        this.Nomenclature = nomenclature;
        this.Characteristic= characteristic;
        this.Weight = weight;
        this.ProductGuid = productGuid;
        this.Quantity = quantity;
    }

    @Override
    public boolean equals(Object input)
    {
        ListViewPresentationModel obj = (ListViewPresentationModel) input;
        return this.ProductGuid.equals(obj.ProductGuid)
                && this.Nomenclature.equals(obj.Nomenclature)
                && this.Characteristic.equals(obj.Characteristic)
                && this.Weight.equals(obj.Weight)
                && this.Quantity.equals(obj.Quantity);
    }
}
