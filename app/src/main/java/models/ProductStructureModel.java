package models;

public class ProductStructureModel extends ProductModel implements ObjectForSaving
{
    public ProductStructureModel(String productGuid, String characteristicGuid, Double weight)
    {
        this.ProductGUID = productGuid;
        this.CharacteristicGUID = characteristicGuid;
        this.Weight = weight;
    }
}