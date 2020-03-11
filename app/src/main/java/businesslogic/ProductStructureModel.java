package businesslogic;

public class ProductStructureModel extends ProductModel implements ObjectForSaving
{
    public ProductStructureModel(String productGuid, String characteristicGuid, Double weight)
    {
        super();
        this.ProductGUID = productGuid;
        this.CharacteristicGUID = characteristicGuid;
        this.Weight = weight;
    }
}